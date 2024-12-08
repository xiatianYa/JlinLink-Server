package com.jinlink.modules.game.service;

import com.ibasco.agql.protocols.valve.source.query.players.SourcePlayer;
import com.jinlink.common.util.mcping.MinecraftPingReply;
import com.jinlink.core.page.PageQuery;
import com.jinlink.core.page.RPage;
import com.jinlink.modules.game.entity.dto.GameServerSearchDTO;
import com.jinlink.modules.game.entity.vo.GameServerVo;
import com.jinlink.modules.game.entity.vo.SourceServerVo;
import com.jinlink.modules.game.entity.vo.SteamServerVo;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.jinlink.modules.game.entity.GameServer;

import java.util.List;

/**
 * 游戏服务器表 服务层。
 *
 * @author Summer
 * @since 1.0.0
 */
public interface GameServerService extends IService<GameServer> {

    /**
     * 分页查询游戏服务器表。
     */
    RPage<GameServerVo> listGameServerVoPage(PageQuery pageQuery, GameServerSearchDTO gameServerSearchDTO);

    /**
     * 查询所有服务器数据。
     */
    List<SourceServerVo> getServerAll(GameServerSearchDTO gameServerSearchDTO);

    /**
     * 查询所有服务器数据分页
     */
    RPage<SteamServerVo> getServerAllPage(PageQuery pageQuery, GameServerSearchDTO gameServerSearchDTO);

    /**
     * 查询所有服务器数据分页
     */
    List<SourcePlayer> fetchGetServerOnlineUser(String addr);


    /**
     * 查询所有服务器数据(Json)
     */
    String getServerAllJson();

    /**
     * 查询我的世界服务器(分页)
     */
    RPage<MinecraftPingReply> getMinecraftPage(PageQuery pageQuery, GameServerSearchDTO gameServerSearchDTO);
}
