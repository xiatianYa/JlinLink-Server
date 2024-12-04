package com.jinlink.modules.game.entity.dto;

/**
 * 地图订阅 新增 DTO 对象
 */

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 地图订阅 修改 DTO 对象
 */
@Getter
@Setter
@Schema(name = "GameMapOrderUpdateDTO", description = "地图订阅 新增 DTO 对象")
public class GameMapOrderAddDTO {
    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "地图ID")
    private Long mapId;

    @Schema(description = "通知记录")
    private String orderTimes;
}
