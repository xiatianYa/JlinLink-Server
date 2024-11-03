package com.jinlink.modules.system.entity;

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

/**
 * 数据字典子项管理 实体类。
 *
 * @author Summer
 * @since 1.0.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "sys_dict_item")
public class SysDictItem extends BaseEntity {

    /**
     * 父字典ID
     */
    private Long dictId;

    /**
     * 父字典编码
     */
    private String dictCode;

    /**
     * 数据值
     */
    private String value;

    /**
     * 中文名称
     */
    private String zhCn;

    /**
     * 英文名称
     */
    private String enUs;

    /**
     * 类型(前端渲染类型)
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
