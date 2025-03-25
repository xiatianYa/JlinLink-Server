package com.jinlink.modules.system.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 意见反馈 编辑更新 DTO 对象
 */

@Getter
@Setter
@Schema(name = "SysDictItemUpdateDTO", description = "意见反馈 编辑更新 DTO 对象")
public class SysFeedbackUpdateDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -5391018643271602831L;

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "意见反馈")
    private String content;

    @Schema(description = "反馈图片")
    private String image;

    @Schema(description = "反馈类型")
    private Integer type;

    @Schema(description = "处理状态")
    private Integer status;

    @Schema(description = "回复内容")
    private String feedback;

}
