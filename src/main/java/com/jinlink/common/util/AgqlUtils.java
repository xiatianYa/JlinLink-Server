package com.jinlink.common.util;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ibasco.agql.protocols.valve.source.query.SourceQueryClient;
import com.ibasco.agql.protocols.valve.source.query.info.SourceQueryInfoResponse;
import com.ibasco.agql.protocols.valve.source.query.info.SourceServer;
import com.ibasco.agql.protocols.valve.source.query.players.SourcePlayer;
import com.ibasco.agql.protocols.valve.source.query.players.SourceQueryPlayerResponse;
import com.jinlink.common.constants.Constants;
import com.jinlink.modules.game.entity.GameCommunity;
import com.jinlink.modules.game.entity.GameMap;
import com.jinlink.modules.game.entity.GameServer;
import com.jinlink.modules.game.entity.dto.ExgMapDTO;
import com.jinlink.modules.game.entity.vo.SourceServerVo;
import com.jinlink.modules.game.entity.vo.SteamServerVo;
import com.jinlink.modules.monitor.entity.vo.GameEntityVo;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class AgqlUtils {

    /**
     * 基于SteamWebApi获取服务器信息
     * @param gameCommunity 社区对象
     * @param gameServers 服务器列表
     * @param gameMapList 地图列表
     * @return 服务器源对象
     */
    public static SourceServerVo getSourceServerVoList(GameCommunity gameCommunity, List<GameServer> gameServers, List<GameMap> gameMapList) throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        StringBuilder paths= new StringBuilder();
        for (GameServer gameServer : gameServers) {
            paths.append("paths=").append(gameServer.getIp()).append(":").append(gameServer.getPort()).append("&");
        }
        return analysisJsonForObject(gameCommunity,gameServers,restTemplate.getForObject(new URI("https://inadvertently.top/steamApi/?" + paths), String.class),gameMapList);
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
     * 将返回的json字符串转为java对象
     */
    private static SourceServerVo analysisJsonForObject(GameCommunity gameCommunity,List<GameServer> gameServers,String forObject,List<GameMap> gameMapList) {
        if (ObjectUtil.isNull(forObject)){
            return null;
        }
        //构建返回对象
        SourceServerVo sourceServerVo = SourceServerVo.builder()
                .gameCommunity(gameCommunity)
                .gameServerVoList(new ArrayList<>())
                .onLineUserNumber(0L)
                .build();
        //构建JSONArray
        JSONArray serverJsonArray = JSON.parseArray(forObject);
        for (Object obj : serverJsonArray) {
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray servers = jsonObject.getJSONObject("response").getJSONArray("servers");
            //如果json为空 则返回循环
            if (StringUtils.isNull(servers)) continue;
            for (Object server : servers) {
                //获取回来的服务器对象
                GameEntityVo gameEntityVo = JSONObject.parseObject(server.toString(), GameEntityVo.class);
                String[] addr = gameEntityVo.getAddr().split(":");
                //获取当前服务器的地图数据
                Optional<GameMap> mapOptional = gameMapList.stream().filter(item -> item.getMapName().equals(gameEntityVo.getMap())).findFirst();
                //获取当前服务器的模式数据
                Optional<GameServer> serverOptional = gameServers.stream().filter(item -> (item.getIp()+":"+item.getPort()).equals(gameEntityVo.getAddr())).findFirst();
                SteamServerVo serverVo = SteamServerVo.builder()
                        .serverName(gameEntityVo.getName())
                        .addr(gameEntityVo.getAddr())
                        .ip(addr[0])
                        .port(addr[1])
                        .modeId(serverOptional.map(GameServer::getModeId).orElse(null))
                        .gameId(serverOptional.map(GameServer::getGameId).orElse(null))
                        .mapName(gameEntityVo.getMap())
                        .mapLabel(mapOptional.map(GameMap::getMapLabel).orElse(null))
                        .mapUrl(mapOptional.map(GameMap::getMapUrl).orElse(null))
                        .type(String.valueOf(mapOptional.map(GameMap::getType).orElse(null)))
                        .tag(JSON.parseArray(mapOptional.map(GameMap::getTag).orElse(""), String.class))
                        .players(gameEntityVo.getPlayers())
                        .maxPlayers(gameEntityVo.getMax_players())
                        .build();
                //当前服务器不被统计
                if (serverOptional.isPresent()) {
                    GameServer gameServer = serverOptional.get();
                    if (gameServer.getIsStatistics().equals(1)){
                        sourceServerVo.setOnLineUserNumber(sourceServerVo.getOnLineUserNumber() + gameEntityVo.getPlayers());
                    }
                }
                sourceServerVo.getGameServerVoList().add(serverVo);
            }
        }
        //查询那些服务器没有数据 走源服务器
        List<SteamServerVo> gameServerVoList = sourceServerVo.getGameServerVoList();
        for (GameServer gameServer : gameServers) {
            //查询是否有这个服务器信息
            Optional<SteamServerVo> isTrue = gameServerVoList.stream().filter(item -> (item.getIp() + ":" + item.getPort())
                    .equals(gameServer.getIp() + ":" + gameServer.getPort())).findFirst();
            if (isTrue.isEmpty()) {
                // 走源服务器查询
                try (SourceQueryClient client = new SourceQueryClient()) {
                    InetSocketAddress address = new InetSocketAddress(gameServer.getIp(), Integer.parseInt(gameServer.getPort()));
                    CompletableFuture<SourceQueryInfoResponse> info = client.getInfo(address);
                    // 设置超时时间为 1 秒
                    SourceQueryInfoResponse response = info.orTimeout(300, TimeUnit.MILLISECONDS).join();
                    SourceServer sourceServer = response.getResult();
                    // 获取当前服务器的地图数据
                    Optional<GameMap> mapOptional = gameMapList.stream().filter(item -> item.getMapName().equals(sourceServer.getMapName())).findFirst();
                    SteamServerVo serverVo = SteamServerVo.builder()
                            .serverName(sourceServer.getName())
                            .addr(gameServer.getIp() + ":" + gameServer.getPort())
                            .ip(gameServer.getIp())
                            .port(gameServer.getPort())
                            .modeId(gameServer.getModeId())
                            .gameId(gameServer.getGameId())
                            .mapName(sourceServer.getMapName())
                            .mapLabel(mapOptional.map(GameMap::getMapLabel).orElse(null))
                            .mapUrl(mapOptional.map(GameMap::getMapUrl).orElse(null))
                            .type(String.valueOf(mapOptional.map(GameMap::getType).orElse(null)))
                            .tag(JSON.parseArray(mapOptional.map(GameMap::getTag).orElse(""), String.class))
                            .players(sourceServer.getNumOfPlayers())
                            .maxPlayers(sourceServer.getMaxPlayers())
                            .isStatistics(gameServer.getIsStatistics())
                            .build();
                    sourceServerVo.setOnLineUserNumber(sourceServerVo.getOnLineUserNumber() + sourceServer.getNumOfPlayers());
                    sourceServerVo.getGameServerVoList().add(serverVo);
                } catch (Exception e) {
                    System.out.println(gameServer.getServerName() + "信息获取超时");
                }
            }
        }
        return sourceServerVo;
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
