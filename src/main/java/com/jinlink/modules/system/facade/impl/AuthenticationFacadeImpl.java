package com.jinlink.modules.system.facade.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import com.jinlink.common.exception.JinLinkException;
import com.jinlink.modules.system.entity.*;
import com.jinlink.modules.system.entity.dto.LoginFormDTO;
import com.jinlink.modules.system.entity.vo.SysUserInfoVO;
import com.jinlink.modules.system.facade.IAuthenticationFacade;
import com.jinlink.modules.system.service.*;
import com.mybatisflex.core.query.QueryWrapper;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户认证门面接口实现层
*/
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationFacadeImpl implements IAuthenticationFacade {
    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysUserRoleService sysUserRoleService;
    @Resource
    private SysRolePermissionService sysRolePermissionService;

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
    public SysUserInfoVO getUserInfo() {
        long loginIdAsLong = StpUtil.getLoginIdAsLong();
        if (ObjectUtil.isNull(loginIdAsLong)){
            throw new JinLinkException("用户未登录!");
        }
        SysUser sysUser = sysUserService.getById(loginIdAsLong);
        if (ObjectUtil.isNull(sysUser)){
            throw new JinLinkException("用户不存在!");
        }
        SysUserInfoVO sysUserInfoVo = new SysUserInfoVO();
        sysUserInfoVo.setUserId(sysUser.getId());
        sysUserInfoVo.setUserName(sysUser.getNickName());

        String[] userRoles = sysUserRoleService.getUserRoles(sysUser.getId());
        sysUserInfoVo.setRoles(userRoles);
        //用户拥有角色的按钮权限
        String[] buttons = sysRolePermissionService.getUserPermissions(sysUser.getId());
        sysUserInfoVo.setButtons(buttons);
        return sysUserInfoVo;
    }
}
