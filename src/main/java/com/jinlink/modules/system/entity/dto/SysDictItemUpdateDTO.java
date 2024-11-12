package com.jinlink.modules.system.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 数据字典子项管理 编辑更新 DTO 对象
 */

@Getter
@Setter
@Schema(name = "SysDictItemUpdateDTO", description = "数据字典子项管理 编辑更新 DTO 对象")
public class SysDictItemUpdateDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -5391018643271602831L;

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "数据值")
    private String value;

    @Schema(description = "中文名称")
    private String zhCn;

    @Schema(description = "英文名称")
    private String enUs;

    @Schema(description = "类型(前端渲染类型)")
    private String type;

    @Schema(description = "排序值")
    private Integer sort;

    @Schema(description = "字典描述")
    private String description;

    @Schema(description = "是否启用(0:禁用,1:启用)")
    private String status;


}