package com.jinlink.modules.game.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * 在线用户统计Line VO 对象
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "GameOnLineStatisticsVo", description = "在线用户统计Line VO 对象")
public class GameOnLineStatisticsLineVo {
    @Schema(description = "社区名称列表")
    private List<String> communityNames;
    @Schema(description = "社区数据列表")
    private List<List<Integer>> communityStatistics;
    @Schema(description = "统计时间列表")
    private List<String> timeMinutes;
}
