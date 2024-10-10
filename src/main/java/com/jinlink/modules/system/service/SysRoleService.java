package com.jinlink.modules.system.service;

import com.jinlink.common.api.Result;
import com.jinlink.common.page.PageQuery;
import com.jinlink.modules.system.entity.dto.SysRoleFormDTO;
import com.jinlink.modules.system.entity.dto.SysRoleSearchDTO;
import com.jinlink.modules.system.entity.vo.SysRoleOptionVo;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.jinlink.modules.system.entity.SysRole;

import java.util.List;

/**
 * 角色管理 服务层。
 *
 * @author Summer
 * @since 1.0.0
 */
public interface SysRoleService extends IService<SysRole> {

    Page<SysRole> listRolePage(PageQuery query, SysRoleSearchDTO sysRoleSearchDTO);

    List<SysRoleOptionVo> getAllRoles();

    Result<String> updateRole(SysRoleFormDTO sysRoleFormDTO);

    Result<String> saveRole(SysRoleFormDTO sysRoleFormDTO);

    Result<String> deleteByIds(List<Long> ids);
}
