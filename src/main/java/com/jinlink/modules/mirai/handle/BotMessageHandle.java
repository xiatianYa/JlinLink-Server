package com.jinlink.modules.mirai.handle;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.jinlink.common.util.ImgUtils;
import com.jinlink.common.util.file.FileUtils;
import com.jinlink.modules.game.entity.vo.SourceServerVo;
import lombok.SneakyThrows;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.contact.MemberPermission;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.internal.deps.okhttp3.*;
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
        //获取图片
        String imageUrl = Image.queryUrl(Img);
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        String fileContentAsBase64 = FileUtils.getFileContentAsBase64(imageUrl, true);
        RequestBody body = RequestBody.create(mediaType, "image=" + fileContentAsBase64);
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/rest/2.0/solution/v1/img_censor/v2/user_defined?access_token=" + baiduKey)
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Accept", "application/json")
                .build();
        Response response = new OkHttpClient().newBuilder().build().newCall(request).execute();
        JSONObject rootNode = JSON.parseObject(response.body().string());
        if (!rootNode.get("conclusion").equals("合规")) {
            try {
                JSONObject dataNode = (JSONObject) rootNode.getJSONArray("data").get(0);
                sender.mute(60);
                //获取管理员对象
                NormalMember owner = group.getOwner();
                //向管理员发送违规图片
                MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
                messageChainBuilder.append("违规人员QQ号 : ").append(String.valueOf(sender.getId()));
                messageChainBuilder.append(Img);
                owner.sendMessage(messageChainBuilder.build());
                //发送违规信息
                sendProhibit(message,group, "禁止发违规图片(原因:" + dataNode.get("msg") + ",发起者:" + sender.getId() + ")");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
