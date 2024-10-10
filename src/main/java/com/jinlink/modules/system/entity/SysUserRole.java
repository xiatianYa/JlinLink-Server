package com.jinlink.modules.system.entity;

import com.jinlink.common.domain.BaseEntity;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;

/**
 * 用户角色管理 实体类。
 *
 * @author Summer
 * @since 1.0.0
 */

@Data
@Table(value = "sys_user_role")
public class SysUserRole extends BaseEntity {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 是否启用(0:禁用,1:启用)
     */
    private String status;

}
