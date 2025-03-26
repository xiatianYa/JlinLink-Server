package com.jinlink.modules.game.mapper;

import com.mybatisflex.core.BaseMapper;
import com.jinlink.modules.game.entity.GameChat;
import org.apache.ibatis.annotations.Mapper;

/**
 * 聊天室消息记录表 映射层。
 *
 * @author Summer
 * @since 1.0.0
 */
@Mapper
public interface GameChatMapper extends BaseMapper<GameChat> {

}
