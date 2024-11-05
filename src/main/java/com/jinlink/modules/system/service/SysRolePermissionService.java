package com.jinlink.modules.system.service;

import com.jinlink.common.api.Result;
import com.jinlink.common.page.PageQuery;
import com.jinlink.modules.system.entity.dto.SysRolePermissionFormDTO;
import com.jinlink.modules.system.entity.vo.SysRolePermissionVo;
import com.mybatisflex.core.paginate.Page;
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

    /**
     * 获取按钮根据角色ID
     */
    List<Long> getPermissionByRoleId(Long roleId);

    /**
     * 修改角色按钮
     */
    @Transactional
    Boolean updateByRoleId(SysRolePermissionFormDTO sysRolePermissionFormDTO);

    /**
     * 获取用户按钮列表
     */
    String[] getUserPermissions(Long id);

    /**
     * 分页查询角色权限管理。
     */
    Page<SysRolePermissionVo> listSysRolePermissionPage(PageQuery query);
}
