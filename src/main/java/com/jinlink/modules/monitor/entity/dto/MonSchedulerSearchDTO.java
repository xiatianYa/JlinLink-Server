package com.jinlink.modules.monitor.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 调度任务 查询 DTO 对象
 */

@Getter
@Setter
@Schema(name = "MonSchedulerSearchDTO", description = "调度任务 查询 DTO 对象")
public class MonSchedulerSearchDTO {
    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 任务组别
     */
    private String jobGroup;

    /**
     * 触发器名称
     */
    private String triggerName;

    /**
     * 触发器组别
     */
    private String triggerGroup;
}
