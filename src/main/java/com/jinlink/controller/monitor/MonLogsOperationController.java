package com.jinlink.controller.monitor;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.bean.BeanUtil;
import com.jinlink.common.api.Result;
import com.jinlink.common.page.PageQuery;
import com.jinlink.common.page.RPage;
import com.jinlink.modules.monitor.entity.dto.MonLogsOperationSearchDTO;
import com.jinlink.modules.monitor.entity.vo.MonLogsOperationVo;
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
import com.jinlink.modules.monitor.entity.MonLogsOperation;
import com.jinlink.modules.monitor.service.MonLogsOperationService;
import org.springframework.web.bind.annotation.RestController;
import java.io.Serializable;
import java.util.List;

/**
 * 操作日志 控制层。
 *
 * @author Summer
 * @since 1.0.0
 */
@RestController
@Tag(name = "操作日志管理")
@RequiredArgsConstructor
@RequestMapping("/monLogsOperation")
public class MonLogsOperationController {

    @NonNull
    private MonLogsOperationService monLogsOperationService;

    /**
     * 添加操作日志。
     *
     * @param monLogsOperation 操作日志
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @Operation(operationId = "1",summary = "新增操作日志")
    @SaCheckPermission("mon:monLogsOperation:save")
    public Result<Boolean> save(@Parameter(description = "操作日志对象", required = true)@RequestBody MonLogsOperation monLogsOperation) {
        return Result.success("请求成功",monLogsOperationService.save(monLogsOperation));
    }

    /**
     * 根据主键删除操作日志。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @Operation(operationId = "2",summary = "删除操作日志")
    @SaCheckPermission("mon:monLogsOperation:delete")
    public Result<Boolean> remove(@Parameter(description = "日志ID", required = true)@PathVariable Serializable id) {
        return Result.success("请求成功",monLogsOperationService.removeById(id));
    }

    /**
     * 根据主键更新操作日志。
     *
     * @param monLogsOperation 操作日志
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @Operation(operationId = "3",summary = "修改操作日志")
    @SaCheckPermission("mon:monLogsOperation:update")
    public Result<Boolean> update(@Parameter(description = "操作日志对象", required = true)@RequestBody MonLogsOperation monLogsOperation) {
        return Result.success("请求成功",monLogsOperationService.updateById(monLogsOperation));
    }

    /**
     * 查询所有操作日志。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @Operation(operationId = "4",summary = "查询所有操作日志")
    @SaCheckPermission("mon:monLogsOperation:list")
    public Result<List<MonLogsOperation>> list() {
        return Result.success("请求成功",BeanUtil.copyToList(monLogsOperationService.list(), MonLogsOperation.class));
    }

    /**
     * 根据操作日志主键获取详细信息。
     *
     * @param id 操作日志主键
     * @return 操作日志详情
     */
    @GetMapping("getInfo/{id}")
    @Operation(operationId = "5",summary = "查询操作日志详细信息")
    @SaCheckPermission("mon:monLogsOperation:info")
    public Result<MonLogsOperationVo> getInfo(@Parameter(description = "日志ID", required = true)@PathVariable Serializable id) {
        return Result.success("请求成功", BeanUtil.copyProperties(monLogsOperationService.getById(id),MonLogsOperationVo.class));
    }

    /**
     * 分页查询操作日志。
     *
     * @param pageQuery 分页参数对象
     * @return 分页对象
     */
    @GetMapping("page")
    @Operation(operationId = "6",summary = "分页查询操作日志管理")
    @SaCheckPermission("mon:monLogsOperation:page")
    public Result<RPage<MonLogsOperationVo>> page(@Parameter(description = "分页对象", required = true) @Valid PageQuery pageQuery,
                                                  @Parameter(description = "查询对象") MonLogsOperationSearchDTO monLogsOperationSearchDTO) {
        RPage<MonLogsOperationVo> monLogsOperationPage= monLogsOperationService.listMonLogsOperationPage(pageQuery,monLogsOperationSearchDTO);
        return Result.data(monLogsOperationPage);
    }

    /**
     * 清空操作日志。
     */
    @DeleteMapping("clearAll")
    @Operation(operationId = "7",summary = "清空操作日志")
    @SaCheckPermission("mon:monLogsOperation:delete")
    public Result<Boolean> clearAll() {
        return Result.data(monLogsOperationService.clearAll());
    }
}
