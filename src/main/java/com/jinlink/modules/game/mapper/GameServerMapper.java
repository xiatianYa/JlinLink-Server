package com.jinlink.modules.game.mapper;

import com.mybatisflex.core.BaseMapper;
import com.jinlink.modules.game.entity.GameServer;
import org.apache.ibatis.annotations.Mapper;

/**
 * 游戏服务器表 映射层。
 *
 * @author Summer
 * @since 1.0.0
 */
@Mapper
public interface GameServerMapper extends BaseMapper<GameServer> {

}
