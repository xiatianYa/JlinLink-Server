package com.jinlink.controller.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.jinlink.common.api.Result;
import com.jinlink.core.page.PageQuery;
import com.jinlink.core.page.RPage;
import com.jinlink.modules.system.entity.dto.SysFeedbackAddDTO;
import com.jinlink.modules.system.entity.dto.SysFeedbackSearchDTO;
import com.jinlink.modules.system.entity.dto.SysFeedbackUpdateDTO;
import com.jinlink.modules.system.entity.dto.SysUserSearchDTO;
import com.jinlink.modules.system.entity.vo.SysFeedbackVo;
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
import com.jinlink.modules.system.service.SysFeedbackService;
import org.springframework.web.bind.annotation.RestController;
import java.io.Serializable;
import java.util.List;

/**
 * 意见反馈表 控制层。
 *
 * @author Summer
 * @since 1.0.0
 */
@RestController
@Tag(name = "意见反馈")
@RequiredArgsConstructor
@RequestMapping("/sysFeedback")
public class SysFeedbackController {

    @NonNull
    private SysFeedbackService sysFeedbackService;

    /**
     * 添加意见反馈表。
     *
     * @param sysFeedbackAddDTO 意见反馈表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @Operation(operationId = "1",summary = "添加意见反馈")
    @SaCheckPermission("sys:sysFeedback:save")
    public Result<Boolean> save(@Parameter(description = "意见反馈对象", required = true)@RequestBody SysFeedbackAddDTO sysFeedbackAddDTO) {
        return Result.success("请求成功",sysFeedbackService.saveSysFeedback(sysFeedbackAddDTO));
    }

    /**
     * 根据主键删除意见反馈表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @Operation(operationId = "2",summary = "删除意见反馈")
    @SaCheckPermission("sys:sysFeedback:delete")
    public Result<Boolean> remove(@Parameter(description = "主键ID", required = true)@PathVariable Serializable id) {
        return Result.success("请求成功",sysFeedbackService.removeById(id));
    }

    /**
     * 根据主键更新意见反馈表。
     *
     * @param sysFeedbackUpdateDTO 意见反馈表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @Operation(operationId = "3",summary = "修改意见反馈")
    @SaCheckPermission("sys:sysFeedback:update")
    public Result<Boolean> update(@Parameter(description = "意见反馈对象", required = true)@RequestBody SysFeedbackUpdateDTO sysFeedbackUpdateDTO) {
        return Result.success("请求成功",sysFeedbackService.updateSysFeedbackUpdateById(sysFeedbackUpdateDTO));
    }

    /**
     * 查询所有意见反馈表。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @Operation(operationId = "4",summary = "查询所有意见反馈")
    @SaCheckPermission("sys:sysFeedback:list")
    public Result<List<SysFeedbackVo>> list() {
        return Result.data(sysFeedbackService.listSysFeedbackVo());
    }

    /**
     * 根据意见反馈表主键获取详细信息。
     *
     * @param id 意见反馈表主键
     * @return 意见反馈表详情
     */
    @GetMapping("getInfo/{id}")
    @Operation(operationId = "5",summary = "获取意见反馈详细信息")
    @SaCheckPermission("sys:sysFeedback:info")
    public Result<SysFeedbackVo> getInfo(@Parameter(description = "主键ID", required = true)@PathVariable Serializable id) {
        return Result.success("请求成功",sysFeedbackService.getSysFeedbackVoById(id));
    }

    /**
     * 分页查询意见反馈表。
     *
     * @param query 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    @Operation(operationId = "6",summary = "获取意见反馈(分页)")
    @SaCheckPermission("sys:sysFeedback:page")
    public Result<RPage<SysFeedbackVo>> page(@Parameter(description = "分页对象", required = true) PageQuery query,@Parameter(description = "查询对象", required = true) SysFeedbackSearchDTO sysFeedbackSearchDTO) {
        Page<SysFeedbackVo> sysFeedbackVoPage =sysFeedbackService.pageSysFeedbackVo(query,sysFeedbackSearchDTO);
        return Result.data(RPage.build(sysFeedbackVoPage));
    }
}
