package com.jinlink.modules.game.entity.vo;

import com.jinlink.modules.game.entity.GameCommunity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * 游戏服务器实体类。
 *
 * @author Summer
 * @since 1.0.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SourceServerVo {

    @Schema(description = "社区对象")
    private GameCommunity gameCommunity;

    @Schema(description = "社区在线玩家数量")
    private Long onLineUserNumber;

    @Schema(description = "社区服务器列表")
    private List<SteamServerVo> gameServerVoList;
}
