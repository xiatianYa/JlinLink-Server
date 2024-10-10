package com.jinlink.modules.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.jinlink.common.api.Result;
import com.jinlink.common.page.PageQuery;
import com.jinlink.modules.system.entity.dto.SysRoleFormDTO;
import com.jinlink.modules.system.entity.dto.SysRoleSearchDTO;
import com.jinlink.modules.system.entity.vo.SysRoleOptionVo;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.jinlink.modules.system.entity.SysRole;
import com.jinlink.modules.system.mapper.SysRoleMapper;
import com.jinlink.modules.system.service.SysRoleService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色管理 服务层实现。
 *
 * @author Summer
 * @since 1.0.0
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Resource
    private SysRoleMapper sysRoleMapper;

    @Override
    public Page<SysRole> listRolePage(PageQuery query, SysRoleSearchDTO sysRoleSearchDTO) {
        //构建查询对象
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("status", sysRoleSearchDTO.getStatus());
        queryWrapper.like("role_name", sysRoleSearchDTO.getRoleName());
        queryWrapper.like("role_code", sysRoleSearchDTO.getRoleCode());
        return sysRoleMapper.paginate(query.getCurrent(),query.getSize(), queryWrapper);
    }

    @Override
    public List<SysRoleOptionVo> getAllRoles() {
        return sysRoleMapper.getRoleAll();
    }

    @Override
    public Result<String> updateRole(SysRoleFormDTO sysRoleFormDTO) {
        int isTrue = sysRoleMapper.update(sysRoleFormDTO);
        if (ObjectUtil.isNull(isTrue)) {
            return Result.failure("更新失败");
        }else{
            return Result.success("更新成功");
        }
    }

    @Override
    public Result<String> saveRole(SysRoleFormDTO sysRoleFormDTO) {
        int isTrue = sysRoleMapper.insert(sysRoleFormDTO);
        if (ObjectUtil.isNull(isTrue)) {
            return Result.failure("更新失败");
        }else{
            return Result.success("更新成功");
        }
    }

    @Override
    public  Result<String> deleteByIds(List<Long> ids) {
        int isTrue = sysRoleMapper.deleteBatchByIds(ids);
        if (isTrue == 0) {
            return Result.failure("删除失败!");
        }
        return Result.success("操作成功");
    }
}
