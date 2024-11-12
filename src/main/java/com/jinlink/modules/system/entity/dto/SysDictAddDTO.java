package com.jinlink.modules.system.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 数据字典管理 新增 DTO 对象
 */

@Getter
@Setter
@Schema(name = "SysDictAddDTO", description = "数据字典管理 新增 DTO 对象")
public class SysDictAddDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -5121364191638332034L;

    @Schema(description = "字典名称")
    private String name;

    @Schema(description = "字典编码")
    private String code;

    @Schema(description = "字典类型(1:系统字典,2:业务字典)")
    private String type;

    @Schema(description = "排序值")
    private Integer sort;

    @Schema(description = "字典描述")
    private String description;

    @Schema(description = "是否启用(0:禁用,1:启用)")
    private String status;

}