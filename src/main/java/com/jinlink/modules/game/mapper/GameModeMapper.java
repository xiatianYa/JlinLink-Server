package com.jinlink.modules.game.mapper;

import com.mybatisflex.core.BaseMapper;
import com.jinlink.modules.game.entity.GameMode;
import org.apache.ibatis.annotations.Mapper;

/**
 * 游戏模式表 映射层。
 *
 * @author Summer
 * @since 1.0.0
 */
@Mapper
public interface GameModeMapper extends BaseMapper<GameMode> {

}
