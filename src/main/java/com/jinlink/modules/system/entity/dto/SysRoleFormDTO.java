package com.jinlink.modules.system.entity.dto;

import com.jinlink.modules.system.entity.SysRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 角色新增/修改 表单对象
 */

@Getter
@Setter
@Schema(name = "SysRoleFormDTO", description = "角色新增/修改 DTO 对象")
public class SysRoleFormDTO extends SysRole {
}
