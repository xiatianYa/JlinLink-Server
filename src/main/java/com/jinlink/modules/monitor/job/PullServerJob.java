package com.jinlink.modules.monitor.job;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.jinlink.common.util.HttpUtils;
import com.jinlink.core.config.redis.service.RedisService;
import com.jinlink.modules.game.entity.GameCommunity;
import com.jinlink.modules.game.entity.GameMap;
import com.jinlink.modules.game.entity.GameOnlineStatistics;
import com.jinlink.modules.game.entity.GameServer;
import com.jinlink.modules.game.entity.vo.GameCommunityVo;
import com.jinlink.modules.game.entity.vo.GameServerVo;
import com.jinlink.modules.game.entity.vo.SteamServerVo;
import com.jinlink.modules.game.service.GameCommunityService;
import com.jinlink.modules.game.service.GameMapService;
import com.jinlink.modules.game.service.GameOnlineStatisticsService;
import com.jinlink.modules.game.service.GameServerService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 拉取服务器数据的定时任务
 */
@Component
@Slf4j
public class PullServerJob implements Job {
    @Resource
    private GameCommunityService gameCommunityService;
    @Resource
    private GameServerService gameServerService;
    @Resource
    private GameMapService gameMapService;
    @Resource
    private GameOnlineStatisticsService gameOnlineStatisticsService;
    @Resource
    private RedisService redisService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        //查询所有社区
        List<GameCommunity> gameCommunityList = gameCommunityService.list();
        //查询所有的服务器
        List<GameServer> gameServers = gameServerService.list();
        //存储至Redis的Map
        List<SteamServerVo> steamServerVos = new ArrayList<>();
        // 创建一个固定大小的线程池
        ExecutorService executor = Executors.newFixedThreadPool(4);
        // 存储Future对象的列表，以便稍后获取结果
        List<Future<List<GameServerVo.ServerVo>>> futures = new ArrayList<>();
        //遍历社区列表 获取服务器数据
        for (GameCommunity gameCommunity : gameCommunityList) {
            //获取服务器列表
            List<GameServer> serverList = gameServers.stream().filter(item -> item.getCommunityId().equals(gameCommunity.getId())).toList();
            ArrayList<String> paths = new ArrayList<>();
            for (GameServer gameServer : serverList) {
                paths.add(gameServer.getIp()+":"+gameServer.getPort());
            }
            Callable<List<GameServerVo.ServerVo>> task = () -> HttpUtils.getSteamApiServer(paths,serverList);
            Future<List<GameServerVo.ServerVo>> future = executor.submit(task);
            futures.add(future);
        }
        // 等待所有任务完成并收集结果
        List<List<GameServerVo.ServerVo>> results = new ArrayList<>();
        for (Future<List<GameServerVo.ServerVo>> future : futures) {
            try {
                List<GameServerVo.ServerVo> gameEntityVos = future.get();
                results.add(gameEntityVos);
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        //查询所有地图
        List<GameMap> gameMapList = gameMapService.list();
        // 处理数据
        int index = 0;
        for (GameCommunity gameCommunity : gameCommunityList) {
            //获取异步返回数据
            List<GameServerVo.ServerVo> gameEntityVos = results.get(index);
            saveOnlineStatistics(gameCommunity.getId(),gameEntityVos);
            //统计总人数
            AtomicInteger onlineCount = new AtomicInteger();
            if (ObjectUtil.isNull(gameEntityVos)) continue;
            gameEntityVos.forEach(gameEntityVo -> {
                //处理地图数据
                Optional<GameMap> gameMapOptional = gameMapList.stream().filter(map -> map.getMapName().equals(gameEntityVo.getMapName())).findFirst();
                if (gameMapOptional.isPresent()){
                    GameMap gameMap = gameMapOptional.get();
                    gameEntityVo.setMapUrl(gameMap.getMapUrl());
                    gameEntityVo.setMapLabel(gameMap.getMapLabel());
                    gameEntityVo.setType(String.valueOf(gameMap.getType()));
                    gameEntityVo.setTag(JSON.parseArray(gameMap.getTag(), String.class));
                }
                onlineCount.addAndGet(gameEntityVo.getPlayers());
            });
            //数据存储进Redis中
            steamServerVos.add(SteamServerVo.builder()
                            .gameCommunityVo(BeanUtil.copyProperties(gameCommunity, GameCommunityVo.class))
                            .onlineCount(onlineCount.get())
                            .gameServerVoList(gameEntityVos)
                    .build());
            index++;
        }
        redisService.deleteObject("server_json");
        if (ObjectUtil.isNotNull(steamServerVos) && ObjectUtil.isNotEmpty(steamServerVos)) redisService.setCacheList("server_json",steamServerVos);
    }

    /**
     * 统计该社区在当前时间下的在线人数
     * @param communityId 社区ID
     * @param gameEntityVos 数据列表
     */
    public void saveOnlineStatistics(Long communityId,List<GameServerVo.ServerVo> gameEntityVos){
        int communityPlay=0;
        if (ObjectUtil.isNotNull(gameEntityVos)){
            for (GameServerVo.ServerVo gameServerVo : gameEntityVos) {
                communityPlay+=gameServerVo.getPlayers();
            }
        }
        GameOnlineStatistics onlineStatistics = GameOnlineStatistics.builder()
                .communityId(communityId)
                .communityPlay(communityPlay)
                .build();
        gameOnlineStatisticsService.save(onlineStatistics);
    }
}
