package com.jinlink.modules.system.service.impl;

import com.jinlink.common.api.Result;
import com.jinlink.modules.system.entity.dto.SysRoleMenuUpdateDTO;
import com.mybatisflex.core.logicdelete.LogicDeleteManager;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.jinlink.modules.system.entity.SysRoleMenu;
import com.jinlink.modules.system.mapper.SysRoleMenuMapper;
import com.jinlink.modules.system.service.SysRoleMenuService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色菜单管理 服务层实现。
 *
 * @author Summer
 * @since 1.0.0
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {
    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public Result<Boolean> updateRoleMenu(SysRoleMenuUpdateDTO sysRoleMenuUpdateDTO) {
        //先删除之前添加的菜单权限
        QueryWrapper userRoleDeleteQuery = new QueryWrapper();
        userRoleDeleteQuery.eq("role_id",sysRoleMenuUpdateDTO.getRoleId());
        LogicDeleteManager.execWithoutLogicDelete(()->
                sysRoleMenuMapper.deleteByQuery(userRoleDeleteQuery)
        );
        //遍历添加角色菜单权限
        List<Long> menuIds = sysRoleMenuUpdateDTO.getMenuIds();
        for (Long menuId : menuIds) {
            SysRoleMenu sysRoleMenu = SysRoleMenu.builder()
                    .roleId(sysRoleMenuUpdateDTO.getRoleId())
                    .menuId(menuId)
                    .build();
            sysRoleMenuMapper.insert(sysRoleMenu);
        }
        return Result.success("更新成功");
    }

    @Override
    public Result<List<Long>> getRoleMenuByRoleId(Long roleId) {
        //通过角色获取菜单列表
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("role_id",roleId);
        List<SysRoleMenu> sysRoleMenus = sysRoleMenuMapper.selectListByQuery(queryWrapper);
        List<Long> userMenus=new ArrayList<>();
        for (SysRoleMenu sysRoleMenu : sysRoleMenus) {
            if (!userMenus.contains(sysRoleMenu.getMenuId())) userMenus.add(sysRoleMenu.getMenuId());
        }
        return Result.success("获取成功!",userMenus);
    }
}
