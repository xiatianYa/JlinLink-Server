package com.jinlink.modules.mirai.handle;

import cn.hutool.core.util.ObjectUtil;
import com.jinlink.common.util.ImgUtils;
import com.jinlink.modules.game.entity.vo.SourceServerVo;
import lombok.SneakyThrows;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.contact.MemberPermission;
import net.mamoe.mirai.message.data.*;
import net.mamoe.mirai.utils.ExternalResource;

import java.io.File;
import java.io.IOException;

public class BotMessageHandle {

    /**
     * 查询服务器消息
     */
    @SneakyThrows
    public static void ServerHandle(SourceServerVo sourceServerVo, Group group) {
        // 构建服务器查询数据图片
        ExternalResource externalResource = null;
        try {
            group.sendMessage("服务器消息获取中...");
            // 记录开始时间
            long startTime = System.currentTimeMillis();
            File imgBuilder = ImgUtils.serverImgBuilder(sourceServerVo.getGameServerVoList());
            externalResource = ExternalResource.create(imgBuilder);
            Image image = group.uploadImage(externalResource);
            //发送信息
            group.sendMessage(image);
            //关闭网络流
            externalResource.close();
            // 记录结束时间
            long endTime = System.currentTimeMillis();
            group.sendMessage("获取服务器耗时:"+ (endTime - startTime)+"ms");
        } catch (IOException e) {
            group.sendMessage("服务器信息获取失败!");
        }finally {
            if (ObjectUtil.isNotNull(externalResource)){
                externalResource.close();
            }
        }
    }

    /**
     * 检测消息是否违规
     */
    public static Boolean ProhibitHandle(MessageChain message,Group group,Member sender,String baiduKey) throws IOException {
        //如果不是管理员 或 群主
        if (sender.getPermission().getLevel() != MemberPermission.MEMBER.getLevel()) return false;
        //处理非法信息
        for (SingleMessage singleMessage : message) {
            String input = singleMessage.contentToString();
            switch (input) {
                case "[视频]":
                    sendProhibit(message, group, "禁止发送视频");
                    return true;
                case "[转发消息]":
                    sendProhibit(message, group, "禁止发送转发消息");
                    return true;
                case "[图片]":
                case "[动画表情]":
                    //检测消息里的每一张图片是否合格 不合格返回false
                    prohibitImage(group, message,sender, (Image) singleMessage,baiduKey);
                    break;
                default:
                    //其他消息则通过
                    break;
            }
        }
        return false;
    }

    /**
     * 发送禁止消息
     */
    public static void sendProhibit(MessageChain message, Group group, String msg) {
        try {
            MessageSource.recall(message);
            group.sendMessage(msg);
        } catch (Exception e) {
            System.out.println("无权限撤回");
        }
    }

    /**
     * 检测图片是否合法
     */
    public static void prohibitImage(Group group, MessageChain message, Member sender, Image Img, String baiduKey) throws IOException {

    }
}
