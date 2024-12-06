package com.jinlink.modules.system.entity;

import com.jinlink.core.domain.BaseEntity;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * 用户 实体类。
 *
 * @author Summer
 * @since 1.0.0
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "sys_user")
public class SysUser extends BaseEntity {

    /**
     * 用户名
     */
    private String userName;

    /**
     * QQ第三方标识
     */
    private String qqOpenId;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 邮箱
     */
    private String userEmail;

    /**
     * 手机
     */
    private String userPhone;

    /**
     * 性别 0保密 1男 2女
     */
    private String userGender;

    /**
     * 是否启用(0:禁用,1:启用)
     */
    private String status;

    /**
     * 是否重置(0:未重置,1:已重置)
     */
    private String isReset;

    /**
     * MD5的盐值，混淆密码
     */
    private String salt;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 修改密码时间
     */
    private LocalDateTime updatePasswordTime;
}
