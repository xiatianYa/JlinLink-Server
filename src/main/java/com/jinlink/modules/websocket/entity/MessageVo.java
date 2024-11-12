package com.jinlink.modules.websocket.entity;

import com.jinlink.common.domain.LoginUser;
import com.jinlink.core.domain.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;

/**
 * websocket 消息  VO 对象
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "MessageVo", description = "websocket 消息  VO 对象")
public class MessageVo extends BaseVO {

    @Serial
    private static final long serialVersionUID = 5769107831011956328L;

    @Schema(description = "发送者")
    private LoginUser sendUser;

    @Schema(description = "接收者ID")
    private Long receiveId;

    @Schema(description = "返回码")
    private String code;

    @Schema(description = "消息体")
    private Object content;

    @Schema(description = "提示语")
    private String msg;
}
