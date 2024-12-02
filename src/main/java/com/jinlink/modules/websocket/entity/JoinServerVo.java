package com.jinlink.modules.websocket.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

/**
 * 自动挤服 消息  VO 对象
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "JoinServerVo", description = "自动挤服 消息  VO 对象")
public class JoinServerVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 5769107831011956328L;

    @Schema(description = "服务器地址")
    private String ip;

    @Schema
    private Integer port;

    @Schema(description = "在线玩家数")
    private Integer players;

    @Schema(description = "最大在线树")
    private Integer maxPlayers;

    @Schema(description = "挤服状态")
    private Boolean status;

    @Schema(description = "返回码")
    private String code;

}
