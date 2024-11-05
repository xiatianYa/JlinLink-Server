package com.jinlink.modules.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.jinlink.common.page.PageQuery;
import com.jinlink.modules.system.entity.SysMenu;
import com.jinlink.modules.system.entity.vo.SysPermissionTreeVo;
import com.jinlink.modules.system.entity.vo.SysPermissionVo;
import com.jinlink.modules.system.mapper.SysMenuMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.jinlink.modules.system.entity.SysPermission;
import com.jinlink.modules.system.mapper.SysPermissionMapper;
import com.jinlink.modules.system.service.SysPermissionService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限(按钮)管理 服务层实现。
 *
 * @author Summer
 * @since 1.0.0
 */
@Slf4j
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {
    @Resource
    private SysPermissionMapper sysPermissionMapper;
    @Resource
    private SysMenuMapper sysMenuMapper;

    /**
     * 查询全部按钮权限列表
     */
    @Override
    public List<SysPermissionTreeVo> getPermissionTree() {
        //返回数据列表
        List<SysPermissionTreeVo> sysPermissionTreeVos = new ArrayList<>();
        //查询所有的菜单
        List<SysMenu> sysMenuList = sysMenuMapper.selectAll();
        //查询所有的按钮
        List<SysPermission> sysPermissionList = sysPermissionMapper.selectAll();
        sysMenuList.forEach(item->{
            SysPermissionTreeVo sysPermissionTreeVo = SysPermissionTreeVo.builder()
                    .id(RandomUtil.randomLong())
                    .label(item.getMenuName())
                    .children(new ArrayList<>())
                    .checkboxDisabled(true)
                    .build();
            //获取子菜单权限
            List<SysPermission> sysPermissions = sysPermissionList.stream().filter(permission -> permission.getMenuId().equals(item.getId())).toList();
            if (ObjectUtil.isNotEmpty(sysPermissions)){
                sysPermissions.forEach(permission->{
                    SysPermissionTreeVo children = SysPermissionTreeVo.builder()
                            .id(permission.getId())
                            .label(permission.getDescription())
                            .code(permission.getCode())
                            .checkboxDisabled(false)
                            .build();
                    sysPermissionTreeVo.getChildren().add(children);
                });
                sysPermissionTreeVos.add(sysPermissionTreeVo);
            }
        });
        return sysPermissionTreeVos;
    }

    @Override
    public Page<SysPermissionVo> listSysPermissionPage(PageQuery query) {
        Page<SysPermission> paginate = sysPermissionMapper.paginate(query.getCurrent(), query.getSize(), new QueryWrapper());
        List<SysPermission> records = paginate.getRecords();
        List<SysPermissionVo> sysPermissionVos = BeanUtil.copyToList(records, SysPermissionVo.class);
        return new Page<>(sysPermissionVos,paginate.getPageNumber(),paginate.getPageSize(),paginate.getTotalRow());
    }
}
