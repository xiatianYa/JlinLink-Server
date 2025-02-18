package com.jinlink.modules.game.entity;

import com.jinlink.core.domain.BaseEntity;
import com.mybatisflex.annotation.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 地图攻略表 实体类。
 *
 * @author Summer
 * @since 1.0.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "game_map_strategy")
public class GameMapStrategy extends BaseEntity {

    /**
     * 攻略名称
     */
    private String title;

    /**
     * 地图名称
     */
    private Long mapId;

    /**
     * 攻略内容
     */
    private String content;

    /**
     * 攻略视频路径
     */
    private String videoUrl;

    /**
     * 审核状态
     */
    private Integer status;

}
