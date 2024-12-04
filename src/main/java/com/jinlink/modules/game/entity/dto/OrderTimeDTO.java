package com.jinlink.modules.game.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 地图订阅时间 DTO 对象
 */

@Getter
@Setter
@Schema(name = "OrderTime", description = "地图订阅时间 DTO 对象")
public class OrderTimeDTO {

    @Schema(description = "通知服务器")
    private String addr;

    @Schema(description = "地图名称")
    private String mapName;

    @Schema(description = "通知时间")
    private String orderTime;
}
