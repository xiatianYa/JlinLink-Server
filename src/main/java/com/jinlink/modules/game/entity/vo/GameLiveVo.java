package com.jinlink.modules.game.entity.vo;

import com.jinlink.core.domain.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 游戏直播 VO 对象
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "GameLiveVo", description = "游戏直播 VO 对象")
public class GameLiveVo extends BaseVO {

    @Schema(description = "用户uid")
    private String uid;

    @Schema(description = "用户头像地址")
    private String avatar;

    @Schema(description = "背景地址")
    private String bgUrl;
}
