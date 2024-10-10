package com.jinlink.common.domain;

import com.mybatisflex.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 通用的实体类
 * @Auth summer
 * @createTime 2024/09/26;
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity implements Serializable{
    /**
     * ID
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 创建用户ID
     */
    private Long createUserId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新用户ID
     */
    private Long updateUserId;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否删除(0:否,1:是)
     */
    @Column(isLogicDelete = true)
    private Boolean isDeleted;
}
