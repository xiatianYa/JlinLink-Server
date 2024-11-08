package com.jinlink.controller.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.bean.BeanUtil;
import com.jinlink.common.api.Result;
import com.jinlink.core.page.PageQuery;
import com.jinlink.core.page.RPage;
import com.jinlink.modules.system.entity.dto.SysRoleFormDTO;
import com.jinlink.modules.system.entity.dto.SysRoleSearchDTO;
import com.jinlink.modules.system.entity.vo.SysRoleOptionVo;
import com.jinlink.modules.system.entity.vo.SysRoleVo;
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
import com.jinlink.modules.system.service.SysRoleService;
import org.springframework.web.bind.annotation.RestController;
import java.io.Serializable;
import java.util.List;

/**
 * 角色管理 控制层。
 *
 * @author Summer
 * @since 1.0.0
 */
@RestController
@Tag(name = "角色管理")
@RequiredArgsConstructor
@RequestMapping("/sysRole")
public class SysRoleController {

    @NonNull
    private SysRoleService sysRoleService;

    /**
     * 添加角色管理。
     *
     * @param sysRoleFormDTO 角色管理
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @Operation(operationId = "1",summary = "添加角色")
    @SaCheckPermission("sys:role:save")
    public Result<Boolean> save(@Parameter(description = "添加对象", required = true)@RequestBody SysRoleFormDTO sysRoleFormDTO) {
        return Result.success("添加成功!",sysRoleService.saveRole(sysRoleFormDTO));
    }

    /**
     * 根据主键删除角色管理。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @Operation(operationId = "2",summary = "删除角色")
    @SaCheckPermission("sys:role:delete")
    public Result<Boolean> remove(@Parameter(description = "角色ID", required = true)@PathVariable Serializable id) {
        return Result.success("操作成功",sysRoleService.removeById(id));
    }

    /**
     * 根据主键删除用户管理。
     *
     * @param ids 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("removeByIds")
    @Operation(operationId = "3",summary = "删除多个角色")
    @SaCheckPermission("sys:role:delete")
    public Result<Boolean> removeByIds(@Parameter(description = "用户IDS", required = true)@RequestBody List<Long> ids) {
        return Result.success("删除成功!",sysRoleService.deleteByIds(ids));
    }

    /**
     * 根据主键更新角色管理。
     *
     * @param sysRoleFormDTO 角色对象DTO
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @Operation(operationId = "4",summary = "修改角色")
    @SaCheckPermission("sys:role:update")
    public Result<Boolean> update(@Parameter(description = "修改对象", required = true)@RequestBody SysRoleFormDTO sysRoleFormDTO) {
        return Result.success("修改成功!",sysRoleService.updateRole(sysRoleFormDTO));
    }

    /**
     * 查询所有角色管理。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @Operation(operationId = "5",summary = "查询全部角色")
    @SaCheckPermission("sys:role:list")
    public Result<List<SysRoleVo>> list() {
        return Result.success("请求成功",BeanUtil.copyToList(sysRoleService.list(), SysRoleVo.class));
    }

    /**
     * 根据角色管理主键获取详细信息。
     *
     * @param id 角色管理主键
     * @return 角色管理详情
     */
    @GetMapping("getInfo/{id}")
    @Operation(operationId = "6",summary = "查询角色详细")
    @SaCheckPermission("sys:role:info")
    public Result<SysRoleVo> getInfo(@Parameter(description = "角色ID", required = true)@PathVariable Serializable id) {
        return Result.success("请求成功", BeanUtil.copyProperties(sysRoleService.getById(id),SysRoleVo.class));
    }

    /**
     * 分页查询角色管理。
     *
     * @param query 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    @Operation(operationId = "7",summary = "查询角色(分页)")
    @SaCheckPermission("sys:role:page")
    public Result<RPage<SysRoleVo>> page(@Parameter(description = "分页对象", required = true) PageQuery query,
                                         @Parameter(description = "查询对象", required = true) SysRoleSearchDTO sysRoleSearchDTO) {
        Page<SysRoleVo> sysRolePage = sysRoleService.listRolePage(query, sysRoleSearchDTO);
        return Result.data(RPage.build(sysRolePage));
    }

    /**
     * 获取所有角色
     */
    @GetMapping("getAllRoles")
    @SaCheckPermission("sys:role:getAllRoles")
    @Operation(operationId = "8",summary = "获取所有角色")
    public Result<List<SysRoleOptionVo>> getAllRoles() {
        return Result.success("请求成功",sysRoleService.getAllRoles());
    }
}
