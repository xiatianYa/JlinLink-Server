package com.jinlink.modules.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.jinlink.common.exception.JinLinkException;
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
import java.util.stream.Stream;

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
        if (ObjectUtil.isNull(roleId)){
            throw new JinLinkException("非法参数");
        }
        //查询出当前角色拥有那些按钮权限
        List<Long> sysRoleListOne = sysRolePermissionMapper.selectListByQuery(new QueryWrapper()
                        .eq("role_id", roleId))
                .stream().map(SysRolePermission::getPermissionId).toList();
        //前端传递过来的新的按钮Id列表
        List<Long> sysRoleListTwo = sysRolePermissionFormDTO.getPermissionList();

        // 计算需要删除的按钮ID列表（即当前角色拥有但表单中未提交的权限ID）
        List<Long> permissionIdsToRemove = sysRoleListOne.stream()
                .filter(permissionId -> !sysRoleListTwo.contains(permissionId))
                .toList();

        //查询出需要删除的Ids
        List<Long> sysRolePermissionRemoveList = sysRolePermissionMapper.selectListByQuery(new QueryWrapper()
                        .in("permission_id", permissionIdsToRemove))
                .stream().map(SysRolePermission::getId).toList();
        if (ObjectUtil.isNotEmpty(sysRolePermissionRemoveList) && ObjectUtil.isNotEmpty(permissionIdsToRemove))
            LogicDeleteManager.execWithoutLogicDelete(()-> sysRolePermissionMapper.deleteBatchByIds(sysRolePermissionRemoveList));

        // 计算需要添加的按钮ID列表（即当前角色拥有但表单中未提交的权限ID）
        List<Long> permissionIdsToAdd = sysRoleListTwo.stream()
                .filter(permissionId -> !sysRoleListOne.contains(permissionId))
                .toList();
        List<SysRolePermission> sysRolePermissionAddList = permissionIdsToAdd.stream().map(item -> {
            SysRolePermission sysRolePermission = new SysRolePermission();
            sysRolePermission.setPermissionId(item);
            sysRolePermission.setRoleId(roleId);
            return sysRolePermission;
        }).toList();
        if (ObjectUtil.isNotEmpty(sysRolePermissionAddList)) sysRolePermissionMapper.insertBatch(sysRolePermissionAddList);
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
        if (ObjectUtil.isEmpty(userPermissionIds)) return new String[0];
        return sysPermissionService.listByIds(userPermissionIds).stream()
                .map(SysPermission::getCode).toArray(String[]::new);
    }
}
