package com.jinlink.modules.game.entity;

import com.jinlink.core.domain.BaseEntity;
import com.mybatisflex.annotation.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 游戏服务器表 实体类。
 *
 * @author Summer
 * @since 1.0.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "game_server")
public class GameServer extends BaseEntity {

    /**
     * 服务器名称
     */
    private String serverName;

    /**
     * 社区ID
     */
    private Long communityId;

    /**
     * 模式ID
     */
    private Long modeId;

    /**
     * 游戏ID
     */
    private Long gameId;

    /**
     * 服务器IP
     */
    private String ip;

    /**
     * 服务器端口
     */
    private String port;

    /**
     * 排序值
     */
    private Integer sort;

    /**
     * 是否统计
     */
    private Integer isStatistics;

    /**
     * 连接指令
     */
    private String connectStr;
}
