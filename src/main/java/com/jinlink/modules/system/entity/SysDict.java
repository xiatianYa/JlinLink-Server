package com.jinlink.modules.system.entity;

import com.jinlink.core.domain.BaseEntity;
import com.mybatisflex.annotation.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 数据字典管理 实体类。
 *
 * @author Summer
 * @since 1.0.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "sys_dict")
public class SysDict extends BaseEntity {

    /**
     * 字典名称
     */
    private String name;

    /**
     * 字典编码
     */
    private String code;

    /**
     * 字典类型(1:系统字典,2:业务字典)
     */
    private String type;

    /**
     * 排序值
     */
    private Integer sort;

    /**
     * 字典描述
     */
    private String description;

    /**
     * 字典状态
     */
    private String status;
}
