package com.jinlink.modules.game.entity.dto;

import com.jinlink.core.domain.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * 游戏 修改 DTO 对象
 */

@Getter
@Setter
@Schema(name = "GameGameUpdateDTO", description = "游戏 修改 DTO 对象")
public class GameGameUpdateDTO extends BaseVO {

    @Serial
    private static final long serialVersionUID = 3142177789879843872L;

    @Schema(description = "游戏名称")
    private String gameName;
}
