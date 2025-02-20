package com.jinlink.modules.game.service;

import com.jinlink.core.page.PageQuery;
import com.jinlink.core.page.RPage;
import com.jinlink.modules.game.entity.dto.GameMapStrategyAddDTO;
import com.jinlink.modules.game.entity.vo.GameMapStrategyVo;
import com.mybatisflex.core.service.IService;
import com.jinlink.modules.game.entity.GameMapStrategy;

import java.io.Serializable;

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

    /**
     * 添加地图攻略表。
     */
    Boolean saveMapStrategy(GameMapStrategyAddDTO gameMapStrategyAddDTO);

    /**
     * 发布文章。
     */
    Boolean pushMapStrategyById(GameMapStrategy gameMapStrategy);

    /**
     * 根据主键删除地图攻略表。
     */
    Boolean removeMapStrategyById(Serializable id);

    /**
     * 根据主键更新地图攻略表。
     */
    Boolean updateMapStrategyById(GameMapStrategy gameMapStrategy);

    /**
     * 根据地图攻略表主键获取详细信息。
     */
    GameMapStrategyVo getMapStrategyInfoById(Serializable id);

    /**
     * 游戏攻略审核
     */
    Boolean examineMapStrategyById(Long id, String type);
}
