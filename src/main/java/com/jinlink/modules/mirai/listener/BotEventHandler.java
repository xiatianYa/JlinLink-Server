package com.jinlink.modules.mirai.listener;

import cn.hutool.core.util.ObjectUtil;
import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.*;
import org.jetbrains.annotations.NotNull;

public class BotEventHandler extends SimpleListenerHost {
    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        super.handleException(context, exception);
    }

    /**
     * 接收到消息(群,好友消息,群临时会话消息,陌生人消息,其他客户端消息)
     * @param event 消息对象
     */
    @EventHandler
    public void onMessageEvent(@NotNull MessageEvent event) {
        System.out.println(event.getMessage());
    }

    /**
     * 成员已经加入群
     * @param event 消息对象
     */
    @EventHandler
    public void onMemberJoinEvent(@NotNull MemberJoinEvent event) {
        event.getGroup().sendMessage("欢迎Sensen:" + event.getMember().getNick() + "进入本群。");
    }

    /**
     * 成员已经离开群
     * @param event 消息对象
     */
    @EventHandler
    public void onMemberLeaveEvent(@NotNull MemberLeaveEvent event) {
        event.getGroup().sendMessage("Sensen;" + event.getMember().getNick() + "离开了本群。");
    }

    /**
     * 一个账号请求加入群
     * @param event 消息对象
     */
    @EventHandler
    public void onMemberJoinRequestEvent(@NotNull MemberJoinRequestEvent event) {
        event.accept();
        if (ObjectUtil.isNotNull(event.getGroup())){
            event.getGroup().sendMessage("Sensen " + event.getFromNick() + "申请加入本群!");
        }
    }
}
