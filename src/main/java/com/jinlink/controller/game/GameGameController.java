package com.jinlink.controller.game;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.bean.BeanUtil;
import com.jinlink.common.api.Result;
import com.jinlink.core.page.PageQuery;
import com.jinlink.core.page.RPage;
import com.jinlink.modules.game.entity.vo.GameGameVo;
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
import com.jinlink.modules.game.entity.GameGame;
import com.jinlink.modules.game.service.GameGameService;
import org.springframework.web.bind.annotation.RestController;
import java.io.Serializable;
import java.util.List;

/**
 * 游戏表 控制层。
 *
 * @author Summer
 * @since 1.0.0
 */
@RestController
@Tag(name = "游戏管理")
@RequiredArgsConstructor
@RequestMapping("/gameGame")
public class GameGameController {

    @NonNull
    private GameGameService gameGameService;

    /**
     * 添加游戏表。
     *
     * @param gameGame 游戏表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @Operation(operationId = "1",summary = "新增游戏")
    @SaCheckPermission("game:gameGame:save")
    public Result<Boolean> save(@Parameter(description = "游戏对象", required = true)@RequestBody GameGame gameGame) {
        return Result.success("请求成功",gameGameService.save(gameGame));
    }

    /**
     * 根据主键删除游戏表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @Operation(operationId = "2",summary = "删除游戏")
    @SaCheckPermission("game:gameGame:delete")
    public Result<Boolean> remove(@Parameter(description = "游戏ID", required = true)@PathVariable Serializable id) {
        return Result.success("请求成功",gameGameService.removeById(id));
    }

    /**
     * 根据主键更新游戏表。
     *
     * @param gameGame 游戏表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @Operation(operationId = "3",summary = "修改游戏")
    @SaCheckPermission("game:gameGame:update")
    public Result<Boolean> update(@Parameter(description = "游戏对象", required = true)@RequestBody GameGame gameGame) {
        return Result.success("请求成功",gameGameService.updateById(gameGame));
    }

    /**
     * 查询所有游戏表。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @Operation(operationId = "4",summary = "获取游戏列表")
    @SaCheckPermission("game:gameGame:list")
    public Result<List<GameGameVo>> list() {
        return Result.success("请求成功",BeanUtil.copyToList(gameGameService.list(), GameGameVo.class));
    }

    /**
     * 根据游戏表主键获取详细信息。
     *
     * @param id 游戏表主键
     * @return 游戏表详情
     */
    @GetMapping("getInfo/{id}")
    @Operation(operationId = "5",summary = "获取游戏详细")
    @SaCheckPermission("game:gameGame:info")
    public Result<GameGameVo> getInfo(@Parameter(description = "游戏ID", required = true)@PathVariable Serializable id) {
        return Result.success("请求成功",BeanUtil.copyProperties(gameGameService.getById(id), GameGameVo.class));
    }

    /**
     * 分页查询游戏表。
     *
     * @param pageQuery 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    @Operation(operationId = "6",summary = "分页查询游戏")
    @SaCheckPermission("game:gameGame:page")
    public Result<RPage<GameGameVo>> page(@Parameter(description = "分页对象", required = true) @Valid PageQuery pageQuery) {
        RPage<GameGameVo> gameGameVoRPage = gameGameService.listGameGameVoPage(pageQuery);
        return Result.data(gameGameVoRPage);
    }

}
