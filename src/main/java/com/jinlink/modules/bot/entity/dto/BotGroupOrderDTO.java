package com.jinlink.modules.bot.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 地图订阅 DTO 对象
 */

@Getter
@Setter
@Schema(name = "BotGroupOrderDTO", description = "地图订阅 DTO 对象")
public class BotGroupOrderDTO {
    @Schema(description = "服务器地图")
    private String addr;
    @Schema(description = "订阅通知的时间间隔")
    private LocalDateTime orderTime;
}
