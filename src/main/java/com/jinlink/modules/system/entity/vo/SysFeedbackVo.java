package com.jinlink.modules.system.entity.vo;

import com.jinlink.core.domain.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * 意见反馈 VO 展示类
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "SysFeedbackVo", description = "意见反馈 VO 对象")
public class SysFeedbackVo extends BaseVO {

    @Schema(description = "意见反馈")
    private String content;

    @Schema(description = "反馈图片")
    private List<String> image;

    @Schema(description = "反馈类型")
    private String type;

    @Schema(description = "反馈状态")
    private String status;

    @Schema(description = "用户名称")
    private String userName;
}
