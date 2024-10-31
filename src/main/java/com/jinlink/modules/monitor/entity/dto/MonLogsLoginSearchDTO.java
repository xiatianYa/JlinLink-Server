package com.jinlink.modules.monitor.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 登录日志 查询 DTO 对象
 */

@Getter
@Setter
@Schema(name = "MonLogsLoginSearchDTO", description = "登录日志 查询 DTO 对象")
public class MonLogsLoginSearchDTO {
    /**
     * 用户名称
     */
    private String userName;
}
