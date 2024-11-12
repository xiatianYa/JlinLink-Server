package com.jinlink.modules.game.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 游戏地图 查询 DTO 对象
 */

@Getter
@Setter
@Schema(name = "GameMapSearchDTO", description = "游戏地图 查询 DTO 对象")
public class GameMapSearchDTO {

    @Schema(description = "地图名称")
    private String mapName;

    @Schema(description = "译名")
    private String mapLabel;

    @Schema(description = "地图类型")
    private String type;

    @Schema(description = "模式ID")
    private String modeId;

}
