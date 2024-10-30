package com.jinlink.modules.monitor.job;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TaskJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        log.info("=========================业务逻辑====================");
        log.info("jobName:{}", jobExecutionContext.getJobDetail().getKey().getName());
        log.info("jobGroup:{}", jobExecutionContext.getJobDetail().getKey().getGroup());
        log.info("triggerName:{}", jobExecutionContext.getTrigger().getKey().getName());
        log.info("triggerGroup:{}", jobExecutionContext.getTrigger().getKey().getGroup());
        log.info("上次触发时间:{}", DateUtil.formatDateTime(jobExecutionContext.getPreviousFireTime()));
        log.info("本次触发时间:{}", DateUtil.formatDateTime(jobExecutionContext.getFireTime()));
        log.info("下次触发时间:{}", DateUtil.formatDateTime(jobExecutionContext.getNextFireTime()));
        log.info("调度时间:{}", DateUtil.formatDateTime(jobExecutionContext.getScheduledFireTime()));
    }
}
