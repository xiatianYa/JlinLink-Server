package com.jinlink.modules.system.entity.vo;

import com.jinlink.core.domain.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 数据字典子项 OPTIONS VO 对象
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "SysDictItemOptionsVO", description = "数据字典子项 OPTIONS VO 对象")
public class SysDictItemOptionsVo extends BaseVO {

    @Schema(description = "显示的值")
    private String label;

    @Schema(description = "实际值")
    private String value;

    @Schema(description = "类型(前端渲染类型)")
    private String type;

    @Schema(description = "排序")
    private Integer sort;

}
