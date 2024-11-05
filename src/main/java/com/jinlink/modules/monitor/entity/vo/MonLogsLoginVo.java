package com.jinlink.modules.monitor.entity.vo;

import com.jinlink.common.domain.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * IP
     */
    private String ip;

    /**
     * IP所属地
     */
    private String ipAddr;

    /**
     * 登录代理
     */
    private String userAgent;

    /**
     * 登录状态(0:失败 1:成功)
     */
    private String status;

    /**
     * 登录错误日志
     */
    private String message;
}
