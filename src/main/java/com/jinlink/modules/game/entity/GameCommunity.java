package com.jinlink.modules.game.entity;

import com.jinlink.core.domain.BaseEntity;
import com.mybatisflex.annotation.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 游戏社区表 实体类。
 *
 * @author Summer
 * @since 1.0.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "game_community")
public class GameCommunity extends BaseEntity {

    /**
     * 社区名称
     */
    private String communityName;

    /**
     * 图标
     */
    private String logo;

    /**
     * 网站
     */
    private String website;

    /**
     * 按键列表
     */
    private String bind;

    /**
     * 是否缓存
     */
    private String isCache;


}
