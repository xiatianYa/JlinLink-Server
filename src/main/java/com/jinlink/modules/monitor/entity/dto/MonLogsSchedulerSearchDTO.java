package com.jinlink.modules.monitor.entity.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 调度日志 查询 DTO 对象
 */

@Getter
@Setter
@Schema(name = "MonLogsSchedulerSearchDTO", description = "调度日志 查询 DTO 对象")
public class MonLogsSchedulerSearchDTO {
    private String jobName;
}
