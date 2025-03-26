package com.jinlink.modules.game.entity;

import com.jinlink.core.domain.BaseEntity;
import com.mybatisflex.annotation.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 聊天室消息记录表 实体类。
 *
 * @author Summer
 * @since 1.0.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "game_chat")
public class GameChat extends BaseEntity {

    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息来源
     */
    private String source;

    /**
     * 消息接收者
     */
    private Long toId;

    /**
     * 消息类型(1:文本,2:图片)
     */
    private String type;

}
