package com.jinlink.controller.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.bean.BeanUtil;
import com.jinlink.common.api.Result;
import com.jinlink.common.page.PageQuery;
import com.jinlink.common.page.RPage;
import com.jinlink.modules.system.entity.vo.SysPermissionTreeVo;
import com.jinlink.modules.system.entity.vo.SysPermissionVo;
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
import com.jinlink.modules.system.entity.SysPermission;
import com.jinlink.modules.system.service.SysPermissionService;
import org.springframework.web.bind.annotation.RestController;
import java.io.Serializable;
import java.util.List;

/**
 * 权限(按钮)管理 控制层。
 *
 * @author Summer
 * @since 1.0.0
 */
@RestController
@Tag(name = "按钮权限管理")
@RequiredArgsConstructor
@RequestMapping("/sysPermission")
public class SysPermissionController {

    @NonNull
    private SysPermissionService sysPermissionService;

    /**
     * 添加权限(按钮)管理。
     *
     * @param sysPermission 权限(按钮)管理
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @Operation(operationId = "1",summary = "添加按钮权限")
    @SaCheckPermission("sys:permission:save")
    public Result<Boolean> save(@Parameter(description = "添加对象", required = true)@RequestBody SysPermission sysPermission) {
        return Result.success("请求成功",sysPermissionService.save(sysPermission));
    }

    /**
     * 根据主键删除权限(按钮)管理。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @Operation(operationId = "2",summary = "删除按钮权限")
    @SaCheckPermission("sys:permission:delete")
    public Result<Boolean> remove(@Parameter(description = "按钮权限ID", required = true)@PathVariable Serializable id) {
        return Result.success("请求成功",sysPermissionService.removeById(id));
    }

    /**
     * 根据主键更新权限(按钮)管理。
     *
     * @param sysPermission 权限(按钮)管理
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @Operation(operationId = "3",summary = "修改按钮权限")
    @SaCheckPermission("sys:permission:update")
    public Result<Boolean> update(@Parameter(description = "修改对象", required = true)@RequestBody SysPermission sysPermission) {
        return Result.success("请求成功",sysPermissionService.updateById(sysPermission));
    }

    /**
     * 查询所有权限(按钮)管理。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @Operation(operationId = "4",summary = "查询全部按钮权限")
    @SaCheckPermission("sys:permission:list")
    public Result<List<SysPermissionVo>> list() {
        return Result.success("请求成功",BeanUtil.copyToList(sysPermissionService.list(),SysPermissionVo.class));
    }

    /**
     * 根据权限(按钮)管理主键获取详细信息。
     *
     * @param id 权限(按钮)管理主键
     * @return 权限(按钮)管理详情
     */
    @GetMapping("getInfo/{id}")
    @Operation(operationId = "5",summary = "查询按钮权限详细")
    @SaCheckPermission("sys:permission:info")
    public Result<SysPermissionVo> getInfo(@Parameter(description = "按钮权限ID", required = true)@PathVariable Serializable id) {
        return Result.success("请求成功", BeanUtil.copyProperties(sysPermissionService.getById(id),SysPermissionVo.class));
    }

    /**
     * 分页查询权限(按钮)管理。
     *
     * @param query 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    @Operation(operationId = "6",summary = "查询按钮权限(分页)")
    @SaCheckPermission("sys:permission:page")
    public Result<RPage<SysPermissionVo>> page(@Parameter(description = "分页对象", required = true) PageQuery query) {
        return Result.data(RPage.build(sysPermissionService.listSysPermissionPage(query)));
    }

    /**
     * 查询所有权限(按钮)管理。
     * @return 列表对象
     */
    @GetMapping("getPermissionTree")
    @Operation(operationId = "7",summary = "查询全部按钮权限列表")
    @SaCheckPermission("sys:permission:getPermissionTree")
    public Result<List<SysPermissionTreeVo>> listAll() {
        return Result.success("获取成功!",sysPermissionService.getPermissionTree());
    }
}
