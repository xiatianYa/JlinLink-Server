package com.jinlink.modules.bot.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 群 添加 DTO 对象
 */

@Getter
@Setter
@Schema(name = "BotGroupAddDTO", description = "群 添加 DTO 对象")
public class BotGroupAddDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 3142177789879843872L;

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
