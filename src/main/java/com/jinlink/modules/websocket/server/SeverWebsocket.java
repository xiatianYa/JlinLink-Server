package com.jinlink.modules.websocket.server;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.jinlink.common.domain.LoginUser;
import com.jinlink.common.util.udp.GameServerUtil;
import com.jinlink.core.config.redis.service.RedisService;
import com.jinlink.core.holder.GlobalUserHolder;
import com.jinlink.modules.game.entity.vo.SteamServerVo;
import com.jinlink.modules.websocket.entity.JoinServerVo;
import com.jinlink.modules.websocket.entity.MessageVo;
import com.jinlink.modules.websocket.entity.dto.ServerSearchDto;
import jakarta.annotation.Resource;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.SneakyThrows;
import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;


/**
 * @author summer
 */
@ServerEndpoint("/ws/server/{token}")
@Component
public class SeverWebsocket {
    /**concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。*/
    private static final ConcurrentHashMap<Long, SeverWebsocket> webSocketMap = new ConcurrentHashMap<>();
    private static final Logger log = LoggerFactory.getLogger(SeverWebsocket.class);
    private final static ExpiringMap<String, Integer[]> expireCacheMap = ExpiringMap.builder()
            // 设置最大值,添加第11个entry时，会导致第1个立马过期(即使没到过期时间)。默认 Integer.MAX_VALUE
            .maxSize(20)
            // 允许 Map 元素具有各自的到期时间，并允许更改到期时间。
            .variableExpiration()
            // 设置过期时间，如果key不设置过期时间，key永久有效。
            .expiration(3, TimeUnit.SECONDS)
            //设置 Map 的过期策略
            .expirationPolicy(ExpirationPolicy.CREATED)
            .build();
    /**与某个客户端的连接会话，需要通过它来给客户端发送数据*/
    private Session session;
    /**登录用户信息*/
    private LoginUser loginUser;

    /**RedisService*/
    private static RedisService redisService;

    @Resource
    public void setRedisService(RedisService redisService) {
        SeverWebsocket.redisService = redisService;
    }

    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session,@PathParam("token") String token) {
        this.session = session;
        //根据token获取登录用户信息
        Object loginId = StpUtil.getLoginIdByToken(token);
        LoginUser loginUser = GlobalUserHolder.getUserById(loginId);
        if (ObjectUtil.isNull(loginUser)){
            return;
        }
        this.loginUser = loginUser;
        webSocketMap.remove(loginUser.getId());
        webSocketMap.put(loginUser.getId(),this);
        //用户登录
        System.out.println("用户:"+loginUser.getNickName()+"连接成功");
        //给自己发登录成功的消息
        MessageVo messageVo = MessageVo.builder()
                .sendUser(this.loginUser)
                .msg("登录成功")
                .code("200")
                .build();
        this.session.getAsyncRemote().sendText(JSON.toJSONString(messageVo));
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if (ObjectUtil.isNotNull(this.loginUser) && webSocketMap.containsKey(loginUser.getId())) {
            //删除当前连接
            webSocketMap.remove(loginUser.getId());
            //用户断开
            System.out.println("用户:"+loginUser.getNickName()+"连接断开");
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        try {
            //Json实例化对象
            ServerSearchDto serverSearchDto = JSONObject.parseObject(message, ServerSearchDto.class);
            Integer[] responseData = expireCacheMap.get(serverSearchDto.getIp()+":"+serverSearchDto.getPort());
            if(ObjectUtil.isNull(responseData)){
                responseData= GameServerUtil.sendAndReceiveUDP(serverSearchDto.getIp(), serverSearchDto.getPort());
                // 缓存50ms
               if (responseData != null && responseData.length > 0) {
                   expireCacheMap.put(serverSearchDto.getIp()+":"+serverSearchDto.getPort(),responseData,2000,TimeUnit.MILLISECONDS);
               }else{
                   expireCacheMap.put(serverSearchDto.getIp()+":"+serverSearchDto.getPort(),new Integer[]{serverSearchDto.getMinPlayers(),serverSearchDto.getMinPlayers()},2000,TimeUnit.MILLISECONDS);
               }
            }
            //返回数据为null 获取失败
            if (ObjectUtil.isNull(responseData)){
                JoinServerVo build = JoinServerVo.builder()
                        .status(false)
                        .code("205")
                        .build();
                session.getAsyncRemote().sendText(JSON.toJSONString(build));
                return;
            }
            JoinServerVo build = JoinServerVo.builder()
                    .players(responseData[0])
                    .maxPlayers(responseData[1])
                    .status(serverSearchDto.getMinPlayers() >= responseData[0])
                    .code("201")
                    .build();
            session.getAsyncRemote().sendText(JSONObject.toJSONString(build));
        }catch (Exception e) {
            JoinServerVo build = JoinServerVo.builder()
                    .status(false)
                    .code("205")
                    .build();
            session.getAsyncRemote().sendText(JSON.toJSONString(build));
        }
    }

    /**
     * 消息发送失败(推送给发送者)
     */
    @SneakyThrows
    public static void onError(String jsonObject, Session session) {
        session.getAsyncRemote().sendText(jsonObject);
    }

    /**
     * 服务器推送在线用户给玩家
     */
    @Scheduled(fixedRate = 30000)
    public void sendAllUserMessage(){

    }

    /**
     * 服务器推送地图数据给服务器端
     */
    @Scheduled(fixedRate = 5000)
    public void sendServerMessage(){
        List<SteamServerVo> steamServerVos = redisService.getCacheList("server_json");
        webSocketMap.forEach((k,v)->{
            try {
                MessageVo build = MessageVo.builder()
                        .msg("获取服务器消息成功")
                        .content(steamServerVos)
                        .code("202")
                        .build();
                v.session.getAsyncRemote().sendText(JSON.toJSONString(build));
            } catch (Exception e) {
                MessageVo build = MessageVo.builder()
                        .msg("服务器信息获取失败!")
                        .code("205")
                        .build();
                v.session.getAsyncRemote().sendText(JSON.toJSONString(build));
            }
        });
    }
}

