package com.jinlink.modules.websocket.entity.vo;

import com.jinlink.common.domain.LoginUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * websocket 消息  VO 对象
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "GroupMessageVo", description = "Group 消息  VO 对象")
public class GroupMessageVo {

    @Schema(description = "发送者")
    private LoginUser sendUser;

    @Schema(description = "消息体")
    private String content;

    @Schema(description = "消息类型 1文字 2图片")
    private Integer type;

    @Schema(description = "返回码")
    private String code;
}
