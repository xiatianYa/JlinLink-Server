package com.jinlink.modules.game.entity.vo;

import com.jinlink.core.domain.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 地图攻略 VO 对象
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "GameMapStrategyVo", description = "地图攻略 VO 对象")
public class GameMapStrategyVo extends BaseVO {

    @Schema(description = "攻略名称")
    private String title;

    @Schema(description = "地图ID")
    private Integer mapId;

    @Schema(description = "地图名称")
    private String mapLabel;

    @Schema(description = "攻略内容")
    private String content;

    @Schema(description = "攻略视频路径")
    private String videoUrl;

    @Schema(description = "审核状态")
    private Integer status;

    @Schema(description = "发布人ID")
    private String createUserId;

    @Schema(description = "发布人名称")
    private String createUserName;
}
