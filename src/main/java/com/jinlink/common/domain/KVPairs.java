package com.jinlink.common.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 键值对
 */

@Data
@Builder
@AllArgsConstructor
public class KVPairs implements Serializable {

    @Serial
    private static final long serialVersionUID = -8443706976095226911L;

    @Schema(description = "键")
    private String key;

    @Schema(description = "值")
    private String value;
}
