package com.jinlink.modules.game.service;

import com.jinlink.modules.game.entity.vo.GameOnLineStatisticsLineVo;
import com.jinlink.modules.game.entity.vo.GameOnLineStatisticsPieVo;
import com.mybatisflex.core.service.IService;
import com.jinlink.modules.game.entity.GameOnlineStatistics;

import java.util.List;

/**
 * 在线玩家统计表 服务层。
 *
 * @author Summer
 * @since 1.0.0
 */
public interface GameOnlineStatisticsService extends IService<GameOnlineStatistics> {

    /**
     * 查询首页Line图
     */
    GameOnLineStatisticsLineVo lineChart();

    /**
     * 查询首页Pie图
     */
    List<GameOnLineStatisticsPieVo> pieChart();
}
