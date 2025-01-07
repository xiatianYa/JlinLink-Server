package com.jinlink.modules.game.service;

import com.jinlink.common.domain.Options;
import com.jinlink.core.page.PageQuery;
import com.jinlink.core.page.RPage;
import com.jinlink.modules.game.entity.dto.GameMapAddDTO;
import com.jinlink.modules.game.entity.dto.GameMapSearchDTO;
import com.jinlink.modules.game.entity.dto.GameMapUpdateDTO;
import com.jinlink.modules.game.entity.vo.GameMapVo;
import com.mybatisflex.core.service.IService;
import com.jinlink.modules.game.entity.GameMap;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 游戏地图表 服务层。
 *
 * @author Summer
 * @since 1.0.0
 */
public interface GameMapService extends IService<GameMap> {
    /**
     * 分页查询游戏地图表。
     */
    RPage<GameMapVo> listGameMapVoPage(PageQuery pageQuery,GameMapSearchDTO gameMapSearchDTO);

    /**
     * 添加游戏地图表。
     */
    @Transactional
    Boolean saveGameMap(GameMapAddDTO gameMapAddDTO);

    /**
     * 根据主键更新游戏地图表。
     */
    @Transactional
    Boolean updateGameMap(GameMapUpdateDTO gameMapUpdateDTO);

    /**
     * 查询所有游戏地图名称。
     */
    List<String> listMapName();

    /**
     * 查询全部地图配置项。
     */
    List<Options<String>> allMapNames();
    /**
     * 查询全部地图模型配置项。
     */
    List<Options<String>> allMapModes();
}
