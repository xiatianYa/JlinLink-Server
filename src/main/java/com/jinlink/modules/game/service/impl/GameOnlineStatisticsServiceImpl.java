package com.jinlink.modules.game.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSONObject;
import com.jinlink.core.config.redis.service.RedisService;
import com.jinlink.modules.game.entity.GameCommunity;
import com.jinlink.modules.game.entity.dto.GameOnLineStatisticsDTO;
import com.jinlink.modules.game.entity.vo.GameOnLineStatisticsLineVo;
import com.jinlink.modules.game.entity.vo.GameOnLineStatisticsPieVo;
import com.jinlink.modules.game.entity.vo.GameServerVo;
import com.jinlink.modules.game.entity.vo.SteamServerVo;
import com.jinlink.modules.game.service.GameCommunityService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.jinlink.modules.game.entity.GameOnlineStatistics;
import com.jinlink.modules.game.mapper.GameOnlineStatisticsMapper;
import com.jinlink.modules.game.service.GameOnlineStatisticsService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 在线玩家统计表 服务层实现。
 *
 * @author Summer
 * @since 1.0.0
 */
@Service
public class GameOnlineStatisticsServiceImpl extends ServiceImpl<GameOnlineStatisticsMapper, GameOnlineStatistics> implements GameOnlineStatisticsService {
    @Resource
    private GameOnlineStatisticsMapper gameOnlineStatisticsMapper;
    @Resource
    private GameCommunityService gameCommunityService;
    @Resource
    private RedisService redisService;

    /**
     * 查询首页Line图
     */
    @Override
    public GameOnLineStatisticsLineVo lineChart() {
        GameOnLineStatisticsLineVo gameOnLineStatisticsLineVo = GameOnLineStatisticsLineVo.builder()
                .communityStatistics(new ArrayList<>())
                .communityNames(new ArrayList<>())
                .build();
        //查询统计数据
        List<GameOnLineStatisticsDTO> gameOnLineStatisticsDTOList = gameOnlineStatisticsMapper.lineChart();
        //查询所有社区
        List<GameCommunity> gameCommunityList = gameCommunityService.list();
        for (GameCommunity gameCommunity : gameCommunityList) {
            //添加社区名称
            gameOnLineStatisticsLineVo.getCommunityNames().add(gameCommunity.getCommunityName());
            //查询社区的在线人数统计
            List<Integer> communityPlays = gameOnLineStatisticsDTOList.stream()
                    .filter(item -> item.getCommunityId().equals(gameCommunity.getId()))
                    .map(GameOnLineStatisticsDTO::getCommunityPlay).toList();
            //设置统计时间
            if (ObjectUtil.isNull(gameOnLineStatisticsLineVo.getTimeMinutes()) && ObjectUtil.isEmpty(gameOnLineStatisticsLineVo.getTimeMinutes())) {
                List<String> timeMinutes = gameOnLineStatisticsDTOList.stream()
                        .filter(item -> item.getCommunityId().equals(gameCommunity.getId()))
                        .map(GameOnLineStatisticsDTO::getTimeMinute).toList();
                gameOnLineStatisticsLineVo.setTimeMinutes(timeMinutes);
            }
            gameOnLineStatisticsLineVo.getCommunityStatistics().add(communityPlays);
        }
        return gameOnLineStatisticsLineVo;
    }

    @Override
    public List<GameOnLineStatisticsPieVo> pieChart() {
        List<GameOnLineStatisticsPieVo> gameOnLineStatisticsPieVos = new ArrayList<>();
        // 从 Redis 缓存中获取服务器 JSON 数据列表
        List<JSONObject> serverJsonList = redisService.getCacheList("server_json");
        // 遍历服务器 JSON 数据列表，解析并筛选匹配的服务器
        for (JSONObject serverJson : serverJsonList) {
            // 直接从 JSONObject 解析为 SteamServerVo 对象
            SteamServerVo steamServerVo = serverJson.toJavaObject(SteamServerVo.class);
            GameOnLineStatisticsPieVo build = GameOnLineStatisticsPieVo.builder()
                    .name(steamServerVo.getGameCommunityVo().getCommunityName())
                    .value(steamServerVo.getGameServerVoList().stream().mapToLong(GameServerVo.ServerVo::getPlayers).sum())
                    .build();
            gameOnLineStatisticsPieVos.add(build);
        }
        return gameOnLineStatisticsPieVos;
    }
}
