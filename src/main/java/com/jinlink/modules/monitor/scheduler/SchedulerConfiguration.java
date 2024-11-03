package com.jinlink.modules.monitor.scheduler;

import com.jinlink.modules.monitor.scheduler.listener.SchedulerJobListener;
import com.jinlink.modules.monitor.service.MonLogsSchedulerService;
import jakarta.annotation.Resource;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * Quartz 调度器配置
 */
@Configuration
public class SchedulerConfiguration {

    @Resource
    private SchedulerFactoryBean schedulerFactoryBean;

    @Bean
    @Primary
    public Scheduler schedulerBean(@Autowired MonLogsSchedulerService monLogsSchedulerService) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        scheduler.getListenerManager().addJobListener(new SchedulerJobListener(monLogsSchedulerService));
        return scheduler;
    }

}
