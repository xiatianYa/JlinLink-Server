package com.jinlink.modules.system.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户信息 修改 表单对象
 */

@Getter
@Setter
@Schema(name = "SysUserOneSelfDTO", description = "用户信息 修改 表单对象")
public class SysUserOneSelfDTO {

    @Schema(description = "用户昵称")
    private String userName;

    @Schema(description = "用户手机号")
    private String userPhone;

    @Schema(description = "用户邮箱")
    private String userEmail;

    @Schema(description = "用户性别")
    private String userGender;
}
