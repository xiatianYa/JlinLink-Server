package com.jinlink.modules.monitor.job;

import com.alibaba.fastjson2.JSONObject;
import com.jinlink.common.exception.JinLinkException;
import com.jinlink.common.util.BiliUtils;
import com.jinlink.common.util.file.ImageUtils;
import com.jinlink.modules.game.entity.GameLive;
import com.jinlink.modules.game.service.GameLiveService;
import com.jinlink.modules.game.service.GameOnlineStatisticsService;
import com.mybatisflex.core.logicdelete.LogicDeleteManager;
import com.mybatisflex.core.query.QueryWrapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

/**
 * 每天运行的系统定时任务
 */
@Component
@Slf4j
public class DailyCronJob implements Job {
    @Resource
    private GameLiveService gameLiveService;
    @Resource
    private GameOnlineStatisticsService gameOnlineStatisticsService;

    /**
     * 上传文件存储在本地的根路径
     */
    @Value("${file.path}")
    private String localFilePath;

    @Override
    @Transactional
    public void execute(JobExecutionContext jobExecutionContext) {
        //更新所有主播的头像
        try {
            List<GameLive> gameLives = gameLiveService.list();
            for (GameLive gameLive : gameLives) {
                String bgPath = Objects.requireNonNull(JSONObject.parseObject(BiliUtils.getBiliLiveApi(gameLive.getUid())))
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
        }catch (Exception e){
            throw new JinLinkException("主播数据更新失败!");
        }

        //删除过期的在线用户数据
        try {
            QueryWrapper queryWrapper = new QueryWrapper();
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime twentyFourHoursAgo = now.minusHours(24);
            queryWrapper.lt("create_time", twentyFourHoursAgo);
            gameOnlineStatisticsService.remove(queryWrapper);
        }catch (Exception e){
            throw new JinLinkException("删除过期在线用户数据失败");
        }
    }
}
