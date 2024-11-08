package com.jinlink.modules.monitor.entity;

import com.jinlink.core.domain.BaseEntity;
import com.mybatisflex.annotation.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 调度管理 实体类。
 *
 * @author Summer
 * @since 1.0.0
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "mon_scheduler")
public class MonScheduler extends BaseEntity {
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

    /**
     * 任务参数
     */
    private String jobData;

    /**
     * 触发器参数
     */
    private String triggerData;

    /**
     * 调度表达式
     */
    private String cron;

    /**
     * 调度任务全类名
     */
    private String jobClassName;

    /**
     * 状态
     */
    private Integer status;
}
