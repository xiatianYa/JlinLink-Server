package com.jinlink.modules.game.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 地图订阅 修改 DTO 对象
 */
@Getter
@Setter
@Schema(name = "GameMapStrategyAddDTO", description = "地图攻略 新增 DTO 对象")
public class GameMapStrategyAddDTO {

    @Schema(description = "攻略标题")
    private String title;

    @Schema(description = "地图ID")
    private Long mapId;

    @Schema(description = "攻略内容")
    private String content;

    @Schema(description = "攻略视频路径")
    private String videoUrl;
}
