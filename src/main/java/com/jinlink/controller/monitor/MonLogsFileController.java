package com.jinlink.controller.monitor;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.bean.BeanUtil;
import com.jinlink.common.api.Result;
import com.jinlink.core.page.PageQuery;
import com.jinlink.core.page.RPage;
import com.jinlink.modules.file.entity.dto.MonLogsFileSearchDTO;
import com.jinlink.modules.monitor.entity.vo.MonLogsFileVo;
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
import com.jinlink.modules.monitor.entity.MonLogsFile;
import com.jinlink.modules.monitor.service.MonLogsFileService;
import org.springframework.web.bind.annotation.RestController;
import java.io.Serializable;
import java.util.List;

/**
 * 文件上传日志 控制层。
 *
 * @author Summer
 * @since 1.0.0
 */
@RestController
@Tag(name = "文件上传日志管理")
@RequiredArgsConstructor
@RequestMapping("/monLogsFile")
public class MonLogsFileController {

    @NonNull
    private MonLogsFileService monLogsFileService;

    /**
     * 添加文件上传日志。
     *
     * @param monLogsFile 文件上传日志
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @Operation(operationId = "1",summary = "新增文件上传日志")
    @SaCheckPermission("mon:monLogsFile:save")
    public Result<Boolean> save(@Parameter(description = "文件日志对象", required = true)@RequestBody MonLogsFile monLogsFile) {
        return Result.success("请求成功",monLogsFileService.save(monLogsFile));
    }

    /**
     * 根据主键删除文件上传日志。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @Operation(operationId = "2",summary = "删除文件上传日志")
    @SaCheckPermission("mon:monLogsFile:delete")
    public Result<Boolean> remove(@Parameter(description = "文件日志ID", required = true)@PathVariable Serializable id) {
        return Result.success("请求成功",monLogsFileService.removeById(id));
    }

    /**
     * 根据主键更新文件上传日志。
     *
     * @param monLogsFile 文件上传日志
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @Operation(operationId = "3",summary = "修改文件上传日志")
    @SaCheckPermission("mon:monLogsFile:update")
    public Result<Boolean> update(@Parameter(description = "文件日志对象", required = true)@RequestBody MonLogsFile monLogsFile) {
        return Result.success("请求成功",monLogsFileService.updateById(monLogsFile));
    }

    /**
     * 查询所有文件上传日志。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @Operation(operationId = "4",summary = "查询所有文件上传日志")
    @SaCheckPermission("mon:monLogsFile:list")
    public Result<List<MonLogsFileVo>> list() {
        return Result.data(BeanUtil.copyToList(monLogsFileService.list(),MonLogsFileVo.class));
    }

    /**
     * 根据文件上传日志主键获取详细信息。
     *
     * @param id 文件上传日志主键
     * @return 文件上传日志详情
     */
    @GetMapping("getInfo/{id}")
    @Operation(operationId = "5",summary = "查询文件上传详细")
    @SaCheckPermission("mon:monLogsFile:info")
    public Result<MonLogsFileVo> getInfo(@Parameter(description = "文件日志ID", required = true)@PathVariable Serializable id) {
        return Result.success("请求成功", BeanUtil.copyProperties(monLogsFileService.getById(id),MonLogsFileVo.class));
    }

    /**
     * 分页查询文件上传日志。
     *
     * @param pageQuery 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    @Operation(operationId = "6",summary = "分页查询文件上传日志管理")
    @SaCheckPermission("mon:monLogsFile:page")
    public Result<RPage<MonLogsFileVo>> page(@Parameter(description = "分页对象", required = true)@Valid PageQuery pageQuery, MonLogsFileSearchDTO monLogsFileSearchDTO) {
        RPage<MonLogsFileVo> monLogsFileVoRPage = monLogsFileService.listMonLogsFileVoPage(pageQuery,monLogsFileSearchDTO);
        return Result.data(monLogsFileVoRPage);
    }

    /**
     * 清空文件日志。
     */
    @DeleteMapping("clearAll")
    @Operation(operationId = "7",summary = "清空文件日志")
    @SaCheckPermission("mon:monLogsLogin:delete")
    public Result<Boolean> clearAll() {
        return Result.data(monLogsFileService.clearAll());
    }
}
