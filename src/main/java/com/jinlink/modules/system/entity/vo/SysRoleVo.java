package com.jinlink.modules.system.entity.vo;

import com.jinlink.core.domain.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 角色管理 VO 展示类
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "SysRoleVo", description = "角色管理 VO 对象")
public class SysRoleVo extends BaseVO {

    @Schema(description = "角色名称")
    private String roleName;

    @Schema(description = "角色编码")
    private String roleCode;

    @Schema(description = "描述")
    private String roleDesc;

    @Schema(description = "是否启用(0:禁用,1:启用)")
    private String status;
}
