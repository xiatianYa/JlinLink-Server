package com.jinlink.controller.system;

import com.jinlink.common.api.Result;
import com.jinlink.common.page.PageQuery;
import com.jinlink.common.page.RPage;
import com.jinlink.modules.system.entity.SysRole;
import com.jinlink.modules.system.entity.dto.SysRoleSearchDTO;
import com.jinlink.modules.system.entity.dto.SysUserSearchDTO;
import com.jinlink.modules.system.entity.vo.SysMenuTreeVO;
import com.jinlink.modules.system.entity.vo.SysMenuVO;
import com.jinlink.modules.system.entity.vo.SysRoleOptionVo;
import com.jinlink.modules.system.entity.vo.SysUserVo;
import com.jinlink.modules.system.service.SysMenuService;
import com.jinlink.modules.system.service.SysRoleService;
import com.jinlink.modules.system.service.SysUserService;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.NonNull;

import java.util.List;

@RestController
@Tag(name = "系统管理")
@RequiredArgsConstructor
@RequestMapping("/systemManage")
public class SystemManageController {
    @NonNull
    private SysRoleService sysRoleService;
    @NonNull
    private SysUserService sysUserService;
    @NonNull
    private SysMenuService sysMenuService;

    /**
     * 获取用户列表
     */
    @GetMapping("getUserList")
    @Operation(operationId = "1",summary = "获取角色信息列表(分页)")
    public Result<RPage<SysUserVo>> getUserList(@Parameter(description = "分页对象", required = true) PageQuery query,
                                       @Parameter(description = "查询对象", required = true) SysUserSearchDTO sysUserSearchDTO) {
        Page<SysUserVo> sysRolePage = sysUserService.listUserPage(query, sysUserSearchDTO);
        return Result.success("请求成功",RPage.build(sysRolePage));
    }

    /**
     * 获取角色列表
     */
    @GetMapping("getRoleList")
    @Operation(operationId = "2",summary = "获取用户信息列表(分页)")
    public Result<RPage<SysRole>> getRoleList(@Parameter(description = "分页对象", required = true) PageQuery query,
                                      @Parameter(description = "查询对象", required = true)SysRoleSearchDTO sysRoleSearchDTO) {
        Page<SysRole> sysRolePage = sysRoleService.listRolePage(query, sysRoleSearchDTO);
        return Result.success("请求成功",RPage.build(sysRolePage));
    }

    /**
     * 获取菜单列表
     */
    @GetMapping("getMenuList/v2")
    @Operation(operationId = "3",summary = "获取菜单列表")
    public Result<RPage<SysMenuVO>> getMenuList(@Parameter(description = "分页对象", required = true) PageQuery query) {
        return Result.success("请求成功",sysMenuService.getMenuList(query));
    }

    /**
     * 获取所有菜单
     */
    @GetMapping("getAllPages")
    @Operation(operationId = "4",summary = "获取所有菜单")
    public Result<List<String>> getAllPages() {
        return Result.success("请求成功",sysMenuService.getAllPages());
    }

    /**
     * 获取所有角色
     */
    @GetMapping("getAllRoles")
    @Operation(operationId = "5",summary = "获取所有路由")
    public Result<List<SysRoleOptionVo>> getAllRoles() {
        return Result.success("请求成功",sysRoleService.getAllRoles());
    }

    /**
     * 获取菜单树
     */
    @GetMapping("getMenuTree")
    @Operation(operationId = "6",summary = "获取菜单树")
    public Result<List<SysMenuTreeVO>> getMenuTree() {
        return Result.success("请求成功",sysMenuService.getMenuTree());
    }
}
