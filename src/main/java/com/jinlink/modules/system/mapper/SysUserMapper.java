package com.jinlink.modules.system.mapper;

import com.mybatisflex.core.BaseMapper;
import com.jinlink.modules.system.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户管理 映射层。
 *
 * @author Summer
 * @since 1.0.0
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    SysUser getUserByUserName(String userName);
}
