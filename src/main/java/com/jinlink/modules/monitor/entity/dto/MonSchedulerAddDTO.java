package com.jinlink.modules.monitor.entity.dto;

import com.jinlink.core.domain.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 定时任务 新增 DTO 对象
 */

@Getter
@Setter
@Schema(name = "MonSchedulerAddDTO", description = "定时任务 新增 DTO 对象")
public class MonSchedulerAddDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 9214057413441264523L;

    @Schema(description = "任务名称")
    private String jobName;

    @Schema(description = "任务组别")
    private String jobGroup;

    @Schema(description = "触发器名称")
    private String triggerName;

    @Schema(description = "触发器组别")
    private String triggerGroup;

    @Schema(description = "任务参数")
    private String jobData;

    @Schema(description = "触发器参数")
    private String triggerData;

    @Schema(description = "调度表达式")
    private String cron;

    @Schema(description = "调度任务全类名")
    private String jobClassName;

    @Schema(description = "状态")
    private Integer status;
}
