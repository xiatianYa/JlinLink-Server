package com.jinlink.controller.game;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.bean.BeanUtil;
import com.jinlink.common.api.Result;
import com.jinlink.core.page.PageQuery;
import com.jinlink.core.page.RPage;
import com.jinlink.modules.game.entity.dto.GameModelAddDTO;
import com.jinlink.modules.game.entity.dto.GameModelUpdateDTO;
import com.jinlink.modules.game.entity.vo.GameModelVo;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.jinlink.modules.game.entity.GameModel;
import com.jinlink.modules.game.service.GameModelService;

import java.io.Serializable;
import java.util.List;

/**
 * 游戏模型表 控制层。
 *
 * @author Summer
 * @since 1.0.0
 */
@RestController
@Tag(name = "模型管理")
@RequiredArgsConstructor
@RequestMapping("/gameModel")
public class GameModelController {

    @NonNull
    private GameModelService gameModelService;

    /**
     * 添加游戏模型表。
     *
     * @param gameModelAddDTO 游戏模型表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @Operation(operationId = "1",summary = "新增模型")
    @SaCheckPermission("game:gameModel:save")
    public Result<Boolean> save(@Parameter(description = "模型对象", required = true)@RequestBody GameModelAddDTO gameModelAddDTO) {
        return Result.success("请求成功",gameModelService.save(BeanUtil.copyProperties(gameModelAddDTO, GameModel.class)));
    }

    /**
     * 根据主键删除游戏模型表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @Operation(operationId = "2",summary = "删除模型")
    @SaCheckPermission("game:gameModel:delete")
    public Result<Boolean> remove(@Parameter(description = "模型ID", required = true)@PathVariable Serializable id) {
        return Result.success("请求成功",gameModelService.removeById(id));
    }

    /**
     * 根据主键更新游戏模型表。
     *
     * @param gameModelUpdateDTO 游戏模型表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @Operation(operationId = "3",summary = "修改模型")
    @SaCheckPermission("game:gameModel:update")
    public Result<Boolean> update(@Parameter(description = "模型对象", required = true)@RequestBody GameModelUpdateDTO gameModelUpdateDTO) {
        return Result.success("请求成功",gameModelService.updateById(BeanUtil.copyProperties(gameModelUpdateDTO, GameModel.class)));
    }

    /**
     * 查询所有游戏模型表。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @Operation(operationId = "4",summary = "获取模型列表")
    @SaCheckPermission("game:gameModel:list")
    public Result<List<GameModelVo>> list() {
        List<GameModel> gameModelList = gameModelService.list();
        List<GameModelVo> gameModelVos = BeanUtil.copyToList(gameModelList, GameModelVo.class);
        return Result.success("请求成功",gameModelVos);
    }

    /**
     * 根据游戏模型表主键获取详细信息。
     *
     * @param id 游戏模型表主键
     * @return 游戏模型表详情
     */
    @GetMapping("getInfo/{id}")
    @Operation(operationId = "5",summary = "获取模型详细")
    @SaCheckPermission("game:gameModel:info")
    public Result<GameModelVo> getInfo(@PathVariable Serializable id) {
        return Result.success("请求成功",BeanUtil.copyProperties(gameModelService.getById(id), GameModelVo.class));
    }

    /**
     * 分页查询游戏模型表。
     *
     * @param pageQuery 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    @Operation(operationId = "6",summary = "分页查询模型")
    @SaCheckPermission("game:gameModel:page")
    public Result<RPage<GameModelVo>> page(@Parameter(description = "分页对象", required = true) @Valid PageQuery pageQuery) {
        RPage<GameModelVo> gameModelVoRPage =gameModelService.listGameModelVoPage(pageQuery);
        return Result.success("请求成功",gameModelVoRPage);
    }

    /**
     * 查询游戏模型表根据类型。
     *
     * @return 所有数据
     */
    @GetMapping("listByType")
    @Operation(operationId = "7",summary = "获取模型列表根据类型")
    @SaCheckPermission("game:gameModel:list")
    public Result<List<GameModelVo>> listByType(@RequestParam String modelType) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("model_type",modelType);
        List<GameModel> gameModelList = gameModelService.list(queryWrapper);
        List<GameModelVo> gameModelVos = BeanUtil.copyToList(gameModelList, GameModelVo.class);
        return Result.success("请求成功",gameModelVos);
    }
}
