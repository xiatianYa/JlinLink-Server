package com.jinlink.modules.game.entity.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jinlink.common.domain.BTPairs;
import com.jinlink.core.domain.BaseVO;
import com.jinlink.modules.system.entity.vo.SysUserRouteVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 游戏服务器 VO 对象
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "GameServerVo", description = "游戏服务器 VO 对象")
public class GameServerVo extends BaseVO {

    @Schema(description = "服务器名称")
    private String serverName;

    @Schema(description = "社区ID")
    private String communityId;

    @Schema(description = "社区名称")
    private String communityName;

    @Schema(description = "模式ID")
    private String modeId;

    @Schema(description = "模式名称")
    private String modeName;

    @Schema(description = "游戏ID")
    private String gameId;

    @Schema(description = "游戏名称")
    private String gameName;

    @Schema(description = "服务器IP")
    private String ip;

    @Schema(description = "服务器端口")
    private String port;

    @Schema(description = "排序值")
    private Integer sort;

    @Schema(description = "服务器对象")
    private ServerVo serverVo;


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "GameVo", description = "服务器对象")
    public static class ServerVo implements Serializable {

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

    }
}
