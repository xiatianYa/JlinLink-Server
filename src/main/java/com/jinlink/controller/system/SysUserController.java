package com.jinlink.controller.system;

import cn.dev33.satoken.annotation.SaCheckOr;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.jinlink.common.api.Result;
import com.jinlink.common.page.PageQuery;
import com.jinlink.common.page.RPage;
import com.jinlink.modules.system.entity.dto.SysUserFormDTO;
import com.jinlink.modules.system.entity.dto.SysUserSearchDTO;
import com.jinlink.modules.system.entity.vo.SysUserVO;
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
import com.jinlink.modules.system.entity.SysUser;
import com.jinlink.modules.system.service.SysUserService;
import org.springframework.web.bind.annotation.RestController;
import java.io.Serializable;
import java.util.List;

/**
 * 用户管理 控制层。
 *
 * @author Summer
 * @since 1.0.0
 */
@RestController
@Tag(name = "用户管理")
@RequiredArgsConstructor
@RequestMapping("/sysUser")
@SaCheckOr(role = @SaCheckRole("R_SUPER"))
public class SysUserController {

    @NonNull
    private SysUserService sysUserService;

    /**
     * 新增用户管理。
     *
     * @param sysUser 用户管理
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @Operation(operationId = "1",summary = "添加用户")
    public Result<Boolean> save(@Parameter(description = "添加对象", required = true)@RequestBody SysUserFormDTO sysUser) {
        return Result.success("新增成功",sysUserService.saveUser(sysUser));
    }

    /**
     * 根据主键删除用户管理。
     *
     * @param ids 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("removeByIds")
    @Operation(operationId = "2",summary = "删除多个用户")
    public Result<Boolean> removeByIds(@Parameter(description = "用户IDS", required = true)@RequestBody List<Long> ids) {
        return Result.success("删除成功!",sysUserService.removeByIds(ids)) ;
    }

    /**
     * 根据主键删除用户管理。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @Operation(operationId = "3",summary = "删除单个用户")
    public Result<Boolean> remove(@Parameter(description = "用户ID", required = true)@PathVariable Long id) {
        return Result.success("操作成功", sysUserService.removeById(id)) ;
    }

    /**
     * 根据主键更新用户管理。
     *
     * @param sysUser 用户管理
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @Operation(operationId = "4",summary = "修改用户对象")
    public Result<Boolean> update(@Parameter(description = "修改对象", required = true)@RequestBody SysUserFormDTO sysUser) {
        return Result.success("更新成功!",sysUserService.updateUser(sysUser));
    }

    /**
     * 查询所有用户管理。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @Operation(operationId = "5",summary = "查询全部用户")
    public List<SysUser> list() {
        return sysUserService.list();
    }

    /**
     * 根据用户管理主键获取详细信息。
     *
     * @param id 用户管理主键
     * @return 用户管理详情
     */
    @GetMapping("getInfo/{id}")
    @Operation(operationId = "6",summary = "查询用户详细")
    public SysUser getInfo(@Parameter(description = "用户ID", required = true)@PathVariable Serializable id) {
        return sysUserService.getById(id);
    }

    /**
     * 分页查询用户管理。
     *
     * @param query 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    @Operation(operationId = "7",summary = "查询用户(分页)")
    public Result<RPage<SysUserVO>> page(@Parameter(description = "分页对象", required = true) PageQuery query,
                              @Parameter(description = "查询对象", required = true) SysUserSearchDTO sysUserSearchDTO) {
        Page<SysUserVO> sysRolePage = sysUserService.listUserPage(query, sysUserSearchDTO);
        return Result.success("请求成功", RPage.build(sysRolePage));
    }

}
