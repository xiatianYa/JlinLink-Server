package com.jinlink.controller.monitor;

import com.jinlink.common.api.Result;
import com.jinlink.common.page.PageQuery;
import com.jinlink.common.page.RPage;
import com.jinlink.modules.monitor.entity.dto.MonLogsOperationSearchDTO;
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
    public boolean save(@RequestBody MonLogsOperation monLogsOperation) {
        return monLogsOperationService.save(monLogsOperation);
    }

    /**
     * 根据主键删除操作日志。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public boolean remove(@PathVariable Serializable id) {
        return monLogsOperationService.removeById(id);
    }

    /**
     * 根据主键更新操作日志。
     *
     * @param monLogsOperation 操作日志
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public boolean update(@RequestBody MonLogsOperation monLogsOperation) {
        return monLogsOperationService.updateById(monLogsOperation);
    }

    /**
     * 查询所有操作日志。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public List<MonLogsOperation> list() {
        return monLogsOperationService.list();
    }

    /**
     * 根据操作日志主键获取详细信息。
     *
     * @param id 操作日志主键
     * @return 操作日志详情
     */
    @GetMapping("getInfo/{id}")
    public MonLogsOperation getInfo(@PathVariable Serializable id) {
        return monLogsOperationService.getById(id);
    }

    /**
     * 分页查询操作日志。
     *
     * @param pageQuery 分页参数对象
     * @return 分页对象
     */
    @GetMapping("page")
    public Result<RPage<MonLogsOperationVO>> page(@Parameter(description = "分页对象", required = true) @Valid PageQuery pageQuery,
                                                  @Parameter(description = "查询对象") MonLogsOperationSearchDTO monLogsOperationSearchDTO) {
        RPage<MonLogsOperationVO> monLogsOperationPage= monLogsOperationService.listMonLogsOperationPage(pageQuery,monLogsOperationSearchDTO);
        return Result.data(monLogsOperationPage);
    }

}
