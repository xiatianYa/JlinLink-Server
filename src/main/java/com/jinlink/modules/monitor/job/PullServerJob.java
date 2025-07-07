package com.jinlink.modules.monitor.job;

import cn.hutool.core.util.ObjectUtil;
import com.ibasco.agql.protocols.valve.source.query.SourceQueryClient;
import com.jinlink.common.constants.Constants;
import com.jinlink.common.util.AgqlUtils;
import com.jinlink.core.config.redis.service.RedisService;
import com.jinlink.modules.game.entity.GameCommunity;
import com.jinlink.modules.game.entity.GameMap;
import com.jinlink.modules.game.entity.GameOnlineStatistics;
import com.jinlink.modules.game.entity.GameServer;
import com.jinlink.modules.game.entity.vo.SourceServerVo;
import com.jinlink.modules.game.service.GameCommunityService;
import com.jinlink.modules.game.service.GameMapService;
import com.jinlink.modules.game.service.GameOnlineStatisticsService;
import com.jinlink.modules.game.service.GameServerService;
import com.mybatisflex.core.query.QueryWrapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.*;

/**
 * 拉取服务器数据的定时任务
 */
@Component
@Slf4j
@DisallowConcurrentExecution // 禁止并发执行
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
        try {
            //查询所有社区
            List<GameCommunity> gameCommunityList = gameCommunityService.list(new QueryWrapper()
                    .eq("is_cache","1"));
            //查询所有的服务器
            List<GameServer> gameServers = gameServerService.list();
            //查询所有地图
            List<GameMap> gameMapList = gameMapService.list();
            // 等待所有任务完成并收集结果
            List<SourceServerVo> serverVos = new ArrayList<>();
            // 创建一个固定大小的线程池
            ExecutorService executor = Executors.newFixedThreadPool(2);
            // 存储Future对象的列表，以便稍后获取结果
            List<Future<SourceServerVo>> futures = new ArrayList<>();
            //遍历社区列表 获取服务器数据
            for (GameCommunity gameCommunity : gameCommunityList) {
                // 假设已初始化queryClient
                SourceQueryClient queryClient = new SourceQueryClient();
                AgqlUtils agqlUtils = new AgqlUtils(queryClient, 3, 1, 200);
                //获取服务器列表
                List<GameServer> serverList = gameServers.stream().filter(item -> item.getCommunityId().equals(gameCommunity.getId()))
                        .sorted(Comparator.comparingInt(GameServer::getSort)).toList();
                Callable<SourceServerVo> callable = () -> AgqlUtils.getSourceServerVoList(agqlUtils,gameCommunity, serverList, gameMapList);;
                Future<SourceServerVo> future = executor.submit(callable);
                futures.add(future);
            }
            for (Future<SourceServerVo> future : futures) {
                SourceServerVo serverVo;
                serverVo = future.get(120L, TimeUnit.SECONDS);
                saveOnlineStatistics(serverVo);
                serverVos.add(serverVo);
            }
            //将数据存储进Redis中
            redisService.deleteObject(Constants.SERVER_VO_KEY);
            if(ObjectUtil.isNotNull(serverVos) && ObjectUtil.isNotEmpty(serverVos)) redisService.setCacheList(Constants.SERVER_VO_KEY,serverVos);
        }catch (Exception e){
            System.out.println("服务器信息获取失败"+e.getMessage());
        }
    }

    /**
     * 统计该社区在当前时间下的在线人数
     * @param sourceServerVo 数据对象
     */
    public void saveOnlineStatistics(SourceServerVo sourceServerVo){
        if (ObjectUtil.isNotNull(sourceServerVo)){
            GameOnlineStatistics onlineStatistics = GameOnlineStatistics.builder()
                    .communityId(sourceServerVo.getGameCommunity().getId())
                    .communityPlay(sourceServerVo.getOnLineUserNumber())
                    .build();
            gameOnlineStatisticsService.save(onlineStatistics);
        }
    }
}
