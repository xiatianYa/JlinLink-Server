package com.jinlink.modules.system.service;

import com.jinlink.common.domain.Options;
import com.jinlink.core.page.PageQuery;
import com.jinlink.modules.system.entity.dto.*;
import com.jinlink.modules.system.entity.vo.SysUserInfoVo;
import com.jinlink.modules.system.entity.vo.SysUserVo;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.jinlink.modules.system.entity.SysUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 用户管理 服务层。
 *
 * @author Summer
 * @since 1.0.0
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 用户登录
     */
    Map<String, String> userNameLogin(LoginFormDTO loginFormDTO);

    /**
     * 获取全部用户(分页)
     */
    Page<SysUserVo> listUserPage(PageQuery query, SysUserSearchDTO sysUserSearchDTO);

    /**
     * 删除用户多个
     */
    @Transactional
    Boolean removeByIds(List<Long> ids);

    /**
     * 修改用户
     */
    @Transactional
    Boolean updateUser(SysUserFormDTO sysUser);

    /**
     * 新增用户
     */
    @Transactional
    Boolean saveUser(SysUserFormDTO sysUser);

    /**
     * 根据用户Ids 获取用户名称Options
     */
    List<Options<String>> getAllUserNames();

    /**
     * 用户第三方登录
     */
    Map<String, String> userOAuthLogin(oAuthLoginDTO loginFormDTO);

    /**
     * 获取用户基本信息
     */
    SysUserInfoVo getUserInfo();

    /**
     * 用户退出登录
     */
    String logout();

    /**
     * 刷新Token
     */
    String refreshToken(String refreshToken);

    /**
     * 获取用户注册验证码
     */
    byte[] getRegisterCode(String userName);

    /**
     * 用户注册
     */
    @Transactional
    Boolean userRegister(RegisterFormDTO registerFormDTO);

    /**
     * 根据主键更新自己用户信息。
     */
    @Transactional
    Boolean updateOneSelf(SysUserOneSelfDTO sysUserOneSelfDTO);

    /**
     * 更新自己密码。
     */
    @Transactional
    Boolean updatePassword(SysUserPasswordDTO sysUserPasswordDTO);

    /**
     * 重置账号名和密码。
     */
    @Transactional
    Boolean reset(SysUserResetDTO sysUserResetDTO);
}
