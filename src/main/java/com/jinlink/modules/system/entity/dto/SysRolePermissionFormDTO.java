package com.jinlink.modules.system.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 按钮权限表 修改 DTO 对象
 */

@Getter
@Setter
@Schema(name = "SysRolePermissionFormDTO", description = "按钮权限表 修改 DTO 对象")
public class SysRolePermissionFormDTO {
    /**
     * 角色权限ID
     */
    private Long roleId;
    /**
     * 权限按钮列表
     */
    private List<Long> permissionList;
}
