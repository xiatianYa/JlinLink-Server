package com.jinlink.modules.bot.entity;

import com.jinlink.core.domain.BaseEntity;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
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
 * 地图订阅表 实体类。
 *
 * @author Summer
 * @since 1.0.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "bot_map_order")
public class BotMapOrder extends BaseEntity {

    /**
     * 群友QQ号
     */
    private Long qqId;

    /**
     * 订阅地图列表
     */
    private String orderJson;

}
