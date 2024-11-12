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
 * 游戏地图表 实体类。
 *
 * @author Summer
 * @since 1.0.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "game_map")
public class GameMap extends BaseEntity {

    /**
     * 地图名称
     */
    private String mapName;

    /**
     * 译名
     */
    private String mapLabel;

    /**
     * 图片路径
     */
    private String mapUrl;

    /**
     * 模式ID
     */
    private Long modeId;

    /**
     * 地图难度
     */
    private Integer type;

    /**
     * 地图标签
     */
    private String tag;

    /**
     * 地图神器
     */
    private String artifact;

}
