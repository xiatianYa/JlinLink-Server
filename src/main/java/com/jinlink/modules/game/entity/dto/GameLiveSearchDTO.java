package com.jinlink.modules.game.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 游戏直播 查询 DTO 对象
 */

@Getter
@Setter
@Schema(name = "GameLiveSearchDTO", description = "游戏直播 查询 DTO 对象")
public class GameLiveSearchDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -5121364191638332034L;

    @Schema(description = "uid")
    private Long uid;
}
