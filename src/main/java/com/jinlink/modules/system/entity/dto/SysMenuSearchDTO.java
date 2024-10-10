package com.jinlink.modules.system.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 菜单管理 查询 DTO 对象
 */

@Getter
@Setter
@Schema(name = "SysMenuSearchDTO", description = "菜单管理 查询 DTO 对象")
public class SysMenuSearchDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 3442419758076585181L;
}