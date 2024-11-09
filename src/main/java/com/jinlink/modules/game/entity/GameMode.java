package com.jinlink.modules.game.entity;

import com.jinlink.core.domain.BaseEntity;
import com.mybatisflex.annotation.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 游戏模式表 实体类。
 *
 * @author Summer
 * @since 1.0.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "game_mode")
public class GameMode extends BaseEntity {

    /**
     * 社区名称
     */
    private String modeName;
}
