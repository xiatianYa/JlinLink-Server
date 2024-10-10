package com.jinlink.modules.system.facade.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import com.jinlink.common.exception.JinLinkException;
import com.jinlink.modules.system.entity.SysRole;
import com.jinlink.modules.system.entity.SysUser;
import com.jinlink.modules.system.entity.SysUserRole;
import com.jinlink.modules.system.entity.dto.LoginFormDTO;
import com.jinlink.modules.system.entity.vo.SysUserInfoVo;
import com.jinlink.modules.system.facade.IAuthenticationFacade;
import com.jinlink.modules.system.service.SysRoleService;
import com.jinlink.modules.system.service.SysUserRoleService;
import com.jinlink.modules.system.service.SysUserService;
import com.mybatisflex.core.query.QueryWrapper;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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
    private SysRoleService sysRoleService;

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
        long loginIdAsLong = StpUtil.getLoginIdAsLong();
        if (ObjectUtil.isNull(loginIdAsLong)){
            throw new JinLinkException("用户未登录!");
        }
        SysUser sysUser = sysUserService.getById(loginIdAsLong);
        if (ObjectUtil.isNull(sysUser)){
            throw new JinLinkException("用户不存在!");
        }
        SysUserInfoVo sysUserInfoVo = new SysUserInfoVo();
        sysUserInfoVo.setUserId(sysUser.getId());
        sysUserInfoVo.setUserName(sysUser.getNickName());
        //获取用户角色信息
        List<SysUserRole> sysUserRoles = sysUserRoleService.list(new QueryWrapper().eq("user_id", sysUser.getId()));
        //查询角色表
        List<SysRole> sysRoles = sysRoleService.
                list(new QueryWrapper().in("id", sysUserRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toList())));
        //用户所拥有的权限
        String[] userRoles = sysRoles.stream().map(SysRole::getRoleCode).toArray(String[]::new);
        sysUserInfoVo.setRoles(userRoles);
        return sysUserInfoVo;
    }
}
