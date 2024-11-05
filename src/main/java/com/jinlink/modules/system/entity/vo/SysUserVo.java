package com.jinlink.modules.system.entity.vo;

import com.jinlink.modules.system.entity.SysUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * 用户菜单对象
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "SysUserVO", description = "用户菜单 VO 对象")
public class SysUserVo extends SysUser {


    @Schema(description = "用户权限列表")
    private List<String> userRoles;
}
