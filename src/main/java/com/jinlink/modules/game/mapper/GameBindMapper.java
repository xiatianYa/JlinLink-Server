package com.jinlink.modules.game.mapper;

import com.mybatisflex.core.BaseMapper;
import com.jinlink.modules.game.entity.GameBind;
import org.apache.ibatis.annotations.Mapper;

/**
 * 游戏绑键表 映射层。
 *
 * @author Summer
 * @since 1.0.0
 */
@Mapper
public interface GameBindMapper extends BaseMapper<GameBind> {

}
