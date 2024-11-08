package com.jinlink.core.holder;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotWebContextException;
import cn.dev33.satoken.stp.StpUtil;
import com.jinlink.common.domain.LoginUser;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 全局用户
 */
@Component
public class GlobalUserHolder {

    private GlobalUserHolder() {

    }

    /**
     * 获取登录用户信息
     *
     * @return {@link LoginUser} 登录用户对象
     */
    public static LoginUser getUser() {
        try {
            return (LoginUser) StpUtil.getSession().get("user");
        } catch (NotLoginException | NotWebContextException exception) {
            return LoginUser.builder().id(-1L).nickName("系统用户").build();
        }
    }

    /**
     * 获取登录用户 ID
     *
     * @return {@link Long} 登录用户 ID
     */
    public static Long getUserId() {
        return getUser().getId();
    }

    /**
     * 获取登录用户名称
     *
     * @return {@link String} 登录用户名称
     */
    public static String getUserName() {
        return getUser().getUserName();
    }

    /**
     * 获取登录用户真实名称
     *
     * @return {@link String} 登录用户真实名称
     */
    public static String getUserRealName() {
        return getUser().getNickName();
    }

    /**
     * 获取登录用户昵称
     *
     * @return {@link String} 登录用户昵称
     */
    public static String getNickName() {
        return getUser().getNickName();
    }

    /**
     * 获取登录用户角色ID列表
     *
     * @return {@link List} 角色ID列表
     */
    public static List<Long> getRoleIds() {
        return getUser().getRoleIds();
    }

    /**
     * 获取登录用户角色Code列表
     *
     * @return {@link List<String>} 角色Code列表
     */
    public static List<String> getRoleCodes() {
        return getUser().getRoleCodes();
    }
}
