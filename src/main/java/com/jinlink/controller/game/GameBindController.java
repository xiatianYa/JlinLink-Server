package com.jinlink.controller.game;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.bean.BeanUtil;
import com.jinlink.common.api.Result;
import com.jinlink.core.page.PageQuery;
import com.jinlink.core.page.RPage;
import com.jinlink.modules.game.entity.dto.GameBindAddDTO;
import com.jinlink.modules.game.entity.dto.GameBindUpdateDTO;
import com.jinlink.modules.game.entity.vo.GameBindVo;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.jinlink.modules.game.entity.GameBind;
import com.jinlink.modules.game.service.GameBindService;

import java.io.Serializable;
import java.util.List;

/**
 * 游戏绑键表 控制层。
 *
 * @author Summer
 * @since 1.0.0
 */
@RestController
@Tag(name = "绑键管理")
@RequiredArgsConstructor
@RequestMapping("/gameBind")
public class GameBindController {

    @NonNull
    private GameBindService gameBindService;

    /**
     * 添加游戏绑键表。
     *
     * @param gameBindAddDTO 游戏绑键表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @Operation(operationId = "1",summary = "新增绑键")
    @SaCheckPermission("game:gameBind:save")
    public Result<Boolean> save(@Parameter(description = "绑键对象", required = true)@RequestBody GameBindAddDTO gameBindAddDTO) {
        return Result.success("请求成功",gameBindService.save(BeanUtil.copyProperties(gameBindAddDTO, GameBind.class)));
    }

    /**
     * 根据主键删除游戏绑键表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @Operation(operationId = "2",summary = "删除绑键")
    @SaCheckPermission("game:gameBind:delete")
    public Result<Boolean> remove(@Parameter(description = "绑键ID", required = true)@PathVariable Serializable id) {
        return Result.success("请求成功",gameBindService.removeById(id));
    }

    /**
     * 根据主键更新游戏绑键表。
     *
     * @param gameBindUpdateDTO 游戏绑键表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @Operation(operationId = "3",summary = "修改绑键")
    @SaCheckPermission("game:gameBind:update")
    public Result<Boolean> update(@Parameter(description = "绑键对象", required = true)@RequestBody GameBindUpdateDTO gameBindUpdateDTO) {
        return Result.success("请求成功",gameBindService.updateGameBind(gameBindUpdateDTO));
    }

    /**
     * 查询所有游戏绑键表。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @Operation(operationId = "4",summary = "获取绑键列表")
    @SaCheckPermission("game:gameBind:list")
    public Result<List<GameBind>> list() {
        return Result.success("请求成功",gameBindService.list());
    }

    /**
     * 根据游戏绑键表主键获取详细信息。
     *
     * @param id 游戏绑键表主键
     * @return 游戏绑键表详情
     */
    @GetMapping("getInfo/{id}")
    @Operation(operationId = "5",summary = "获取绑键详细")
    @SaCheckPermission("game:gameBind:info")
    public Result<GameBindVo> getInfo(@PathVariable Serializable id) {
        return Result.success("请求成功",BeanUtil.copyProperties(gameBindService.getById(id), GameBindVo.class));
    }

    /**
     * 分页查询游戏绑键表。
     *
     * @param pageQuery 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    @Operation(operationId = "6",summary = "分页查询绑键")
    @SaCheckPermission("game:gameBind:page")
    public Result<RPage<GameBindVo>> page(@Parameter(description = "分页对象", required = true) @Valid PageQuery pageQuery) {
        return Result.success("请求成功",gameBindService.listGameBindVopage(pageQuery));
    }

    /**
     * 根据游戏绑键表社区Id获取详细信息。
     *
     * @param communityId 游戏社区Id
     * @return 游戏绑键表详情
     */
    @GetMapping("getInfoByCommunityId")
    @Operation(operationId = "7",summary = "获取绑键详细根据社区Id")
    @SaCheckPermission("game:gameBind:info")
    public Result<GameBindVo> getInfoByCommunityId(@RequestParam(name = "communityId") Long communityId) {
        return Result.success("请求成功",gameBindService.getInfoByCommunityId(communityId));
    }

}
