package com.jinlink.modules.game.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.jinlink.common.domain.LoginUser;
import com.jinlink.core.holder.GlobalUserHolder;
import com.jinlink.modules.game.entity.vo.GameChatRecordVo;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.jinlink.modules.game.entity.GameChat;
import com.jinlink.modules.game.mapper.GameChatMapper;
import com.jinlink.modules.game.service.GameChatService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 聊天室消息记录表 服务层实现。
 *
 * @author Summer
 * @since 1.0.0
 */
@Service
public class GameChatServiceImpl extends ServiceImpl<GameChatMapper, GameChat> implements GameChatService {

    @Resource
    private GameChatMapper gameChatMapper;

    @Override
    public List<GameChatRecordVo> record(Integer index, Integer num) {
        //构建查询条件
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderBy("create_time",false);
        queryWrapper.limit(index,num);
        List<GameChat> gameChats = gameChatMapper.selectListByQuery(queryWrapper);
        Collections.reverse(gameChats);
        //返回数据列表
        List<GameChatRecordVo> recordVos = new ArrayList<>();
        for (int i = 0; i < gameChats.size(); i++) {
            GameChat gameChat = gameChats.get(i);
            GameChatRecordVo gameChatRecordVo = new GameChatRecordVo();
            LoginUser loginUser = GlobalUserHolder.getUserById(gameChat.getCreateUserId());
            gameChatRecordVo.setLoginUser(loginUser);
            gameChatRecordVo.setFromId(gameChat.getCreateUserId());
            gameChatRecordVo.setId(gameChat.getId());
            gameChatRecordVo.setContent(gameChat.getContent());
            gameChatRecordVo.setSource(gameChat.getSource());
            gameChatRecordVo.setToId(gameChat.getToId());
            gameChatRecordVo.setType(gameChat.getType());
            gameChatRecordVo.setCreateTime(gameChat.getCreateTime());
            gameChatRecordVo.setUpdateTime(gameChat.getUpdateTime());

            if (i > 0) {
                GameChatRecordVo prevRecord = recordVos.get(i - 1);
                Duration duration = Duration.between(prevRecord.getCreateTime(), gameChatRecordVo.getCreateTime());
                gameChatRecordVo.setIsShowTime(duration.toMinutes() >= 5);
            }else{
                gameChatRecordVo.setIsShowTime(true);
            }

            recordVos.add(gameChatRecordVo);
        }
        return recordVos;
    }
}
