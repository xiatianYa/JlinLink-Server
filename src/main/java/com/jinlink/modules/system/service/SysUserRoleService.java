package com.jinlink.modules.system.service;

import com.jinlink.common.api.Result;
import com.mybatisflex.core.service.IService;
import com.jinlink.modules.system.entity.SysUserRole;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * 用户角色管理 服务层。
 *
 * @author Summer
 * @since 1.0.0
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    @Transactional
    Result<String> removeRoleById(Serializable id);

    @Transactional
    Result<Boolean> removeRoleByIds(List<Long> ids);
}
