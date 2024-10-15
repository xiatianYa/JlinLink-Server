package com.jinlink.modules.system.facade;

import com.jinlink.modules.system.entity.dto.LoginFormDTO;
import com.jinlink.modules.system.entity.vo.SysUserInfoVO;

import java.util.Map;

public interface IAuthenticationFacade {
    Map<String, String> userNameLogin(LoginFormDTO loginFormDTO);

    SysUserInfoVO getUserInfo();
}
