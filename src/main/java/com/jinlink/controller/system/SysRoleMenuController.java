package com.jinlink.controller.system;

import cn.dev33.satoken.annotation.SaCheckOr;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.jinlink.common.api.Result;
import com.jinlink.common.page.RPage;
import com.jinlink.modules.system.entity.dto.SysRoleMenuUpdateDTO;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.jinlink.modules.system.entity.SysRoleMenu;
import com.jinlink.modules.system.service.SysRoleMenuService;

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
@SaCheckOr(role = @SaCheckRole("R_SUPER"))
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
    public Result<Boolean> save(@Parameter(description = "菜单角色", required = true)@RequestBody SysRoleMenu sysRoleMenu) {
        return Result.success("请求成功",sysRoleMenuService.save(sysRoleMenu));
    }

    /**
     * 根据主键删除角色菜单管理。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @Operation(operationId = "2",summary = "删除单个菜单角色")
    public Result<Boolean> remove(@Parameter(description = "菜单角色ID", required = true)@PathVariable Serializable id) {
        return Result.success("请求成功",sysRoleMenuService.removeById(id));
    }

    /**
     * 根据主键更新角色菜单管理。
     *
     * @param sysRoleMenu 角色菜单管理
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @Operation(operationId = "3",summary = "修改菜单角色")
    public Result<Boolean> update(@Parameter(description = "菜单角色", required = true)@RequestBody SysRoleMenu sysRoleMenu) {
        return Result.success("请求成功",sysRoleMenuService.updateById(sysRoleMenu));
    }

    /**
     * 查询所有角色菜单管理。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @Operation(operationId = "4",summary = "查询所有菜单角色列表")
    public Result<List<SysRoleMenu>> list() {
        return Result.success("请求成功",sysRoleMenuService.list());
    }

    /**
     * 查询用户角色菜单列表。
     *
     * @return 所有数据
     */
    @GetMapping("getRoleByRoleId/{roleId}")
    @Operation(operationId = "5",summary = "查询权限菜单列表")
    public Result<List<Long>> getRoleMenuByRoleId(@Parameter(description = "权限Id", required = true)@PathVariable Long roleId) {
        return Result.success("请求成功",sysRoleMenuService.getRoleMenuByRoleId(roleId));
    }

    /**
     * 根据角色菜单管理主键获取详细信息。
     *
     * @param id 角色菜单管理主键
     * @return 角色菜单管理详情
     */
    @GetMapping("getInfo/{id}")
    @Operation(operationId = "6",summary = "查询菜单角色详细")
    public Result<SysRoleMenu> getInfo(@Parameter(description = "菜单角色ID", required = true)@PathVariable Serializable id) {
        return Result.success("请求成功",sysRoleMenuService.getById(id));
    }

    /**
     * 分页查询角色菜单管理。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    @Operation(operationId = "7",summary = "查询菜单角色(分页)")
    public Result<RPage<SysRoleMenu>> page(@Parameter(description = "菜单角色分页对象", required = true)Page<SysRoleMenu> page) {
        return Result.data(RPage.build(sysRoleMenuService.page(page)));
    }

    /**
     * 根据主键更新角色菜单管理。
     *
     * @param sysRoleMenuUpdateDTO 角色菜单管理
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("updateRoleMenu")
    @Operation(operationId = "8",summary = "修改角色的菜单权限")
    public Result<Boolean> updateRoleMenu(@Parameter(description = "角色菜单修改对象", required = true)@RequestBody SysRoleMenuUpdateDTO sysRoleMenuUpdateDTO) {
        return Result.success("修改成功!",sysRoleMenuService.updateRoleMenu(sysRoleMenuUpdateDTO));
    }
}
