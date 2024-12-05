package com.jinlink.modules.system.entity;

import com.jinlink.core.domain.BaseEntity;
import com.mybatisflex.annotation.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 意见反馈表 实体类。
 *
 * @author Summer
 * @since 1.0.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "sys_feedback")
public class SysFeedback extends BaseEntity{

    /**
     * 意见反馈
     */
    private String content;

    /**
     * 反馈图片
     */
    private String image;

    /**
     * 反馈类型
     */
    private Integer type;

    /**
     * 处理状态
     */
    private Integer status;

}
