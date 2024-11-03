package com.jinlink.modules.system.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 数据字典子项管理 删除 DTO 对象
 */

@Getter
@Setter
@Schema(name = "SysDictItemDeleteDTO", description = "数据字典子项管理 删除 DTO 对象")
public class SysDictItemDeleteDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 4518788057326824664L;

    @Schema(description = "IDs")
    private List<Long> ids;

}
