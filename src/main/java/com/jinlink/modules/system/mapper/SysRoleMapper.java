package com.jinlink.modules.system.mapper;

import com.jinlink.modules.system.entity.vo.SysRoleOptionVO;
import com.mybatisflex.core.BaseMapper;
import com.jinlink.modules.system.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色管理 映射层。
 *
 * @author Summer
 * @since 1.0.0
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<SysRoleOptionVO> getRoleAll();
}
