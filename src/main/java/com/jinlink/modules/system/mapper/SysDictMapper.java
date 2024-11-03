package com.jinlink.modules.system.mapper;

import com.mybatisflex.core.BaseMapper;
import com.jinlink.modules.system.entity.SysDict;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据字典管理 映射层。
 *
 * @author Summer
 * @since 1.0.0
 */
@Mapper
public interface SysDictMapper extends BaseMapper<SysDict> {

}
