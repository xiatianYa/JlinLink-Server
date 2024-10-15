package com.jinlink.modules.system.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.jinlink.common.api.Result;
import com.jinlink.common.page.PageQuery;
import com.jinlink.common.page.RPage;
import com.jinlink.modules.system.entity.SysPermission;
import com.jinlink.modules.system.entity.SysRoleMenu;
import com.jinlink.modules.system.entity.SysUserRole;
import com.jinlink.modules.system.entity.dto.SysMenuFormDTO;
import com.jinlink.common.domain.BTPairs;
import com.jinlink.modules.system.entity.vo.SysMenuTreeVO;
import com.jinlink.modules.system.entity.vo.SysMenuVO;
import com.jinlink.modules.system.entity.vo.SysUserRouteVO;
import com.jinlink.modules.system.service.*;
import com.mybatisflex.core.logicdelete.LogicDeleteManager;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.jinlink.modules.system.entity.SysMenu;
import com.jinlink.modules.system.mapper.SysMenuMapper;
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
    @Resource
    private SysPermissionService sysPermissionService;
    @Resource
    private SysRolePermissionService sysRolePermissionService;

    @Override
    public List<String> getAllPages() {
        return sysMenuMapper.getAllPages();
    }

    @Override
    public List<SysMenuTreeVO> getMenuTree() {
        List<SysMenu> sysMenus = sysMenuMapper.selectAll();
        return initMenuChildren(sysMenus);
    }

    @Override
    public RPage<SysMenuVO> getMenuList(PageQuery query) {
        Page<SysMenu> paginate = sysMenuMapper.paginate(query.getCurrent(), query.getSize(), new QueryWrapper());
        return iniSysMenuChildren(paginate);
    }

    @Override
    public Result<String> updateMenu(SysMenuFormDTO sysMenuVo) {
        //拷贝属性到SysMenu
        SysMenu sysMenu = BeanUtil.copyProperties(sysMenuVo, SysMenu.class);
        //转换属性
        sysMenu.setKeepAlive(sysMenuVo.getKeepAlive()? "Y" : "N");
        sysMenu.setHideInMenu(sysMenuVo.getHideInMenu()? "Y" : "N");
        sysMenu.setMultiTab(sysMenuVo.getMultiTab() ? "Y" : "N");
        sysMenu.setConstant(sysMenuVo.getConstant() ? "Y" : "N");
        sysMenu.setQuery(JSON.toJSONString(sysMenuVo.getQuery()));
        //添加按钮配置
        List<BTPairs> buttons = sysMenuVo.getButtons();
        //删除不在这个按钮列表的按钮
        QueryWrapper deletePermissionWrapper = new QueryWrapper();
        deletePermissionWrapper.eq("menu_id",sysMenu.getId());
        deletePermissionWrapper.notIn("code",buttons.stream().map(BTPairs::getCode).toArray());
        List<Long> sysPermissionDeleteList = sysPermissionService.list(deletePermissionWrapper).stream().map(SysPermission::getId).toList();
        //删除按钮表数据
        LogicDeleteManager.execWithoutLogicDelete(()->
                sysPermissionService.remove(deletePermissionWrapper)
        );
        //删除角色按钮关联表数据
        if (ObjectUtil.isNotEmpty(sysPermissionDeleteList)){
            QueryWrapper deleteRolePermissionWrapper = new QueryWrapper();
            deleteRolePermissionWrapper.in("permission_id",sysPermissionDeleteList);
            LogicDeleteManager.execWithoutLogicDelete(()->
                    sysRolePermissionService.remove(deleteRolePermissionWrapper)
            );
        }
        buttons.forEach(button->{
            //查询有没有这个按钮 有则添加 没用则删除
            QueryWrapper getOneWrapper = new QueryWrapper();
            getOneWrapper.eq("menu_id",sysMenu.getId()).eq("code",button.getCode());
            SysPermission one = sysPermissionService.getOne(getOneWrapper);
            if (ObjectUtil.isNotNull(one)){
                //修改
                one.setDescription(button.getDesc());
                sysPermissionService.updateById(one);
            }else{
                //新增
                SysPermission sysPermission = SysPermission.builder()
                        .menuId(sysMenu.getId())
                        .menuName(sysMenu.getMenuName())
                        .code(button.getCode())
                        .description(button.getDesc())
                        .build();
                sysPermissionService.save(sysPermission);
            }
        });
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
        //转换属性
        sysMenu.setKeepAlive(sysMenuVo.getKeepAlive()? "Y" : "N");
        sysMenu.setHideInMenu(sysMenuVo.getHideInMenu()? "Y" : "N");
        sysMenu.setMultiTab(sysMenuVo.getMultiTab() ? "Y" : "N");
        sysMenu.setConstant(sysMenuVo.getConstant() ? "Y" : "N");
        sysMenu.setQuery(JSON.toJSONString(sysMenuVo.getQuery()));
        //添加按钮配置
        List<BTPairs> buttons = sysMenuVo.getButtons();
        //添加现有按钮
        buttons.forEach(item->{
            SysPermission sysPermission = SysPermission.builder()
                    .menuId(sysMenu.getId())
                    .menuName(sysMenu.getMenuName())
                    .code(item.getCode())
                    .description(item.getDesc())
                    .build();
            sysPermissionService.save(sysPermission);
        });
        int isTrue = sysMenuMapper.insert(sysMenu);
        if (ObjectUtil.isNotNull(isTrue)) {
            return Result.success("新增成功!");
        }else{
            return Result.failure("新增失败!");
        }
    }

    /**
     * 删除菜单 及 子菜单
     */
    @Override
    public Result<Boolean> removeMenuById(Serializable id) {
        //先删除当前菜单
        LogicDeleteManager.execWithoutLogicDelete(()->
                sysMenuMapper.deleteById(id)
        );
        //删除当前菜单下的子菜单
        sysMenuMapper.deleteByQuery(new QueryWrapper().eq("parent_id", id));

        //删除角色菜单表关联
        LogicDeleteManager.execWithoutLogicDelete(()->
                sysRoleMenuService.remove(new QueryWrapper().eq("menu_id",id))
        );

        //删除按钮表数据
        QueryWrapper deletePermissionWrapper = new QueryWrapper();
        deletePermissionWrapper.eq("menu_id",id);
        List<Long> sysPermissionDeleteList = sysPermissionService.list(deletePermissionWrapper).stream().map(SysPermission::getId).toList();
        LogicDeleteManager.execWithoutLogicDelete(()->
                sysPermissionService.remove(deletePermissionWrapper)
        );

        //删除角色按钮关联表数据
        if (ObjectUtil.isNotEmpty(sysPermissionDeleteList)){
            QueryWrapper deleteRolePermissionWrapper = new QueryWrapper();
            deleteRolePermissionWrapper.in("permission_id",sysPermissionDeleteList);
            LogicDeleteManager.execWithoutLogicDelete(()->
                    sysRolePermissionService.remove(deleteRolePermissionWrapper)
            );
        }
        return Result.success("删除成功!", true);
    }

    /**
     * 删除多个菜单 及其下 子菜单
     */
    @Override
    public Result<Boolean> removeMenuByIds(List<Long> ids) {
        LogicDeleteManager.execWithoutLogicDelete(()->
                sysMenuMapper.deleteBatchByIds(ids)
        );
        //删除当前菜单下的子菜单
        sysMenuMapper.deleteByQuery(new QueryWrapper().in("parent_id", ids));
        //删除角色菜单表关联
        LogicDeleteManager.execWithoutLogicDelete(()->
                sysRoleMenuService.remove(new QueryWrapper().in("menu_id",ids))
        );

        //删除按钮表数据
        QueryWrapper deletePermissionWrapper = new QueryWrapper();
        deletePermissionWrapper.in("menu_id",ids);
        List<Long> sysPermissionDeleteList = sysPermissionService.list(deletePermissionWrapper).stream().map(SysPermission::getId).toList();
        LogicDeleteManager.execWithoutLogicDelete(()->
                sysPermissionService.remove(deletePermissionWrapper)
        );

        //删除角色按钮关联表数据
        if (ObjectUtil.isNotEmpty(sysPermissionDeleteList)){
            QueryWrapper deleteRolePermissionWrapper = new QueryWrapper();
            deleteRolePermissionWrapper.in("permission_id",sysPermissionDeleteList);
            LogicDeleteManager.execWithoutLogicDelete(()->
                    sysRolePermissionService.remove(deleteRolePermissionWrapper)
            );
        }
        return Result.success("删除成功!", true);
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
            List<SysMenu> sysMenus = sysMenuMapper.selectListByQuery(new QueryWrapper().eq("parent_id", sysRoleMenu.getMenuId()));
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
                            .hideInMenu(sysMenu.getHideInMenu().equals("Y"))
                            .activeMenu(sysMenu.getActiveMenu())
                            .multiTab(sysMenu.getMultiTab().equals("Y"))
                            .fixedIndexInTab(sysMenu.getFixedIndexInTab())
                            .query(JSON.parseObject(sysMenu.getQuery(), new TypeReference<>() {
                            }))
                            .build())
                    .build();
            //添加子路由
            for (SysMenu children : sysMenus) {
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
                                .hideInMenu(children.getHideInMenu().equals("Y"))
                                .activeMenu(children.getActiveMenu())
                                .multiTab(children.getMultiTab().equals("Y"))
                                .fixedIndexInTab(children.getFixedIndexInTab())
                                .query(JSON.parseObject(sysMenu.getQuery(), new TypeReference<>() {
                                }))
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

    @Override
    public Result<List<Map<String, Object>>> getConstantRoutes() {
        List<Map<String, Object>> routes = new ArrayList<>();
        // 添加login路由
        Map<String, Object> loginRoute = new HashMap<>();
        loginRoute.put("name", "login");
        loginRoute.put("path", "/login/:module(pwd-login|code-login|register|reset-pwd|bind-wechat)?");
        loginRoute.put("component", "layout.blank$view.login");
        Map<String, Object> loginMeta = new HashMap<>();
        loginMeta.put("title", "login");
        loginMeta.put("i18nKey", "route.login");
        loginMeta.put("constant", true);
        loginMeta.put("hideInMenu", true);
        loginRoute.put("meta", loginMeta);
        routes.add(loginRoute);
        // 类似地，添加403, 404, 500路由...
        // 添加403、404、500路由
        for (String statusCode : new String[]{"403", "404", "500"}) {
            Map<String, Object> route = new HashMap<>();
            route.put("name", statusCode);
            route.put("path", "/" + statusCode);
            route.put("component", "layout.blank$view." + statusCode);
            Map<String, Object> meta = new HashMap<>();
            meta.put("title", statusCode);
            meta.put("i18nKey", "route." + statusCode);
            meta.put("constant", true);
            meta.put("hideInMenu", true);
            route.put("meta", meta);
            routes.add(route);
        }
        return Result.success("操作成功!",routes);
    }

    private RPage<SysMenuVO> iniSysMenuChildren(Page<SysMenu> paginate) {
        List<SysMenu> sysMenus = paginate.getRecords();
        // 根据 parentId 获取菜单列表
        List<SysMenu> parentMenuList = sysMenus.stream()
                .filter(item -> item.getParentId().equals(0L)).toList();
        List<SysMenuVO> menuPageVOList =new ArrayList<>();
        for (SysMenu sysMenu : parentMenuList) {//获取菜单按钮权限列表
            SysMenuVO sysMenuVO = sysMenuVOBuilder(sysMenu);
            menuPageVOList.add(sysMenuVO);
        }
        for (SysMenu item : sysMenus) {// 递归获取子菜单
            if (item.getId() != 0L) {
                //查询子菜单属于那个父菜单 添加到父菜单内
                for (SysMenuVO menu : menuPageVOList) {
                    if (ObjectUtil.isNull(menu.getChildren())) menu.setChildren(new ArrayList<>());
                    if (item.getParentId().equals(menu.getId())) {
                        //获取菜单按钮权限列表
                        SysMenuVO sysMenuVO = sysMenuVOBuilder(item);
                        menu.getChildren().add(sysMenuVO);
                    }
                }
            }
        }
        return RPage.build(new Page<>(menuPageVOList, paginate.getPageNumber(), paginate.getPageSize(),menuPageVOList.size()));
    }

    private  List<SysMenuTreeVO> initMenuChildren(List<SysMenu> sysMenus) {
        // 根据 parentId 获取菜单列表
        List<SysMenu> parentMenuList = sysMenus.stream()
                .filter(item -> item.getParentId().equals(0L)).toList();
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
        menuPageVOList.sort(Comparator.comparing(SysMenuTreeVO::getSort, Comparator.nullsLast(Comparator.naturalOrder())));
        return menuPageVOList;
    }
    public SysMenuVO sysMenuVOBuilder(SysMenu sysMenu){
        QueryWrapper permissionWrapper = new QueryWrapper();
        permissionWrapper.eq("menu_id", sysMenu.getId());
        List<SysPermission> sysPermissionsList = sysPermissionService.list(permissionWrapper);
        List<BTPairs> buVoList = new ArrayList<>();
        sysPermissionsList.forEach(sysPermission -> {
            buVoList.add(BTPairs.builder()
                    .code(sysPermission.getCode())
                    .desc(sysPermission.getDescription())
                    .build());
        });
        //拷贝到新列表中
        return SysMenuVO.builder()
                .id(sysMenu.getId())
                .parentId(sysMenu.getParentId())
                .children(new ArrayList<>())
                .parentId(sysMenu.getParentId())
                .menuType(sysMenu.getMenuType())
                .menuName(sysMenu.getMenuName())
                .i18nKey(sysMenu.getI18nKey())
                .routeName(sysMenu.getRouteName())
                .routePath(sysMenu.getRoutePath())
                .icon(sysMenu.getIcon())
                .iconType(sysMenu.getIconType())
                .component(sysMenu.getComponent())
                .keepAlive(sysMenu.getKeepAlive().equals("Y"))
                .hideInMenu(sysMenu.getHideInMenu().equals("Y"))
                .constant(sysMenu.getConstant().equals("Y"))
                .href(sysMenu.getHref())
                .activeMenu(sysMenu.getActiveMenu())
                .sort(sysMenu.getSort())
                .multiTab(sysMenu.getMultiTab().equals("Y"))
                .fixedIndexInTab(sysMenu.getFixedIndexInTab())
                .buttons(buVoList)
                .query(JSON.parseObject(sysMenu.getQuery(), new TypeReference<>() {
                }))
                .status(sysMenu.getStatus())
                .build();
    }
}
