package com.jinlink.modules.game.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.jinlink.core.config.redis.service.RedisService;
import com.jinlink.core.page.PageQuery;
import com.jinlink.core.page.RPage;
import com.jinlink.modules.game.entity.*;
import com.jinlink.modules.game.entity.dto.GameServerSearchDTO;
import com.jinlink.modules.game.entity.vo.GameCommunityVo;
import com.jinlink.modules.game.entity.vo.GameServerVo;
import com.jinlink.modules.game.entity.vo.SteamServerVo;
import com.jinlink.modules.game.service.*;
import com.jinlink.modules.monitor.entity.vo.GameEntityVo;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.jinlink.modules.game.mapper.GameServerMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * 游戏服务器表 服务层实现。
 *
 * @author Summer
 * @since 1.0.0
 */
@Service
public class GameServerServiceImpl extends ServiceImpl<GameServerMapper, GameServer> implements GameServerService {
    @Resource
    private GameServerMapper gameServerMapper;
    @Resource
    private GameCommunityService gameCommunityService;
    @Resource
    private GameModeService gameModeService;
    @Resource
    private GameGameService gameGameService;
    @Resource
    private GameMapService gameMapService;
    @Resource
    private RedisService redisService;

    /**
     * 分页查询游戏服务器表。
     */
    @Override
    public RPage<GameServerVo> listGameServerVoPage(PageQuery pageQuery, GameServerSearchDTO gameServerSearchDTO) {
        Page<GameServer> paginate = gameServerMapper.paginate(pageQuery.getCurrent(), pageQuery.getSize(), new QueryWrapper()
                .eq("community_id", gameServerSearchDTO.getCommunityId())
                .eq("mode_id", gameServerSearchDTO.getModeId())
                .eq("game_id", gameServerSearchDTO.getGameId())
                .orderBy("community_id", false));
        List<GameServer> records = paginate.getRecords();
        List<GameServerVo> gameServerVos = BeanUtil.copyToList(records, GameServerVo.class);
        //查询所有社区
        List<GameCommunity> gameCommunityList = gameCommunityService.list();
        //查询所有游戏模式
        List<GameMode> gameModeList = gameModeService.list();
        //查询所有游戏名称
        List<GameGame> gameGameList = gameGameService.list();
        gameServerVos.forEach(item->{
            //查询当前对象下社区名称 | 游戏模式 | 游戏名称
            Optional<GameCommunity> gameCommunity = gameCommunityList.stream().filter(community -> item.getCommunityId().equals(String.valueOf(community.getId()))).findFirst();
            Optional<GameMode> gameMode = gameModeList.stream().filter(mode -> item.getModeId().equals(String.valueOf(mode.getId()))).findFirst();
            Optional<GameGame> gameGame = gameGameList.stream().filter(game -> item.getGameId().equals(String.valueOf(game.getId()))).findFirst();
            gameCommunity.ifPresent(community -> item.setCommunityName(community.getCommunityName()));
            gameMode.ifPresent(mode -> item.setModeName(mode.getModeName()));
            gameGame.ifPresent(game -> item.setGameName(game.getGameName()));
        });
        return RPage.build(new Page<>(gameServerVos,paginate.getPageNumber(),paginate.getPageSize(),paginate.getTotalRow()));
    }

    /**
     * 查询所有服务器数据(依据SteamApi)。
     */
    @Override
    public List<SteamServerVo>getServerAll(GameServerSearchDTO gameServerSearchDTO) {
        // 从 Redis 缓存中获取服务器 JSON 数据列表
        List<JSONObject> serverJsonList = redisService.getCacheList("server_json");

        // 查询与指定社区 ID 匹配的社区 ID 列表
        List<Long> matchingCommunityIds = gameCommunityService.list(new QueryWrapper()
                        .eq("id", gameServerSearchDTO.getCommunityId()))
                .stream()
                .map(GameCommunity::getId)
                .toList();

        // 准备用于存储匹配服务器数据的列表
        List<SteamServerVo> resultSteamServerVos = new ArrayList<>();

        // 遍历服务器 JSON 数据列表，解析并筛选匹配的服务器
        for (JSONObject serverJson : serverJsonList) {
            // 直接从 JSONObject 解析为 SteamServerVo 对象
            SteamServerVo steamServerVo = serverJson.toJavaObject(SteamServerVo.class);

            // 检查服务器所属的社区 ID 是否在匹配的社区 ID 列表中
            if (matchingCommunityIds.contains(steamServerVo.getGameCommunityVo().getId())) {
                // 如果匹配，则添加到结果列表中
                resultSteamServerVos.add(steamServerVo);
            }
        }

        // 返回处理后的数据列表
        return resultSteamServerVos;
    }
}
