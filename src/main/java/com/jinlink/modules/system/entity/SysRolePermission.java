package com.jinlink.modules.system.entity;

import com.jinlink.core.domain.BaseEntity;
import com.mybatisflex.annotation.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 角色权限管理 实体类。
 *
 * @author Summer
 * @since 1.0.0
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "sys_role_permission")
public class SysRolePermission extends BaseEntity {
    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 权限ID
     */
    private Long permissionId;

}
