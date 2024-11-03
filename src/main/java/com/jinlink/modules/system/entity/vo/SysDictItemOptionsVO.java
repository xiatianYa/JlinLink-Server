package com.jinlink.modules.system.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

/**
 * 数据字典子项 OPTIONS VO 对象
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "SysDictItemOptionsVO", description = "数据字典子项 OPTIONS VO 对象")
public class SysDictItemOptionsVO implements Serializable {

    @Serial
    private static final long serialVersionUID = -1089041051912903245L;

    @Schema(description = "显示的值")
    private String label;

    @Schema(description = "实际值")
    private String value;

    @Schema(description = "类型(前端渲染类型)")
    private String type;

    @Schema(description = "排序")
    private Integer sort;

}
