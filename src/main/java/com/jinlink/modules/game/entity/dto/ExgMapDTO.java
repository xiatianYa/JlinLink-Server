package com.jinlink.modules.game.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 社区地图CD 查询 DTO 对象
 */

@Getter
@Setter
@Schema(name = "ExgMapDTO", description = "社区地图CD 查询 DTO 对象")
public class ExgMapDTO {
    // 地图名称
    private String Name;
    // 地图名称(翻译)
    private String CnName;
    // 地图名称(最后运行时间)
    private String LastRun;
    // 地图名称(地图冷却)
    private Integer CooldownMinute;
    // 冷却截至
    private String deadline;
    // 是否可预定
    private Boolean isOrder;
}
