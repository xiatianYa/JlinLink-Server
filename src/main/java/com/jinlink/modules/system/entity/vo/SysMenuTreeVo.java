package com.jinlink.modules.system.entity.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jinlink.common.domain.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * 菜单管理列表 VO 对象
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "SysMenuPageVO", description = "菜单管理列表 VO 对象")
public class SysMenuTreeVo extends BaseVO {

    @Schema(description = "label")
    private String label;

    @Schema(description = "pid")
    private String pid;

    @Schema(description = "sort")
    private String sort;

    @Schema(description = "子对象")
    private List<SysMenuTreeVo> children;
}
