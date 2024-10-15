package com.jinlink.modules.system.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SysPermissionVo {
    //id
    private Long id;
    //描述
    private String label;
    //按钮编码
    private String code;
}
