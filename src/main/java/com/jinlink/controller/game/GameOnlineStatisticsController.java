package com.jinlink.controller.game;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.jinlink.common.api.Result;
import com.jinlink.modules.game.entity.vo.GameOnLineStatisticsLineVo;
import com.jinlink.modules.game.entity.vo.GameOnLineStatisticsPieVo;
import com.jinlink.modules.game.service.GameOnlineStatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 各社区在线用户
 *
 * @author Summer
 * @since 1.0.0
 */
@RestController
@Tag(name = "在线用户数据管理")
@RequiredArgsConstructor
@RequestMapping("/gameOnlineStatistics")
public class GameOnlineStatisticsController {
    @NonNull
    private GameOnlineStatisticsService gameOnlineStatisticsService;

    /**
     * 查询首页Line图
     *
     * @return 所有数据
     */
    @GetMapping("lineChart")
    @Operation(operationId = "1",summary = "获取在线用户统计图表")
    @SaCheckLogin
    public Result<GameOnLineStatisticsLineVo> lineChart() {
        return Result.data(gameOnlineStatisticsService.lineChart());
    }

    /**
     * 查询首页Pie图
     *
     * @return 所有数据
     */
    @GetMapping("pieChart")
    @Operation(operationId = "2",summary = "获取在线用户统计饼图")
    @SaCheckLogin
    public Result<List<GameOnLineStatisticsPieVo>> pieChart() {
        return Result.data(gameOnlineStatisticsService.pieChart());
    }
}
