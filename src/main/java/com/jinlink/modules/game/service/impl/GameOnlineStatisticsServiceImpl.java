package com.jinlink.modules.game.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.jinlink.modules.game.entity.GameOnlineStatistics;
import com.jinlink.modules.game.mapper.GameOnlineStatisticsMapper;
import com.jinlink.modules.game.service.GameOnlineStatisticsService;
import org.springframework.stereotype.Service;

/**
 * 在线玩家统计表 服务层实现。
 *
 * @author Summer
 * @since 1.0.0
 */
@Service
public class GameOnlineStatisticsServiceImpl extends ServiceImpl<GameOnlineStatisticsMapper, GameOnlineStatistics> implements GameOnlineStatisticsService {

}
