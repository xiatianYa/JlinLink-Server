package com.jinlink.modules.system.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SysUserInfoVo implements Serializable {

    @Serial
    private static final long serialVersionUID = -6246685532247396266L;

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long userId;
    /**
     * 用户名称
     */
    @Schema(description = "用户名称")
    private String userName;
    /**
     * 用户权限列表
     */
    @Schema(description = "用户权限列表")
    private String[] roles;
    /**
     * 用户权限按钮列表
     */
    @Schema(description = "用户权限按钮列表")
    private String[] buttons;
}
