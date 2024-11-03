package com.jinlink.controller.monitor;

import cn.dev33.satoken.annotation.SaCheckOr;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.jinlink.common.api.Result;
import com.jinlink.common.page.PageQuery;
import com.jinlink.common.page.RPage;
import com.jinlink.modules.monitor.entity.dto.MonLogsSchedulerSearchDTO;
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
import com.jinlink.modules.monitor.entity.MonLogsScheduler;
import com.jinlink.modules.monitor.service.MonLogsSchedulerService;
import org.springframework.web.bind.annotation.RestController;
import java.io.Serializable;
import java.util.List;

/**
 * 调度日志 控制层。
 *
 * @author Summer
 * @since 1.0.0
 */
@RestController
@Tag(name = "定时任务日志管理")
@RequiredArgsConstructor
@RequestMapping("/monLogsScheduler")
@SaCheckOr(role = @SaCheckRole("R_SUPER"))
public class MonLogsSchedulerController {

    @NonNull
    private MonLogsSchedulerService monLogsSchedulerService;

    /**
     * 添加调度日志。
     *
     * @param monLogsScheduler 调度日志
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @Operation(operationId = "1",summary = "新增添加调度日志")
    public Result<Boolean> save(@Parameter(description = "调度日志对象", required = true)@RequestBody MonLogsScheduler monLogsScheduler) {
        return Result.success("请求成功",monLogsSchedulerService.save(monLogsScheduler));
    }

    /**
     * 根据主键删除调度日志。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @Operation(operationId = "2",summary = "删除调度日志")
    public Result<Boolean> remove(@Parameter(description = "日志ID", required = true)@PathVariable Serializable id) {
        return Result.success("请求成功",monLogsSchedulerService.removeById(id));
    }

    /**
     * 根据主键更新调度日志。
     *
     * @param monLogsScheduler 调度日志
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @Operation(operationId = "3",summary = "修改调度日志")
    public boolean update(@Parameter(description = "调度日志对象", required = true)@RequestBody MonLogsScheduler monLogsScheduler) {
        return monLogsSchedulerService.updateById(monLogsScheduler);
    }

    /**
     * 查询所有调度日志。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @Operation(operationId = "4",summary = "查询所有调度日志")
    public List<MonLogsScheduler> list() {
        return monLogsSchedulerService.list();
    }

    /**
     * 根据调度日志主键获取详细信息。
     *
     * @param id 调度日志主键
     * @return 调度日志详情
     */
    @GetMapping("getInfo/{id}")
    @Operation(operationId = "5",summary = "根据调度日志主键获取详细信息")
    public MonLogsScheduler getInfo(@Parameter(description = "日志ID", required = true)@PathVariable Serializable id) {
        return monLogsSchedulerService.getById(id);
    }

    /**
     * 分页查询调度日志。
     *
     * @param query 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    @Operation(operationId = "6",summary = "分页查询调度日志")
    public Result<RPage<MonLogsScheduler>> page(@Parameter(description = "分页对象", required = true) PageQuery query, @Parameter(description = "查询对象", required = true) MonLogsSchedulerSearchDTO monLogsSchedulerSearchDTO) {
        Page<MonLogsScheduler> monLogsSchedulerPage = monLogsSchedulerService.listMonLogsSchedulerPage(query,monLogsSchedulerSearchDTO);
        return Result.data(RPage.build(monLogsSchedulerPage));
    }

}
