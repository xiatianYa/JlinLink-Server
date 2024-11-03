package com.jinlink.modules.system.entity.vo;

import com.jinlink.common.domain.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;

/**
 * 数据字典子项管理 VO 展示类
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "SysDictItemVO", description = "数据字典子项管理 VO 对象")
public class SysDictItemVO extends BaseVO {

    @Serial
    private static final long serialVersionUID = -7429297391463712321L;

    @Schema(description = "父字典ID")
    private Long dictId;

    @Schema(description = "父字典编码")
    private String dictCode;

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
