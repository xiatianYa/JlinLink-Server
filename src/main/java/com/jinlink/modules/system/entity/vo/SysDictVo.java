package com.jinlink.modules.system.entity.vo;

import com.jinlink.common.domain.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;

/**
 * 数据字典管理 VO 展示类
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "SysDictVO", description = "数据字典管理 VO 对象")
public class SysDictVo extends BaseVO {

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