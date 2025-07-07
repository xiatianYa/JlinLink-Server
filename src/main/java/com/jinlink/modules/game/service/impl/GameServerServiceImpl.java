package com.jinlink.modules.game.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSONObject;
import com.ibasco.agql.protocols.valve.source.query.players.SourcePlayer;
import com.jinlink.common.exception.JinLinkException;
import com.jinlink.common.util.AgqlUtils;
import com.jinlink.common.util.PageUtil;
import com.jinlink.common.util.mcping.MinecraftPing;
import com.jinlink.common.util.mcping.MinecraftPingOptions;
import com.jinlink.common.util.mcping.MinecraftPingReply;
import com.jinlink.core.config.redis.service.RedisService;
import com.jinlink.core.page.PageQuery;
import com.jinlink.core.page.RPage;
import com.jinlink.modules.game.entity.*;
import com.jinlink.modules.game.entity.dto.GameServerSearchDTO;
import com.jinlink.modules.game.entity.vo.GameServerVo;
import com.jinlink.modules.game.entity.vo.SourceServerJsonVo;
import com.jinlink.modules.game.entity.vo.SourceServerVo;
import com.jinlink.modules.game.entity.vo.SteamServerVo;
import com.jinlink.modules.game.service.*;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.jinlink.modules.game.mapper.GameServerMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.*;

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
    private RedisService redisService;

    /**
     * 分页查询游戏服务器表。
     */
    @Override
    public RPage<GameServerVo> listGameServerVoPage(PageQuery pageQuery, GameServerSearchDTO gameServerSearchDTO) {
        Page<GameServer> paginate = gameServerMapper.paginate(pageQuery.getCurrent(), pageQuery.getSize(), new QueryWrapper()
                .in("community_id", gameServerSearchDTO.getCommunityIds())
                .in("mode_id", gameServerSearchDTO.getModeIds())
                .eq("game_id", gameServerSearchDTO.getGameId())
                .orderBy("community_id", false)
                .orderBy("sort",true));
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
            Optional<GameCommunity> gameCommunity = gameCommunityList.stream()
                    .filter(community -> item.getCommunityId() != null && item.getCommunityId().equals(String.valueOf(community.getId())))
                    .findFirst();

            Optional<GameMode> gameMode = gameModeList.stream()
                    .filter(mode -> item.getModeId() != null && item.getModeId().equals(String.valueOf(mode.getId())))
                    .findFirst();

            Optional<GameGame> gameGame = gameGameList.stream()
                    .filter(game -> item.getGameId() != null && item.getGameId().equals(String.valueOf(game.getId())))
                    .findFirst();
            gameCommunity.ifPresent(community -> item.setCommunityName(community.getCommunityName()));
            gameMode.ifPresent(mode -> item.setModeName(mode.getModeName()));
            gameGame.ifPresent(game -> item.setGameName(game.getGameName()));
        });
        return RPage.build(new Page<>(gameServerVos,paginate.getPageNumber(),paginate.getPageSize(),paginate.getTotalRow()));
    }

    /**
     * 查询所有服务器数据。
     */
    @Override
    public List<SourceServerVo> getServerAll(GameServerSearchDTO gameServerSearchDTO) {
        // 从 Redis 缓存中获取服务器 JSON 数据列表
        List<JSONObject> serverJsonList = redisService.getCacheList("serverVo");
        // 返回的数据列表
        List<SourceServerVo> serverVos = new ArrayList<>();
        for (JSONObject jsonObject : serverJsonList) {
            // 直接从 JSONObject 解析为 SourceServerVo 对象
            SourceServerVo sourceServerVo = jsonObject.toJavaObject(SourceServerVo.class);
            // 筛选用户不要的社区
            if (ObjectUtil.isNotEmpty(gameServerSearchDTO.getCommunityIds())
                    && !gameServerSearchDTO.getCommunityIds().contains(sourceServerVo.getGameCommunity().getId())) continue;
            // 筛选用户不要的游戏 | 模式
            List<SteamServerVo> gameServerVoList = sourceServerVo.getGameServerVoList();
            // 拷贝列表
            List<SteamServerVo> gameServerVoCpList = new ArrayList<>();
            for (SteamServerVo steamServerVo : gameServerVoList) {
                //判断这个服务器是不是用户要的模式
                if (ObjectUtil.isNotEmpty(steamServerVo.getModeId()) && ObjectUtil.isNotEmpty(gameServerSearchDTO.getModeIds()) && !gameServerSearchDTO.getModeIds().contains(steamServerVo.getModeId())) continue;
                //判断这个服务器是不是用户要的游戏
                if (ObjectUtil.isNotEmpty(gameServerSearchDTO.getGameId()) && !steamServerVo.getGameId().equals(gameServerSearchDTO.getGameId())) continue;
                //都符合条件 则添加进列表
                gameServerVoCpList.add(steamServerVo);
            }
            if (ObjectUtil.isNotEmpty(gameServerVoCpList)){
                sourceServerVo.setGameServerVoList(gameServerVoCpList);
                serverVos.add(sourceServerVo);
            }
        }
        return serverVos;
    }

    /**
     * 查询所有服务器数据(分页)。
     */
    @Override
    public RPage<SteamServerVo> getServerAllPage(PageQuery pageQuery, GameServerSearchDTO gameServerSearchDTO) {
        // 从 Redis 缓存中获取服务器 JSON 数据列表
        List<JSONObject> serverJsonList = redisService.getCacheList("serverVo");
        // 返回的数据列表
        List<SteamServerVo> steamServerVos = new ArrayList<>();
        for (JSONObject jsonObject : serverJsonList) {
            // 直接从 JSONObject 解析为 SourceServerVo 对象
            SourceServerVo sourceServerVo = jsonObject.toJavaObject(SourceServerVo.class);
            //社区的服务器列表
            List<SteamServerVo> gameServerVoList = sourceServerVo.getGameServerVoList();
            for (SteamServerVo steamServerVo : gameServerVoList) {
                //判断这个服务器是不是用户要的游戏
                if (ObjectUtil.isNotNull(gameServerSearchDTO.getGameId()) && ObjectUtil.isNotNull(steamServerVo.getGameId()) && !steamServerVo.getGameId().equals(gameServerSearchDTO.getGameId())) continue;
                //都符合条件 则添加进列表
                steamServerVos.add(steamServerVo);
            }
        }
        return RPage.build(new Page<>(PageUtil.getPage(steamServerVos,pageQuery.getCurrent(),pageQuery.getSize()),pageQuery.getCurrent(),pageQuery.getSize(),steamServerVos.size()));
    }

    /**
     * 查询所有服务器人数。
     */
    @Override
    public List<SourcePlayer> fetchGetServerOnlineUser(String addr) {
        if(ObjectUtil.isNull(addr)) throw new JinLinkException("非法参数");
        String[] split = addr.split(":");
        return AgqlUtils.getGameUserInfoByServer(split[0], Integer.parseInt(split[1]));
    }

    /**
     * 查询所有服务器返回JSON。
     */
    @Override
    public String getServerAllJson() {
        // 从 Redis 缓存中获取服务器 JSON 数据列表
        List<JSONObject> serverJsonList = redisService.getCacheList("serverVo");
        // 返回的数据列表
        List<SourceServerJsonVo> serverVos = new ArrayList<>();
        for (JSONObject jsonObject : serverJsonList) {
            // 直接从 JSONObject 解析为 SourceServerVo 对象
            SourceServerVo sourceServerVo = jsonObject.toJavaObject(SourceServerVo.class);
            SourceServerJsonVo build = SourceServerJsonVo.builder()
                    .communityName(sourceServerVo.getGameCommunity().getCommunityName().toLowerCase())
                    .onLineUserNumber(sourceServerVo.getOnLineUserNumber())
                    .gameServerVoList(sourceServerVo.getGameServerVoList())
                    .build();
            serverVos.add(build);
        }
        return JSONObject.toJSONString(serverVos);
    }

    /**
     * 查询我的世界服务器(分页)
     */
    @Override
    public RPage<MinecraftPingReply> getMinecraftPage(PageQuery pageQuery,GameServerSearchDTO gameServerSearchDTO) {
        Page<GameServer> paginate = gameServerMapper.paginate(pageQuery.getCurrent(), pageQuery.getSize(),
                new QueryWrapper()
                        .eq("game_id", gameServerSearchDTO.getGameId()));
        List<GameServer> records = paginate.getRecords();
        List<MinecraftPingReply> minecraftPingReplies = new ArrayList<>();
        records.forEach(gameServer -> {
            try {
                MinecraftPingReply minecraftPingReply = new MinecraftPing()
                        .getPing(new MinecraftPingOptions()
                                .setHostname(gameServer.getIp())
                                .setPort(Integer.parseInt(gameServer.getPort())));
                minecraftPingReply.setAddr(gameServer.getIp()+":"+gameServer.getPort());
                minecraftPingReplies.add(minecraftPingReply);
            }catch (Exception e){
                System.out.println("服务器信息获取失败!");
            }
        });
        return RPage.build(new Page<>(minecraftPingReplies,paginate.getPageNumber(),paginate.getPageSize(),paginate.getTotalRow()));
    }
}
