package com.jinlink.controller.game;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.bean.BeanUtil;
import com.jinlink.common.api.Result;
import com.jinlink.common.domain.Options;
import com.jinlink.core.page.PageQuery;
import com.jinlink.core.page.RPage;
import com.jinlink.modules.game.entity.GameMap;
import com.jinlink.modules.game.entity.dto.GameMapAddDTO;
import com.jinlink.modules.game.entity.dto.GameMapSearchDTO;
import com.jinlink.modules.game.entity.dto.GameMapUpdateDTO;
import com.jinlink.modules.game.entity.vo.GameMapVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.jinlink.modules.game.service.GameMapService;

import java.io.Serializable;
import java.util.List;

/**
 * 游戏地图表 控制层。
 *
 * @author Summer
 * @since 1.0.0
 */
@RestController
@Tag(name = "地图管理")
@RequiredArgsConstructor
@RequestMapping("/gameMap")
public class GameMapController {

    @NonNull
    private GameMapService gameMapService;

    /**
     * 添加游戏地图表。
     *
     * @param gameMapAddDTO 游戏地图表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @Operation(operationId = "1",summary = "新增游戏地图")
    @SaCheckPermission("game:gameMap:save")
    public Result<Boolean> save(@Parameter(description = "游戏地图对象", required = true)@RequestBody GameMapAddDTO gameMapAddDTO) {
        return Result.success("请求成功",gameMapService.saveGameMap(gameMapAddDTO));
    }

    /**
     * 根据主键删除游戏地图表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @Operation(operationId = "2",summary = "删除游戏地图")
    @SaCheckPermission("game:gameMap:delete")
    public Result<Boolean> remove(@Parameter(description = "游戏地图ID", required = true)@PathVariable Serializable id) {
        return Result.success("请求成功",gameMapService.removeById(id));
    }

    /**
     * 根据主键更新游戏地图表。
     *
     * @param gameMapUpdateDTO 游戏地图表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @Operation(operationId = "3",summary = "修改游戏地图")
    @SaCheckPermission("game:gameMap:update")
    public Result<Boolean> update(@Parameter(description = "游戏地图对象", required = true)@RequestBody GameMapUpdateDTO gameMapUpdateDTO) {
        return Result.success("请求成功",gameMapService.updateGameMap(gameMapUpdateDTO));
    }

    /**
     * 查询所有游戏地图表。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @Operation(operationId = "4",summary = "获取游戏地图列表")
    @CrossOrigin(origins = "*")
    public Result<List<GameMap>> list() {
        return Result.data(gameMapService.list());
    }

    /**
     * 根据游戏地图表主键获取详细信息。
     *
     * @param id 游戏地图表主键
     * @return 游戏地图表详情
     */
    @GetMapping("getInfo/{id}")
    @Operation(operationId = "5",summary = "获取游戏地图详细")
    @SaCheckPermission("game:gameMap:info")
    public Result<GameMapVo> getInfo(@PathVariable Serializable id) {
        return Result.success("请求成功",BeanUtil.copyProperties(gameMapService.getById(id),GameMapVo.class));
    }

    /**
     * 分页查询游戏地图表。
     *
     * @param pageQuery 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    @Operation(operationId = "6",summary = "分页查询游戏地图")
    @SaCheckPermission("game:gameMap:page")
    public Result<RPage<GameMapVo>> page(@Parameter(description = "分页对象", required = true) @Valid PageQuery pageQuery, @Parameter(description = "查询对象", required = true) GameMapSearchDTO gameMapSearchDTO) {
        RPage<GameMapVo> gameModeVoRPage = gameMapService.listGameMapVoPage(pageQuery,gameMapSearchDTO);
        return Result.data(gameModeVoRPage);
    }

    /**
     * 查询所有游戏地图名称。
     *
     * @return 所有数据
     */
    @GetMapping("listMapName")
    @Operation(operationId = "7",summary = "获取游戏地图名称")
    public Result<List<String>> listMapName() {
        return Result.data(gameMapService.listMapName());
    }

    /**
     * 查询全部地图配置项。
     */
    @GetMapping("allMapNames")
    @Operation(operationId = "8",summary = "查询全部模式名称")
    @SaCheckPermission("game:gameMap:allMapNames")
    public Result<List<Options<String>>> allMapNames() {
        return Result.success("请求成功",gameMapService.allMapNames());
    }

    /**
     * 查询全部地图模型配置项。
     */
    @GetMapping("allMapModes")
    @Operation(operationId = "9",summary = "查询全部地图模型配置项(有模型)")
    public Result<List<Options<String>>> allMapModes() {
        return Result.success("请求成功",gameMapService.allMapModes());
    }
}
