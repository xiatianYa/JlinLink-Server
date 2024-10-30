package com.jinlink.modules.system.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jinlink.common.domain.BaseEntity;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.util.List;
import java.util.Map;

/**
 * 菜单管理 实体类。
 *
 * @author Summer
 * @since 1.0.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "sys_menu")
public class SysMenu extends BaseEntity {

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
    private String hideInMenu;

    /**
     * 是否为常量路由(Y:是,N:否)
     */
    private String constant;

    /**
     * 外部链接
     */
    private String href;

    /**
     * 排序值
     */
    private Integer order;

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
    private String activeMenu;

    /**
     * 路由查询参数
     */
    private String query;

    /**
     * 按键查询参数
     */
    private List<Map<String,String>> buttons;

    /**
     * 是否启用(0:禁用,1:启用)
     */
    private String status;

}
