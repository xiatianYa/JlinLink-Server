package com.jinlink.modules.monitor.entity;

import com.jinlink.common.domain.BaseEntity;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;

/**
 * 调度日志 实体类。
 *
 * @author Summer
 * @since 1.0.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "mon_logs_scheduler")
public class MonLogsScheduler extends BaseEntity {

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
     * 耗时
     */
    private Long useTime;

    /**
     * 状态
     */
    private String status;

    /**
     * 异常信息
     */
    private String exceptionMessage;

    /**
     * 异常类
     */
    private String exceptionClass;

    /**
     * 异常行号
     */
    private Integer line;

    /**
     * 堆栈信息
     */
    private String stackTrace;

}
