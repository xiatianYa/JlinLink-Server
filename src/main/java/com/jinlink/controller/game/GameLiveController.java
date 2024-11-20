package com.jinlink.controller.game;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSONObject;
import com.jinlink.common.api.Result;
import com.jinlink.common.exception.JinLinkException;
import com.jinlink.common.util.BiliUtils;
import com.jinlink.core.page.PageQuery;
import com.jinlink.core.page.RPage;
import com.jinlink.modules.game.entity.dto.GameLiveSearchDTO;
import com.jinlink.modules.game.entity.dto.GameMapSearchDTO;
import com.jinlink.modules.game.entity.vo.GameLiveVo;
import com.mybatisflex.core.query.QueryWrapper;
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
import com.jinlink.modules.game.entity.GameLive;
import com.jinlink.modules.game.service.GameLiveService;
import org.springframework.web.bind.annotation.RestController;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 游戏直播表 控制层。
 *
 * @author Summer
 * @since 1.0.0
 */
@RestController
@Tag(name = "直播管理")
@RequiredArgsConstructor
@RequestMapping("/gameLive")
public class GameLiveController {

    @NonNull
    private GameLiveService gameLiveService;

    /**
     * 添加游戏直播表。
     *
     * @param gameLive 游戏直播表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @Operation(operationId = "1",summary = "新增游戏直播")
    @SaCheckPermission("game:gameLive:save")
    public Result<Boolean> save(@Parameter(description = "游戏直播对象", required = true)@RequestBody GameLive gameLive) {
        Long loginIdAsLong = StpUtil.getLoginIdAsLong();
        if (ObjectUtil.isNotNull(gameLiveService.getOne(new QueryWrapper().eq("createBy", loginIdAsLong)))){
            throw new JinLinkException("你已经入驻过了,不能再次入驻!");
        }
        return Result.success("请求成功",gameLiveService.save(gameLive));
    }

    /**
     * 根据主键删除游戏直播表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @Operation(operationId = "2",summary = "删除游戏直播")
    @SaCheckPermission("game:gameLive:delete")
    public Result<Boolean> remove(@PathVariable Serializable id) {
        return Result.success("请求成功",gameLiveService.removeById(id));
    }

    /**
     * 根据主键更新游戏直播表。
     *
     * @param gameLive 游戏直播表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @Operation(operationId = "3",summary = "修改游戏地图")
    @SaCheckPermission("game:gameLive:update")
    public Result<Boolean> update(@Parameter(description = "游戏直播对象", required = true)@RequestBody GameLive gameLive) {
        return Result.success("请求成功",gameLiveService.updateById(gameLive));
    }

    /**
     * 查询所有游戏直播表。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @Operation(operationId = "4",summary = "获取游戏直播列表")
    @SaCheckPermission("game:gameLive:list")
    public Result<List<GameLiveVo>> list() {
        return Result.data(BeanUtil.copyToList(gameLiveService.list(), GameLiveVo.class));
    }

    /**
     * 根据游戏直播表主键获取详细信息。
     *
     * @param id 游戏直播表主键
     * @return 游戏直播表详情
     */
    @GetMapping("getInfo/{id}")
        @Operation(operationId = "5",summary = "获取游戏直播详细")
    @SaCheckPermission("game:gameLive:info")
    public Result<GameLiveVo> getInfo(@PathVariable Serializable id) {
        return Result.success("请求成功",BeanUtil.copyProperties(gameLiveService.getById(id), GameLiveVo.class));
    }

    /**
     * 分页查询游戏直播表。
     *
     * @param pageQuery 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    @Operation(operationId = "6",summary = "分页查询游戏直播")
    @SaCheckPermission("game:gameLive:page")
    public Result<RPage<GameLiveVo>> page(@Parameter(description = "分页对象", required = true) @Valid PageQuery pageQuery,@Parameter(description = "查询对象", required = true) GameLiveSearchDTO gameLiveSearchDTO) {
        RPage<GameLiveVo> gameLiveVoRPage = gameLiveService.listGameLiveVoPage(pageQuery,gameLiveSearchDTO);
        return Result.data(gameLiveVoRPage);
    }

    /**
     * 查询所有入驻主播。
     */
    @GetMapping("listAll")
    @Operation(operationId = "6",summary = "查询所有入驻主播")
    @SaCheckPermission("game:gameLive:list")
    public Result<List<GameLiveVo>> listAll() {
        List<GameLiveVo> gameLiveVos = new ArrayList<>();
        List<GameLive> gameLives = gameLiveService.list();
        for (GameLive gameLive : gameLives) {
            GameLiveVo gameLiveVo = BeanUtil.copyProperties(gameLive, GameLiveVo.class);
            JSONObject jsonObject = JSONObject
                    .parseObject(BiliUtils.getBiliLiveApi(gameLive.getUid()))
                    .getJSONObject("data")
                    .getJSONObject("by_room_ids")
                    .getJSONObject(gameLive.getUid());
            GameLiveVo.BiliVo biliVo = JSONObject.parseObject(jsonObject.toJSONString(), GameLiveVo.BiliVo.class);
            gameLiveVo.setBiliVo(biliVo);
            gameLiveVos.add(gameLiveVo);
        }
        gameLiveVos.sort(new Comparator<GameLiveVo>() {
            @Override
            public int compare(GameLiveVo o1, GameLiveVo o2) {
                // 假设你想要升序排序
                return o2.getBiliVo().getOnline().compareTo(o1.getBiliVo().getOnline());
            }
        });
        return Result.data(gameLiveVos);
    }
}
