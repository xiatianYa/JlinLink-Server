package com.jinlink.common.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 登录用户
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginUser implements Serializable {

    @Serial
    private static final long serialVersionUID = 8648911578225057361L;

    /**
     * 用户id
     */
    private Long id;

    /**
     * 账号
     */
    private String userName;

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
     * 角色IDs
     */
    private List<Long> roleIds;

    /**
     * 角色Codes
     */
    private List<String> roleCodes;

}
