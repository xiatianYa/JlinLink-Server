package com.jinlink.modules.game.service;

import com.jinlink.core.page.PageQuery;
import com.jinlink.core.page.RPage;
import com.jinlink.modules.game.entity.vo.GameModelVo;
import com.mybatisflex.core.service.IService;
import com.jinlink.modules.game.entity.GameModel;

/**
 * 游戏模型表 服务层。
 *
 * @author Summer
 * @since 1.0.0
 */
public interface GameModelService extends IService<GameModel> {

    /**
     * 分页查询游戏模型表。
     */
    RPage<GameModelVo> listGameModelVoPage(PageQuery pageQuery);
}
