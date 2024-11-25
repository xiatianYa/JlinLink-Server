package com.jinlink.modules.game.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 游戏 添加 DTO 对象
 */

@Getter
@Setter
@Schema(name = "GameGameAddDTO", description = "游戏 添加 DTO 对象")
public class GameGameAddDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 3142177789879843872L;

    @Schema(description = "游戏名称")
    private String gameName;
}
