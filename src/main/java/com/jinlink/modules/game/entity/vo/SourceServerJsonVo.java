package com.jinlink.modules.game.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
/**
 * 游戏服务器公开返回JSON类。
 *
 * @author Summer
 * @since 1.0.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SourceServerJsonVo {
    @Schema(description = "社区名称")
    private String communityName;

    @Schema(description = "社区在线玩家数量")
    private Long onLineUserNumber;

    @Schema(description = "社区服务器列表")
    private List<SteamServerVo> gameServerVoList;
}
