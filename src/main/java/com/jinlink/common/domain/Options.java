package com.jinlink.common.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 前端 Options 对象
 */

@Data
@Builder
public class Options<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = -9064055350852836125L;

    @Schema(description = "显示的值")
    private String label;

    @Schema(description = "实际值")
    private transient T value;
}
