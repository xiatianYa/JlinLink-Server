package com.jinlink.modules.system.entity.vo;

import com.jinlink.common.domain.BTPairs;
import com.jinlink.common.domain.BaseVO;
import com.jinlink.common.domain.KVPairs;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.util.List;

/**
 * 菜单管理 VO 展示类
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "SysMenuVO", description = "菜单管理 VO 对象")
public class SysMenuVo extends BaseVO {

    @Schema(description = "父菜单ID")
    private Long parentId;

    @Schema(description = "菜单类型 1:目录 2:菜单")
    private String menuType;

    @Schema(description = "菜单名称")
    private String menuName;

    @Schema(description = "多语言标题")
    private String i18nKey;

    @Schema(description = "路由名称")
    private String routeName;

    @Schema(description = "路由路径")
    private String routePath;

    @Schema(description = "菜单图标")
    private String icon;

    @Schema(description = "图标类型 1:iconify icon 2:local icon")
    private String iconType;

    @Schema(description = "路由组件")
    private String component;

    @Schema(description = "缓存路由(Y:是,N:否)")
    private Boolean keepAlive;

    @Schema(description = "是否隐藏(Y:是,N:否)")
    private Boolean hideInMenu;

    @Schema(description = "是否为常量路由(Y:是,N:否)")
    private Boolean constant;

    @Schema(description = "外部链接")
    private String href;

    @Schema(description = "内嵌链接 Iframe URL")
    private String activeMenu;

    @Schema(description = "排序值")
    private Integer order;

    @Schema(description = "支持多标签(Y:是,N:否)")
    private Boolean multiTab;

    @Schema(description = "固定在页签中的序号")
    private Integer fixedIndexInTab;

    @Schema(description = "路由查询参数 JSON 字符串")
    private List<KVPairs> query;

    @Schema(description = "路由查询参数 JSON 字符串")
    private List<BTPairs> buttons;

    @Schema(description = "是否启用(0:禁用,1:启用)")
    private String status;

    @Schema(description = "子对象")
    private List<SysMenuVo> children;
}