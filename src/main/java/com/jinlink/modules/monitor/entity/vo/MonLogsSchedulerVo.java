package com.jinlink.modules.monitor.entity.vo;

import com.jinlink.common.domain.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 调度日志 VO 展示类
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "MonLogsSchedulerVo", description = "调度日志 VO 对象")
public class MonLogsSchedulerVo extends BaseVO {

    @Schema(description = "任务名称")
    private String jobName;

    @Schema(description = "任务组别")
    private String jobGroup;

    @Schema(description = "触发器名称")
    private String triggerName;

    @Schema(description = "触发器组别")
    private String triggerGroup;

    @Schema(description = "耗时")
    private Long useTime;

    @Schema(description = "状态")
    private String status;

    @Schema(description = "异常信息")
    private String exceptionMessage;

    @Schema(description = "异常类")
    private String exceptionClass;

    @Schema(description = "异常行号")
    private Integer line;

    @Schema(description = "堆栈信息")
    private String stackTrace;
}
