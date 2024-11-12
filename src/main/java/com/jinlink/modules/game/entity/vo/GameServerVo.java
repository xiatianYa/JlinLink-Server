package com.jinlink.modules.game.entity.vo;

import com.jinlink.core.domain.BaseVO;
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
public class GameServerVo extends BaseVO {

    @Schema(description = "服务器名称")
    private String serverName;

    @Schema(description = "社区ID")
    private String communityId;

    @Schema(description = "社区名称")
    private String communityName;

    @Schema(description = "模式ID")
    private String modeId;

    @Schema(description = "模式名称")
    private String modeName;

    @Schema(description = "游戏ID")
    private String gameId;

    @Schema(description = "游戏名称")
    private String gameName;

    @Schema(description = "服务器IP")
    private String ip;

    @Schema(description = "服务器端口")
    private String port;

    @Schema(description = "排序值")
    private Integer sort;
}
