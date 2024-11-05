package com.jinlink.controller.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.bean.BeanUtil;
import com.jinlink.common.api.Result;
import com.jinlink.common.page.PageQuery;
import com.jinlink.common.page.RPage;
import com.jinlink.modules.system.entity.dto.SysRoleMenuUpdateDTO;
import com.jinlink.modules.system.entity.vo.SysRoleMenuVo;
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
    @SaCheckPermission("sys:role:menu:save")
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
    @SaCheckPermission("sys:role:menu:delete")
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
    @SaCheckPermission("sys:role:menu:update")
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
    @SaCheckPermission("sys:role:menu:list")
    public Result<List<SysRoleMenuVo>> list() {
        return Result.success("请求成功",BeanUtil.copyToList(sysRoleMenuService.list(), SysRoleMenuVo.class));
    }

    /**
     * 查询用户角色菜单列表。
     *
     * @return 所有数据
     */
    @GetMapping("getRoleByRoleId/{roleId}")
    @Operation(operationId = "5",summary = "查询权限菜单列表")
    @SaCheckPermission("sys:role:menu:getRoleByRoleId")
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
    @SaCheckPermission("sys:role:menu:info")
    public Result<SysRoleMenuVo> getInfo(@Parameter(description = "菜单角色ID", required = true)@PathVariable Serializable id) {
        return Result.success("请求成功", BeanUtil.copyProperties(sysRoleMenuService.getById(id), SysRoleMenuVo.class));
    }

    /**
     * 分页查询角色菜单管理。
     *
     * @param query 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    @Operation(operationId = "7",summary = "查询菜单角色(分页)")
    @SaCheckPermission("sys:role:menu:page")
    public Result<RPage<SysRoleMenuVo>> page(@Parameter(description = "分页对象", required = true) PageQuery query) {
        Page<SysRoleMenuVo> sysRoleMenuVoPage = sysRoleMenuService.listRoleMenuPage(query);
        return Result.data(RPage.build(sysRoleMenuVoPage));
    }

    /**
     * 根据主键更新角色菜单管理。
     *
     * @param sysRoleMenuUpdateDTO 角色菜单管理
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("updateRoleMenu")
    @Operation(operationId = "8",summary = "修改角色的菜单权限")
    @SaCheckPermission("sys:role:menu:updateRoleMenu")
    public Result<Boolean> updateRoleMenu(@Parameter(description = "角色菜单修改对象", required = true)@RequestBody SysRoleMenuUpdateDTO sysRoleMenuUpdateDTO) {
        return Result.success("修改成功!",sysRoleMenuService.updateRoleMenu(sysRoleMenuUpdateDTO));
    }
}
