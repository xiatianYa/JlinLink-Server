package com.jinlink.modules.system.service.impl;

import com.jinlink.common.api.Result;
import com.jinlink.modules.system.entity.dto.SysRolePermissionFormDTO;
import com.mybatisflex.core.logicdelete.LogicDeleteManager;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.jinlink.modules.system.entity.SysRolePermission;
import com.jinlink.modules.system.mapper.SysRolePermissionMapper;
import com.jinlink.modules.system.service.SysRolePermissionService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色权限管理 服务层实现。
 *
 * @author Summer
 * @since 1.0.0
 */
@Service
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission> implements SysRolePermissionService {
    @Resource
    private SysRolePermissionMapper sysRolePermissionMapper;

    @Override
    public Result<List<Long>> getPermissionByRoleId(Long roleId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("role_id",roleId);
        List<SysRolePermission> sysRolePermissions = sysRolePermissionMapper.selectListByQuery(queryWrapper);
        List<Long> resultData = sysRolePermissions.stream()
                .map(SysRolePermission::getPermissionId)
                .collect(Collectors.toList());
        return Result.success("操作成功!",resultData);
    }

    @Override
    public Result<Boolean> updateByRoleId(SysRolePermissionFormDTO sysRolePermissionFormDTO) {
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
        return Result.success("操作成功!",true);
    }
}
