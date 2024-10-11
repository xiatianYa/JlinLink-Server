package com.jinlink.modules.system.service;

import com.jinlink.common.api.Result;
import com.jinlink.modules.system.entity.dto.SysRoleMenuUpdateDTO;
import com.mybatisflex.core.service.IService;
import com.jinlink.modules.system.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;
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
    @Transactional

    Result<Boolean> updateRoleMenu(SysRoleMenuUpdateDTO sysRoleMenuUpdateDTO);

    Result<List<Long>> getRoleMenuByRoleId(Long roleId);
}
