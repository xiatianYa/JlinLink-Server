package com.jinlink.modules.system.service;

import com.jinlink.common.api.Result;
import com.jinlink.common.page.PageQuery;
import com.jinlink.common.page.RPage;
import com.jinlink.modules.system.entity.dto.SysMenuFormDTO;
import com.jinlink.modules.system.entity.vo.SysMenuTreeVO;
import com.jinlink.modules.system.entity.vo.SysMenuVO;
import com.jinlink.modules.system.entity.vo.SysUserRouteVO;
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

    List<String> getAllPages();

    List<SysMenuTreeVO> getMenuTree();

    RPage<SysMenuVO> getMenuList(PageQuery query);

    @Transactional
    Result<String> updateMenu(SysMenuFormDTO sysMenu);

    @Transactional
    Result<String> saveMenu(SysMenuFormDTO sysMenu);

    @Transactional
    Result<Boolean> removeMenuById(Serializable id);

    @Transactional
    Result<Boolean> removeMenuByIds(List<Long> ids);

    Result<SysUserRouteVO> getUserRoutes();

    Result<List<Map<String, Object>>> getConstantRoutes();
}
