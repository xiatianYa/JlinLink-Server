package com.jinlink.modules.system.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户 修改密码 表单对象
 */

@Getter
@Setter
@Schema(name = "SysUserPasswordDTO", description = "用户 修改密码 表单对象")
public class SysUserPasswordDTO {

    @Schema(description = "旧密码")
    private String oldPassword;

    @Schema(description = "新密码")
    private String newPassword;

    @Schema(description = "确认密码")
    private String confirmPassword;

}
