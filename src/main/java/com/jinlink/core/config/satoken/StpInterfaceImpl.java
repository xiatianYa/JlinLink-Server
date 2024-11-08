package com.jinlink.core.config.satoken;

import cn.dev33.satoken.stp.StpInterface;
import com.jinlink.modules.system.service.SysRolePermissionService;
import com.jinlink.modules.system.service.SysUserRoleService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * 自定义权限加载接口实现类
 */
@Component
public class StpInterfaceImpl implements StpInterface {
    @Resource
    private SysUserRoleService userRoleService;
    @Resource
    private SysRolePermissionService sysRolePermissionService;
    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return sysRolePermissionService.getUserPermissions(Long.parseLong((String) loginId));
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return userRoleService.getUserRoleCodes(Long.parseLong((String) loginId));
    }
}
