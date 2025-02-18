package com.jinlink.modules.game.service;

import com.jinlink.core.page.PageQuery;
import com.jinlink.core.page.RPage;
import com.jinlink.modules.game.entity.vo.GameMapStrategyVo;
import com.mybatisflex.core.service.IService;
import com.jinlink.modules.game.entity.GameMapStrategy;

/**
 * 地图攻略表 服务层。
 *
 * @author Summer
 * @since 1.0.0
 */
public interface GameMapStrategyService extends IService<GameMapStrategy> {

    /**
     * 分页查询地图攻略表。
     */
    RPage<GameMapStrategyVo> listGameMapStrategyVoPage(PageQuery pageQuery);
}
