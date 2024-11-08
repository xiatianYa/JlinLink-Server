package com.jinlink.modules.system.service;

import com.jinlink.core.page.PageQuery;
import com.jinlink.modules.system.entity.vo.SysUserRoleVo;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.jinlink.modules.system.entity.SysUserRole;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * 用户角色管理 服务层。
 *
 * @author Summer
 * @since 1.0.0
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    /**
     *删除用户角色根据ID
     */
    @Transactional
    Boolean removeRoleById(Serializable id);

    /**
     *删除用户角色根据ID多个
     */
    @Transactional
    Boolean removeRoleByIds(List<Long> ids);

    /**
     * 获取用户角色列表
     */
    List<String> getUserRoleCodes(Long id);

    /**
     * 分页查询用户角色管理。
     */
    Page<SysUserRoleVo> listSysUserRolePage(PageQuery query);

    /**
     * 获取用户角色Code列表
     */
    List<Long> getUserRoleIds(Long id);
}
