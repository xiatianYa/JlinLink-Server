package com.jinlink.modules.system.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户登录对象
 */

@Getter
@Setter
@Schema(name = "LoginFormDTO", description = "用户 登录 DTO 对象")
public class LoginFormDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 3098906381389407972L;

    @NotBlank
    @Schema(description = "用户名")
    private String userName;

    @NotBlank
    @Schema(description = "密码")
    private String password;
}
