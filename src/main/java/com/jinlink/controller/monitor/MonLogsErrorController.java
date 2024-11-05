package com.jinlink.controller.monitor;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.bean.BeanUtil;
import com.jinlink.common.api.Result;
import com.jinlink.common.page.PageQuery;
import com.jinlink.common.page.RPage;
import com.jinlink.modules.monitor.entity.dto.MonLogsOperationSearchDTO;
import com.jinlink.modules.monitor.entity.vo.MonLogsErrorVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.jinlink.modules.monitor.entity.MonLogsError;
import com.jinlink.modules.monitor.service.MonLogsErrorService;
import org.springframework.web.bind.annotation.RestController;
import java.io.Serializable;
import java.util.List;

/**
 * 错误异常日志 控制层。
 *
 * @author Summer
 * @since 1.0.0
 */
@RestController
@Tag(name = "异常日志管理")
@RequiredArgsConstructor
@RequestMapping("/monLogsError")
public class MonLogsErrorController {

    @NonNull
    private MonLogsErrorService monLogsErrorService;

    /**
     * 添加错误异常日志。
     *
     * @param monLogsError 错误异常日志
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @Operation(operationId = "1",summary = "新增异常日志")
    @SaCheckPermission("mon:monLogsError:save")
    public Result<Boolean> save(@Parameter(description = "日常日志对象", required = true)@RequestBody MonLogsError monLogsError) {
        return Result.success("请求成功",monLogsErrorService.save(monLogsError));
    }

    /**
     * 根据主键删除错误异常日志。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @Operation(operationId = "2",summary = "删除异常日志")
    @SaCheckPermission("mon:monLogsError:delete")
    public Result<Boolean> remove(@Parameter(description = "日志ID", required = true)@PathVariable Serializable id) {
        return Result.success("请求成功",monLogsErrorService.removeById(id));
    }

    /**
     * 根据主键更新错误异常日志。
     *
     * @param monLogsError 错误异常日志
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @Operation(operationId = "3",summary = "更新异常日志")
    @SaCheckPermission("mon:monLogsError:update")
    public Result<Boolean> update(@Parameter(description = "日常日志对象", required = true)@RequestBody MonLogsError monLogsError) {
        return Result.success("请求成功",monLogsErrorService.updateById(monLogsError));
    }

    /**
     * 查询所有错误异常日志。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @Operation(operationId = "4",summary = "查询所有异常日志")
    @SaCheckPermission("mon:monLogsError:list")
    public Result<List<MonLogsErrorVo>> list() {
        return Result.success("请求成功",BeanUtil.copyToList(monLogsErrorService.list(), MonLogsErrorVo.class));
    }

    /**
     * 根据错误异常日志主键获取详细信息。
     *
     * @param id 错误异常日志主键
     * @return 错误异常日志详情
     */
    @GetMapping("getInfo/{id}")
    @Operation(operationId = "5",summary = "查询异常日志详细")
    @SaCheckPermission("mon:monLogsError:info")
    public Result<MonLogsErrorVo> getInfo(@Parameter(description = "日志ID", required = true)@PathVariable Serializable id) {
        return Result.success("请求成功",BeanUtil.copyProperties(monLogsErrorService.getById(id), MonLogsErrorVo.class));
    }

    /**
     * 分页查询错误异常日志。
     *
     * @param pageQuery 分页参数
     * @return 分页对象
     */
    @GetMapping("page")
    @Operation(operationId = "6",summary = "分页查询异常日志管理")
    @SaCheckPermission("mon:monLogsError:page")
    public Result<RPage<MonLogsErrorVo>> page(@Parameter(description = "分页对象", required = true) @Valid PageQuery pageQuery,
                                              @Parameter(description = "查询对象") MonLogsOperationSearchDTO monLogsOperationSearchDTO) {
        RPage<MonLogsErrorVo> monLogsOperationPage= monLogsErrorService.listMonLogsErrorPage(pageQuery,monLogsOperationSearchDTO);
        return Result.data(monLogsOperationPage);
    }

}
