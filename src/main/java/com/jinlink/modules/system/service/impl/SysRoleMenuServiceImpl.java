package com.jinlink.modules.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.jinlink.core.page.PageQuery;
import com.jinlink.modules.system.entity.dto.SysRoleMenuUpdateDTO;
import com.jinlink.modules.system.entity.vo.SysRoleMenuVo;
import com.mybatisflex.core.logicdelete.LogicDeleteManager;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.jinlink.modules.system.entity.SysRoleMenu;
import com.jinlink.modules.system.mapper.SysRoleMenuMapper;
import com.jinlink.modules.system.service.SysRoleMenuService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色菜单管理 服务层实现。
 *
 * @author Summer
 * @since 1.0.0
 */
@Slf4j
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {
    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;

    /**
     * 修改角色菜单
     */
    @Override
    public Boolean updateRoleMenu(SysRoleMenuUpdateDTO sysRoleMenuUpdateDTO) {
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
        return true;
    }

    /**
     * 获取角色菜单根据角色ID
     */
    @Override
    public List<Long> getRoleMenuByRoleId(Long roleId) {
        //通过角色获取菜单列表
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("role_id",roleId);
        List<SysRoleMenu> sysRoleMenus = sysRoleMenuMapper.selectListByQuery(queryWrapper);
        List<Long> userMenus=new ArrayList<>();
        for (SysRoleMenu sysRoleMenu : sysRoleMenus) {
            if (!userMenus.contains(sysRoleMenu.getMenuId())) userMenus.add(sysRoleMenu.getMenuId());
        }
        return userMenus;
    }

    @Override
    public Page<SysRoleMenuVo> listRoleMenuPage(PageQuery query) {
        Page<SysRoleMenu> paginate = sysRoleMenuMapper.paginate(query.getCurrent(), query.getSize(), new QueryWrapper());
        List<SysRoleMenu> records = paginate.getRecords();
        List<SysRoleMenuVo> sysRoleMenuVos = BeanUtil.copyToList(records, SysRoleMenuVo.class);
        return new Page<>(sysRoleMenuVos,paginate.getPageNumber(),paginate.getPageSize(),paginate.getTotalRow());
    }
}
