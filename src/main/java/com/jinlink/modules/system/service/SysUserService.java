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

    Map<String, String> userNameLogin(LoginFormDTO loginFormDTO);

    Page<SysUserVO> listUserPage(PageQuery query, SysUserSearchDTO sysUserSearchDTO);

    @Transactional
    Result<Boolean> removeByIds(List<Long> ids);

    @Transactional
    Result<String> updateUser(SysUserFormDTO sysUser);

    @Transactional
    Result<String> saveUser(SysUserFormDTO sysUser);
}
