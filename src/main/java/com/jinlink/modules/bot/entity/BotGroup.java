package com.jinlink.modules.bot.entity;

import com.jinlink.core.domain.BaseEntity;
import com.mybatisflex.annotation.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 入驻群管理 实体类。
 *
 * @author Summer
 * @since 1.0.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "bot_group")
public class BotGroup extends BaseEntity {

    /**
     * 群号
     */
    private Long groupId;

    /**
     * 是否允许查询服务器
     */
    private String isServer;

    /**
     * 是否允许管控
     */
    private String isProhibit;

    /**
     * 是否允许娶群友
     */
    private String isMarry;

    /**
     * 是否启用(0:禁用,1:启用)
     */
    private String status;

}
