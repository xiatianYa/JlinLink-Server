package com.jinlink.modules.websocket.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibasco.agql.protocols.valve.source.query.players.SourcePlayer;
import com.jinlink.common.domain.LoginUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 服务器接收消息对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SteamServerPushVo {

    @Schema(description = "推送人名称")
    private String pushUserName;

    @Schema(description = "推送内容")
    private String description;

    @Schema(description = "服务器名称")
    private String serverName;

    @Schema(description = "服务器地址")
    private String addr;

    @Schema(description = "服务器IP")
    private String ip;

    @Schema(description = "服务器端口")
    private String port;

    @Schema(description = "服务器模式")
    private Long modeId;

    @Schema(description = "游戏ID")
    private Long gameId;

    @Schema(description = "地图名称")
    private String mapName;

    @Schema(description = "译名")
    private String mapLabel;

    @Schema(description = "图片路径")
    private String mapUrl;

    @Schema(description = "地图类型")
    private String type;

    @Schema(description = "地图标签")
    private List<String> tag;

    @Schema(description = "服务器在线玩家数")
    private Integer players;

    @Schema(description = "服务器最大玩家数")
    private Integer maxPlayers;

    @Schema(description = "服务器在线玩家信息列表")
    private List<SourcePlayer> sourcePlayers;
}