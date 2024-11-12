package com.jinlink.modules.monitor.job;

import cn.hutool.core.util.ObjectUtil;
import com.jinlink.common.util.HttpUtils;
import com.jinlink.core.config.redis.service.RedisService;
import com.jinlink.modules.game.entity.GameCommunity;
import com.jinlink.modules.game.entity.GameOnlineStatistics;
import com.jinlink.modules.game.entity.GameServer;
import com.jinlink.modules.game.service.GameCommunityService;
import com.jinlink.modules.game.service.GameOnlineStatisticsService;
import com.jinlink.modules.game.service.GameServerService;
import com.jinlink.modules.monitor.entity.vo.GameEntityVo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Component
@Slf4j
public class PullServerJob implements Job {
    @Resource
    private GameCommunityService gameCommunityService;
    @Resource
    private GameServerService gameServerService;
    @Resource
    private GameOnlineStatisticsService gameOnlineStatisticsService;
    @Resource
    private RedisService redisService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        // 获取当前时间（开始时间）
        long startTime = System.currentTimeMillis();
        //查询所有社区
        List<GameCommunity> gameCommunityList = gameCommunityService.list();
        //查询所有的服务器
        List<GameServer> gameServers = gameServerService.list();
        //存储至Redis的Map
        Map<String, List<GameEntityVo>> serverMap = new HashMap<>();
        // 创建一个固定大小的线程池
        ExecutorService executor = Executors.newFixedThreadPool(4);
        // 存储Future对象的列表，以便稍后获取结果
        List<Future<List<GameEntityVo>>> futures = new ArrayList<>();
        //遍历社区列表 获取服务器数据
        for (GameCommunity gameCommunity : gameCommunityList) {
            //获取服务器列表
            List<GameServer> serverList = gameServers.stream().filter(item -> item.getCommunityId().equals(gameCommunity.getId())).toList();
            ArrayList<String> paths = new ArrayList<>();
            for (GameServer gameServer : serverList) {
                paths.add(gameServer.getIp()+":"+gameServer.getPort());
            }
            Callable<List<GameEntityVo>> task = () -> HttpUtils.getSteamApiServer(paths);
            Future<List<GameEntityVo>> future = executor.submit(task);
            futures.add(future);
        }
        // 等待所有任务完成并收集结果
        List<List<GameEntityVo>> results = new ArrayList<>();
        for (Future<List<GameEntityVo>> future : futures) {
            try {
                List<GameEntityVo> gameEntityVos = future.get();
                results.add(gameEntityVos);
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        // 处理数据
        int index = 0;
        for (GameCommunity gameCommunity : gameCommunityList) {
            //获取异步返回数据
            List<GameEntityVo> gameEntityVos = results.get(index);
            saveOnlineStatistics(gameCommunity.getId(),gameEntityVos);
            //数据存储进Redis中
            serverMap.put(gameCommunity.getId().toString(), gameEntityVos);
            index++;
        }
        redisService.setCacheMap("server_json",serverMap);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        // 输出方法执行时间
        System.out.println("获取服务器信息耗时 : " + elapsedTime + "毫秒");
    }

    /**
     * 统计该社区在当前时间下的在线人数
     * @param communityId 社区ID
     * @param gameEntityVos 数据列表
     */
    public void saveOnlineStatistics(Long communityId,List<GameEntityVo> gameEntityVos){
        int communityPlay=0;
        if (ObjectUtil.isNotNull(gameEntityVos)){
            for (GameEntityVo gameEntityVo : gameEntityVos) {
                communityPlay+=gameEntityVo.getPlayers();
            }
        }
        GameOnlineStatistics onlineStatistics = GameOnlineStatistics.builder()
                .communityId(communityId)
                .communityPlay(communityPlay)
                .build();
        gameOnlineStatisticsService.save(onlineStatistics);
    }
}
