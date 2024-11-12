package com.jinlink.modules.game.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
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
        //返回数据列表
        List<SteamServerVo> result = new ArrayList<>();
        //查询所有社区
        List<GameCommunity> gameCommunityList = gameCommunityService.list(new QueryWrapper()
                .eq("id", gameServerSearchDTO.getCommunityId()));
        //查询所有服务器
        List<GameServer> gameServerList = gameServerMapper.selectListByQuery(new QueryWrapper()
                .eq("community_id", gameServerSearchDTO.getCommunityId())
                .eq("mode_id", gameServerSearchDTO.getModeId()));
        //查询所有的地图
        List<GameMap> gameMapList = gameMapService.list();
        //从Redis获取所有服务器信息
        Map<String, JSONArray> serverJson = redisService.getCacheMap("server_json");
        //匹配数据
        gameCommunityList.forEach(community->{
            List<GameServerVo> gameServerVoList = new ArrayList<>();
            //在线人数
            AtomicReference<Integer> onlineCount = new AtomicReference<>(0);
            //当前社区下的服务器列表
            JSONArray objects = serverJson.get(String.valueOf(community.getId()));
            List<GameEntityVo> gameEntityVos = JSON.parseArray(objects.toJSONString(), GameEntityVo.class);
            //匹配服务器
            gameEntityVos.forEach(gameEntityVo->{
                Optional<GameServer> serverOptional = gameServerList.stream()
                        .filter(server -> (server.getIp() + ":" + server.getPort()).equals(gameEntityVo.getAddr())).findFirst();
                //如果找到了匹配的服务器
                if (serverOptional.isPresent()) {
                    GameServer gameServer = serverOptional.get();
                    GameServerVo gameServerVo = BeanUtil.copyProperties(gameServer, GameServerVo.class);
                    gameServerVo.setServerName(gameEntityVo.getName());
                    gameServerVo.setServerVo(GameServerVo.ServerVo.builder()
                                    .mapName(gameEntityVo.getMap())
                                    .players(gameEntityVo.getPlayers())
                                    .maxPlayers(gameEntityVo.getMax_players())
                            .build());
                    //匹配地图
                    Optional<GameMap> first = gameMapList.stream().filter(map -> map.getMapName().equals(gameEntityVo.getMap())).findFirst();
                    if (first.isPresent()) {
                        GameMap gameMap = first.get();
                        GameServerVo.ServerVo serverVo = gameServerVo.getServerVo();
                        serverVo.setMapLabel(gameMap.getMapLabel());
                        serverVo.setMapUrl(gameMap.getMapUrl());
                        serverVo.setTag(JSON.parseArray(gameMap.getTag(), String.class));
                        serverVo.setType(String.valueOf(gameMap.getType()));
                    }else{
                        //没有查询到地图
                        GameServerVo.ServerVo serverVo = gameServerVo.getServerVo();
                        serverVo.setMapLabel("暂无翻译");
                    }
                    onlineCount.updateAndGet(v -> v + gameEntityVo.getPlayers());
                    gameServerVoList.add(gameServerVo);
                }
            });
            SteamServerVo build = SteamServerVo.builder()
                    .gameCommunityVo(BeanUtil.copyProperties(community, GameCommunityVo.class))
                    .onlineCount(onlineCount.get())
                    .gameServerVoList(gameServerVoList)
                    .build();
            result.add(build);
        });
        return result;
    }
}
