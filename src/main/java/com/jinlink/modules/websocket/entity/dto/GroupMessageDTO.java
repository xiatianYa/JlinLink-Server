package com.jinlink.modules.websocket.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 服务器 查询  DTO 对象
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "ServerSearchDto", description = "群聊 DTO 对象")
public class GroupMessageDTO {
    @Schema(description = "消息体")
    private String content;
    @Schema(description = "消息类型 1文字 2图片")
    private Integer type;
}
