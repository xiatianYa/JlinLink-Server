package com.jinlink.controller.system;

import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.jinlink.modules.system.entity.SysRoleMenu;
import com.jinlink.modules.system.service.SysRoleMenuService;
import org.springframework.web.bind.annotation.RestController;
import java.io.Serializable;
import java.util.List;

/**
 * 角色菜单管理 控制层。
 *
 * @author Summer
 * @since 1.0.0
 */
@RestController
@Tag(name = "菜单角色关联管理")
@RequiredArgsConstructor
@RequestMapping("/sysRoleMenu")
public class SysRoleMenuController {

    @NonNull
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 添加角色菜单管理。
     *
     * @param sysRoleMenu 角色菜单管理
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @Operation(operationId = "1",summary = "添加菜单角色对象")
    public boolean save(@Parameter(description = "菜单角色", required = true)@RequestBody SysRoleMenu sysRoleMenu) {
        return sysRoleMenuService.save(sysRoleMenu);
    }

    /**
     * 根据主键删除角色菜单管理。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @Operation(operationId = "2",summary = "删除单个菜单角色")
    public boolean remove(@Parameter(description = "菜单角色ID", required = true)@PathVariable Serializable id) {
        return sysRoleMenuService.removeById(id);
    }

    /**
     * 根据主键更新角色菜单管理。
     *
     * @param sysRoleMenu 角色菜单管理
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @Operation(operationId = "3",summary = "修改菜单角色")
    public boolean update(@Parameter(description = "菜单角色", required = true)@RequestBody SysRoleMenu sysRoleMenu) {
        return sysRoleMenuService.updateById(sysRoleMenu);
    }

    /**
     * 查询所有角色菜单管理。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @Operation(operationId = "4",summary = "查询所有菜单角色列表")
    public List<SysRoleMenu> list() {
        return sysRoleMenuService.list();
    }

    /**
     * 根据角色菜单管理主键获取详细信息。
     *
     * @param id 角色菜单管理主键
     * @return 角色菜单管理详情
     */
    @GetMapping("getInfo/{id}")
    @Operation(operationId = "5",summary = "查询菜单角色详细")
    public SysRoleMenu getInfo(@Parameter(description = "菜单角色ID", required = true)@PathVariable Serializable id) {
        return sysRoleMenuService.getById(id);
    }

    /**
     * 分页查询角色菜单管理。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    @Operation(operationId = "6",summary = "查询菜单角色(分页)")
    public Page<SysRoleMenu> page(@Parameter(description = "菜单角色分页对象", required = true)Page<SysRoleMenu> page) {
        return sysRoleMenuService.page(page);
    }

}
