package com.jinlink.modules.game.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 在线用户数据 查询 DTO 对象
 */

@Getter
@Setter
@Schema(name = "GameOnLineStatisticsDTO", description = "在线用户数据 查询 DTO 对象")
public class GameOnLineStatisticsDTO {

    @Schema(description = "社区ID")
    private Long communityId;

    @Schema(description = "社区在线用户")
    private Integer communityPlay;

    @Schema(description = "统计时间")
    private String timeMinute;
}
