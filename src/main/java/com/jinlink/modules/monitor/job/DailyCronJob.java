package com.jinlink.modules.monitor.job;

import com.alibaba.fastjson2.JSONObject;
import com.jinlink.common.util.BiliUtils;
import com.jinlink.common.util.file.ImageUtils;
import com.jinlink.modules.game.entity.GameLive;
import com.jinlink.modules.game.service.GameLiveService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 每天运行的系统定时任务
 */
@Component
@Slf4j
public class DailyCronJob implements Job {
    @Resource
    private GameLiveService gameLiveService;

    /**
     * 上传文件存储在本地的根路径
     */
    @Value("${file.path}")
    private String localFilePath;

    @Override
    @Transactional
    public void execute(JobExecutionContext jobExecutionContext) {
        List<GameLive> gameLives = gameLiveService.list();
        for (GameLive gameLive : gameLives) {
            String bgPath = JSONObject.parseObject(BiliUtils.getBiliLiveApi(gameLive.getUid()))
                    .getJSONObject("data")
                    .getJSONObject("by_room_ids")
                    .getJSONObject(gameLive.getUid())
                    .getString("cover");
            String avatarPath = BiliUtils.getBiliLiveUserInfoApi(gameLive.getUid());
            //获取背景
            String bgUrl = ImageUtils.downloadImageAsResource(bgPath,localFilePath+"/live/", gameLive.getUid()+"bg.jpg");
            //获取头像
            String avatarUrl = ImageUtils.downloadImageAsResource(avatarPath,localFilePath+"/live/", gameLive.getUid()+".jpg");
            gameLive.setBgUrl(bgUrl);
            gameLive.setAvatar(avatarUrl);
            gameLiveService.updateById(gameLive);
        }
    }
}
