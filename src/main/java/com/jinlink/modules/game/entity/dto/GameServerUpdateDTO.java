package com.jinlink.modules.game.entity.dto;

import com.jinlink.core.domain.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "GameServerUpdateDTO", description = "游戏服务器 修改 DTO 对象")
public class GameServerUpdateDTO extends BaseVO {

    @Schema(description = "服务器名称")
    private String serverName;

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

    @Schema(description = "排序值")
    private Integer sort;

    @Schema(description = "排序值")
    private Integer isStatistics;
}
