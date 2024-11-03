package com.jinlink.controller.monitor;

import com.jinlink.common.api.Result;
import com.jinlink.common.page.PageQuery;
import com.jinlink.common.page.RPage;
import com.jinlink.modules.monitor.entity.dto.MonLogsOperationSearchDTO;
import com.jinlink.modules.monitor.entity.vo.MonLogsErrorVO;
import com.jinlink.modules.monitor.entity.vo.MonLogsOperationVO;
import com.mybatisflex.core.paginate.Page;
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
import org.springframework.beans.factory.annotation.Autowired;
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
    public boolean save(@RequestBody MonLogsError monLogsError) {
        return monLogsErrorService.save(monLogsError);
    }

    /**
     * 根据主键删除错误异常日志。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public boolean remove(@PathVariable Serializable id) {
        return monLogsErrorService.removeById(id);
    }

    /**
     * 根据主键更新错误异常日志。
     *
     * @param monLogsError 错误异常日志
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public boolean update(@RequestBody MonLogsError monLogsError) {
        return monLogsErrorService.updateById(monLogsError);
    }

    /**
     * 查询所有错误异常日志。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public List<MonLogsError> list() {
        return monLogsErrorService.list();
    }

    /**
     * 根据错误异常日志主键获取详细信息。
     *
     * @param id 错误异常日志主键
     * @return 错误异常日志详情
     */
    @GetMapping("getInfo/{id}")
    public MonLogsError getInfo(@PathVariable Serializable id) {
        return monLogsErrorService.getById(id);
    }

    /**
     * 分页查询错误异常日志。
     *
     * @param pageQuery 分页参数
     * @return 分页对象
     */
    @GetMapping("page")
    public Result<RPage<MonLogsErrorVO>> page(@Parameter(description = "分页对象", required = true) @Valid PageQuery pageQuery,
                                            @Parameter(description = "查询对象") MonLogsOperationSearchDTO monLogsOperationSearchDTO) {
        RPage<MonLogsErrorVO> monLogsOperationPage= monLogsErrorService.listMonLogsErrorPage(pageQuery,monLogsOperationSearchDTO);
        return Result.data(monLogsOperationPage);
    }

}
