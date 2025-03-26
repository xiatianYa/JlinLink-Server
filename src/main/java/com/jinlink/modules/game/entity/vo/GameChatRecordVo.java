package com.jinlink.modules.game.entity.vo;

import com.jinlink.common.domain.LoginUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * 聊天室消息 VO 对象
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "GameChatRecordVo", description = "聊天室消息 VO 对象")
public class GameChatRecordVo {
    // 消息发送者的 ID
    private Long fromId;
    // 消息发送者的详细信息，是一个 LoginUser 对象
    private LoginUser loginUser;
    // 消息的唯一标识符
    private Long id;
    // 是否显示消息时间的标志
    private Boolean isShowTime;
    // 消息内容
    private String content;
    // 消息来源，例如 "group"
    private String source;
    // 消息接收者的 ID
    private Long toId;
    // 消息类型，例如 "recall"
    private String type;
    // 消息更新时间，格式为 "yyyy-MM-dd HH:mm:ss.SSS"
    private LocalDateTime updateTime;
    // 消息创建时间，格式为 "yyyy-MM-dd HH:mm:ss.SSS"
    private LocalDateTime createTime;
}
