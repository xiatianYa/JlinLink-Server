package com.jinlink.modules.system.service.impl;

import com.jinlink.common.api.Result;
import com.jinlink.modules.system.entity.vo.SysPermissionVo;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.jinlink.modules.system.entity.SysPermission;
import com.jinlink.modules.system.mapper.SysPermissionMapper;
import com.jinlink.modules.system.service.SysPermissionService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限(按钮)管理 服务层实现。
 *
 * @author Summer
 * @since 1.0.0
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {
    @Resource
    private SysPermissionMapper sysPermissionMapper;

    @Override
    public Result<List<SysPermissionVo>> listAll() {
        //查询全部权限按钮
        List<SysPermission> sysPermissions = sysPermissionMapper.selectAll();
        //返回对接
        List<SysPermissionVo> sysPermissionVoList = new ArrayList<>();
        for (SysPermission sysPermission : sysPermissions) {
            SysPermissionVo sysPermissionVo = SysPermissionVo.builder()
                    .id(sysPermission.getId())
                    .code(sysPermission.getCode())
                    .label(sysPermission.getDescription())
                    .build();
            sysPermissionVoList.add(sysPermissionVo);
        }
        return Result.success("操作成功!", sysPermissionVoList);
    }
}
