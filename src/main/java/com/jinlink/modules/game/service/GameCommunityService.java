package com.jinlink.modules.game.service;

import com.jinlink.core.page.PageQuery;
import com.jinlink.core.page.RPage;
import com.jinlink.modules.game.entity.vo.GameCommunityVo;
import com.mybatisflex.core.service.IService;
import com.jinlink.modules.game.entity.GameCommunity;

/**
 * 游戏社区表 服务层。
 *
 * @author Summer
 * @since 1.0.0
 */
public interface GameCommunityService extends IService<GameCommunity> {

    /**
     * 分页查询游戏社区表。
     */
    RPage<GameCommunityVo> listGameCommunityVoPage(PageQuery pageQuery);
}
