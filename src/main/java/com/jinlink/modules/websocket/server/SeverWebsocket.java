package com.jinlink.modules.websocket.server;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.jinlink.common.constants.Constants;
import com.jinlink.common.domain.LoginUser;
import com.jinlink.common.exception.JinLinkException;
import com.jinlink.common.util.udp.GameServerUtil;
import com.jinlink.core.config.redis.service.RedisService;
import com.jinlink.core.holder.GlobalUserHolder;
import com.jinlink.modules.game.entity.vo.SourceServerVo;
import com.jinlink.modules.websocket.entity.*;
import com.jinlink.modules.websocket.entity.dto.ServerSearchDto;
import com.jinlink.modules.websocket.entity.vo.JoinServerVo;
import com.jinlink.modules.websocket.entity.vo.MessageVo;
import com.jinlink.modules.websocket.entity.vo.SteamServerPushVo;
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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPOutputStream;


/**
 * @author summer
 */
@ServerEndpoint("/ws/server/{token}")
@Component
public class SeverWebsocket {
    /**concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。*/
    private static final ConcurrentHashMap<Long, SeverWebsocket> webSocketMap = new ConcurrentHashMap<>();
    /** 服务器缓存挤服数据Map */
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
    /** 服务器缓存链接数据Map */
    private final static ExpiringMap<Long, String> expireOnLineCacheMap = ExpiringMap.builder()
            //设置 Map 的过期策略
            .expirationPolicy(ExpirationPolicy.CREATED)
            // 设置过期时间，如果key不设置过期时间，key永久有效。
            .expiration(3, TimeUnit.HOURS)
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
        try {
            LoginUser loginUser = GlobalUserHolder.getUserById(loginId);
            if (ObjectUtil.isNull(loginUser) || !StpUtil.isLogin(loginId)){
                throw new JinLinkException("用户未登录!");
            }
            this.loginUser = loginUser;
        }catch (Exception e){
            System.out.println("用户未登录!");
            return;
        }
        SeverWebsocket severWebsocket = webSocketMap.get(this.loginUser.getId());
        if (ObjectUtil.isNotNull(severWebsocket))webSocketMap.remove(this.loginUser.getId());
        webSocketMap.remove(this.loginUser.getId());
        webSocketMap.put(this.loginUser.getId(),this);
        //用户登录
        System.out.println("用户:"+this.loginUser.getNickName()+"连接成功");
        //给自己发登录成功的消息
        MessageVo build = MessageVo.builder()
                .sendUser(this.loginUser)
                .msg("登录成功")
                .code("200")
                .build();
        byte[] compressedData = compress(JSON.toJSONString(build));
        this.session.getAsyncRemote().sendBinary(ByteBuffer.wrap(compressedData));
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if (ObjectUtil.isNotNull(this.loginUser) && webSocketMap.containsKey(this.loginUser.getId())) {
            //删除当前连接
            webSocketMap.remove(this.loginUser.getId());
            //删除用户保存的信息
            expireOnLineCacheMap.remove(this.loginUser.getId());
            //用户断开
            System.out.println("用户:"+this.loginUser.getNickName()+"连接断开");
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
            ReceiveMessage receiveMessage = JSON.parseObject(message,ReceiveMessage.class);
            switch (receiveMessage.getType()) {
                case 0:
                    //Json实例化对象
                    ServerSearchDto serverSearchDto = JSONObject.parseObject(receiveMessage.getData(), ServerSearchDto.class);
                    Integer[] responseData = expireCacheMap.get(serverSearchDto.getIp()+":"+serverSearchDto.getPort());
                    if(ObjectUtil.isNull(responseData)){
                        responseData= GameServerUtil.sendAndReceiveUDP(serverSearchDto.getIp(), serverSearchDto.getPort());
                        // 缓存50ms
                        if (ObjectUtil.isNotNull(responseData) && responseData.length > 0) {
                            expireCacheMap.put(serverSearchDto.getIp()+":"+serverSearchDto.getPort(),responseData,100,TimeUnit.MILLISECONDS);
                        }else{
                            responseData = new Integer[]{serverSearchDto.getMaxPlayers(),serverSearchDto.getMaxPlayers()};
                            expireCacheMap.put(serverSearchDto.getIp()+":"+serverSearchDto.getPort(),responseData,5000,TimeUnit.MILLISECONDS);
                        }
                    }
                    //返回数据为null 获取失败
                    if (ObjectUtil.isNull(responseData)){
                        JoinServerVo build = JoinServerVo.builder()
                                .status(false)
                                .code("205")
                                .build();
                        byte[] compressedData = compress(JSON.toJSONString(build));
                        session.getAsyncRemote().sendBinary(ByteBuffer.wrap(compressedData));
                        return;
                    }
                    JoinServerVo build = JoinServerVo.builder()
                            .ip(serverSearchDto.getIp())
                            .port(serverSearchDto.getPort())
                            .players(responseData[0])
                            .maxPlayers(responseData[1])
                            .status(serverSearchDto.getMinPlayers() >= responseData[0])
                            .code("201")
                            .build();
                    byte[] compressedJoinData = compress(JSON.toJSONString(build));
                    session.getAsyncRemote().sendBinary(ByteBuffer.wrap(compressedJoinData));
                    break;
                case 1:
                    //将消息推送给所有在线用户
                    SteamServerPushVo steamServerPushVo = JSON.parseObject(receiveMessage.getData(), SteamServerPushVo.class);
                    steamServerPushVo.setPushUserName(this.loginUser.getNickName());
                    webSocketMap.forEach((k,v)->{
                        MessageVo messageVo = MessageVo.builder()
                                .code("206")
                                .data(steamServerPushVo)
                                .build();
                        byte[] compressedPushData = compress(JSON.toJSONString(messageVo));
                        v.session.getAsyncRemote().sendBinary(ByteBuffer.wrap(compressedPushData));
                    });
                    break;
                case 2:
                    //用户连接服务器
                    expireOnLineCacheMap.put(this.loginUser.getId(),receiveMessage.getData());
                    break;
            }
        }catch (Exception e) {
            JoinServerVo build = JoinServerVo.builder()
                    .status(false)
                    .code("205")
                    .build();
            byte[] compressedData = compress(JSON.toJSONString(build));
            session.getAsyncRemote().sendBinary(ByteBuffer.wrap(compressedData));
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
     * 服务器推送地图数据给客户端
     */
    @Scheduled(fixedRate = 5000)
    public void sendServerMessage(){
        List<SourceServerVo> serverVos = redisService.getCacheList(Constants.SERVER_VO_KEY);
        webSocketMap.forEach((k,v)->{
            try {
                MessageVo build = MessageVo.builder()
                        .msg("获取服务器消息成功")
                        .data(serverVos)
                        .code("202")
                        .build();
                byte[] compressedData = compress(JSON.toJSONString(build));
                v.session.getAsyncRemote().sendBinary(ByteBuffer.wrap(compressedData));
            } catch (Exception e) {
                MessageVo build = MessageVo.builder()
                        .msg("服务器信息获取失败!")
                        .code("205")
                        .build();
                byte[] compressedData = compress(JSON.toJSONString(build));
                v.session.getAsyncRemote().sendBinary(ByteBuffer.wrap(compressedData));
            }
        });
    }

    /**
     * 服务器推送在线用户给客户端
     */
    @Scheduled(fixedRate = 20000)
    public void sendOnlineUserMessage() {
        List<OnLineUser> onlineUsers = new ArrayList<>();
        webSocketMap.forEach((k,v)->{
            if (ObjectUtil.isNotNull(v.loginUser)) onlineUsers.add(BeanUtil.copyProperties(v.loginUser,OnLineUser.class));
        });
        MessageVo build = MessageVo.builder()
                .msg("获取在线用户列表成功!")
                .code("203")
                .data(onlineUsers)
                .build();
        webSocketMap.forEach((k,v)->{
            byte[] compressedData = compress(JSON.toJSONString(build));
            // 发送二进制数据
            try {
                v.session.getBasicRemote().sendBinary(ByteBuffer.wrap(compressedData));
            } catch (IOException e) {
                System.out.println("在线用户列表消息推送失败!");
            }
        });
    }

    /**
     * 根据ID获取用户所在服务器
     */
    public static String getUserOnlineServer(Long id){
        return expireOnLineCacheMap.get(id);
    }


    // 压缩字符串的方法
    private byte[] compress(String str){
        if (str == null || str.isEmpty()) {
            return new byte[0];
        }
        ByteArrayOutputStream obj = new ByteArrayOutputStream();
        try (GZIPOutputStream gzip = new GZIPOutputStream(obj)) {
            gzip.write(str.getBytes(StandardCharsets.UTF_8));
        }catch (Exception e){
            return new byte[0];
        }
        return obj.toByteArray();
    }
}

