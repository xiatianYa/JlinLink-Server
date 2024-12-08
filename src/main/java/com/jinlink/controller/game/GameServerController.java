package com.jinlink.controller.game;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.bean.BeanUtil;
import com.ibasco.agql.protocols.valve.source.query.players.SourcePlayer;
import com.jinlink.common.api.Result;
import com.jinlink.common.util.mcping.MinecraftPingReply;
import com.jinlink.core.page.PageQuery;
import com.jinlink.core.page.RPage;
import com.jinlink.modules.game.entity.dto.GameServerAddDTO;
import com.jinlink.modules.game.entity.dto.GameServerSearchDTO;
import com.jinlink.modules.game.entity.dto.GameServerUpdateDTO;
import com.jinlink.modules.game.entity.vo.GameServerVo;
import com.jinlink.modules.game.entity.vo.SourceServerVo;
import com.jinlink.modules.game.entity.vo.SteamServerVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.jinlink.modules.game.entity.GameServer;
import com.jinlink.modules.game.service.GameServerService;

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
     * @param gameServerAddDTO 游戏服务器表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @Operation(operationId = "1",summary = "新增游戏服务器")
    @SaCheckPermission("game:gameServer:save")
    public Result<Boolean> save(@Parameter(description = "游戏服务器对象", required = true)@RequestBody GameServerAddDTO gameServerAddDTO) {
        GameServer gameServer = BeanUtil.copyProperties(gameServerAddDTO, GameServer.class);
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
     * @param gameServerUpdateDTO 游戏服务器表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @Operation(operationId = "3",summary = "修改游戏服务器")
    @SaCheckPermission("game:gameServer:update")
    public Result<Boolean> update(@Parameter(description = "游戏服务器对象", required = true)@RequestBody GameServerUpdateDTO gameServerUpdateDTO) {
        GameServer gameServer = BeanUtil.copyProperties(gameServerUpdateDTO, GameServer.class);
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
        return Result.data(BeanUtil.copyToList(gameServerService.list(), GameServerVo.class));
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
    @Operation(operationId = "6",summary = "获取游戏服务器分页")
    @SaCheckPermission("game:gameServer:page")
    public Result<RPage<GameServerVo>> page(@Parameter(description = "分页对象", required = true) @Valid PageQuery pageQuery, @Parameter(description = "查询对象", required = true) GameServerSearchDTO gameServerSearchDTO) {
        RPage<GameServerVo> gameServerVoRPage = gameServerService.listGameServerVoPage(pageQuery,gameServerSearchDTO);
        return Result.data(gameServerVoRPage);
    }

    /**
     * 查询所有服务器数据
     *
     * @param gameServerSearchDTO 分页对象
     * @return 分页对象
     */
    @GetMapping("getServerAll")
    @Operation(operationId = "6",summary = "查询所有服务器数据")
    public Result<List<SourceServerVo>> getServerAll(@Parameter(description = "查询对象", required = true) GameServerSearchDTO gameServerSearchDTO) {
        List<SourceServerVo> gameServiceServerAll = gameServerService.getServerAll(gameServerSearchDTO);
        return Result.data(gameServiceServerAll);
    }

    /**
     * 查询所有服务器数据分页
     *
     * @param gameServerSearchDTO 分页对象
     * @return 分页对象
     */
    @GetMapping("getServerAllPage")
    @Operation(operationId = "7",summary = "查询所有服务器数据(分页)")
    @SaCheckPermission("game:gameServer:getServerAll")
    public Result<RPage<SteamServerVo>> getServerAllPage(@Parameter(description = "分页对象", required = true) @Valid PageQuery pageQuery,@Parameter(description = "查询对象", required = true) GameServerSearchDTO gameServerSearchDTO) {
        RPage<SteamServerVo> gameServiceServerAll = gameServerService.getServerAllPage(pageQuery,gameServerSearchDTO);
        return Result.data(gameServiceServerAll);
    }

    /**
     * 查询所有服务器数据分页
     */
    @GetMapping("getServerOnlineUser")
    @Operation(operationId = "8",summary = "查询服务器在线玩家列表")
    @SaCheckPermission("game:gameServer:getServerAll")
    public Result<List<SourcePlayer>> fetchGetServerOnlineUser(@RequestParam String addr) {
        List<SourcePlayer> sourcePlayers = gameServerService.fetchGetServerOnlineUser(addr);
        return Result.data(sourcePlayers);
    }

    /**
     * 查询所有服务器数据(Json)
     */
    @GetMapping("getServerAllJson")
    @Operation(operationId = "9",summary = "查询服务器(Json)")
    public String getServerAllJson(){
        return gameServerService.getServerAllJson();
    }

    /**
     * 查询我的世界服务器(分页)
     *
     * @param pageQuery 分页对象
     * @return 分页对象
     */
    @GetMapping("getMinecraftPage")
    @Operation(operationId = "10",summary = "查询我的世界服务器(分页)")
    @SaCheckPermission("game:gameServer:getServerAll")
    public Result<RPage<MinecraftPingReply>> getMinecraftPage(@Parameter(description = "分页对象", required = true) @Valid PageQuery pageQuery,@Parameter(description = "查询对象", required = true) GameServerSearchDTO gameServerSearchDTO) {
        RPage<MinecraftPingReply> gameServiceServerAll = gameServerService.getMinecraftPage(pageQuery,gameServerSearchDTO);
        return Result.data(gameServiceServerAll);
    }
}
