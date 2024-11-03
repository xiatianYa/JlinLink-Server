package com.jinlink.modules.system.entity.bo;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 数据字典子项 Options 业务处理对象
 *
 */
@Data
@Builder
public class SysDictItemOptions implements Serializable {

    @Serial
    private static final long serialVersionUID = -8807424818908992565L;

    /**
     * 显示的值
     */
    private String label;

    /**
     * 值
     */
    private String value;

    /**
     * 类型(前端渲染类型)
     */
    private String type;

    /**
     * 排序
     */
    private Integer sort;

}
