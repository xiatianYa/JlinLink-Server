package com.jinlink.controller.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.jinlink.common.api.Result;
import com.jinlink.common.page.PageQuery;
import com.jinlink.common.page.RPage;
import com.jinlink.modules.system.entity.SysDictItem;
import com.jinlink.modules.system.entity.dto.SysDictItemDeleteDTO;
import com.jinlink.modules.system.entity.dto.SysDictItemSearchDTO;
import com.jinlink.modules.system.entity.vo.SysDictItemVO;
import com.jinlink.modules.system.service.SysDictItemService;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * 数据字典子项管理 控制层。
 *
 * @author Summer
 * @since 1.0.0
 */
@RestController
@Tag(name = "字典子项管理")
@RequiredArgsConstructor
@RequestMapping("/sysDictItem")
public class SysDictItemController {

    @NonNull
    private SysDictItemService sysDictItemService;

    /**
     * 添加数据字典子项管理。
     *
     * @param sysDictItem 数据字典子项管理
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @Operation(operationId = "1",summary = "添加子字典")
    @SaCheckPermission("sys:dict:item:save")
    public Result<Boolean> save(@Parameter(description = "子字典对象", required = true)@RequestBody SysDictItem sysDictItem) {
        return Result.success("请求成功",sysDictItemService.save(sysDictItem));
    }

    /**
     * 根据主键删除数据字典子项管理。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @Operation(operationId = "2",summary = "删除子字典")
    @SaCheckPermission("sys:dict:item:delete")
    public Result<Boolean> remove(@Parameter(description = "主键ID", required = true)@PathVariable Serializable id) {
        return Result.success("请求成功",sysDictItemService.removeById(id));
    }

    /**
     * 根据主键更新数据字典子项管理。
     *
     * @param sysDictItem 数据字典子项管理
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @Operation(operationId = "3",summary = "修改子字典")
    @SaCheckPermission("sys:dict:item:update")
    public Result<Boolean> update(@RequestBody SysDictItem sysDictItem) {
        return Result.success("请求成功",sysDictItemService.updateById(sysDictItem));
    }

    /**
     * 查询所有数据字典子项管理。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @Operation(operationId = "4",summary = "查询所有子字典")
    @SaCheckPermission("sys:dict:item:list")
    public Result<List<SysDictItem>> list() {
        return Result.success("请求成功",sysDictItemService.list());
    }

    /**
     * 根据数据字典子项管理主键获取详细信息。
     *
     * @param id 数据字典子项管理主键
     * @return 数据字典子项管理详情
     */
    @GetMapping("getInfo/{id}")
    @Operation(operationId = "5",summary = "查询子字典详细信息")
    @SaCheckPermission("sys:dict:item:info")
    public Result<SysDictItem> getInfo(@PathVariable Serializable id) {
        return Result.success("请求成功",sysDictItemService.getById(id));
    }

    /**
     * 分页查询数据字典子项管理。
     *
     * @param pageQuery 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    @Operation(operationId = "6",summary = "查询子字典分页")
    @SaCheckPermission("sys:dict:item:page")
    public Result<RPage<SysDictItemVO>> page(@Parameter(description = "分页对象", required = true) @Valid PageQuery pageQuery,
                                             @Parameter(description = "查询对象") SysDictItemSearchDTO sysDictItemSearchDTO) {
        Page<SysDictItemVO> sysDictItemPage = sysDictItemService.listSysDictItemPage(pageQuery,sysDictItemSearchDTO);
        return Result.data(RPage.build(sysDictItemPage));
    }

    /**
     * 根据主键删除数据字典子项管理(批量删除)。
     */
    @DeleteMapping
    @Operation(operationId = "7", summary = "批量删除数据字典子项管理信息")
    @SaCheckPermission("sys:dict:item:delete")
    public Result<Boolean> batchDelete(@Parameter(description = "删除对象") @RequestBody SysDictItemDeleteDTO sysDictItemDeleteDTO) {
        return Result.success("请求成功",sysDictItemService.removeByIds(sysDictItemDeleteDTO.getIds()));
    }
}
