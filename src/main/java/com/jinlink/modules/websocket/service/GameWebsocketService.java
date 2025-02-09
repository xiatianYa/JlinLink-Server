package com.jinlink.modules.websocket.service;

import com.jinlink.modules.websocket.entity.vo.UserOnlineInfoVo;

public interface GameWebsocketService {

    /**
     * 查询用户连接的服务器信息。
     */
    UserOnlineInfoVo getUserServerInfo(Long id);
}
