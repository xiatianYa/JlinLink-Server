package com.jinlink.controller.game;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.bean.BeanUtil;
import com.jinlink.common.api.Result;
import com.jinlink.core.page.PageQuery;
import com.jinlink.core.page.RPage;
import com.jinlink.modules.game.entity.vo.GameServerVo;
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
import com.jinlink.modules.game.entity.GameServer;
import com.jinlink.modules.game.service.GameServerService;
import org.springframework.web.bind.annotation.RestController;
import java.io.Serializable;
import java.util.List;

/**
 * 游戏服务器表 控制层。
 *
 * @author Summer
 * @since 1.0.0
 */
@RestController
@Tag(name = "服务器管理")
@RequiredArgsConstructor
@RequestMapping("/gameServer")
public class GameServerController {

    @NonNull
    private GameServerService gameServerService;

    /**
     * 添加游戏服务器表。
     *
     * @param gameServer 游戏服务器表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @Operation(operationId = "1",summary = "新增游戏服务器")
    @SaCheckPermission("game:gameServer:save")
    public Result<Boolean> save(@Parameter(description = "游戏服务器对象", required = true)@RequestBody GameServer gameServer) {
        return Result.success("请求成功", gameServerService.save(gameServer));
    }

    /**
     * 根据主键删除游戏服务器表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @Operation(operationId = "2",summary = "删除游戏服务器")
    @SaCheckPermission("game:gameServer:delete")
    public Result<Boolean> remove(@Parameter(description = "游戏服务器ID", required = true)@PathVariable Serializable id) {
        return Result.success("请求成功",gameServerService.removeById(id));
    }

    /**
     * 根据主键更新游戏服务器表。
     *
     * @param gameServer 游戏服务器表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @Operation(operationId = "3",summary = "修改游戏服务器")
    @SaCheckPermission("game:gameServer:update")
    public Result<Boolean> update(@Parameter(description = "游戏服务器对象", required = true)@RequestBody GameServer gameServer) {
        return Result.success("请求成功",gameServerService.updateById(gameServer));
    }

    /**
     * 查询所有游戏服务器表。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @Operation(operationId = "4",summary = "获取游戏服务器列表")
    @SaCheckPermission("game:gameServer:list")
    public Result<List<GameServerVo>> list() {
        return Result.success("请求成功",BeanUtil.copyToList(gameServerService.list(), GameServerVo.class));
    }

    /**
     * 根据游戏服务器表主键获取详细信息。
     *
     * @param id 游戏服务器表主键
     * @return 游戏服务器表详情
     */
    @GetMapping("getInfo/{id}")
    @Operation(operationId = "5",summary = "获取游戏服务器详细")
    @SaCheckPermission("game:gameServer:info")
    public Result<GameServerVo> getInfo(@Parameter(description = "游戏服务器ID", required = true)@PathVariable Serializable id) {
        return Result.success("请求成功",BeanUtil.copyProperties(gameServerService.getById(id), GameServerVo.class));
    }

    /**
     * 分页查询游戏服务器表。
     *
     * @param pageQuery 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    @Operation(operationId = "6",summary = "获取游戏服务器详细")
    @SaCheckPermission("game:gameServer:page")
    public Result<RPage<GameServerVo>> page(@Parameter(description = "分页对象", required = true) @Valid PageQuery pageQuery) {
        RPage<GameServerVo> gameServerVoRPage = gameServerService.listGameServerVoPage(pageQuery);
        return Result.data(gameServerVoRPage);
    }

}
