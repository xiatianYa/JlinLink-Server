package com.jinlink.modules.system.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 意见反馈 查询 DTO 对象
 */

@Getter
@Setter
@Schema(name = "SysFeedbackSearchDTO", description = "意见反馈 查询 DTO 对象")
public class SysFeedbackSearchDTO implements Serializable {

    @Schema(description = "反馈内容")
    private String content;

    @Schema(description = "反馈类型")
    private String type;

    @Schema(description = "反馈状态")
    private String status;

}
