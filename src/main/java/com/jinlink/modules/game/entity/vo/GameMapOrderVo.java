package com.jinlink.modules.game.entity.vo;

import com.jinlink.core.domain.BaseVO;
import com.jinlink.modules.game.entity.dto.OrderTimeDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * 地图订阅 VO 对象
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "GameMapOrderVo", description = "地图订阅 VO 对象")
public class GameMapOrderVo extends BaseVO {
    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "地图对象")
    private GameMapVo gameMapVo;

    @Schema(description = "通知记录")
    private List<OrderTimeDTO> orderTimes;
}
