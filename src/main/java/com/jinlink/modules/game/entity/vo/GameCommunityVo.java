package com.jinlink.modules.game.entity.vo;

import com.jinlink.core.domain.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 游戏社区 VO 对象
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "GameCommunityVo", description = "游戏社区 VO 对象")
public class GameCommunityVo extends BaseVO {


    @Schema(description = "社区名称")
    private String communityName;

    @Schema(description = "图标")
    private String logo;

    @Schema(description = "网站")
    private String website;

    @Schema(description = "按键列表")
    private String bind;

    @Schema(description = "是否缓存")
    private String isCache;
}
