package com.jinlink.modules.game.service;

import com.jinlink.modules.game.entity.vo.GameChatRecordVo;
import com.mybatisflex.core.service.IService;
import com.jinlink.modules.game.entity.GameChat;

import java.util.List;

/**
 * 聊天室消息记录表 服务层。
 *
 * @author Summer
 * @since 1.0.0
 */
public interface GameChatService extends IService<GameChat> {
    /**
     * 查询聊天记录record。
     */
    List<GameChatRecordVo> record(Integer index, Integer num);
}
