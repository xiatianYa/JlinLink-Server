package com.jinlink.modules.game.service;

import com.jinlink.core.page.PageQuery;
import com.jinlink.core.page.RPage;
import com.jinlink.modules.game.entity.dto.GameMapOrderAddDTO;
import com.jinlink.modules.game.entity.vo.GameMapOrderVo;
import com.mybatisflex.core.service.IService;
import com.jinlink.modules.game.entity.GameMapOrder;

import java.util.List;

/**
 * 地图订阅表 服务层。
 *
 * @author Summer
 * @since 1.0.0
 */
public interface GameMapOrderService extends IService<GameMapOrder> {
    /**
     * 分页查询地图订阅表。
     */
    RPage<GameMapOrderVo> listGameMapOrderVoPage(PageQuery pageQuery);

    /**
     * 查询所有地图订阅表。
     */
    List<GameMapOrderVo> listGameMapOrderVo();

    /**
     * 查询用户所有地图订阅表。
     */
    List<GameMapOrderVo> listGameMapOrderVoByUser();

    /**
     * 添加地图订阅表。
     */
    Boolean saveGameMapOrder(GameMapOrderAddDTO gameMapOrderAddDTO);
}
