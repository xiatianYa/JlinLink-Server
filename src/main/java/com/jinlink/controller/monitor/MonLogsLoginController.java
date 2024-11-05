package com.jinlink.controller.monitor;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.bean.BeanUtil;
import com.jinlink.common.api.Result;
import com.jinlink.common.page.PageQuery;
import com.jinlink.common.page.RPage;
import com.jinlink.modules.monitor.entity.dto.MonLogsLoginSearchDTO;
import com.jinlink.modules.monitor.entity.vo.MonLogsLoginVo;
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
import com.jinlink.modules.monitor.entity.MonLogsLogin;
import com.jinlink.modules.monitor.service.MonLogsLoginService;
import org.springframework.web.bind.annotation.RestController;
import java.io.Serializable;
import java.util.List;

/**
 * 登录日志 控制层。
 *
 * @author Summer
 * @since 1.0.0
 */
@RestController
@Tag(name = "登录日志管理")
@RequiredArgsConstructor
@RequestMapping("/monLogsLogin")
public class MonLogsLoginController {

    @NonNull
    private MonLogsLoginService monLogsLoginService;

    /**
     * 添加登录日志。
     *
     * @param monLogsLogin 登录日志
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @Operation(operationId = "1",summary = "新增登录日志")
    @SaCheckPermission("mon:monLogsLogin:save")
    public Result<Boolean> save(@Parameter(description = "登录日志对象", required = true)@RequestBody MonLogsLogin monLogsLogin) {
        return Result.success("请求成功",monLogsLoginService.save(monLogsLogin));
    }

    /**
     * 根据主键删除登录日志。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @Operation(operationId = "2",summary = "删除登录日志")
    @SaCheckPermission("mon:monLogsLogin:delete")
    public Result<Boolean> remove(@Parameter(description = "日志ID", required = true)@PathVariable Serializable id) {
        return Result.success("请求成功",monLogsLoginService.removeById(id));
    }

    /**
     * 根据主键更新登录日志。
     *
     * @param monLogsLogin 登录日志
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @Operation(operationId = "3",summary = "修改登录日志")
    @SaCheckPermission("mon:monLogsLogin:update")
    public Result<Boolean> update(@Parameter(description = "登录日志对象", required = true)@RequestBody MonLogsLogin monLogsLogin) {
        return Result.success("请求成功",monLogsLoginService.updateById(monLogsLogin));
    }

    /**
     * 查询所有登录日志。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @Operation(operationId = "4",summary = "查询所有登录日志")
    @SaCheckPermission("mon:monLogsLogin:list")
    public Result<List<MonLogsLoginVo>> list() {
        return Result.success("请求成功",BeanUtil.copyToList(monLogsLoginService.list(),MonLogsLoginVo.class));
    }

    /**
     * 根据登录日志主键获取详细信息。
     *
     * @param id 登录日志主键
     * @return 登录日志详情
     */
    @GetMapping("getInfo/{id}")
    @Operation(operationId = "5",summary = "根据登录日志主键获取详细信息")
    @SaCheckPermission("mon:monLogsLogin:info")
    public Result<MonLogsLoginVo> getInfo(@Parameter(description = "日志ID", required = true)@PathVariable Serializable id) {
        return Result.success("请求成功", BeanUtil.copyProperties(monLogsLoginService.getById(id), MonLogsLoginVo.class));
    }

    /**
     * 分页查询登录日志。
     *
     * @param query 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    @Operation(operationId = "6",summary = "分页查询登录日志")
    @SaCheckPermission("mon:monLogsLogin:page")
    public Result<RPage<MonLogsLoginVo>> page(@Parameter(description = "分页对象", required = true) PageQuery query,@Parameter(description = "查询对象", required = true) MonLogsLoginSearchDTO monLogsLoginSearchDTO) {
        Page<MonLogsLoginVo> monLogsLoginPage = monLogsLoginService.listMonLogsLoginPage(query,monLogsLoginSearchDTO);
        return Result.data(RPage.build(monLogsLoginPage));
    }

}
