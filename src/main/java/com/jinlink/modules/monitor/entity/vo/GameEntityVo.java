package com.jinlink.modules.monitor.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 游戏服务器数据 VO 对象
 */

@Data
@Builder
@Schema(name = "GameEntityVo", description = "游戏服务器数据 VO 对象")
public class GameEntityVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 6911476596639057720L;

    @Schema(description = "服务器地址")
    private String addr;

    @Schema(description = "服务器端口")
    private String gameport;

    @Schema(description = "服务器SteamId")
    private String steamid;

    @Schema(description = "服务器名称")
    private String name;

    @Schema(description = "游戏AppId")
    private Integer appid;

    @Schema(description = "gamedir")
    private String gamedir;

    @Schema(description = "服务器版本")
    private String version;

    @Schema(description = "product")
    private String product;

    @Schema(description = "region")
    private Integer region;

    @Schema(description = "服务器在线玩家数")
    private Integer players;

    @Schema(description = "服务器最大玩家数")
    private Integer max_players;

    @Schema(description = "机器人")
    private Integer bots;

    @Schema(description = "服务器地图")
    private String map;

    @Schema(description = "secure")
    private Boolean secure;

    @Schema(description = "dedicated")
    private Boolean dedicated;

    @Schema(description = "os")
    private String os;

    @Schema(description = "gametype")
    private String gametype;
}
