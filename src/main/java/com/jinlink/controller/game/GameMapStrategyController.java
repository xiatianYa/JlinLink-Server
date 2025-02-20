package com.jinlink.controller.game;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.hutool.core.bean.BeanUtil;
import com.jinlink.common.api.Result;
import com.jinlink.core.page.PageQuery;
import com.jinlink.core.page.RPage;
import com.jinlink.modules.game.entity.dto.GameMapStrategyAddDTO;
import com.jinlink.modules.game.entity.vo.GameMapStrategyVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.jinlink.modules.game.entity.GameMapStrategy;
import com.jinlink.modules.game.service.GameMapStrategyService;

import java.io.Serializable;
import java.util.List;

/**
 * 地图攻略表 控制层。
 *
 * @author Summer
 * @since 1.0.0
 */
@RestController
@Tag(name = "地图攻略管理")
@RequiredArgsConstructor
@RequestMapping("/gameMapStrategy")
public class GameMapStrategyController {

    @Resource
    private GameMapStrategyService gameMapStrategyService;

    /**
     * 添加地图攻略表。
     *
     * @param gameMapStrategyAddDTO 地图攻略表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @Operation(operationId = "1",summary = "新增地图攻略")
    @SaCheckPermission("game:gameMapStrategy:save")
    public Result<Boolean> save(@Parameter(description = "游戏地图攻略对象", required = true)@RequestBody GameMapStrategyAddDTO gameMapStrategyAddDTO) {
        return Result.success("请求成功",gameMapStrategyService.saveMapStrategy(gameMapStrategyAddDTO));
    }

    /**
     * 根据主键删除地图攻略表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @Operation(operationId = "2",summary = "删除地图攻略")
    @SaCheckPermission("game:gameMapStrategy:delete")
    public Result<Boolean> remove(@PathVariable Serializable id) {
        return Result.success("请求成功",gameMapStrategyService.removeMapStrategyById(id));
    }

    /**
     * 根据主键更新地图攻略表。
     *
     * @param gameMapStrategy 地图攻略表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @Operation(operationId = "3",summary = "修改游戏地图攻略")
    @SaCheckPermission("game:gameMapStrategy:update")
    public Result<Boolean> update(@Parameter(description = "游戏地图攻略", required = true)@RequestBody GameMapStrategy gameMapStrategy) {
        return Result.success("请求成功",gameMapStrategyService.updateMapStrategyById(gameMapStrategy));
    }

    /**
     * 查询所有地图攻略表。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @Operation(operationId = "4",summary = "获取游戏地图攻略列表")
    @SaCheckPermission("game:gameMapStrategy:list")
    public Result<List<GameMapStrategyVo>> list() {
        List<GameMapStrategyVo> gameMapStrategyVos = BeanUtil.copyToList(gameMapStrategyService.list(), GameMapStrategyVo.class);
        return Result.success("请求成功",gameMapStrategyVos);
    }

    /**
     * 根据地图攻略表主键获取详细信息。
     *
     * @param id 地图攻略表主键
     * @return 地图攻略表详情
     */
    @GetMapping("getInfo/{id}")
    @Operation(operationId = "5",summary = "获取游戏地图攻略详细")
    @SaCheckPermission("game:gameMapStrategy:info")
    public Result<GameMapStrategyVo> getInfo(@PathVariable Serializable id) {
        return Result.success("请求成功",gameMapStrategyService.getMapStrategyInfoById(id));
    }

    /**
     * 分页查询地图攻略表。
     *
     * @param pageQuery 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    @Operation(operationId = "6",summary = "分页查询游戏地图攻略")
    @SaCheckPermission("game:gameMapStrategy:page")
    public Result<RPage<GameMapStrategyVo>> page(@Parameter(description = "分页对象", required = true) @Valid PageQuery pageQuery) {
        return Result.success("请求成功",gameMapStrategyService.listGameMapStrategyVoPage(pageQuery));
    }

    /**
     * 发布游戏地图攻略。
     *
     * @param gameMapStrategy 地图攻略表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("publish")
    @Operation(operationId = "7",summary = "发布游戏地图攻略")
    @SaCheckPermission("game:gameMapStrategy:update")
    public Result pushMapStrategyById(@Parameter(description = "游戏地图攻略", required = true)@RequestBody GameMapStrategy gameMapStrategy) {
        return Result.success("请求成功",gameMapStrategyService.pushMapStrategyById(gameMapStrategy));
    }

    /**
     * 游戏攻略审核
     */
    @PutMapping("examine")
    @Operation(operationId = "8",summary = "游戏攻略审核")
    @SaCheckPermission("game:gameMapStrategy:examine")
    public Result<Boolean> examineMapStrategyById(@RequestParam(name = "id") Long id,@RequestParam(name="type") String type) {
        return Result.success("请求成功",gameMapStrategyService.examineMapStrategyById(id,type));
    }
}
