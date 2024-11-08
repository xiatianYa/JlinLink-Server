package com.jinlink.modules.monitor.entity;

import com.jinlink.core.domain.BaseEntity;
import com.mybatisflex.annotation.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 登录日志 实体类。
 *
 * @author Summer
 * @since 1.0.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "mon_logs_login")
public class MonLogsLogin extends BaseEntity {

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
