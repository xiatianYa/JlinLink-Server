package com.jinlink.modules.monitor.entity.vo;

import com.jinlink.core.domain.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 登录日志 VO 对象
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "MonLogsLoginVo", description = "登录日志 对象")
public class MonLogsLoginVo extends BaseVO {

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "用户名称")
    private String userName;

    @Schema(description = "IP")
    private String ip;

    @Schema(description = "IP所属地")
    private String ipAddr;

    @Schema(description = "登录代理")
    private String userAgent;

    @Schema(description = "登录状态(0:失败 1:成功)")
    private String status;

    @Schema(description = "登录错误日志")
    private String message;
}
