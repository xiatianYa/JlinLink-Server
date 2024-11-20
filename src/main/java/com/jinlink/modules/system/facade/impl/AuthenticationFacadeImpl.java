package com.jinlink.modules.system.facade.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSONObject;
import com.jinlink.common.constants.Constants;
import com.jinlink.common.exception.JinLinkException;
import com.jinlink.modules.system.entity.*;
import com.jinlink.modules.system.entity.bo.QQBo;
import com.jinlink.modules.system.entity.dto.LoginFormDTO;
import com.jinlink.modules.system.entity.dto.RefreshTokenDTO;
import com.jinlink.modules.system.entity.dto.oAuthLoginDTO;
import com.jinlink.modules.system.entity.vo.SysUserInfoVo;
import com.jinlink.modules.system.facade.IAuthenticationFacade;
import com.jinlink.modules.system.service.*;
import com.mybatisflex.core.query.QueryWrapper;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
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

    @Override
    public String logout() {
        return sysUserService.logout();
    }

    @Override
    public String refreshToken(String refreshToken) {
        return sysUserService.refreshToken(refreshToken);
    }
}
