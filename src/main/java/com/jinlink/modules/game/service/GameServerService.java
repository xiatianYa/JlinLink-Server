package com.jinlink.modules.game.service;

import com.jinlink.core.page.PageQuery;
import com.jinlink.core.page.RPage;
import com.jinlink.modules.game.entity.vo.GameServerVo;
import com.mybatisflex.core.service.IService;
import com.jinlink.modules.game.entity.GameServer;

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
    RPage<GameServerVo> listGameServerVoPage(PageQuery pageQuery);
}
