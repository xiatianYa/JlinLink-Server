package com.jinlink.modules.bot.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MapOrderJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {

    }
}
