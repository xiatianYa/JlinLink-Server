package com.jinlink.modules.game.service;

import com.jinlink.core.page.PageQuery;
import com.jinlink.core.page.RPage;
import com.jinlink.modules.game.entity.GameCommunity;
import com.jinlink.modules.game.entity.dto.GameServerSearchDTO;
import com.jinlink.modules.game.entity.vo.GameServerVo;
import com.jinlink.modules.game.entity.vo.SteamServerVo;
import com.mybatisflex.core.service.IService;
import com.jinlink.modules.game.entity.GameServer;

import java.util.List;
import java.util.Map;

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
     * 查询所有服务器数据(依据SteamApi) key社区 value社区下服务器数据。
     */
    List<SteamServerVo> getServerAll(GameServerSearchDTO gameServerSearchDTO);
}
