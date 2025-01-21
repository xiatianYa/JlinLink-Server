package com.jinlink.modules.game.entity.vo;

import com.jinlink.core.domain.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 绑键 VO 对象
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "GameBindVo", description = "绑键 VO 对象")
public class GameBindVo extends BaseVO {

    @Schema(description = "社区ID")
    private Long communityId;

    @Schema(description = "cfg配置")
    private String cfg;

}
