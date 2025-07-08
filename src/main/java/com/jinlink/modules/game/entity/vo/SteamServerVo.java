package com.jinlink.modules.game.entity.vo;

import com.ibasco.agql.protocols.valve.source.query.players.SourcePlayer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.checkerframework.checker.units.qual.C;

import java.util.List;

/**
 * Steam服务器数据 VO 对象
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "SteamServerVo", description = "Steam服务器数据 VO 对象")
public class SteamServerVo {

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

    @Schema(description = "排序值")
    private Integer sort;

    @Schema(description = "服务器在线玩家数")
    private Integer players;

    @Schema(description = "服务器最大玩家数")
    private Integer maxPlayers;

    @Schema(description = "是否统计")
    private Integer isStatistics;

    @Schema(description = "连接指令")
    private String connectStr;

    @Schema(description = "服务器在线玩家信息列表")
    private List<SourcePlayer> sourcePlayers;

}
