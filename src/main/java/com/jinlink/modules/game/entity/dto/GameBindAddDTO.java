package com.jinlink.modules.game.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Schema(name = "GameBindAddDTO", description = "游戏绑键 添加 DTO 对象")
public class GameBindAddDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -5121364191638332034L;

    @Schema(description = "社区ID")
    private Long communityId;

    @Schema(description = "cfg配置")
    private String cfg;
}
