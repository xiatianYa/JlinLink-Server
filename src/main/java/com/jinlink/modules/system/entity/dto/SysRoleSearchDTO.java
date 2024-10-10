package com.jinlink.modules.system.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 角色表 查询 DTO 对象
 */

@Getter
@Setter
@Schema(name = "SysRoleSearchDTO", description = "角色表 查询 DTO 对象")
public class SysRoleSearchDTO {
    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 是否启用(0:禁用,1:启用)
     */
    private String status;
}
