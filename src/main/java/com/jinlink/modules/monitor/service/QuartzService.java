package com.jinlink.modules.monitor.service;

import com.jinlink.modules.monitor.entity.MonScheduler;

public interface QuartzService {

    /**
     * 开启定时任务
     *
     */
    void addCronJob(MonScheduler monScheduler);

    /**
     * 停止定时任务
     *
     */
    void deleteCronJob(MonScheduler monScheduler);

    /**
     * 执行定时任务(一次)
     *
     */
    void executeImmediately(MonScheduler monScheduler);
}