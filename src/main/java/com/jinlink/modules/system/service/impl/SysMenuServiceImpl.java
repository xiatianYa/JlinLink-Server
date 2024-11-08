package com.jinlink.modules.system.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.jinlink.common.exception.JinLinkException;
import com.jinlink.core.page.PageQuery;
import com.jinlink.core.page.RPage;
import com.jinlink.modules.system.entity.SysPermission;
import com.jinlink.modules.system.entity.SysRoleMenu;
import com.jinlink.modules.system.entity.SysUserRole;
import com.jinlink.modules.system.entity.dto.SysMenuFormDTO;
import com.jinlink.common.domain.BTPairs;
import com.jinlink.modules.system.entity.vo.SysMenuTreeVo;
import com.jinlink.modules.system.entity.vo.SysMenuVo;
import com.jinlink.modules.system.entity.vo.SysUserRouteVo;
import com.jinlink.modules.system.service.*;
import com.mybatisflex.core.logicdelete.LogicDeleteManager;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.jinlink.modules.system.entity.SysMenu;
import com.jinlink.modules.system.mapper.SysMenuMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 菜单管理 服务层实现。
 *
 * @author Summer
 * @since 1.0.0
 */
@Slf4j
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

    /**
     * 获取所有菜单(分页)
     */
    @Override
    public List<String> getAllPages() {
        return sysMenuMapper.selectAll().stream().map(SysMenu::getRouteName).toList();
    }

    /**
     * 获取菜单树
     */
    @Override
    public List<SysMenuTreeVo> getMenuTree() {
        List<SysMenu> sysMenus = sysMenuMapper.selectAll();
        return initMenuChildren(sysMenus);
    }

    /**
     * 获取菜单列表(分页)
     */
    @Override
    public RPage<SysMenuVo> getMenuList(PageQuery query) {
        //查询所有父菜单
        Page<SysMenu> paginate = sysMenuMapper.paginate(query.getCurrent(), query.getSize(), new QueryWrapper().eq("parent_id",0));
        List<SysMenuVo> menuPageVOList = iniSysMenuChildren(paginate.getRecords());
        return RPage.build(new Page<>(menuPageVOList, paginate.getPageNumber(), paginate.getPageSize(),menuPageVOList.size()));
    }

    /**
     * 修改菜单
     */
    @Override
    public Boolean updateMenu(SysMenuFormDTO sysMenuVo) {
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
            return true;
        }else {
            throw new JinLinkException("更新失败!");
        }
    }

    /**
     * 新增菜单
     */
    @Override
    public Boolean saveMenu(SysMenuFormDTO sysMenuVo) {
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
            return true;
        }else{
            throw new JinLinkException("新增失败!");
        }
    }

    /**
     * 删除菜单 及 子菜单
     */
    @Override
    public Boolean removeMenuById(Serializable id) {
        //先删除当前菜单
        LogicDeleteManager.execWithoutLogicDelete(()->
                sysMenuMapper.deleteById(id)
        );
        //删除当前菜单下的子菜单
        LogicDeleteManager.execWithoutLogicDelete(()->
        sysMenuMapper.deleteByQuery(new QueryWrapper().eq("parent_id", id))
        );

        //删除角色菜单表关联
        LogicDeleteManager.execWithoutLogicDelete(()->
                sysRoleMenuService.remove(new QueryWrapper().eq("menu_id",id))
        );

        //删除按钮表数据
        QueryWrapper deletePermissionWrapper = new QueryWrapper();
        deletePermissionWrapper.eq("menu_id",id);
        List<Long> sysPermissionDeleteList = sysPermissionService
                .list(deletePermissionWrapper).stream().map(SysPermission::getId).toList();
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
        return true;
    }

    /**
     * 删除多个菜单 及其下 子菜单
     */
    @Override
    public Boolean removeMenuByIds(List<Long> ids) {
        LogicDeleteManager.execWithoutLogicDelete(()->
                sysMenuMapper.deleteBatchByIds(ids)
        );

        //删除当前菜单下的子菜单
        LogicDeleteManager.execWithoutLogicDelete(()->
        sysMenuMapper.deleteByQuery(new QueryWrapper().in("parent_id", ids))
        );

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
        return true;
    }

    /**
     * 获取用户路由权限
     */
    @Override
    public SysUserRouteVo getUserRoutes() {
        List<SysUserRouteVo.Route> resultRoute = new ArrayList<>();
        //查询用户角色
        List<SysUserRole> sysUserRoles = sysUserRoleService
                .list(new QueryWrapper().eq("user_id", StpUtil.getLoginIdAsLong()));
        //用户角色列表
        List<Long> roleIds = sysUserRoles.stream().map(SysUserRole::getRoleId).toList();
        //通过用户角色查询角色下拥有的菜单权限
        List<Long> menuIds = new ArrayList<>();
        List<SysRoleMenu> sysRoleMenus = sysRoleMenuService.list(new QueryWrapper()
                        .in("role_id", roleIds)).stream()
                .filter(item -> menuIds.add(item.getMenuId()))
                .toList();
        //查询所有符合条件的父菜单
        List<SysMenu> parentMenus = sysMenuMapper.selectListByQuery(new QueryWrapper().eq("status", 1).eq("parent_id", 0));
        //查询所有符合条件的子菜单
        List<SysMenu> childrenMenus = sysMenuMapper.selectListByQuery(new QueryWrapper().ne("parent_id",0).in("id",menuIds));
        //查询用户菜单信息
        for (SysRoleMenu sysRoleMenu : sysRoleMenus) {
            //父路由
            Optional<SysMenu> sysMenu = parentMenus.stream().filter(item -> item.getId().equals(sysRoleMenu.getMenuId())).findFirst();
            if (sysMenu.isEmpty()) continue;
            //构建路由对象
            SysUserRouteVo.Route route = SysUserRouteVOBuilder(sysMenu.get(),childrenMenus);
            resultRoute.add(route);
        }
        return SysUserRouteVo.builder()
                .home("home")
                .routes(resultRoute)
                .build();
    }

    /**
     * 获取常量路由
     */
    @Override
    public List<Map<String, Object>> getConstantRoutes() {
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
        return routes;
    }

    /**
     * 初始化子菜单分页
     */
    private List<SysMenuVo> iniSysMenuChildren(List<SysMenu> sysMenus) {
        //查询所有子菜单
        List<SysMenu> childrenMenus = sysMenuMapper.selectListByQuery(new QueryWrapper().ne("parent_id",0));
        sysMenus = Stream.concat(childrenMenus.stream(), sysMenus.stream())
                .collect(Collectors.toList());
        //配置
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        //数据ID
        treeNodeConfig.setIdKey("id");
        // 父级id字段
        treeNodeConfig.setParentIdKey("parentId");
        // 排序字段，这个字段不能是null，不然会报错，默认最好是数字
        treeNodeConfig.setWeightKey("order");
        // 设置子菜单属性名称
        treeNodeConfig.setChildrenKey("children");
        // 最大递归深度
        treeNodeConfig.setDeep(3);
        //转换器
        List<Tree<Integer>> treeList = TreeUtil.build(sysMenus, 0, treeNodeConfig, ((sysMenu, treeNode) -> {
            //获取菜单下的按钮列表
            List<SysPermission> sysPermissionsList = sysPermissionService.list(new QueryWrapper().eq("menu_id", sysMenu.getId()));
            List<BTPairs> buVoList = new ArrayList<>();
            sysPermissionsList.forEach(sysPermission -> {
                buVoList.add(BTPairs.builder()
                        .code(sysPermission.getCode())
                        .desc(sysPermission.getDescription())
                        .build());
            });
            treeNode.setId(sysMenu.getId().intValue());
            treeNode.setParentId(sysMenu.getParentId().intValue());
            treeNode.putExtra("menuType", sysMenu.getMenuType());
            treeNode.putExtra("menuName", sysMenu.getMenuName());
            treeNode.putExtra("i18nKey", sysMenu.getI18nKey());
            treeNode.putExtra("routeName", sysMenu.getRouteName());
            treeNode.putExtra("routePath", sysMenu.getRoutePath());
            treeNode.putExtra("icon", sysMenu.getIcon());
            treeNode.putExtra("iconType", sysMenu.getIconType());
            treeNode.putExtra("component", sysMenu.getComponent());
            treeNode.putExtra("href", sysMenu.getHref());
            treeNode.putExtra("activeMenu", sysMenu.getActiveMenu());
            treeNode.putExtra("order", sysMenu.getOrder());
            treeNode.putExtra("fixedIndexInTab", sysMenu.getFixedIndexInTab());
            treeNode.putExtra("query",JSON.parseObject(sysMenu.getQuery(), new TypeReference<>() {
            }));
            treeNode.putExtra("buttons",buVoList);
            treeNode.putExtra("keepAlive", sysMenu.getKeepAlive().equals("Y"));
            treeNode.putExtra("hideInMenu", sysMenu.getHideInMenu().equals("Y"));
            treeNode.putExtra("constant", sysMenu.getConstant().equals("Y"));
            treeNode.putExtra("multiTab", sysMenu.getMultiTab().equals("y"));
            treeNode.putExtra("createUserId", sysMenu.getCreateUserId());
            treeNode.putExtra("createTime", sysMenu.getCreateTime());
            treeNode.putExtra("updateUserId", sysMenu.getUpdateUserId());
            treeNode.putExtra("updateTime", sysMenu.getUpdateTime());
            treeNode.putExtra("status",sysMenu.getStatus());
        }));
        return JSON.parseArray(JSON.toJSONString(treeList), SysMenuVo.class);
    }

    /**
     * 构建SysUserRouteVO.Route
     */
    public SysUserRouteVo.Route SysUserRouteVOBuilder(SysMenu sysMenu, List<SysMenu> sysMenus){
        sysMenus.add(sysMenu);
        //配置
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        //数据ID
        treeNodeConfig.setIdKey("id");
        // 父级id字段
        treeNodeConfig.setParentIdKey("parentId");
        // 设置子菜单属性名称
        treeNodeConfig.setChildrenKey("children");
        // 最大递归深度
        treeNodeConfig.setDeep(3);
        //转换器
        List<Tree<Integer>> tree = TreeUtil.build(sysMenus, 0, treeNodeConfig, ((sysMenuObj, treeNode) -> {
            treeNode.setId(sysMenuObj.getId().intValue());
            treeNode.setParentId(sysMenuObj.getParentId().intValue());
            treeNode.putExtra("name", sysMenuObj.getRouteName());
            treeNode.putExtra("path", sysMenuObj.getRoutePath());
            treeNode.putExtra("component", sysMenuObj.getComponent());
            treeNode.putExtra("meta", SysUserRouteVo.Meta.builder()
                    .title(sysMenuObj.getRouteName())
                    .i18nKey(sysMenuObj.getI18nKey())
                    .order(sysMenuObj.getOrder())
                    .href(sysMenuObj.getHref())
                    .fixedIndexInTab(ObjectUtil.isNotNull(sysMenuObj.getFixedIndexInTab()) ? sysMenuObj.getFixedIndexInTab() : null)
                    .query(JSON.parseObject(sysMenuObj.getQuery(), new TypeReference<>() {
                    }))
                    .activeMenu(sysMenuObj.getActiveMenu())
                    .keepAlive(sysMenuObj.getKeepAlive().equals("Y"))
                    .constant(sysMenuObj.getConstant().equals("Y"))
                    .hideInMenu(sysMenuObj.getHideInMenu().equals("Y"))
                    .multiTab(sysMenuObj.getMultiTab().equals("Y"))
                    .icon(sysMenuObj.getIconType().equals("1") ? sysMenuObj.getIcon() : null)
                    .localIcon(sysMenuObj.getIconType().equals("2") ? sysMenuObj.getIcon() : null)
                    .build());
        }));
        sysMenus.remove(sysMenu);
        return JSON.parseArray(JSON.toJSONString(tree), SysUserRouteVo.Route.class).get(0);
    }

    /**
     * 初始化子菜单
     */
    private  List<SysMenuTreeVo> initMenuChildren(List<SysMenu> sysMenus) {
        //查询所有子菜单
        List<SysMenu> childrenMenus = sysMenuMapper.selectListByQuery(new QueryWrapper().ne("parent_id",0));
        sysMenus = Stream.concat(childrenMenus.stream(), sysMenus.stream())
                .collect(Collectors.toList());
        //配置
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        //数据ID
        treeNodeConfig.setIdKey("id");
        // 父级id字段
        treeNodeConfig.setParentIdKey("parentId");
        // 排序字段，这个字段不能是null，不然会报错，默认最好是数字
        treeNodeConfig.setWeightKey("sort");
        // 设置子菜单属性名称
        treeNodeConfig.setChildrenKey("children");
        // 最大递归深度
        treeNodeConfig.setDeep(3);
        //转换器
        List<Tree<Integer>> treeList = TreeUtil.build(sysMenus, 0, treeNodeConfig, ((sysMenu, treeNode) -> {
            //获取菜单下的按钮列表
            treeNode.setId(sysMenu.getId().intValue());
            treeNode.setParentId(sysMenu.getParentId().intValue());
            treeNode.putExtra("label",sysMenu.getMenuName());
            treeNode.putExtra("pid",sysMenu.getParentId().intValue());
            treeNode.putExtra("sort", sysMenu.getOrder());
        }));
        return JSON.parseArray(JSON.toJSONString(treeList), SysMenuTreeVo.class);
    }
}
