package com.jinlink.modules.game.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 游戏服务器 查询 DTO 对象
 */

@Getter
@Setter
@Schema(name = "GameServerSearchDTO", description = "游戏服务器 查询 DTO 对象")
public class GameServerSearchDTO {

    @Schema(description = "社区ID")
    private Long communityId;

    @Schema(description = "模式ID")
    private Long modeId;

    @Schema(description = "游戏ID")
    private Long gameId;

}
