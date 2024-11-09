package com.jinlink.modules.game.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 游戏服务器 VO 对象
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "GameServerVo", description = "游戏服务器 VO 对象")
public class GameServerVo {

    @Schema(description = "社区ID")
    private Long communityId;

    @Schema(description = "模式ID")
    private Long modeId;

    @Schema(description = "游戏ID")
    private Long gameId;

    @Schema(description = "服务器IP")
    private String ip;

    @Schema(description = "服务器端口")
    private String port;
}
