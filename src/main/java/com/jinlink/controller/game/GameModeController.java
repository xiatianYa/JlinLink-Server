package com.jinlink.controller.game;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.bean.BeanUtil;
import com.jinlink.common.api.Result;
import com.jinlink.common.domain.Options;
import com.jinlink.core.page.PageQuery;
import com.jinlink.core.page.RPage;
import com.jinlink.modules.game.entity.vo.GameModeVo;
import com.mybatisflex.core.paginate.Page;
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
import org.springframework.beans.factory.annotation.Autowired;
import com.jinlink.modules.game.entity.GameMode;
import com.jinlink.modules.game.service.GameModeService;
import org.springframework.web.bind.annotation.RestController;
import java.io.Serializable;
import java.util.List;

/**
 * 游戏模式表 控制层。
 *
 * @author Summer
 * @since 1.0.0
 */
@RestController
@Tag(name = "模式管理")
@RequiredArgsConstructor
@RequestMapping("/gameMode")
public class GameModeController {

    @NonNull
    private GameModeService gameModeService;

    /**
     * 添加游戏模式表。
     *
     * @param gameMode 游戏模式表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @Operation(operationId = "1",summary = "新增游戏模式")
    @SaCheckPermission("game:gameMode:save")
    public Result<Boolean> save(@Parameter(description = "游戏模式对象", required = true)@RequestBody GameMode gameMode) {
        return Result.success("请求成功",gameModeService.save(gameMode));
    }

    /**
     * 根据主键删除游戏模式表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @Operation(operationId = "2",summary = "删除游戏模式")
    @SaCheckPermission("game:gameMode:delete")
    public Result<Boolean> remove(@Parameter(description = "游戏模式ID", required = true)@PathVariable Serializable id) {
        return Result.success("请求成功",gameModeService.removeById(id));
    }

    /**
     * 根据主键更新游戏模式表。
     *
     * @param gameMode 游戏模式表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @Operation(operationId = "3",summary = "修改游戏模式")
    @SaCheckPermission("game:gameMode:update")
    public Result<Boolean> update(@Parameter(description = "游戏模式对象", required = true)@RequestBody GameMode gameMode) {
        return Result.success("请求成功",gameModeService.updateById(gameMode));
    }

    /**
     * 查询所有游戏模式表。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @Operation(operationId = "4",summary = "获取游戏模式列表")
    @SaCheckPermission("game:gameMode:list")
    public Result<List<GameModeVo>> list() {
        return Result.success("请求成功", BeanUtil.copyToList(gameModeService.list(), GameModeVo.class));
    }

    /**
     * 根据游戏模式表主键获取详细信息。
     *
     * @param id 游戏模式表主键
     * @return 游戏模式表详情
     */
    @GetMapping("getInfo/{id}")
    @Operation(operationId = "5",summary = "获取游戏模式详细")
    @SaCheckPermission("game:gameMode:info")
    public Result<GameModeVo> getInfo(@Parameter(description = "游戏模式ID", required = true)@PathVariable Serializable id) {
        return Result.success("请求成功",BeanUtil.copyProperties(gameModeService.getById(id), GameModeVo.class));
    }

    /**
     * 分页查询游戏模式表。
     *
     * @param pageQuery 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    @Operation(operationId = "6",summary = "分页查询游戏模式")
    @SaCheckPermission("game:gameMode:page")
    public Result<RPage<GameModeVo>> page(@Parameter(description = "分页对象", required = true) @Valid PageQuery pageQuery) {
        RPage<GameModeVo> gameModeVoRPage = gameModeService.listGameModeVoPage(pageQuery);
        return Result.data(gameModeVoRPage);
    }

    /**
     * 查询全部模式名称。
     */
    @GetMapping("allModeNames")
    @Operation(operationId = "7",summary = "查询全部模式名称")
    @SaCheckPermission("game:gameMode:allModeNames")
    public Result<List<Options<String>>> allModeNames() {
        return Result.success("请求成功",gameModeService.allModeNames());
    }
}
