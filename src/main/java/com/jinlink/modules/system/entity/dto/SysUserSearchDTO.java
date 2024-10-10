package com.jinlink.modules.system.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户表 查询 DTO 对象"
 */

@Getter
@Setter
@Schema(name = "SysRoleSearchDTO", description = "用户表 查询 DTO 对象")
public class SysUserSearchDTO {
    /**
     * 用户名
     */
    private String userName;

    /**
     * 性别 0保密 1男 2女
     */
    private String userGender;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 手机
     */
    private String phone;

    /**
     * 邮箱
     */
    private String userEmail;

    /**
     * 是否启用(0:禁用,1:启用)
     */
    private String status;
}
