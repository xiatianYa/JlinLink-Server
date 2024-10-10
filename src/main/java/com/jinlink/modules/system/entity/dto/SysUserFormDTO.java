package com.jinlink.modules.system.entity.dto;

import com.jinlink.modules.system.entity.SysUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * 用户新增/修改 表单对象
 */

@Getter
@Setter
@Schema(name = "SysUserFormDTO", description = "用户新增/修改 DTO 对象")
public class SysUserFormDTO extends SysUser {
    /**
     * 用户权限列表
     */
    private List<String> userRoles;
}
