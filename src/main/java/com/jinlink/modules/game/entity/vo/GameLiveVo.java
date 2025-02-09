package com.jinlink.modules.game.entity.vo;

import com.alibaba.fastjson2.annotation.JSONField;
import com.jinlink.core.domain.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * 游戏直播 VO 对象
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "GameLiveVo", description = "游戏直播 VO 对象")
public class GameLiveVo extends BaseVO {

    @Schema(description = "用户uid")
    private String uid;

    @Schema(description = "用户头像地址")
    private String avatar;

    @Schema(description = "背景地址")
    private String bgUrl;

    @Schema(description = "OBS配置")
    private String obsOptions;

    @Schema(description = "主播Bili对象")
    private BiliVo biliVo;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "GameVo", description = "服务器对象")
    public static class BiliVo implements Serializable {
        @Schema(description = "直播间ID")
        @JSONField(name = "room_id")
        private Long roomId;
        @Schema(description = "uid")
        @JSONField(name = "uid")
        private Long uid;
        @Schema(description = "直播状态")
        @JSONField(name = "live_status")
        private Integer liveStatus;
        @Schema(description = "直播间网页url")
        @JSONField(name = "live_url")
        private String liveUrl;
        @Schema(description = "直播间标题")
        @JSONField(name = "title")
        private String title;
        @Schema(description = "直播间父分区名称")
        @JSONField(name = "parent_area_name")
        private String parentAreaName;
        @Schema(description = "直播间分区名称")
        @JSONField(name = "area_name")
        private String areaName;
        @Schema(description = "开播时间")
        @JSONField(name = "live_time")
        private String liveTime;
        @Schema(description = "在线人数")
        @JSONField(name = "online")
        private Long online;
        @Schema(description = "主播用户名")
        @JSONField(name = "uname")
        private String uname;
        @Schema(description = "直播间头像url")
        private String avatarUrl;
        @Schema(description = "直播间背景url")
        private String bgUrl;
    }
}
