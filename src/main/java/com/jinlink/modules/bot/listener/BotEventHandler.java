package com.jinlink.modules.bot.listener;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSONObject;
import com.jinlink.common.constants.Constants;
import com.jinlink.common.util.AgqlUtils;
import com.jinlink.core.config.redis.service.RedisService;
import com.jinlink.modules.bot.entity.BotGroup;
import com.jinlink.modules.bot.handle.BotMessageHandle;
import com.jinlink.modules.bot.service.BotGroupService;
import com.jinlink.modules.bot.service.BotMapOrderService;
import com.jinlink.modules.game.entity.GameCommunity;
import com.jinlink.modules.game.entity.vo.SourceServerVo;
import com.jinlink.modules.game.service.GameCommunityService;
import com.mybatisflex.core.query.QueryWrapper;
import jakarta.annotation.Resource;
import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.*;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Bot消息处理器
 */
@Service
public class BotEventHandler extends SimpleListenerHost {

    /**RedisService*/
    private static RedisService redisService;

    /**GameCommunityService*/
    private static GameCommunityService gameCommunityService;
    /**BotGroupService*/
    private static BotGroupService botGroupService;
    /**BotMapOrderService*/
    private static BotMapOrderService botMapOrderService;

    @Resource
    public void setRedisService(RedisService redisService) {
        BotEventHandler.redisService = redisService;
    }

    @Resource
    public void setGameCommunityService(GameCommunityService gameCommunityService) {
        BotEventHandler.gameCommunityService = gameCommunityService;
    }

    @Resource
    public void setBotGroupService(BotGroupService botGroupService) {
        BotEventHandler.botGroupService = botGroupService;
    }

    @Resource
    public void setBotMapOrderService(BotMapOrderService botMapOrderService) {
        BotEventHandler.botMapOrderService = botMapOrderService;
    }

    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        super.handleException(context, exception);
    }

    /**
     * 群消息
     * @param event 消息对象
     */
    @EventHandler
    public void onGroupMessageEvent(@NotNull GroupMessageEvent event) {
        // 发送的消息
        MessageChain message = event.getMessage();
        // 接收消息的群
        Group group = event.getGroup();
        // 发送消息的人
        Member sender = event.getSender();
        // 判断群是否入驻
        BotGroup groupOne = botGroupService.getOne(new QueryWrapper().eq("group_id", group.getId()));
        if (ObjectUtil.isNull(groupOne)) return;
        // 判断消息是否违规
        if (groupOne.getIsProhibit().equals(Constants.COMMON_SUCCESS_STATUS)){
            try {
                String baiduKey = redisService.getCacheObject(Constants.BAIDU_KEY);
                if (BotMessageHandle.ProhibitHandle(message,group,sender,baiduKey)) return;
            }catch (Exception e){
                //重新获取百度Key
                String accessToken = AgqlUtils.getAccessToken(Constants.BAIDU_API_KEY, Constants.BAIDU_SECRET_KEY);
                redisService.setCacheObject(Constants.BAIDU_KEY,accessToken);
            }
        }
        // 根据消息判断 转发消息
        if (message.get(1) instanceof PlainText) {
            String msg = ((PlainText) message.get(1)).getContent();
            //判断其是否在查询服务器(转为大写)
            if (groupOne.getIsServer().equals(Constants.COMMON_SUCCESS_STATUS)){
                GameCommunity gameCommunity = gameCommunityService.getOne(new QueryWrapper().eq("community_name", msg.toLowerCase()));
                //有服务器消息
                if (ObjectUtil.isNotNull(gameCommunity)) {
                    // 服务器数据列表
                    List<JSONObject> serverJsonList = redisService.getCacheList(Constants.SERVER_VO_KEY);
                    for (JSONObject jsonObject : serverJsonList) {
                        // 直接从 JSONObject 解析为 SourceServerVo 对象
                        SourceServerVo sourceServerVo = jsonObject.toJavaObject(SourceServerVo.class);
                        if (sourceServerVo.getGameCommunity().getId().equals(gameCommunity.getId())) {
                            //绘制图片 发送消息
                            BotMessageHandle.ServerHandle(sourceServerVo,group);
                            return;
                        }
                    }
                }
            }
            //判断其是否在使用娶群友
            if (groupOne.getIsMarry().equals(Constants.COMMON_SUCCESS_STATUS)){
                //群群友命令列表
                switch (msg){
                    case "娶群友":
                        break;
                    case "离婚":
                        break;
                    case "当小三":
                        break;
                    case "纳妾":
                        break;
                }
            }
        }
    }

    /**
     * 成员已经加入群
     * @param event 消息对象
     */
    @EventHandler
    public void onMemberJoinEvent(@NotNull MemberJoinEvent event) {
        event.getGroup().sendMessage("邦邦卡邦 欢迎老师:" + event.getMember().getNick() + "进入本群。");
    }

    /**
     * 成员已经离开群
     * @param event 消息对象
     */
    @EventHandler
    public void onMemberLeaveEvent(@NotNull MemberLeaveEvent event) {
        event.getGroup().sendMessage(event.getMember().getNick() + "离开了本群。");
    }

    /**
     * 一个账号请求加入群
     * @param event 消息对象
     */
    @EventHandler
    public void onMemberJoinRequestEvent(@NotNull MemberJoinRequestEvent event) {
        event.accept();
        if (ObjectUtil.isNotNull(event.getGroup())){
            event.getGroup().sendMessage(event.getFromNick() + "申请加入本群!");
        }
    }

    /**
     * 群成员被禁言
     * @param event 消息对象
     */
    @EventHandler
    public void onMemberMuteEvent(@NotNull MemberMuteEvent event) {
        event.getGroup().sendMessage(event.getMember().getNick()+"你怎么不说话了?");
    }

    /**
     * 群成员被取消禁言
     * @param event 消息对象
     */
    @EventHandler
    public void onMemberUnmuteEvent(@NotNull MemberUnmuteEvent event) {
        event.getGroup().sendMessage(event.getMember().getNick()+"说话说话!");
    }
}
