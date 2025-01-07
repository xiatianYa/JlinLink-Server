package com.jinlink.modules.game.entity.dto;

import com.jinlink.core.domain.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "GameModelUpdateDTO", description = "游戏模型 修改 DTO 对象")
public class GameModelUpdateDTO extends BaseVO {

    @Schema(description = "模型名称")
    private String modelName;

    @Schema(description = "模型类型")
    private String modelType;

    @Schema(description = "模型图片")
    private String modelUrl;

    @Schema(description = "模型地址")
    private String modelModeUrl;
}
