package com.jinlink.modules.system.service;

import com.jinlink.common.api.Result;
import com.jinlink.modules.system.entity.dto.SysRolePermissionFormDTO;
import com.mybatisflex.core.service.IService;
import com.jinlink.modules.system.entity.SysRolePermission;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色权限管理 服务层。
 *
 * @author Summer
 * @since 1.0.0
 */
@Service
public interface SysRolePermissionService extends IService<SysRolePermission> {

    Result<List<Long>> getPermissionByRoleId(Long roleId);
    @Transactional

    Result<Boolean> updateByRoleId(SysRolePermissionFormDTO sysRolePermissionFormDTO);
}
