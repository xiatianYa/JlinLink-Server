package com.jinlink.modules.system.service.impl;

import com.jinlink.common.api.Result;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.jinlink.modules.system.entity.SysUserRole;
import com.jinlink.modules.system.mapper.SysUserRoleMapper;
import com.jinlink.modules.system.service.SysUserRoleService;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * 用户角色管理 服务层实现。
 *
 * @author Summer
 * @since 1.0.0
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    @Override
    public Result<String> removeRoleById(Serializable id) {
        return null;
    }

    @Override
    public Result<Boolean> removeRoleByIds(List<Long> ids) {
        return null;
    }
}
