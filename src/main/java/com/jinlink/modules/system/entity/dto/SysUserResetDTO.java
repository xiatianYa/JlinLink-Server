package com.jinlink.modules.system.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户 重置 表单对象
 */
@Getter
@Setter
@Schema(name = "SysUserResetDTO", description = "用户 重置 表单对象")
public class SysUserResetDTO {

    @Schema(description = "用户昵称")
    private String userName;

    @Schema(description = "用户密码")
    private String password;

}
