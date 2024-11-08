package com.jinlink.modules.monitor.entity.vo;

import com.jinlink.core.domain.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 调度管理 VO
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "MonSchedulerVo", description = "调度管理 VO 对象")
public class MonSchedulerVo extends BaseVO {

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
    private String status;
}
