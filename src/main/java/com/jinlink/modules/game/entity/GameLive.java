package com.jinlink.modules.game.entity;

import com.jinlink.core.domain.BaseEntity;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;

/**
 * 游戏直播表 实体类。
 *
 * @author Summer
 * @since 1.0.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "game_live")
public class GameLive extends BaseEntity {

    /**
     * 用户uid
     */
    private String uid;

    /**
     * 用户头像地址
     */
    private String avatar;

    /**
     * 背景地址
     */
    private String bgUrl;

    /**
     * OBS配置
     */
    private String obsOptions;

}
