package com.jinlink.modules.system.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.jinlink.common.exception.JinLinkException;
import com.jinlink.common.page.PageQuery;
import com.jinlink.common.pool.StringPools;
import com.jinlink.modules.system.entity.SysRole;
import com.jinlink.modules.system.entity.SysUserRole;
import com.jinlink.modules.system.entity.dto.LoginFormDTO;
import com.jinlink.modules.system.entity.dto.SysUserFormDTO;
import com.jinlink.modules.system.entity.dto.SysUserSearchDTO;
import com.jinlink.modules.system.entity.vo.SysUserVO;
import com.jinlink.modules.system.mapper.SysRoleMapper;
import com.jinlink.modules.system.mapper.SysUserRoleMapper;
import com.mybatisflex.core.logicdelete.LogicDeleteManager;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.jinlink.modules.system.entity.SysUser;
import com.jinlink.modules.system.mapper.SysUserMapper;
import com.jinlink.modules.system.service.SysUserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户管理 服务层实现。
 *
 * @author Summer
 * @since 1.0.0
 */
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    /**
     * 用户登录
     */
    @Override
    public Map<String, String> userNameLogin(LoginFormDTO loginFormDTO) {
        SysUser userForUserName = sysUserMapper.getUserByUserName(loginFormDTO.getUserName());
        if (ObjectUtils.isEmpty(userForUserName)) {
            throw new JinLinkException("查找不到用户名 %s".formatted(loginFormDTO.getUserName()));
        }
        if (StringPools.ZERO.equals(userForUserName.getStatus())) {
            throw new JinLinkException("当前用户 %s 已被禁止登录".formatted(loginFormDTO.getUserName()));
        }
        // 密码拼接
        String inputPassword = loginFormDTO.getPassword() + userForUserName.getSalt();
        String s = DigestUtils.sha256Hex(inputPassword);
        String password = userForUserName.getPassword();
        // 密码比对
        if (!s.equals(password)) {
            throw new JinLinkException("登录失败，请核实用户名以及密码");
        }
        // sa token 进行登录
        StpUtil.login(userForUserName.getId());
        // 更新用户登录时间
        userForUserName.setLastLoginTime(LocalDateTime.now());
        super.updateById(userForUserName);
        return Map.of("token", StpUtil.getTokenValue(),"refreshToken", StpUtil.getTokenValue());
    }

    /**
     * 用户分页
     */
    @Override
    public Page<SysUserVO> listUserPage(PageQuery query, SysUserSearchDTO sysUserSearchDTO) {
        //查询所有角色
        List<SysRole> sysRoles = sysRoleMapper.selectAll();
        //返回数据列表
        List<SysUserVO> sysUserVOS = new ArrayList<>();
        //构建查询对象
        QueryWrapper sysUserQuery = new QueryWrapper();
        sysUserQuery.eq("user_name", sysUserSearchDTO.getUserName());
        sysUserQuery.like("nick_name", sysUserSearchDTO.getNickName());
        sysUserQuery.like("user_gender", sysUserSearchDTO.getUserGender());
        sysUserQuery.like("user_phone", sysUserSearchDTO.getPhone());
        sysUserQuery.like("user_email", sysUserSearchDTO.getUserEmail());
        sysUserQuery.eq("status", sysUserSearchDTO.getStatus());
        //查询分页数据
        Page<SysUser> paginate = sysUserMapper.paginate(query.getCurrent(), query.getSize(), sysUserQuery);
        //数据列表
        List<SysUser> records = paginate.getRecords();
        records.forEach(item->{
            SysUserVO sysUserVo = BeanUtil.copyProperties(item, SysUserVO.class);
            sysUserVo.setUserRoles(new ArrayList<>());
            QueryWrapper sysUserRoleQuery = new QueryWrapper();
            sysUserRoleQuery.eq("user_id", item.getId());
            List<SysUserRole> sysUserRoles = sysUserRoleMapper.selectListByQuery(sysUserRoleQuery);
            sysUserRoles.forEach(userRole->{
                for (SysRole role : sysRoles) {
                    if (role.getId().equals(userRole.getRoleId())) {
                        sysUserVo.getUserRoles().add(role.getRoleCode());
                        break;
                    }
                }
            });
            sysUserVOS.add(sysUserVo);
        });
        return new Page<>(sysUserVOS, paginate.getPageNumber(), paginate.getPageSize(), sysUserVOS.size());
    }

    /**
     * 删除多个用户
     */
    @Override
    public Boolean removeByIds(List<Long> ids) {
        int isTrue = sysUserMapper.deleteBatchByIds(ids);
        if (isTrue == 0) {
            throw new JinLinkException("删除失败!");
        }
        return true;
    }

    /**
     * 修改用户
     */
    @Override
    public Boolean updateUser(SysUserFormDTO sysUser) {
        int isTrue = sysUserMapper.update(sysUser);
        if (ObjectUtil.isNotNull(isTrue)){
            //更新用户权限列表
            List<String> userRoles = sysUser.getUserRoles();
            //删除当前用户角色
            QueryWrapper userRoleDeleteQuery = new QueryWrapper();
            userRoleDeleteQuery.eq("user_id",sysUser.getId());
            LogicDeleteManager.execWithoutLogicDelete(()->
                    sysUserRoleMapper.deleteByQuery(userRoleDeleteQuery)
            );
            //遍历角色 添加新角色
            userRoles.forEach(item->{
                //查询出角色
                QueryWrapper roleQuery = new QueryWrapper();
                roleQuery.eq("role_code",item);
                SysRole sysRole = sysRoleMapper.selectOneByQuery(roleQuery);
                if(ObjectUtil.isNotNull(sysRole)){
                    //添加角色
                    SysUserRole sysUserRole = new SysUserRole();
                    sysUserRole.setUserId(sysUser.getId());
                    sysUserRole.setRoleId(sysRole.getId());
                    sysUserRoleMapper.insert(sysUserRole);
                }
            });
            return true;
        }else{
            throw new JinLinkException("修改失败!");
        }
    }

    /**
     * 新增用户
     */
    @Override
    public Boolean saveUser(SysUserFormDTO sysUser) {
        //查询当前用户是否已存在
        SysUser userForUserName = sysUserMapper.getUserByUserName(sysUser.getUserName());
        if (ObjectUtil.isNotNull(userForUserName)) {
            throw new JinLinkException("用户已存在!");
        }else{
            sysUserMapper.insert(sysUser);
            //添加用户权限
            //用户权限列表
            List<String> userRoles = sysUser.getUserRoles();
            userRoles.forEach(item->{
                //查询出角色
                QueryWrapper roleQuery = new QueryWrapper();
                roleQuery.eq("role_code",item);
                SysRole sysRole = sysRoleMapper.selectOneByQuery(roleQuery);
                //添加角色
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setUserId(sysUser.getId());
                sysUserRole.setRoleId(sysRole.getId());
                sysUserRoleMapper.insert(sysUserRole);
            });
            return true;
        }
    }
}
