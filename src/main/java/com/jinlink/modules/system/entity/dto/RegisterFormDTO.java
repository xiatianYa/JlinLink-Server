package com.jinlink.modules.system.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户注册对象
 */

@Getter
@Setter
@Schema(name = "RegisterFormDTO", description = "用户 注册 DTO 对象")
public class RegisterFormDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 3098906381389407972L;

    @NotBlank
    @Schema(description = "用户名")
    private String userName;

    @NotBlank
    @Schema(description = "用户昵称")
    private String nickName;

    @NotBlank
    @Schema(description = "用户头像")
    private String avatar;

    @NotBlank
    @Schema(description = "用户密码")
    private String password;

    @NotBlank
    @Schema(description = "用户确认密码")
    private String confirmPassword;

    @NotBlank
    @Schema(description = "验证码")
    private String code;
}
