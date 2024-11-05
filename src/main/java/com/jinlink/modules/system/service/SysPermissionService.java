package com.jinlink.modules.system.service;

import com.jinlink.common.api.Result;
import com.jinlink.common.page.PageQuery;
import com.jinlink.modules.system.entity.vo.SysPermissionTreeVo;
import com.jinlink.modules.system.entity.vo.SysPermissionVo;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.jinlink.modules.system.entity.SysPermission;

import java.util.List;

/**
 * 权限(按钮)管理 服务层。
 *
 * @author Summer
 * @since 1.0.0
 */
public interface SysPermissionService extends IService<SysPermission> {

    /**
     * 获取按钮树
     */
    List<SysPermissionTreeVo> getPermissionTree();

    /**
     * 分页查询权限(按钮)管理。
     */
    Page<SysPermissionVo> listSysPermissionPage(PageQuery query);
}
