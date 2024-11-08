package com.jinlink.modules.monitor.entity.vo;

import com.jinlink.core.domain.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;

/**
 * 文件上传日志 VO 对象
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "MonLogsFileVo", description = "文件上传日志 对象")
public class MonLogsFileVo extends BaseVO {

    @Serial
    private static final long serialVersionUID = 5769107831011956328L;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "用户名称")
    private String userName;

    @Schema(description = "文件路径")
    private String fileUrl;

    @Schema(description = "文件大小")
    private String fileSize;

    @Schema(description = "上传状态(0:失败 1:成功)")
    private String status;
}
