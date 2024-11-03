package com.jinlink.controller.system;

import com.jinlink.common.api.Result;
import com.jinlink.common.page.RPage;
import com.jinlink.modules.system.entity.dto.SysDictItemDeleteDTO;
import com.jinlink.modules.system.entity.vo.SysDictItemOptionsVO;
import com.jinlink.modules.system.entity.vo.SysDictVO;
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
import com.jinlink.modules.system.entity.SysDict;
import com.jinlink.modules.system.service.SysDictService;
import org.springframework.web.bind.annotation.RestController;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 数据字典管理 控制层。
 *
 * @author Summer
 * @since 1.0.0
 */
@RestController
@Tag(name = "字典管理")
@RequiredArgsConstructor
@RequestMapping("/sysDict")
public class SysDictController {

    @NonNull
    private SysDictService sysDictService;

    /**
     * 添加数据字典管理。
     *
     * @param sysDict 数据字典管理
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @Operation(operationId = "1",summary = "添加字典")
    public Result<Boolean> save(@Parameter(description = "字典对象", required = true)@RequestBody SysDict sysDict) {
        return Result.success("请求成功",sysDictService.save(sysDict));
    }

    /**
     * 根据主键删除数据字典管理。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @Operation(operationId = "2",summary = "删除字典")
    public Result<Boolean> remove(@Parameter(description = "主键ID", required = true)@PathVariable Serializable id) {
        return Result.success("请求成功",sysDictService.removeDictById(id));
    }

    /**
     * 根据主键更新数据字典管理。
     *
     * @param sysDict 数据字典管理
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @Operation(operationId = "3",summary = "修改字典")
    public Result<Boolean> update(@Parameter(description = "字典对象", required = true)@RequestBody SysDict sysDict) {
        return Result.success("请求成功",sysDictService.updateById(sysDict));
    }

    /**
     * 查询所有数据字典管理。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @Operation(operationId = "4",summary = "查询所有字典")
    public Result<List<SysDict>> list() {
        return Result.success("请求成功",sysDictService.list());
    }

    /**
     * 根据数据字典管理主键获取详细信息。
     *
     * @param id 数据字典管理主键
     * @return 数据字典管理详情
     */
    @GetMapping("getInfo/{id}")
    @Operation(operationId = "5",summary = "查询字典详细信息")
    public Result<SysDictVO> getInfo(@PathVariable Serializable id) {
        return Result.success("请求成功",sysDictService.getInfo(id));
    }

    /**
     * 分页查询数据字典管理。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    @Operation(operationId = "6",summary = "查询字典分页")
    public Result<RPage<SysDict>> page(Page<SysDict> page) {
        return Result.data(RPage.build(sysDictService.page(page)));
    }

    /**
     * 查询全部数据字典Map。
     */
    @GetMapping("/allDict")
    @Operation(operationId = "7", summary = "查询所有的数据字典子项 Map 结构")
    public Result<Map<String, List<SysDictItemOptionsVO>>> queryAllDictItemMap() {
        return Result.data(sysDictService.queryAllDictMap());
    }

    /**
     * 根据主键删除数据字典管理(批量删除)。
     */
    @DeleteMapping
    @Operation(operationId = "8", summary = "批量删除数据字典信息")
    public Result<Boolean> batchDelete(@Parameter(description = "删除对象") @RequestBody SysDictItemDeleteDTO sysDictItemDeleteDTO) {
        return Result.success("请求成功",sysDictService.removeDictByIds(sysDictItemDeleteDTO.getIds()));
    }
}
