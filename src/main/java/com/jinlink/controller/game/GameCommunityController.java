package com.jinlink.controller.game;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.bean.BeanUtil;
import com.jinlink.common.api.Result;
import com.jinlink.common.domain.Options;
import com.jinlink.core.page.PageQuery;
import com.jinlink.core.page.RPage;
import com.jinlink.modules.game.entity.vo.GameCommunityVo;
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
import com.jinlink.modules.game.entity.GameCommunity;
import com.jinlink.modules.game.service.GameCommunityService;
import org.springframework.web.bind.annotation.RestController;
import java.io.Serializable;
import java.util.List;

/**
 * 游戏社区表 控制层。
 *
 * @author Summer
 * @since 1.0.0
 */
@RestController
@Tag(name = "社区管理")
@RequiredArgsConstructor
@RequestMapping("/gameCommunity")
public class GameCommunityController {

    @NonNull
    private GameCommunityService gameCommunityService;

    /**
     * 添加游戏社区表。
     *
     * @param gameCommunity 游戏社区表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @Operation(operationId = "1",summary = "新增社区")
    @SaCheckPermission("game:gameCommunity:save")
    public Result<Boolean> save(@Parameter(description = "社区对象", required = true)@RequestBody GameCommunity gameCommunity) {
        return Result.success("请求成功",gameCommunityService.save(gameCommunity));
    }

    /**
     * 根据主键删除游戏社区表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @Operation(operationId = "2",summary = "删除社区")
    @SaCheckPermission("game:gameCommunity:delete")
    public Result<Boolean> remove(@Parameter(description = "社区ID", required = true)@PathVariable Serializable id) {
        return Result.success("请求成功",gameCommunityService.removeById(id));
    }

    /**
     * 根据主键更新游戏社区表。
     *
     * @param gameCommunity 游戏社区表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @Operation(operationId = "3",summary = "修改社区")
    @SaCheckPermission("game:gameCommunity:update")
    public Result<Boolean> update(@Parameter(description = "社区对象", required = true)@RequestBody GameCommunity gameCommunity) {
        return Result.success("请求成功",gameCommunityService.updateById(gameCommunity));
    }

    /**
     * 查询所有游戏社区表。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @Operation(operationId = "4",summary = "获取社区列表")
    @SaCheckPermission("game:gameCommunity:list")
    public Result<List<GameCommunityVo>> list() {
        return Result.success("请求成功", BeanUtil.copyToList(gameCommunityService.list(),GameCommunityVo.class));
    }

    /**
     * 根据游戏社区表主键获取详细信息。
     *
     * @param id 游戏社区表主键
     * @return 游戏社区表详情
     */
    @GetMapping("getInfo/{id}")
    @Operation(operationId = "5",summary = "获取社区详细")
    @SaCheckPermission("game:gameCommunity:info")
    public Result<GameCommunityVo> getInfo(@Parameter(description = "社区ID", required = true)@PathVariable Serializable id) {
        return Result.success("请求成功",BeanUtil.copyProperties(gameCommunityService.getById(id),GameCommunityVo.class));
    }

    /**
     * 分页查询游戏社区表。
     *
     * @param pageQuery 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    @Operation(operationId = "6",summary = "分页查询社区")
    @SaCheckPermission("game:gameCommunity:page")
    public Result<RPage<GameCommunityVo>> page(@Parameter(description = "分页对象", required = true) @Valid PageQuery pageQuery) {
        RPage<GameCommunityVo> gameCommunityVoRPage = gameCommunityService.listGameCommunityVoPage(pageQuery);
        return Result.data(gameCommunityVoRPage);
    }

    /**
     * 查询全部社区名称。
     */
    @GetMapping("allCommunityNames")
    @Operation(operationId = "7",summary = "查询全部社区名称")
    @SaCheckPermission("game:gameCommunity:allCommunityNames")
    public Result<List<Options<String>>> allCommunityNames() {
        return Result.success("请求成功",gameCommunityService.getAllCommunityNames());
    }
}
