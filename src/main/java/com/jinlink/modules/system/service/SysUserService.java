package com.jinlink.modules.system.service;

import com.jinlink.common.api.Result;
import com.jinlink.common.page.PageQuery;
import com.jinlink.modules.system.entity.dto.LoginFormDTO;
import com.jinlink.modules.system.entity.dto.SysUserFormDTO;
import com.jinlink.modules.system.entity.dto.SysUserSearchDTO;
import com.jinlink.modules.system.entity.vo.SysUserVO;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.jinlink.modules.system.entity.SysUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 用户管理 服务层。
 *
 * @author Summer
 * @since 1.0.0
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 用户登录
     */
    Map<String, String> userNameLogin(LoginFormDTO loginFormDTO);

    /**
     * 获取全部用户(分页)
     */
    Page<SysUserVO> listUserPage(PageQuery query, SysUserSearchDTO sysUserSearchDTO);

    /**
     * 删除用户多个
     */
    @Transactional
    Boolean removeByIds(List<Long> ids);

    /**
     * 修改用户
     */
    @Transactional
    Boolean updateUser(SysUserFormDTO sysUser);

    /**
     * 新增用户
     */
    @Transactional
    Boolean saveUser(SysUserFormDTO sysUser);
}
