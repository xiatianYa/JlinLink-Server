package com.jinlink.modules.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.jinlink.common.exception.JinLinkException;
import com.jinlink.core.page.PageQuery;
import com.jinlink.modules.system.entity.dto.SysRoleFormDTO;
import com.jinlink.modules.system.entity.dto.SysRoleSearchDTO;
import com.jinlink.modules.system.entity.vo.SysRoleOptionVo;
import com.jinlink.modules.system.entity.vo.SysRoleVo;
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
    public Page<SysRoleVo> listRolePage(PageQuery query, SysRoleSearchDTO sysRoleSearchDTO) {
        //构建查询对象
        QueryWrapper queryWrapper = new QueryWrapper()
                .eq("status", sysRoleSearchDTO.getStatus())
                .like("role_name", sysRoleSearchDTO.getRoleName())
                .like("role_code", sysRoleSearchDTO.getRoleCode());
        Page<SysRole> paginate = sysRoleMapper.paginate(query.getCurrent(), query.getSize(), queryWrapper);
        List<SysRole> records = paginate.getRecords();
        List<SysRoleVo> sysRoleVos = BeanUtil.copyToList(records, SysRoleVo.class);
        return new Page<>(sysRoleVos,paginate.getPageNumber(),paginate.getPageSize(),paginate.getTotalRow());
    }

    /**
     * 获取全部角色
     */
    @Override
    public List<SysRoleOptionVo> getAllRoles() {
        return sysRoleMapper.getRoleAll();
    }

    /**
     * 修改角色
     */
    @Override
    public Boolean updateRole(SysRoleFormDTO sysRoleFormDTO) {
        int isTrue = sysRoleMapper.update(sysRoleFormDTO);
        if (ObjectUtil.isNotNull(isTrue)) {
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
