package com.jinlink.modules.game.service;

import com.jinlink.common.domain.Options;
import com.jinlink.core.page.PageQuery;
import com.jinlink.core.page.RPage;
import com.jinlink.modules.game.entity.vo.GameCommunityVo;
import com.mybatisflex.core.service.IService;
import com.jinlink.modules.game.entity.GameCommunity;

import java.io.Serializable;
import java.util.List;

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

    /**
     * 查询全部社区名称。
     */
    List<Options<String>> getAllCommunityNames();

    /**
     * 根据主键删除游戏社区表。
     */
    Boolean removeCommunityById(Serializable id);
}
