package com.jinlink.modules.system.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 用户菜单权限 修改 DTO 对象
 */

@Getter
@Setter
@Schema(name = "SysRoleMenuUpdateDTO", description = "用户菜单权限 修改 DTO 对象")
public class SysRoleMenuUpdateDTO {

    /**
     * 菜单ID集合
     */
    private List<Long> menuIds;
    /**
     * 角色ID
     */
    private Long roleId;
}
