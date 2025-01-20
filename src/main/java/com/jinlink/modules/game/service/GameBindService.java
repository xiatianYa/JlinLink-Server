package com.jinlink.modules.game.service;

import com.jinlink.core.page.PageQuery;
import com.jinlink.core.page.RPage;
import com.jinlink.modules.game.entity.dto.GameBindUpdateDTO;
import com.jinlink.modules.game.entity.vo.GameBindVo;
import com.mybatisflex.core.service.IService;
import com.jinlink.modules.game.entity.GameBind;

/**
 * 游戏绑键表 服务层。
 *
 * @author Summer
 * @since 1.0.0
 */
public interface GameBindService extends IService<GameBind> {
    /**
     * 分页查询游戏绑键表。
     */
    RPage<GameBindVo> listGameBindVopage(PageQuery pageQuery);
    /**
     * 根据游戏绑键表社区Id获取详细信息。
     */
    GameBindVo getInfoByCommunityId(Long communityId);
    /**
     * 根据主键更新/新增游戏绑键表。
     */
    Boolean updateGameBind(GameBindUpdateDTO gameBindUpdateDTO);
}
