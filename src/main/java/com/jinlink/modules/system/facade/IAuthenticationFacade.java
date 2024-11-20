package com.jinlink.modules.system.facade;

import com.jinlink.modules.system.entity.dto.LoginFormDTO;
import com.jinlink.modules.system.entity.dto.RefreshTokenDTO;
import com.jinlink.modules.system.entity.dto.oAuthLoginDTO;
import com.jinlink.modules.system.entity.vo.SysUserInfoVo;

import java.util.Map;

public interface IAuthenticationFacade {
    Map<String, String> userNameLogin(LoginFormDTO loginFormDTO);

    SysUserInfoVo getUserInfo();

    Map<String, String> userOAuthLogin(oAuthLoginDTO loginFormDTO);

    String logout();

    String refreshToken(String refreshToken);
}
