package com.jinlink.modules.game.service;

import com.jinlink.core.page.PageQuery;
import com.jinlink.core.page.RPage;
import com.jinlink.modules.game.entity.dto.GameLiveSearchDTO;
import com.jinlink.modules.game.entity.vo.GameLiveVo;
import com.mybatisflex.core.service.IService;
import com.jinlink.modules.game.entity.GameLive;

import java.util.List;

/**
 * 游戏直播表 服务层。
 *
 * @author Summer
 * @since 1.0.0
 */
public interface GameLiveService extends IService<GameLive> {

    /**
     * 分页查询游戏直播表。
     */
    RPage<GameLiveVo> listGameLiveVoPage(PageQuery pageQuery, GameLiveSearchDTO gameLiveSearchDTO);

    /**
     * 查询所有入驻主播。
     */
    List<GameLiveVo> listAll();
}
