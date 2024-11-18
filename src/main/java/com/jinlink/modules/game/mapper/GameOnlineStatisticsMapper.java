package com.jinlink.modules.game.mapper;

import com.jinlink.modules.game.entity.dto.GameOnLineStatisticsDTO;
import com.mybatisflex.core.BaseMapper;
import com.jinlink.modules.game.entity.GameOnlineStatistics;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 在线玩家统计表 映射层。
 *
 * @author Summer
 * @since 1.0.0
 */
@Mapper
public interface GameOnlineStatisticsMapper extends BaseMapper<GameOnlineStatistics> {

    List<GameOnLineStatisticsDTO> lineChart();
}
