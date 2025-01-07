package com.jinlink.modules.game.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 游戏地图 添加 DTO 对象
 */

@Getter
@Setter
@Schema(name = "GameModelAddDTO", description = "游戏模型 添加 DTO 对象")
public class GameModelAddDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 3142177789879843872L;

    @Schema(description = "模型名称")
    private String modelName;

    @Schema(description = "模型类型")
    private String modelType;

    @Schema(description = "模型图片")
    private String modelUrl;

    @Schema(description = "模型地址")
    private String modelModeUrl;
}
