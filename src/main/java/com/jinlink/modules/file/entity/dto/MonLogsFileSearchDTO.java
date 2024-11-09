package com.jinlink.modules.file.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 文件日志 查询 DTO 对象
 */

@Getter
@Setter
@Schema(name = "MonLogsFileSearchDTO", description = "文件日志 查询 DTO 对象")
public class MonLogsFileSearchDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -8518349960545896766L;

    @Schema(description = "用户ID")
    private Long userName;
}
