package com.jinlink.modules.system.service;

import com.jinlink.core.page.PageQuery;
import com.jinlink.core.page.RPage;
import com.jinlink.modules.system.entity.dto.SysMenuFormDTO;
import com.jinlink.modules.system.entity.vo.SysMenuTreeVo;
import com.jinlink.modules.system.entity.vo.SysMenuVo;
import com.jinlink.modules.system.entity.vo.SysUserRouteVo;
import com.mybatisflex.core.service.IService;
import com.jinlink.modules.system.entity.SysMenu;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 菜单管理 服务层。
 *
 * @author Summer
 * @since 1.0.0
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 获取全部菜单(分页)
     */
    List<String> getAllPages();

    /**
     * 获取菜单树
     */
    List<SysMenuTreeVo> getMenuTree();

    /**
     * 获取菜单列表(分页)
     */
    RPage<SysMenuVo> getMenuList(PageQuery query);

    /**
     * 修改菜单
     */
    @Transactional
    Boolean updateMenu(SysMenuFormDTO sysMenu);

    /**
     * 新增菜单
     */
    @Transactional
    Boolean saveMenu(SysMenuFormDTO sysMenu);

    /**
     * 删除菜单单个
     */
    @Transactional
    Boolean removeMenuById(Serializable id);

    /**
     * 删除菜单多个
     */
    @Transactional
    Boolean removeMenuByIds(List<Long> ids);

    /**
     * 获取用户路由
     */
    SysUserRouteVo getUserRoutes();

    /**
     * 获取常量路由
     */
    List<Map<String, Object>> getConstantRoutes();
}
