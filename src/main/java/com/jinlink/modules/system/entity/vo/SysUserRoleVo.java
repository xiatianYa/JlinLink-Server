package com.jinlink.modules.system.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 用户角色管理 VO 对象
 */


@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "SysUserRoleVo", description = "用户角色管理 VO 对象")
public class SysUserRoleVo {

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "角色ID")
    private Long roleId;

    @Schema(description = "是否启用(0:禁用,1:启用)")
    private String status;
}
