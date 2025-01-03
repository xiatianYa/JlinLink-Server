package com.jinlink.modules.game.service;

import com.jinlink.common.domain.Options;
import com.jinlink.core.page.PageQuery;
import com.jinlink.core.page.RPage;
import com.jinlink.modules.game.entity.vo.GameGameVo;
import com.mybatisflex.core.service.IService;
import com.jinlink.modules.game.entity.GameGame;

import java.util.List;

/**
 * 游戏表 服务层。
 *
 * @author Summer
 * @since 1.0.0
 */
public interface GameGameService extends IService<GameGame> {
    /**
     * 分页查询游戏表。
     */
    RPage<GameGameVo> listGameGameVoPage(PageQuery pageQuery);

    /**
     * 查询全部游戏名称。
     */
    List<Options<String>> getAllGameNames();
}
