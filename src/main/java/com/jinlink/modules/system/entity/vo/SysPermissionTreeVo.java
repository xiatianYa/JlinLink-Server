package com.jinlink.modules.system.entity.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jinlink.common.domain.BaseVO;
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
@Schema(name = "SysPermissionTreeVo", description = "按钮管理列表 VO 对象")
public class SysPermissionTreeVo extends BaseVO {

    @Schema(description = "label")
    private String label;

    @Schema(description = "code")
    private String code;

    @Schema(description = "是否禁止勾选")
    private Boolean checkboxDisabled;

    @Schema(description = "子对象")
    private List<SysPermissionTreeVo> children;
}
