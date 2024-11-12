package com.jinlink.modules.game.entity.vo;

import com.jinlink.common.domain.BTPairs;
import com.jinlink.core.domain.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * 游戏地图 VO 对象
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "GameMapVo", description = "游戏地图 VO 对象")
public class GameMapVo extends BaseVO {

    @Schema(description = "地图名称")
    private String mapName;

    @Schema(description = "译名")
    private String mapLabel;

    @Schema(description = "图片路径")
    private String mapUrl;

    @Schema(description = "模式ID")
    private String modeId;

    @Schema(description = "地图类型")
    private String type;

    @Schema(description = "地图标签")
    private List<String> tag;

    @Schema(description = "地图神器")
    private List<BTPairs> artifact;
}
