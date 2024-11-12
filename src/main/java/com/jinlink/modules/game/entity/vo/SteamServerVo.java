package com.jinlink.modules.game.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * Steam服务器数据 VO 对象
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "SteamServerVo", description = "Steam服务器数据 VO 对象")
public class SteamServerVo {

    @Schema(description = "社区对象")
    private GameCommunityVo gameCommunityVo;

    @Schema(description = "在线人数")
    private Integer onlineCount;

    @Schema(description = "社区下服务器数据")
    private List<GameServerVo> gameServerVoList;
}
