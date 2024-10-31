package com.jinlink.common.domain;

import com.mybatisflex.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 通用的实体类
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity implements Serializable{

    @Id(keyType = KeyType.Auto)
    @Schema(description = "ID")
    private Long id;

    @Schema(description = "创建用户名称")
    private Long createUserId;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新用户名称")
    private Long updateUserId;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "是否删除(0:否,1:是)")
    @Column(isLogicDelete = true)
    private Boolean isDeleted;
}
