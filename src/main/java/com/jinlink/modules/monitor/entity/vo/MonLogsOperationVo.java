package com.jinlink.modules.monitor.entity.vo;

import com.jinlink.core.domain.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 操作日志 VO 展示类
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "MonLogsOperationVO", description = "操作日志 VO 对象")
public class MonLogsOperationVo extends BaseVO {

    @Schema(description = "请求ID")
    private String requestId;

    @Schema(description = "IP")
    private String ip;

    @Schema(description = "IP所属地")
    private String ipAddr;

    @Schema(description = "登录代理")
    private String userAgent;

    @Schema(description = "请求URI")
    private String requestUri;

    @Schema(description = "请求方式")
    private String requestMethod;

    @Schema(description = "请求内容类型")
    private String contentType;

    @Schema(description = "接口说明")
    private String operation;

    @Schema(description = "方法名称")
    private String methodName;

    @Schema(description = "请求参数")
    private String methodParams;

    @Schema(description = "请求耗时")
    private Long useTime;

    @Schema(description = "操作用户ID")
    private Long createUserId;

    @Schema(description = "操作用户名称")
    private String createUser;
}