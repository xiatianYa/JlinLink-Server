package com.jinlink.controller.system;

import com.jinlink.common.api.Result;
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
import com.jinlink.modules.system.entity.SysUserRole;
import com.jinlink.modules.system.service.SysUserRoleService;
import org.springframework.web.bind.annotation.RestController;
import java.io.Serializable;
import java.util.List;

/**
 * 用户角色管理 控制层。
 *
 * @author Summer
 * @since 1.0.0
 */
@RestController
@Tag(name = "用户角色关联管理")
@RequiredArgsConstructor
@RequestMapping("/sysUserRole")
public class SysUserRoleController {

    @NonNull
    private SysUserRoleService sysUserRoleService;

    /**
     * 添加用户角色管理。
     *
     * @param sysUserRole 用户角色管理
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @Operation(operationId = "1",summary = "添加用户角色对象")
    public boolean save(@Parameter(description = "用户角色", required = true)@RequestBody SysUserRole sysUserRole) {
        return sysUserRoleService.save(sysUserRole);
    }

    /**
     * 根据主键删除用户角色管理。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @Operation(operationId = "2",summary = "删除单个用户角色")
    public Result<String> remove(@Parameter(description = "用户角色ID", required = true)@PathVariable Serializable id) {
        return sysUserRoleService.removeRoleById(id);
    }

    /**
     * 根据主键删除用户管理。
     *
     * @param ids 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("removeByIds")
    @Operation(operationId = "3",summary = "删除多个用户角色")
    public Result<Boolean> removeByIds(@Parameter(description = "用户角色IDS", required = true)@RequestBody List<Long> ids) {
        return sysUserRoleService.removeRoleByIds(ids) ;
    }

    /**
     * 根据主键更新用户角色管理。
     *
     * @param sysUserRole 用户角色管理
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @Operation(operationId = "4",summary = "修改用户角色")
    public boolean update(@Parameter(description = "修改用户角色对象", required = true)@RequestBody SysUserRole sysUserRole) {
        return sysUserRoleService.updateById(sysUserRole);
    }

    /**
     * 查询所有用户角色管理。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @Operation(operationId = "5",summary = "查询所有用户角色列表")
    public List<SysUserRole> list() {
        return sysUserRoleService.list();
    }

    /**
     * 根据用户角色管理主键获取详细信息。
     *
     * @param id 用户角色管理主键
     * @return 用户角色管理详情
     */
    @GetMapping("getInfo/{id}")
    @Operation(operationId = "6",summary = "查询用户角色详细")
    public SysUserRole getInfo(@Parameter(description = "用户ID", required = true)@PathVariable Serializable id) {
        return sysUserRoleService.getById(id);
    }

    /**
     * 分页查询用户角色管理。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    @Operation(operationId = "7",summary = "查询用户角色(分页)")
    public Page<SysUserRole> page(@Parameter(description = "查询用户角色(分页)对象", required = true)Page<SysUserRole> page) {
        return sysUserRoleService.page(page);
    }

}
