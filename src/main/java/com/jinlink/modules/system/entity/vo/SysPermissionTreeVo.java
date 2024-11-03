package com.jinlink.modules.system.entity.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * 按钮管理列表 VO 对象
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Schema(name = "SysPermissionTreeVo", description = "按钮管理列表 VO 对象")
public class SysPermissionTreeVo {
    @Schema(description = "id")
    private Long id;

    @Schema(description = "label")
    private String label;

    @Schema(description = "code")
    private String code;

    @Schema(description = "是否禁止勾选")
    private Boolean checkboxDisabled;

    @Schema(description = "子对象")
    private List<SysPermissionTreeVo> children;
}
