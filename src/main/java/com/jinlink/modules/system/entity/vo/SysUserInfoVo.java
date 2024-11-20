package com.jinlink.modules.system.entity.vo;

import com.jinlink.core.domain.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * 用户信息 VO 展示类
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "SysUserInfoVo", description = "用户信息 VO 对象")
public class SysUserInfoVo extends BaseVO {

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
     * 用户头像
     */
    @Schema(description = "用户头像")
    private String avatar;
    /**
     * 用户权限列表
     */
    @Schema(description = "用户权限列表")
    private List<String> roles;
    /**
     * 用户权限按钮列表
     */
    @Schema(description = "用户权限按钮列表")
    private List<String> buttons;
}
