package com.jinlink.controller.system;

import cn.dev33.satoken.annotation.SaCheckOr;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.jinlink.common.api.Result;
import com.jinlink.modules.system.entity.dto.SysMenuFormDTO;
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
import com.jinlink.modules.system.entity.SysMenu;
import com.jinlink.modules.system.service.SysMenuService;
import org.springframework.web.bind.annotation.RestController;
import java.io.Serializable;
import java.util.List;

/**
 * 菜单管理 控制层。
 *
 * @author Summer
 * @since 1.0.0
 */
@RestController
@Tag(name = "菜单管理")
@RequiredArgsConstructor
@RequestMapping("/sysMenu")
@SaCheckOr(role = @SaCheckRole("R_SUPER"))
public class SysMenuController {
    @NonNull
    private SysMenuService sysMenuService;

    /**
     * 添加菜单管理。
     *
     * @param sysMenu 菜单管理
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @Operation(operationId = "1",summary = "添加菜单")
    public Result<Boolean> save(@RequestBody SysMenuFormDTO sysMenu) {
        return Result.success("新增成功!",sysMenuService.saveMenu(sysMenu));
    }

    /**
     * 根据主键删除菜单管理。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @Operation(operationId = "2",summary = "删除菜单")
    public Result<Boolean> remove(@PathVariable Serializable id) {
        return Result.success("删除成功!",sysMenuService.removeMenuById(id));
    }

    /**
     * 根据主键删除菜单管理。
     *
     * @param ids 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("removeByIds")
    @Operation(operationId = "3",summary = "删除多个菜单")
    public Result<Boolean> removeByIds(@Parameter(description = "用户IDS", required = true)@RequestBody List<Long> ids) {
        return Result.success("删除成功",sysMenuService.removeMenuByIds(ids));
    }

    /**
     * 根据主键更新菜单管理。
     *
     * @param sysMenu 菜单管理
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @Operation(operationId = "4",summary = "修改菜单")
    public Result<Boolean> update(@RequestBody SysMenuFormDTO sysMenu) {
        return Result.success("修改成功!",sysMenuService.updateMenu(sysMenu));
    }

    /**
     * 查询所有菜单管理。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @Operation(operationId = "5",summary = "查询所有菜单")
    public List<SysMenu> list() {
        return sysMenuService.list();
    }

    /**
     * 根据菜单管理主键获取详细信息。
     *
     * @param id 菜单管理主键
     * @return 菜单管理详情
     */
    @GetMapping("getInfo/{id}")
    @Operation(operationId = "6",summary = "获取菜单详细")
    public SysMenu getInfo(@PathVariable Serializable id) {
        return sysMenuService.getById(id);
    }

    /**
     * 分页查询菜单管理。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    @Operation(operationId = "7",summary = "获取菜单列表(分页)")
    public Page<SysMenu> page(Page<SysMenu> page) {
        return sysMenuService.page(page);
    }

}
