package com.jinlink.modules.system.service;

import com.jinlink.common.page.PageQuery;
import com.jinlink.modules.system.entity.dto.SysRoleFormDTO;
import com.jinlink.modules.system.entity.dto.SysRoleSearchDTO;
import com.jinlink.modules.system.entity.vo.SysRoleOptionVo;
import com.jinlink.modules.system.entity.vo.SysRoleVo;
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

    /**
     * 获取角色(分页)
     */
    Page<SysRoleVo> listRolePage(PageQuery query, SysRoleSearchDTO sysRoleSearchDTO);

    /**
     * 获取全部角色
     */
    List<SysRoleOptionVo> getAllRoles();

    /**
     * 修改角色
     */
    Boolean updateRole(SysRoleFormDTO sysRoleFormDTO);

    /**
     * 新增角色
     */
    Boolean saveRole(SysRoleFormDTO sysRoleFormDTO);

    /**
     * 删除角色多个
     */
    Boolean deleteByIds(List<Long> ids);
}
