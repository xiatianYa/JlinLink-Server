package com.jinlink.modules.websocket.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 服务器接收消息对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReceiveMessage {
    /**
     * 消息类型 (0:自动挤服,1:推送)
     */
    private Integer type;
    /**
     * 消息体
     */
    private String data;
}
