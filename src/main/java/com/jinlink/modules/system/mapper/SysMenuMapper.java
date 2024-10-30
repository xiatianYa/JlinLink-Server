package com.jinlink.modules.system.mapper;

import com.mybatisflex.core.BaseMapper;
import com.jinlink.modules.system.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜单管理 映射层。
 *
 * @author Summer
 * @since 1.0.0
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

}
