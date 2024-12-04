package com.jinlink.controller.game;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.jinlink.common.api.Result;
import com.jinlink.common.exception.JinLinkException;
import com.jinlink.core.page.PageQuery;
import com.jinlink.core.page.RPage;
import com.jinlink.modules.game.entity.dto.GameMapOrderAddDTO;
import com.jinlink.modules.game.entity.dto.GameMapOrderUpdateDTO;
import com.jinlink.modules.game.entity.vo.GameMapOrderVo;
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
import com.jinlink.modules.game.entity.GameMapOrder;
import com.jinlink.modules.game.service.GameMapOrderService;
import org.springframework.web.bind.annotation.RestController;
import java.io.Serializable;
import java.util.List;

/**
 * 地图订阅表 控制层。
 *
 * @author Summer
 * @since 1.0.0
 */
@RestController
@Tag(name = "地图订阅管理")
@RequiredArgsConstructor
@RequestMapping("/gameMapOrder")
public class GameMapOrderController {

    @NonNull
    private GameMapOrderService gameMapOrderService;

    /**
     * 添加地图订阅表。
     *
     * @param gameMapOrderAddDTO 地图订阅表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @Operation(operationId = "1",summary = "新增地图订阅")
    @SaCheckPermission("game:gameMapOrder:save")
    public Result<Boolean> save(@Parameter(description = "地图订阅对象", required = true)@RequestBody GameMapOrderAddDTO gameMapOrderAddDTO) {
        return Result.success("请求成功",gameMapOrderService.saveGameMapOrder(gameMapOrderAddDTO));
    }

    /**
     * 根据主键删除地图订阅表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @Operation(operationId = "2",summary = "删除地图订阅")
    @SaCheckPermission("game:gameMapOrder:delete")
    public Result<Boolean> remove(@Parameter(description = "订阅ID", required = true)@PathVariable Serializable id) {
        return Result.success("请求成功",gameMapOrderService.removeById(id));
    }

    /**
     * 根据主键更新地图订阅表。
     *
     * @param gameMapOrderUpdateDto 地图订阅表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @Operation(operationId = "3",summary = "修改地图订阅")
    @SaCheckPermission("game:gameMapOrder:update")
    public Result<Boolean> update(@Parameter(description = "地图订阅对象", required = true)@RequestBody GameMapOrderUpdateDTO gameMapOrderUpdateDto) {
        GameMapOrder gameMapOrder = BeanUtil.copyProperties(gameMapOrderUpdateDto, GameMapOrder.class);
        return Result.success("请求成功",gameMapOrderService.updateById(gameMapOrder));
    }

    /**
     * 查询所有地图订阅表。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @Operation(operationId = "4",summary = "查询全部地图订阅")
    @SaCheckPermission("game:gameMapOrder:list")
    public Result<List<GameMapOrderVo>> list() {
        List<GameMapOrderVo> gameMapOrderVos=gameMapOrderService.listGameMapOrderVo();
        return Result.data(gameMapOrderVos);
    }

    /**
     * 根据地图订阅表主键获取详细信息。
     *
     * @param id 地图订阅表主键
     * @return 地图订阅表详情
     */
    @GetMapping("getInfo/{id}")
    @Operation(operationId = "5",summary = "查询地图订阅详细")
    @SaCheckPermission("game:gameMapOrder:info")
    public Result<GameMapOrder> getInfo(@Parameter(description = "订阅ID", required = true)@PathVariable Serializable id) {
        return Result.success("请求成功",gameMapOrderService.getById(id));
    }

    /**
     * 分页查询地图订阅表。
     *
     * @param pageQuery 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    @Operation(operationId = "6",summary = "分页查询地图订阅")
    @SaCheckPermission("game:gameMapOrder:page")
    public Result<RPage<GameMapOrderVo>> page(@Parameter(description = "分页对象", required = true) @Valid PageQuery pageQuery) {
        RPage<GameMapOrderVo> gameMapOrderVoRPage =gameMapOrderService.listGameMapOrderVoPage(pageQuery);
        return Result.data(gameMapOrderVoRPage);
    }

    /**
     * 查询用户所有地图订阅表。
     *
     * @return 所有数据
     */
    @GetMapping("listByUser")
    @Operation(operationId = "7",summary = "查询全部地图订阅")
    @SaCheckPermission("game:gameMapOrder:list")
    public Result<List<GameMapOrderVo>> listByUser() {
        List<GameMapOrderVo> gameMapOrderVos=gameMapOrderService.listGameMapOrderVoByUser();
        return Result.data(gameMapOrderVos);
    }
}
