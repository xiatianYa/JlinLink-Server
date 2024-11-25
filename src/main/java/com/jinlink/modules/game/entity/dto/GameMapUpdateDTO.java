package com.jinlink.modules.game.entity.dto;

import com.jinlink.common.domain.BTPairs;
import com.jinlink.core.domain.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 游戏地图 修改 DTO 对象
 */

@Getter
@Setter
@Schema(name = "GameMapUpdateDTO", description = "游戏地图 修改 DTO 对象")
public class GameMapUpdateDTO extends BaseVO {

    @Serial
    private static final long serialVersionUID = 3142177789879843872L;

    @Schema(description = "地图名称")
    private String mapName;

    @Schema(description = "译名")
    private String mapLabel;

    @Schema(description = "图片路径")
    private String mapUrl;

    @Schema(description = "模式ID")
    private Long modeId;

    @Schema(description = "地图难度")
    private Integer type;

    @Schema(description = "地图标签")
    private List<String> tag;

    @Schema(description = "地图神器")
    private List<BTPairs> artifact;
}
