package com.jinlink.modules.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.jinlink.common.exception.JinLinkException;
import com.jinlink.core.page.PageQuery;
import com.jinlink.modules.system.entity.SysRole;
import com.jinlink.modules.system.entity.vo.SysUserRoleVo;
import com.jinlink.modules.system.service.SysRoleService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.jinlink.modules.system.entity.SysUserRole;
import com.jinlink.modules.system.mapper.SysUserRoleMapper;
import com.jinlink.modules.system.service.SysUserRoleService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户角色管理 服务层实现。
 *
 * @author Summer
 * @since 1.0.0
 */
@Slf4j
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {
    @Resource
    private SysUserRoleMapper sysUserRoleMapper;
    @Resource
    private SysRoleService sysRoleService;

    /**
     *删除用户角色根据ID
     */
    @Override
    public Boolean removeRoleById(Serializable id) {
        int isTrue = sysUserRoleMapper.deleteById(id);
        if (isTrue == 0){
            throw new JinLinkException("删除失败!");
        }
        return true;
    }

    /**
     *删除用户角色根据ID多个
     */
    @Override
    public Boolean removeRoleByIds(List<Long> ids) {
        int isTrue = sysUserRoleMapper.deleteBatchByIds(ids);
        if (isTrue == 0){
            throw new JinLinkException("删除失败!");
        }
        return true;
    }


    /**
     * 获取用户角色列表
     */
    @Override
    public List<String> getUserRoleCodes(Long id) {
        //获取用户角色信息
        List<SysUserRole> sysUserRoles = sysUserRoleMapper.selectListByQuery(new QueryWrapper().eq("user_id",id));
        //查询角色表
        List<SysRole> sysRoles = sysRoleService.
                list(new QueryWrapper().eq("status",1).in("id", sysUserRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toList())));
        //用户所拥有的权限
        return sysRoles.stream().map(SysRole::getRoleCode).toList();
    }

    @Override
    public Page<SysUserRoleVo> listSysUserRolePage(PageQuery query) {
        Page<SysUserRole> paginate = sysUserRoleMapper.paginate(query.getCurrent(), query.getSize(), new QueryWrapper());
        List<SysUserRole> records = paginate.getRecords();
        List<SysUserRoleVo> sysUserRoleVos = BeanUtil.copyToList(records, SysUserRoleVo.class);
        return new Page<>(sysUserRoleVos,paginate.getPageNumber(),paginate.getPageSize(),paginate.getTotalRow());
    }

    @Override
    public List<Long> getUserRoleIds(Long id) {
        //获取用户角色信息
        List<SysUserRole> sysUserRoles = sysUserRoleMapper.selectListByQuery(new QueryWrapper().eq("user_id",id));
        //查询角色表
        List<SysRole> sysRoles = sysRoleService.
                list(new QueryWrapper().eq("status",1).in("id", sysUserRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toList())));
        //用户所拥有的权限
        return sysRoles.stream().map(SysRole::getId).toList();
    }
}
