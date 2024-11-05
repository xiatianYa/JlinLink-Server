package com.jinlink.controller.monitor;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.jinlink.common.api.Result;
import com.jinlink.common.domain.Options;
import com.jinlink.common.page.PageQuery;
import com.jinlink.common.page.RPage;
import com.jinlink.modules.monitor.entity.dto.MonSchedulerSearchDTO;
import com.jinlink.modules.monitor.entity.vo.MonSchedulerVo;
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
import com.jinlink.modules.monitor.entity.MonScheduler;
import com.jinlink.modules.monitor.service.MonSchedulerService;
import org.springframework.web.bind.annotation.RestController;
import java.io.Serializable;
import java.util.List;

/**
 * 调度管理 控制层。
 *
 * @author Summer
 * @since 1.0.0
 */
@RestController
@Tag(name = "定时任务管理")
@RequiredArgsConstructor
@RequestMapping("/monScheduler")
public class MonSchedulerController {

    @NonNull
    private MonSchedulerService monSchedulerService;

    /**
     * 添加调度管理。
     *
     * @param monScheduler 调度管理
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @Operation(operationId = "1",summary = "新增定时任务")
    @SaCheckPermission("mon:scheduler:save")
    public Result<Boolean> save(@Parameter(description = "定时任务对象", required = true)@RequestBody MonScheduler monScheduler) {
        return Result.success("请求成功",monSchedulerService.saveScheduler(monScheduler));
    }

    /**
     * 根据主键删除调度管理。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @Operation(operationId = "2",summary = "删除定时任务")
    @SaCheckPermission("mon:scheduler:delete")
    public Result<Boolean> remove(@Parameter(description = "定时任务ID", required = true)@PathVariable Serializable id) {
        return Result.success("请求成功",monSchedulerService.removeSchedulerById(id));
    }

    /**
     * 根据主键更新调度管理。
     *
     * @param monScheduler 调度管理
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @Operation(operationId = "3",summary = "修改定时任务")
    @SaCheckPermission("mon:scheduler:update")
    public Result<Boolean> update(@Parameter(description = "定时任务对象", required = true)@RequestBody MonScheduler monScheduler) {
        return Result.success("请求成功!",monSchedulerService.updateSchedulerById(monScheduler)) ;
    }

    /**
     * 查询所有调度管理。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @Operation(operationId = "4",summary = "查询所有调度管理")
    @SaCheckPermission("mon:scheduler:list")
    public Result<List<MonScheduler>> list() {
        return Result.success("请求成功",monSchedulerService.list());
    }

    /**
     * 根据调度管理主键获取详细信息。
     *
     * @param id 调度管理主键
     * @return 调度管理详情
     */
    @GetMapping("getInfo/{id}")
    @Operation(operationId = "5",summary = "根据调度管理主键获取详细信息")
    @SaCheckPermission("mon:scheduler:info")
    public Result<MonScheduler> getInfo(@Parameter(description = "定时任务ID", required = true)@PathVariable Serializable id) {
        return Result.success("请求成功",monSchedulerService.getById(id));
    }

    /**
     * 分页查询调度管理。
     *
     * @param query 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    @Operation(operationId = "6",summary = "分页查询调度管理")
    @SaCheckPermission("mon:scheduler:page")
    public Result<RPage<MonSchedulerVo>> page(@Parameter(description = "分页对象", required = true) PageQuery query, @Parameter(description = "查询对象", required = true) MonSchedulerSearchDTO monSchedulerSearchDTO) {
        return Result.data(monSchedulerService.listMonSchedulerPage(query,monSchedulerSearchDTO));
    }

    /**
     * 根据主键删除调度管理。
     *
     * @param ids 主键列表
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("removeByIds")
    @Operation(operationId = "7",summary = "删除多个定时任务")
    @SaCheckPermission("mon:scheduler:delete")
    public Result<Boolean> removeByIds(@Parameter(description = "定时任务IDS", required = true)@RequestBody List<Long> ids) {
        return Result.success("删除成功",monSchedulerService.removeSchedulerByIds(ids));
    }

    /**
     * 查询全部调度任务名称。
     */
    @GetMapping("allJobNames")
    @Operation(operationId = "8",summary = "查询全部调度任务名称")
    @SaCheckPermission("mon:scheduler:allJobNames")
    public Result<List<Options<String>>> allJobNames() {
        return Result.success("请求成功",monSchedulerService.allJobNames());
    }
}
