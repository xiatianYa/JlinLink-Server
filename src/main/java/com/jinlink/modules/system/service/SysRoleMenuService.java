package com.jinlink.modules.system.service;

import com.jinlink.core.page.PageQuery;
import com.jinlink.modules.system.entity.dto.SysRoleMenuUpdateDTO;
import com.jinlink.modules.system.entity.vo.SysRoleMenuVo;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.jinlink.modules.system.entity.SysRoleMenu;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色菜单管理 服务层。
 *
 * @author Summer
 * @since 1.0.0
 */
@Service
public interface SysRoleMenuService extends IService<SysRoleMenu> {

    /**
     * 修改角色菜单。
     */
    @Transactional
    Boolean updateRoleMenu(SysRoleMenuUpdateDTO sysRoleMenuUpdateDTO);

    /**
     * 分页查询角色菜单根据角色ID。
     */
    List<Long> getRoleMenuByRoleId(Long roleId);

    /**
     * 分页查询角色菜单管理。
     */
    Page<SysRoleMenuVo> listRoleMenuPage(PageQuery query);
}
