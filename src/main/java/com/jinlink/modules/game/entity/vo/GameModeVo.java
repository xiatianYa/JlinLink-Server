package com.jinlink.modules.game.entity.vo;

import com.jinlink.core.domain.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 游戏模式 VO 对象
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "GameModeVo", description = "游戏模式 VO 对象")
public class GameModeVo extends BaseVO {

    @Schema(description = "游戏模式名称")
    private String modeName;
}
