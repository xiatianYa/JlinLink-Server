package com.jinlink.modules.websocket.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSONObject;
import com.jinlink.core.config.redis.service.RedisService;
import com.jinlink.modules.game.entity.vo.SourceServerVo;
import com.jinlink.modules.game.entity.vo.SteamServerVo;
import com.jinlink.modules.websocket.entity.vo.UserOnlineInfoVo;
import com.jinlink.modules.websocket.server.SeverWebsocket;
import com.jinlink.modules.websocket.service.GameWebsocketService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameWebsocketServiceImpl implements GameWebsocketService {
    @Resource
    private RedisService redisService;

    @Override
    public UserOnlineInfoVo getUserServerInfo(Long id) {
        // 获取用户所在服务器地址
        String address = SeverWebsocket.getUserOnlineServer(id);
        // 用户地址不存在
        if(ObjectUtil.isNull(address)) return null;
        // 查询当前服务器信息
        List<JSONObject> serverJsonList = redisService.getCacheList("serverVo");
        for (JSONObject jsonObject : serverJsonList) {
            // 直接从 JSONObject 解析为 SourceServerVo 对象
            SourceServerVo sourceServerVo = jsonObject.toJavaObject(SourceServerVo.class);
            for (SteamServerVo steamServerVo : sourceServerVo.getGameServerVoList()) {
                if (steamServerVo.getAddr().equals(address)) {
                    return BeanUtil.copyProperties(steamServerVo, UserOnlineInfoVo.class);
                }
            }
        }
        return null;
    }
}
