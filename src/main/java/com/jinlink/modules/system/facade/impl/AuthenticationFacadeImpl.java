package com.jinlink.modules.system.facade.impl;

import com.jinlink.modules.system.entity.dto.LoginFormDTO;
import com.jinlink.modules.system.entity.dto.RegisterFormDTO;
import com.jinlink.modules.system.entity.dto.oAuthLoginDTO;
import com.jinlink.modules.system.entity.vo.SysUserInfoVo;
import com.jinlink.modules.system.facade.IAuthenticationFacade;
import com.jinlink.modules.system.service.*;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 用户认证门面接口实现层
*/
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationFacadeImpl implements IAuthenticationFacade {
    @Resource
    private SysUserService sysUserService;

    /**
     * 用户登录
     */
    @Override
    public Map<String, String> userNameLogin(LoginFormDTO loginFormDTO) {
        return sysUserService.userNameLogin(loginFormDTO);
    }

    /**
     * 获取用户鉴权信息
     */
    @Override
    public SysUserInfoVo getUserInfo() {
        return sysUserService.getUserInfo();
    }

    /**
     * 用户第三方登陆
     * @param loginFormDTO 登陆数据
     */
    @Override
    public Map<String, String> userOAuthLogin(oAuthLoginDTO loginFormDTO) {
        return sysUserService.userOAuthLogin(loginFormDTO);
    }

    /**
     * 用户退出登陆
     */
    @Override
    public String logout() {
        return sysUserService.logout();
    }

    /**
     * 刷新token
     */
    @Override
    public String refreshToken(String refreshToken) {
        return sysUserService.refreshToken(refreshToken);
    }

    /**
     * 获取用户注册验证码
     */
    @Override
    public byte[] getRegisterCode(String userName) {
        return sysUserService.getRegisterCode(userName);
    }

    /**
     * 用户注册
     */
    @Override
    public Boolean userRegister(RegisterFormDTO registerFormDTO) {
        return sysUserService.userRegister(registerFormDTO);
    }
}
