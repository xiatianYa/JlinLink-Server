package com.jinlink.common.util;

import com.alibaba.fastjson2.JSON;
import com.ibasco.agql.protocols.valve.source.query.SourceQueryClient;
import com.ibasco.agql.protocols.valve.source.query.info.SourceQueryInfoResponse;
import com.ibasco.agql.protocols.valve.source.query.info.SourceServer;
import com.ibasco.agql.protocols.valve.source.query.players.SourcePlayer;
import com.ibasco.agql.protocols.valve.source.query.players.SourceQueryPlayerResponse;
import com.jinlink.modules.game.entity.GameCommunity;
import com.jinlink.modules.game.entity.GameMap;
import com.jinlink.modules.game.entity.GameServer;
import com.jinlink.modules.game.entity.dto.ExgMapDTO;
import com.jinlink.modules.game.entity.vo.SourceServerVo;
import com.jinlink.modules.game.entity.vo.SteamServerVo;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

public class AgqlUtils {

    private final SourceQueryClient queryClient;
    private final ExecutorService executor;
    private final int maxRetryAttempts;
    private final long retryDelayMillis;

    /**
     * 构造函数
     * @param queryClient SourceQuery客户端实例
     * @param threadPoolSize 线程池大小
     * @param maxRetryAttempts 最大重试次数
     * @param retryDelayMillis 重试延迟时间（毫秒）
     */
    public AgqlUtils(SourceQueryClient queryClient, int threadPoolSize, int maxRetryAttempts, long retryDelayMillis) {
        this.queryClient = queryClient;
        this.executor = Executors.newFixedThreadPool(threadPoolSize);
        this.maxRetryAttempts = maxRetryAttempts;
        this.retryDelayMillis = retryDelayMillis;
    }

