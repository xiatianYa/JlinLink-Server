package com.jinlink.modules.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.jinlink.common.exception.JinLinkException;
import com.jinlink.common.page.PageQuery;
import com.jinlink.modules.system.entity.dto.SysRoleFormDTO;
import com.jinlink.modules.system.entity.dto.SysRoleSearchDTO;
import com.jinlink.modules.system.entity.vo.SysRoleOptionVO;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.jinlink.modules.system.entity.SysRole;
import com.jinlink.modules.system.mapper.SysRoleMapper;
import com.jinlink.modules.system.service.SysRoleService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色管理 服务层实现。
 *
 * @author Summer
 * @since 1.0.0
 */
@Slf4j
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Resource
    private SysRoleMapper sysRoleMapper;

    /**
     * 获取角色(分页)
     */
    @Override
    public Page<SysRole> listRolePage(PageQuery query, SysRoleSearchDTO sysRoleSearchDTO) {
        //构建查询对象
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("status", sysRoleSearchDTO.getStatus());
        queryWrapper.like("role_name", sysRoleSearchDTO.getRoleName());
        queryWrapper.like("role_code", sysRoleSearchDTO.getRoleCode());
        return sysRoleMapper.paginate(query.getCurrent(),query.getSize(), queryWrapper);
    }

    /**
     * 获取全部角色
     */
    @Override
    public List<SysRoleOptionVO> getAllRoles() {
        return sysRoleMapper.getRoleAll();
    }

    /**
     * 修改角色
     */
    @Override
    public Boolean updateRole(SysRoleFormDTO sysRoleFormDTO) {
        int isTrue = sysRoleMapper.update(sysRoleFormDTO);
        if (ObjectUtil.isNull(isTrue)) {
            return true;
        }
        throw new JinLinkException("删除失败!");
    }

    /**
     * 新增角色
     */
    @Override
    public Boolean saveRole(SysRoleFormDTO sysRoleFormDTO) {
        int isTrue = sysRoleMapper.insert(sysRoleFormDTO);
        if (ObjectUtil.isNull(isTrue)) {
            return true;
        }
        throw new JinLinkException("删除失败!");
    }

    /**
     * 删除角色多个
     */
    @Override
    public Boolean deleteByIds(List<Long> ids) {
        int isTrue = sysRoleMapper.deleteBatchByIds(ids);
        if (isTrue == 0) {
            throw new JinLinkException("删除失败!");
        }
        return true;
    }
}
