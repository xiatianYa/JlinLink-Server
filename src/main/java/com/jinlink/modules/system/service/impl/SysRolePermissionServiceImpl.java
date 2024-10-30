package com.jinlink.modules.system.service.impl;

import com.jinlink.modules.system.entity.SysPermission;
import com.jinlink.modules.system.entity.SysUserRole;
import com.jinlink.modules.system.entity.dto.SysRolePermissionFormDTO;
import com.jinlink.modules.system.service.SysPermissionService;
import com.jinlink.modules.system.service.SysUserRoleService;
import com.mybatisflex.core.logicdelete.LogicDeleteManager;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.jinlink.modules.system.entity.SysRolePermission;
import com.jinlink.modules.system.mapper.SysRolePermissionMapper;
import com.jinlink.modules.system.service.SysRolePermissionService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色权限管理 服务层实现。
 *
 * @author Summer
 * @since 1.0.0
 */
@Slf4j
@Service
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission> implements SysRolePermissionService {
    @Resource
    private SysRolePermissionMapper sysRolePermissionMapper;
    @Resource
    private SysUserRoleService sysUserRoleService;
    @Resource
    private SysPermissionService sysPermissionService;

    /**
     * 获取角色按钮根据角色ID
     */
    @Override
    public List<Long> getPermissionByRoleId(Long roleId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("role_id",roleId);
        List<SysRolePermission> sysRolePermissions = sysRolePermissionMapper.selectListByQuery(queryWrapper);
        return sysRolePermissions.stream()
                .map(SysRolePermission::getPermissionId)
                .collect(Collectors.toList());
    }

    /**
     * 更新角色按钮根据角色ID
     */
    @Override
    public Boolean updateByRoleId(SysRolePermissionFormDTO sysRolePermissionFormDTO) {
        Long roleId = sysRolePermissionFormDTO.getRoleId();
        //删除当前角色用户的权限按钮
        QueryWrapper deleteQuery = new QueryWrapper();
        deleteQuery.eq("role_id",roleId);
        LogicDeleteManager.execWithoutLogicDelete(()->
                sysRolePermissionMapper.deleteByQuery(deleteQuery)
        );
        //新增权限按钮
        List<Long> permissionList = sysRolePermissionFormDTO.getPermissionList();
        permissionList.forEach(item->{
            SysRolePermission sysRolePermission = SysRolePermission.builder()
                    .roleId(roleId)
                    .permissionId(item)
                    .build();
            sysRolePermissionMapper.insert(sysRolePermission);
        });
        return true;
    }

    /**
     * 获取用户按钮列表
     */
    @Override
    public String[] getUserPermissions(Long id) {
        //获取用户角色信息
        List<Long> userRoleIds = sysUserRoleService.list(new QueryWrapper().eq("user_id", id)).stream()
                .map(SysUserRole::getUserId).toList();

        //获取用户拥有的按钮Ids
        List<Long> userPermissionIds = sysRolePermissionMapper.selectListByQuery(new QueryWrapper().in("role_id", userRoleIds)).stream()
                .map(SysRolePermission::getPermissionId).toList();
        //获取用户拥有的按钮
        return sysPermissionService.list(new QueryWrapper().in("id", userPermissionIds)).stream()
                .map(SysPermission::getCode).toArray(String[]::new);
    }
}
