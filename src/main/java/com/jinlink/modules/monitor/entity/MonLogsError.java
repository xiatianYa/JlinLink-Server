package com.jinlink.modules.monitor.entity;

import com.jinlink.common.domain.BaseEntity;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;

/**
 * 错误异常日志 实体类。
 *
 * @author Summer
 * @since 1.0.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "mon_logs_error")
public class MonLogsError extends BaseEntity {

    /**
     * 请求ID
     */
    private String requestId;

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
     * 请求URI
     */
    private String requestUri;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 请求内容类型
     */
    private String contentType;

    /**
     * 接口说明
     */
    private String operation;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 请求参数
     */
    private String methodParams;

    /**
     * 请求耗时
     */
    private Long useTime;

    /**
     * 异常信息
     */
    private String exceptionMessage;

    /**
     * 异常类
     */
    private String exceptionClass;

    /**
     * 异常行号
     */
    private Integer line;

    /**
     * 堆栈信息
     */
    private String stackTrace;
}
