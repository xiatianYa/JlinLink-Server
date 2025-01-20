package com.jinlink.modules.game.entity.dto;

import com.jinlink.core.domain.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 游戏绑键 修改 DTO 对象
 */

@Getter
@Setter
@Schema(name = "GameBindUpdateDTO", description = "游戏绑键 修改 DTO 对象")
public class GameBindUpdateDTO extends BaseVO {

    @Schema(description = "社区ID")
    private Long communityId;

    @Schema(description = "cfg配置")
    private String cfg;

}
