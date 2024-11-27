package com.jinlink.modules.bot.entity.vo;

import com.jinlink.core.domain.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 群 VO 对象
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "BotGroupVo", description = "群 VO 对象")
public class BotGroupVo extends BaseVO {
    @Schema(description = "群号")
    private Long groupId;

    @Schema(description = "是否允许查询服务器")
    private String isServer;

    @Schema(description = "是否允许管控")
    private String isProhibit;

    @Schema(description = "是否允许娶群友")
    private String isMarry;

    @Schema(description = "是否启用(0:禁用,1:启用)")
    private String status;
}
