package com.jinlink.controller.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.bean.BeanUtil;
import com.jinlink.common.api.Result;
import com.jinlink.core.page.PageQuery;
import com.jinlink.core.page.RPage;
import com.jinlink.modules.system.entity.dto.SysRolePermissionFormDTO;
import com.jinlink.modules.system.entity.vo.SysRolePermissionVo;
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
import com.jinlink.modules.system.entity.SysRolePermission;
import com.jinlink.modules.system.service.SysRolePermissionService;
import org.springframework.web.bind.annotation.RestController;
import java.io.Serializable;
import java.util.List;

/**
 * 角色权限管理 控制层。
 *
 * @author Summer
 * @since 1.0.0
 */
@RestController
@Tag(name = "按钮权限角色管理")
@RequiredArgsConstructor
@RequestMapping("/sysRolePermission")
public class SysRolePermissionController {

    @NonNull
    private SysRolePermissionService sysRolePermissionService;

    /**
     * 添加角色权限管理。
     *
     * @param sysRolePermission 角色权限管理
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @Operation(operationId = "1",summary = "添加按钮权限角色")
    @SaCheckPermission("sys:role:permission:save")
    public Result<Boolean> save(@Parameter(description = "添加对象", required = true)@RequestBody SysRolePermission sysRolePermission) {
        return Result.success("请求成功",sysRolePermissionService.save(sysRolePermission));
    }

    /**
     * 根据主键删除角色权限管理。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @Operation(operationId = "2",summary = "删除按钮权限角色")
    @SaCheckPermission("sys:role:permission:delete")
    public Result<Boolean> remove(@Parameter(description = "按钮权限角色ID", required = true)@PathVariable Serializable id) {
        return Result.success("请求成功",sysRolePermissionService.removeById(id));
    }

    /**
     * 根据主键更新角色权限管理。
     *
     * @param sysRolePermission 角色权限管理
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @Operation(operationId = "3",summary = "修改按钮权限角色")
    @SaCheckPermission("sys:role:permission:update")
    public Result<Boolean> update(@Parameter(description = "修改对象", required = true)@RequestBody SysRolePermission sysRolePermission) {
        return Result.success("请求成功",sysRolePermissionService.updateById(sysRolePermission));
    }

    /**
     * 查询所有角色权限管理。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @Operation(operationId = "4",summary = "查询全部按钮权限角色")
    @SaCheckPermission("sys:role:permission:list")
    public Result<List<SysRolePermissionVo>> list() {
        return Result.success("请求成功",BeanUtil.copyToList(sysRolePermissionService.list(),SysRolePermissionVo.class));
    }

    /**
     * 根据角色权限管理主键获取详细信息。
     *
     * @param id 角色权限管理主键
     * @return 角色权限管理详情
     */
    @GetMapping("getInfo/{id}")
    @Operation(operationId = "5",summary = "查询按钮权限角色详细")
    @SaCheckPermission("sys:role:permission:info")
    public Result<SysRolePermissionVo> getInfo(@Parameter(description = "按钮权限角色ID", required = true)@PathVariable Serializable id) {
        return Result.success("请求成功", BeanUtil.copyProperties(sysRolePermissionService.getById(id),SysRolePermissionVo.class));
    }

    /**
     * 分页查询角色权限管理。
     *
     * @param query 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    @Operation(operationId = "6",summary = "查询按钮权限角色(分页)")
    @SaCheckPermission("sys:role:permission:page")
    public Result<RPage<SysRolePermissionVo>> page(@Parameter(description = "分页对象", required = true) PageQuery query) {
        Page<SysRolePermissionVo> sysRolePermissionPage = sysRolePermissionService.listSysRolePermissionPage(query);
        return Result.data(RPage.build(sysRolePermissionPage));
    }

    /**
     * 查询角色拥有的按钮管理。
     * @return 权限Long列表
     */
    @GetMapping("getPermissionByRoleId/{roleId}")
    @Operation(operationId = "7",summary = "查询角色拥有的按钮权限列表")
    @SaCheckPermission("sys:role:permission:getPermissionByRoleId")
    public Result<List<Long>> getPermissionByRoleId(@Parameter(description = "按钮权限角色ID", required = true)@PathVariable Long roleId) {
        return Result.success("操作成功!",sysRolePermissionService.getPermissionByRoleId(roleId));
    }

    /**
     * 根据主键更新角色权限管理。
     *
     * @param sysRolePermissionFormDTO 角色权限管理
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("updateByRoleId")
    @Operation(operationId = "8",summary = "修改按钮权限角色(多个)")
    @SaCheckPermission("sys:role:permission:updateByRoleId")
    public Result<Boolean> updateByRoleId(@Parameter(description = "修改对象", required = true)@RequestBody SysRolePermissionFormDTO sysRolePermissionFormDTO) {
        return Result.success("修改成功!",sysRolePermissionService.updateByRoleId(sysRolePermissionFormDTO));
    }
}
