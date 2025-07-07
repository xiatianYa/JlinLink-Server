package com.jinlink.modules.monitor.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.jinlink.common.exception.JinLinkException;
import com.jinlink.modules.monitor.entity.MonScheduler;
import com.jinlink.modules.monitor.service.QuartzService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Service;

import java.lang.reflect.Constructor;

@Service
@Slf4j
public class QuartzServiceImpl implements QuartzService {

    @Resource
    private Scheduler scheduler;

    @Override
    public void addCronJob(MonScheduler monScheduler) {
        try {
            // 当前任务不存在才进行添加
            JobKey jobKey = JobKey.jobKey(monScheduler.getJobName(), monScheduler.getJobGroup());
            if (scheduler.checkExists(jobKey)) {
                //如果当前定时任务已添加 则删除定时任务
                deleteCronJob(monScheduler);
                return;
            }

            // 构建 Job
            JobDetail job = JobBuilder.newJob(getClass(monScheduler.getJobClassName()).getClass())
                    .storeDurably()
                    .withIdentity(jobKey).build();

            // cron表达式定时构造器
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(monScheduler.getCron());

            // 构建 Trigger
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(TriggerKey.triggerKey(monScheduler.getJobName(), monScheduler.getTriggerGroup()))
                    .withSchedule(cronScheduleBuilder).build();

            // 启动调度器
            scheduler.scheduleJob(job, trigger);
            scheduler.start();
        } catch (Exception e) {
            throw new JinLinkException("[新增定时任务]失败，报错：" + e);
        }
    }

    @Override
    public void deleteCronJob(MonScheduler monScheduler) {
        try {

            JobKey jobKey = JobKey.jobKey(monScheduler.getJobName(), monScheduler.getJobGroup());

            TriggerKey triggerKey = TriggerKey.triggerKey(monScheduler.getTriggerName(), monScheduler.getTriggerGroup());

            Trigger trigger = scheduler.getTrigger(triggerKey);

            if (ObjectUtil.isNull(trigger)) {
                log.info("[停止定时任务]根据triggerName:{}和triggerGroup:{}未查询到相应的trigger!", monScheduler.getTriggerName(), monScheduler.getTriggerGroup());
            }
            //暂停触发器
            scheduler.pauseTrigger(triggerKey);
            // 移除触发器
            scheduler.unscheduleJob(triggerKey);
            // 删除任务
            scheduler.deleteJob(jobKey);
            log.info("[停止定时任务]jobName:{},jobGroup:{}, triggerName:{}, triggerGroup:{},停止--------------", monScheduler.getJobName(), monScheduler.getJobGroup(), monScheduler.getTriggerName(), monScheduler.getTriggerGroup());
        } catch (SchedulerException e) {
            throw new JinLinkException("[停止定时任务]失败，报错：" + e);
        }
    }


    public static Job getClass(String className) throws Exception {
        Class<?> classTemp = Class.forName(className);
        // 获取无参构造函数（注意：如果有多个构造函数，需要选择正确的）
        Constructor<?> constructor = classTemp.getDeclaredConstructor();
        // 设置为可访问（如果需要调用私有构造函数）
        constructor.setAccessible(true);
        //返回实例对象
        return (Job) constructor.newInstance();
    }

    @Override
    public void executeImmediately(MonScheduler monScheduler) {
        try {
            JobKey jobKey = JobKey.jobKey(monScheduler.getJobName(), monScheduler.getJobGroup());
            JobDetail job = JobBuilder.newJob(getClass(monScheduler.getJobClassName()).getClass())
                    .withIdentity(jobKey).build();
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(TriggerKey.triggerKey(monScheduler.getJobName(), monScheduler.getTriggerGroup()))
                    .build();
            // 启动调度器
            scheduler.scheduleJob(job, trigger);
            scheduler.start();
        } catch (Exception e) {
            throw new JinLinkException("[立即执行一次任务，不定时]失败，报错：" + e);
        }
    }
}
