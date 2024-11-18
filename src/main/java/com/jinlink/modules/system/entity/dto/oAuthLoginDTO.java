package com.jinlink.modules.system.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 第三方登陆 DTO 对象
 */

@Getter
@Setter
@Schema(name = "oAuthLoginDTO", description = "第三方登陆 DTO 对象")
public class oAuthLoginDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -1877337158312263515L;

    @Schema(description = "用户Token")
    private String accessToken;

    @Schema(description = "用户OpenId")
    private String openId;

    @Schema(description = "登陆类型")
    private Integer type;
}