    /**
     * 批量获取多个服务器的INFO信息
     * @param addresses 服务器地址列表
     * @param progressListener 进度监听器（可为null）
     * @return 包含服务器INFO信息的结果映射
     */
    public Map<InetSocketAddress, ServerInfoResult> fetchServerInfo(List<InetSocketAddress> addresses, BiConsumer<Integer, Integer> progressListener) {
        Map<InetSocketAddress, ServerInfoResult> results = new ConcurrentHashMap<>();
        AtomicInteger completedCount = new AtomicInteger(0);
        int totalCount = addresses.size();

        // 创建所有查询任务
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        for (InetSocketAddress address : addresses) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    // 执行带重试机制的查询
                    SourceQueryInfoResponse infoResponse = retryableQuery(address);
                    ServerInfoResult result = new ServerInfoResult(infoResponse.getResult(), null);
                    results.put(address, result);
                } catch (Exception e) {
                    results.put(address, new ServerInfoResult(null, e));
                } finally {
                    int count = completedCount.incrementAndGet();
                    if (progressListener != null) {
                        progressListener.accept(count, totalCount);
                    }
                }
            }, executor);
            futures.add(future);
        }

        // 等待所有任务完成
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        // 关闭线程池
        executor.shutdown();
        return results;
    }

    /**
     * 执行带重试机制的INFO查询
     * @param address 服务器地址
     * @return 查询响应
     * @throws Exception 查询失败且超过最大重试次数
     */
    private SourceQueryInfoResponse retryableQuery(InetSocketAddress address) throws Exception {
        Exception lastException = null;
        for (int attempt = 1; attempt <= maxRetryAttempts; attempt++) {
            try {
                return queryClient.getInfo(address).get();
            } catch (Exception e) {
                lastException = e;
                if (attempt < maxRetryAttempts) {
                    Thread.sleep(retryDelayMillis * attempt); // 指数退避策略
                }
            }
        }
        throw new CompletionException("查询失败，已达到最大重试次数: " + maxRetryAttempts, lastException);
    }

    /**
     * 服务器INFO查询结果类
     */
    public static class ServerInfoResult {
        private final SourceServer serverInfo;
        private final Throwable error;

        public ServerInfoResult(SourceServer serverInfo, Throwable error) {
            this.serverInfo = serverInfo;
            this.error = error;
        }

        public boolean isSuccess() {
            return serverInfo != null && error == null;
        }

        public SourceServer getServerInfo() {
            return serverInfo;
        }

        public Throwable getError() {
            return error;
        }
    }

    /**
     * 基于SteamWebApi获取服务器信息
     * @param gameCommunity 社区对象
     * @param gameServers 服务器列表
     * @param gameMapList 地图列表
     * @return 服务器源对象
     */
    public static SourceServerVo getSourceServerVoList(AgqlUtils agqlUtils,GameCommunity gameCommunity, List<GameServer> gameServers, List<GameMap> gameMapList) {
        //构建返回对象
        SourceServerVo sourceServerVo = SourceServerVo.builder()
                .gameCommunity(gameCommunity)
                .gameServerVoList(new ArrayList<>())
                .onLineUserNumber(0L)
                .build();

        List<InetSocketAddress> addresses = new ArrayList<>();
        gameServers.forEach(item->{
            addresses.add(new InetSocketAddress(item.getIp(), Integer.parseInt(item.getPort())));
        });
        // 执行查询
        Map<InetSocketAddress, ServerInfoResult> results = agqlUtils.fetchServerInfo(addresses,
                (completed, total) -> System.out.printf("进度: %d/%d (%.2f%%)\n", completed, total, (completed * 100.0) / total));
        // 处理结果
        for (Map.Entry<InetSocketAddress, ServerInfoResult> entry : results.entrySet()) {
            InetSocketAddress address = entry.getKey();
            ServerInfoResult result = entry.getValue();

            if (result.isSuccess()) {
                SourceServer serverInfo = result.getServerInfo();
                String hostString = serverInfo.getAddress().getHostString();
                int hostPort = serverInfo.getAddress().getPort();
                //获取当前服务器的地图数据
                Optional<GameMap> mapOptional = gameMapList.stream().filter(item -> item.getMapName().equals(serverInfo.getMapName())).findFirst();
                //获取当前服务器的模式数据
                Optional<GameServer> serverOptional = gameServers.stream().filter(item -> (item.getIp()+":"+item.getPort()).equals(hostString+":"+hostPort)).findFirst();
                SteamServerVo serverVo = SteamServerVo.builder()
                        .serverName(serverInfo.getName())
                        .addr(hostString+":"+hostPort)
                        .ip(hostString)
                        .port(String.valueOf(hostPort))
                        .modeId(serverOptional.map(GameServer::getModeId).orElse(null))
                        .gameId(serverOptional.map(GameServer::getGameId).orElse(null))
                        .mapName(serverInfo.getMapName())
                        .mapLabel(mapOptional.map(GameMap::getMapLabel).orElse(null))
                        .mapUrl(mapOptional.map(GameMap::getMapUrl).orElse(null))
                        .type(String.valueOf(mapOptional.map(GameMap::getType).orElse(null)))
                        .tag(JSON.parseArray(mapOptional.map(GameMap::getTag).orElse(""), String.class))
                        .players(serverInfo.getNumOfPlayers())
                        .maxPlayers(serverInfo.getMaxPlayers())
                        .build();
                sourceServerVo.getGameServerVoList().add(serverVo);
            } else {
                System.out.printf("地址: %s:%d | 查询失败: %s\n",
                        address.getHostString(),
                        address.getPort(),
                        result.getError().getMessage());
            }
        }
        return sourceServerVo;
    }

    /**
     * 获取EXG社区地图CD列表
     * @return EXG地图CD列表
     */
    public static List<ExgMapDTO> getExgMapApiServer(){
        try {
            RestTemplate restTemplate = new RestTemplate();
            String forObject = restTemplate.getForObject(new URI("https://list.darkrp.cn:9000/ServerList/CurrentCs2MapStatus"), String.class);
            return JSON.parseArray(forObject, ExgMapDTO.class);
        }catch (Exception e){
            System.out.println("地图CD信息获取失败!");
            return new ArrayList<>();
        }
    }

    /**
     * 获取服务器在线玩家列表
     */
    public static List<SourcePlayer> getGameUserInfoByServer(String ip, Integer port){
        try (SourceQueryClient client = new SourceQueryClient()) {
            InetSocketAddress address = new InetSocketAddress(ip, port);
            SourceQueryPlayerResponse response = client.getPlayers(address).join();
            return response.getResult();
        }catch (Exception e){
            return null;
        }
    }
}
