package com.jinlink.modules.websocket.entity.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户所在服务器喜
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserOnlineInfoVo {
    @Schema(description = "服务器名称")
    private String serverName;

    @Schema(description = "服务器地址")
    private String addr;

    @Schema(description = "地图名称")
    private String mapName;

    @Schema(description = "译名")
    private String mapLabel;

    @Schema(description = "图片路径")
    private String mapUrl;

    @Schema(description = "服务器在线玩家数")
    private Integer players;

    @Schema(description = "服务器最大玩家数")
    private Integer maxPlayers;
}
