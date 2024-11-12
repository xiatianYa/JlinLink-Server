package com.jinlink.modules.websocket.entity.dto;

import com.jinlink.core.domain.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 服务器 查询  DTO 对象
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "ServerSearchDto", description = "服务器 查询  DTO 对象")
public class ServerSearchDto extends BaseVO {
    @Schema(description = "服务器IP")
    private String ip;
    @Schema(description = "服务器端口")
    private Integer port;
    @Schema(description = "最小在线数")
    private Integer minPlayers;
    @Schema(description = "返回码")
    private String code;
    @Schema(description = "返回信息")
    private String message;
}
