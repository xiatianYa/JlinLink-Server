package com.jinlink.modules.game.service;

import com.jinlink.core.page.PageQuery;
import com.jinlink.core.page.RPage;
import com.jinlink.modules.game.entity.vo.GameModeVo;
import com.mybatisflex.core.service.IService;
import com.jinlink.modules.game.entity.GameMode;

/**
 * 游戏模式表 服务层。
 *
 * @author Summer
 * @since 1.0.0
 */
public interface GameModeService extends IService<GameMode> {
    /**
     * 分页查询游戏模式表。
     */
    RPage<GameModeVo> listGameModeVoPage(PageQuery pageQuery);
}
