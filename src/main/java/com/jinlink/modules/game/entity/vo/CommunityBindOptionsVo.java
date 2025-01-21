package com.jinlink.modules.game.entity.vo;

import com.jinlink.common.domain.BTPairs;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * 绑键 VO 对象
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "CommunityOptions", description = "社区绑键列表 VO 对象")
public class CommunityBindOptionsVo {

    /**
     * 社区Id
     */
    private Long communityId;

    /**
     * 社区名称
     */
    private String communityName;

    /**
     * 按键列表
     */
    private List<BTPairs> options;
}
