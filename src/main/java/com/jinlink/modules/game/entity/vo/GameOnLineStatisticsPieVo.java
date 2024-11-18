package com.jinlink.modules.game.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 在线用户统计Pie VO 对象
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "GameOnLineStatisticsPieVo", description = "在线用户统计Pie VO 对象")
public class GameOnLineStatisticsPieVo {
    @Schema(description = "社区名称")
    private String name;
    @Schema(description = "在线人数")
    private Long value;
}
