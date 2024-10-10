package com.jinlink.modules.system.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.jinlink.common.api.Result;
import com.jinlink.common.domain.KVPairs;
import com.jinlink.common.page.PageQuery;
import com.jinlink.common.page.RPage;
import com.jinlink.modules.system.entity.SysRoleMenu;
import com.jinlink.modules.system.entity.SysUserRole;
import com.jinlink.modules.system.entity.dto.SysMenuFormDTO;
import com.jinlink.modules.system.entity.vo.SysMenuTreeVO;
import com.jinlink.modules.system.entity.vo.SysMenuVO;
import com.jinlink.modules.system.entity.vo.SysUserRouteVO;
import com.jinlink.modules.system.service.SysRoleMenuService;
import com.jinlink.modules.system.service.SysUserRoleService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.jinlink.modules.system.entity.SysMenu;
import com.jinlink.modules.system.mapper.SysMenuMapper;
import com.jinlink.modules.system.service.SysMenuService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

/**
 * 菜单管理 服务层实现。
 *
 * @author Summer
 * @since 1.0.0
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
    @Resource
    private SysMenuMapper sysMenuMapper;
    @Resource
    private SysUserRoleService sysUserRoleService;
    @Resource
    private SysRoleMenuService sysRoleMenuService;

    @Override
    public List<String> getAllPages() {
        return sysMenuMapper.getAllPages();
    }

    @Override
    public List<SysMenuTreeVO> getMenuTree() {
        List<SysMenu> sysMenus = sysMenuMapper.selectAll();
        return initMenuChildren(0L,sysMenus);
    }

    @Override
    public RPage<SysMenuVO> getMenuList(PageQuery query) {
        Page<SysMenu> paginate = sysMenuMapper.paginate(query.getCurrent(), query.getSize(), new QueryWrapper());
        return iniSysMenuChildren(0L, paginate);
    }

    @Override
    public Result<String> updateMenu(SysMenuFormDTO sysMenuVo) {
        //拷贝属性到SysMenu
        SysMenu sysMenu = BeanUtil.copyProperties(sysMenuVo, SysMenu.class);
        sysMenu.setQuery(JSON.toJSONString(sysMenuVo.getQuery()));
        int isTrue = sysMenuMapper.update(sysMenu);
        if (ObjectUtil.isNotNull(isTrue)){
            return Result.success("更改成功!");
        }else {
            return Result.failure("更改失败!");
        }
    }

    @Override
    public Result<String> saveMenu(SysMenuFormDTO sysMenuVo) {
        //拷贝属性到SysMenu
        SysMenu sysMenu = BeanUtil.copyProperties(sysMenuVo, SysMenu.class);
        int isTrue = sysMenuMapper.insert(sysMenu);
        if (ObjectUtil.isNotNull(isTrue)) {
            return Result.success("新增成功!");
        }else{
            return Result.failure("新增失败!");
        }
    }

    /**
     * 删除菜单 及 子菜单
     * @param id
     * @return
     */
    @Override
    public Result<Boolean> removeMenuById(Serializable id) {
        //先删除当前菜单
        int isTrue = sysMenuMapper.deleteById(id);
        if (ObjectUtil.isNotNull(isTrue)){
            //删除当前菜单下的子菜单
            sysMenuMapper.deleteByQuery(new QueryWrapper().eq("parent_id", id));
            return Result.success("删除成功!", true);
        }else{
            return Result.failure("删除失败!");
        }
    }

    /**
     * 删除多个菜单 及其下 子菜单
     * @param ids
     */
    @Override
    public Result<Boolean> removeMenuByIds(List<Long> ids) {
        int isTrue = sysMenuMapper.deleteBatchByIds(ids);
        if (ObjectUtil.isNotNull(isTrue)){
            //删除当前菜单下的子菜单
            sysMenuMapper.deleteByQuery(new QueryWrapper().in("parent_id", ids));
            return Result.success("删除成功!", true);
        }else{
            return Result.failure("删除失败!");
        }
    }

    @Override
    public Result<SysUserRouteVO> getUserRoutes() {
        List<SysUserRouteVO.Route> resultRoute = new ArrayList<>();
        //查询用户角色
        List<SysUserRole> sysUserRoles = sysUserRoleService
                .list(new QueryWrapper().eq("user_id", StpUtil.getLoginIdAsLong()));
        //用户角色列表
        List<Long> roleIds = sysUserRoles.stream().map(SysUserRole::getRoleId).toList();
        //通过用户角色查询角色下拥有的菜单权限
        Set<Long> menuIds = new LinkedHashSet<>();
        List<SysRoleMenu> sysRoleMenus = sysRoleMenuService.list(new QueryWrapper()
                        .in("role_id", roleIds)).stream()
                .filter(item -> menuIds.add(item.getMenuId()))
                .toList();
        //查询用户菜单信息
        for (SysRoleMenu sysRoleMenu : sysRoleMenus) {
            //父路由
            SysMenu sysMenu = sysMenuMapper.selectOneByQuery(new QueryWrapper().eq("id", sysRoleMenu.getMenuId()).eq("parent_id",0));
            if (ObjectUtil.isNull(sysMenu)) continue;
            //查询当前路由下的子路由
            List<SysMenu> childrens = sysMenuMapper.selectListByQuery(new QueryWrapper().eq("parent_id", sysRoleMenu.getMenuId()));
            SysUserRouteVO.Route route = SysUserRouteVO.Route.builder()
                    .name(sysMenu.getRouteName())
                    .path(sysMenu.getRoutePath())
                    .component(sysMenu.getComponent())
                    .children(new ArrayList<>())
                    .meta(SysUserRouteVO.Meta.builder()
                            .title(sysMenu.getRouteName())
                            .i18nKey(sysMenu.getI18nKey())
                            .keepAlive(sysMenu.getKeepAlive().equals("Y"))
                            .constant(sysMenu.getConstant().equals("Y"))
                            .icon(sysMenu.getIconType().equals("1") ? sysMenu.getIcon() : null)
                            .localIcon(sysMenu.getIconType().equals("2") ? sysMenu.getIcon() : null)
                            .order(sysMenu.getSort())
                            .href(sysMenu.getHref())
                            .hideInMenu(sysMenu.getHide().equals("Y"))
                            .activeMenu(sysMenu.getActiveMenu())
                            .multiTab(sysMenu.getMultiTab().equals("Y"))
                            .fixedIndexInTab(sysMenu.getFixedIndexInTab())
                            .query(JSON.parseObject(sysMenu.getQuery(), List.class))
                            .build())
                    .build();
            //添加子路由
            for (SysMenu children : childrens) {
                SysUserRouteVO.Route childrenRouter = SysUserRouteVO.Route.builder()
                        .name(children.getRouteName())
                        .path(children.getRoutePath())
                        .component(children.getComponent())
                        .meta(SysUserRouteVO.Meta.builder()
                                .title(children.getRouteName())
                                .i18nKey(children.getI18nKey())
                                .keepAlive(children.getKeepAlive().equals("Y"))
                                .constant(children.getConstant().equals("Y"))
                                .icon(children.getIconType().equals("1") ? children.getIcon() : null)
                                .localIcon(children.getIconType().equals("2") ? children.getIcon() : null)
                                .order(children.getSort())
                                .href(children.getHref())
                                .hideInMenu(children.getHide().equals("Y"))
                                .activeMenu(children.getActiveMenu())
                                .multiTab(children.getMultiTab().equals("Y"))
                                .fixedIndexInTab(children.getFixedIndexInTab())
                                .query(JSON.parseObject(children.getQuery(), List.class))
                                .build())
                        .build();
                route.getChildren().add(childrenRouter);
            }
            resultRoute.add(route);
        }
        SysUserRouteVO routers = SysUserRouteVO.builder()
                .home("home")
                .routes(resultRoute)
                .build();
        return Result.success("请求成功",routers);
    }

    private RPage<SysMenuVO> iniSysMenuChildren(Long parentId, Page<SysMenu> paginate) {
        List<SysMenu> sysMenus = paginate.getRecords();
        // 根据 parentId 获取菜单列表
        List<SysMenu> parentMenuList = sysMenus.stream()
                .filter(item -> item.getParentId().equals(parentId)).toList();
        List<SysMenuVO> menuPageVOList =new ArrayList<>();
        parentMenuList.forEach(item -> {
            //拷贝到新列表中
            menuPageVOList.add( BeanUtil.copyProperties(item, SysMenuVO.class));
        });
        sysMenus.forEach(item -> {
            // 递归获取子菜单
            if (item.getId() != 0L){
                //查询子菜单属于那个父菜单 添加到父菜单内
                menuPageVOList.forEach(menu->{
                    if(ObjectUtil.isNull(menu.getChildren())) menu.setChildren(new ArrayList<>());
                    if(item.getParentId().equals(menu.getId())){
                        //拷贝到新列表中
                        menu.getChildren().add(BeanUtil.copyProperties(item, SysMenuVO.class));
                    }
                });
            }
        });
        return RPage.build(new Page<>(menuPageVOList, paginate.getPageNumber(), paginate.getPageSize(),menuPageVOList.size()));
    }

    private  List<SysMenuTreeVO> initMenuChildren(Long parentId, List<SysMenu> sysMenus) {
        // 根据 parentId 获取菜单列表
        List<SysMenu> parentMenuList = sysMenus.stream()
                .filter(item -> item.getParentId().equals(parentId)).toList();
        List<SysMenuTreeVO> menuPageVOList =new ArrayList<>();
        parentMenuList.forEach(item -> {
            //拷贝到新列表中
            SysMenuTreeVO sysMenuTreeVO = BeanUtil.copyProperties(item, SysMenuTreeVO.class);
            sysMenuTreeVO.setLabel(item.getMenuName());
            menuPageVOList.add(sysMenuTreeVO);
        });
        sysMenus.forEach(item -> {
            // 递归获取子菜单
            if (item.getId() != 0L){
                //查询子菜单属于那个父菜单 添加到父菜单内
                menuPageVOList.forEach(menu->{
                    if(ObjectUtil.isNull(menu.getChildren())) menu.setChildren(new ArrayList<>());
                    if(item.getParentId().equals(menu.getId())){
                        //拷贝到新列表中
                        SysMenuTreeVO sysMenuTreeVO = BeanUtil.copyProperties(item, SysMenuTreeVO.class);
                        sysMenuTreeVO.setLabel(item.getMenuName());
                        menu.getChildren().add(sysMenuTreeVO);
                    }
                });
            }
        });
        // 按照排序值排序
        menuPageVOList.sort(Comparator.comparing(SysMenuTreeVO::getSort));
        return menuPageVOList;
    }
}
