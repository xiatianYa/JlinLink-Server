package com.jinlink.modules.system.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 数据字典子项管理 查询 DTO 对象
 */

@Getter
@Setter
@Schema(name = "SysDictItemSearchDTO", description = "数据字典子项管理 查询 DTO 对象")
public class SysDictItemSearchDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 4174689758388221049L;

    @Schema(description = "字典ID")
    private Long dictId;

    @Schema(description = "字典值")
    private String value;

    @Schema(description = "中文名称")
    private String zhCn;

    @Schema(description = "英文名称")
    private String enUs;

    @Schema(description = "字典描述")
    private String description;

}