package com.jinlink.modules.game.entity;

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
 * 在线玩家统计表 实体类。
 *
 * @author Summer
 * @since 1.0.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "game_online_statistics")
public class GameOnlineStatistics extends BaseEntity {

    /**
     * 社区id
     */
    private Long communityId;

    /**
     * 社区在线人数
     */
    private Long communityPlay;
}
