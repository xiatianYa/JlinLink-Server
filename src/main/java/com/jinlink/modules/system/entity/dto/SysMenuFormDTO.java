package com.jinlink.modules.system.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jinlink.common.domain.BaseEntity;
import com.jinlink.common.domain.KVPairs;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * 用户新增/修改 表单对象
 */

@Getter
@Setter
@Schema(name = "SysMenuFormDTO", description = "菜单新增/修改 DTO 对象")
public class SysMenuFormDTO extends BaseEntity {
    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 菜单类型 1:目录 2:菜单
     */
    private String menuType;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 多语言标题
     */
    private String i18nKey;

    /**
     * 路由名称
     */
    private String routeName;

    /**
     * 菜单路径
     */
    private String routePath;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 图标类型 1:iconify icon 2:local icon
     */
    private String iconType;

    /**
     * 路由组件
     */
    private String component;

    /**
     * 缓存页面(Y:是,N:否)
     */
    private String keepAlive;

    /**
     * 是否隐藏(Y:是,N:否)
     */
    private String hide;

    /**
     * 外部链接
     */
    private String href;

    /**
     * 排序值
     */
    private Integer sort;

    /**
     * 支持多标签(Y:是,N:否)
     */
    private String multiTab;

    /**
     * 固定在页签中的序号
     */
    private Integer fixedIndexInTab;

    /**
     * 内链URL
     */
    private String iframeUrl;

    /**
     * 是否启用(0:禁用,1:启用)
     */
    private String status;

    /**
     * 子菜单
     */
    private List<SysMenuFormDTO> children;
    /**
     * 路由查询参数
     */
    @Schema(description = "路由查询参数")
    private List<KVPairs> query;
    /**
     * 按钮查询参数
     */
    @Schema(description = "按钮查询参数")
    private List<KVPairs> buttons;
}
