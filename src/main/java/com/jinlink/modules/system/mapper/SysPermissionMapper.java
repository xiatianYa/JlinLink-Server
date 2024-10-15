package com.jinlink.modules.system.mapper;

import com.mybatisflex.core.BaseMapper;
import com.jinlink.modules.system.entity.SysPermission;
import org.apache.ibatis.annotations.Mapper;

/**
 * 权限(按钮)管理 映射层。
 *
 * @author Summer
 * @since 1.0.0
 */
@Mapper
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

}
