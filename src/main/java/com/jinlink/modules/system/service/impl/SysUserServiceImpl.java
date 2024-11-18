package com.jinlink.modules.system.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.servlet.JakartaServletUtil;
import com.alibaba.fastjson2.JSONObject;
import com.jinlink.common.constants.Constants;
import com.jinlink.common.constants.RequestConstant;
import com.jinlink.common.domain.LoginUser;
import com.jinlink.common.domain.Options;
import com.jinlink.common.exception.JinLinkException;
import com.jinlink.core.page.PageQuery;
import com.jinlink.common.pool.StringPools;
import com.jinlink.common.util.IPUtil;
import com.jinlink.core.util.ServletHolderUtil;
import com.jinlink.modules.monitor.entity.MonLogsLogin;
import com.jinlink.modules.monitor.service.MonLogsLoginService;
import com.jinlink.modules.system.entity.SysRole;
import com.jinlink.modules.system.entity.SysUserRole;
import com.jinlink.modules.system.entity.bo.QQBo;
import com.jinlink.modules.system.entity.dto.LoginFormDTO;
import com.jinlink.modules.system.entity.dto.SysUserFormDTO;
import com.jinlink.modules.system.entity.dto.SysUserSearchDTO;
import com.jinlink.modules.system.entity.dto.oAuthLoginDTO;
import com.jinlink.modules.system.entity.vo.SysUserVo;
import com.jinlink.modules.system.service.SysRoleService;
import com.jinlink.modules.system.service.SysUserRoleService;
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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
    private SysRoleService sysRoleService;
    @Resource
    private SysUserRoleService sysUserRoleService;
    @Resource
    private MonLogsLoginService monLogsLoginService;

    /**
     * 用户登录
     */
    @Override
    public Map<String, String> userNameLogin(LoginFormDTO loginFormDTO) {
        //查询当前用户
        SysUser userForUserName = sysUserMapper.getUserByUserName(loginFormDTO.getUserName());
        MonLogsLogin loginLogs = initLoginLog(loginFormDTO);;
        try {
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
            // saToken 进行登录
            StpUtil.login(userForUserName.getId());
            // 更新用户登录时间
            userForUserName.setLastLoginTime(LocalDateTime.now());
            saveUserToSession(userForUserName, false);
            // 操作用户登录日志
            loginLogs.setUserId(userForUserName.getId());
            super.updateById(userForUserName);
        }catch (Exception e){
            loginLogs.setStatus(StringPools.ZERO);
            loginLogs.setMessage(e.getMessage());
            throw new JinLinkException("登录失败"+e.getMessage());
        }finally {
            monLogsLoginService.save(loginLogs);
        }
        return Map.of("token", StpUtil.getTokenValue(),"refreshToken", StpUtil.getTokenValue());
    }

    private void saveUserToSession(SysUser sysUser, boolean needCheck) {
        if (needCheck) {
            sysUser = super.getById(sysUser.getId());
        }
        // 用户转换
        LoginUser loginUser = BeanUtil.copyProperties(sysUser, LoginUser.class);
        // 获取用户角色
        List<Long> userRoleIds = sysUserRoleService.getUserRoleIds(loginUser.getId());
        List<String> userRoleNames = sysUserRoleService.getUserRoleCodes(loginUser.getId());
        loginUser.setRoleIds(userRoleIds);
        loginUser.setRoleCodes(userRoleNames);
        // Session 放入用户对象
        StpUtil.getSessionByLoginId(sysUser.getId()).set("user", loginUser);
    }

    /**
     * 用户分页
     */
    @Override
    public Page<SysUserVo> listUserPage(PageQuery query, SysUserSearchDTO sysUserSearchDTO) {
        //查询所有角色
        List<SysRole> sysRoles = sysRoleService.list();
        //返回数据列表
        List<SysUserVo> sysUserVos = new ArrayList<>();
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
            SysUserVo sysUserVo = BeanUtil.copyProperties(item, SysUserVo.class);
            sysUserVo.setUserRoles(new ArrayList<>());
            QueryWrapper sysUserRoleQuery = new QueryWrapper();
            sysUserRoleQuery.eq("user_id", item.getId());
            List<SysUserRole> sysUserRoles = sysUserRoleService.list(sysUserRoleQuery);
            sysUserRoles.forEach(userRole->{
                for (SysRole role : sysRoles) {
                    if (role.getId().equals(userRole.getRoleId())) {
                        sysUserVo.getUserRoles().add(role.getRoleCode());
                        break;
                    }
                }
            });
            sysUserVos.add(sysUserVo);
        });
        return new Page<>(sysUserVos, paginate.getPageNumber(), paginate.getPageSize(), sysUserVos.size());
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
                    sysUserRoleService.remove(userRoleDeleteQuery)
            );
            //遍历角色 添加新角色
            userRoles.forEach(item->{
                //查询出角色
                QueryWrapper roleQuery = new QueryWrapper();
                roleQuery.eq("role_code",item);
                SysRole sysRole = sysRoleService.getOne(roleQuery);
                if(ObjectUtil.isNotNull(sysRole)){
                    //添加角色
                    SysUserRole sysUserRole = new SysUserRole();
                    sysUserRole.setUserId(sysUser.getId());
                    sysUserRole.setRoleId(sysRole.getId());
                    sysUserRoleService.save(sysUserRole);
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
                SysRole sysRole = sysRoleService.getOne(roleQuery);
                //添加角色
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setUserId(sysUser.getId());
                sysUserRole.setRoleId(sysRole.getId());
                sysUserRoleService.save(sysUserRole);
            });
            return true;
        }
    }

    @Override
    public List<Options<String>> getAllUserNames() {
        List<SysUser> sysUsers = sysUserMapper.selectAll();
        return sysUsers.stream()
                .map(item -> Options.<String>builder()
                        .label(item.getNickName())
                        .value(String.valueOf(item.getId()))
                        .build())
                .toList();
    }

    @Override
    public Map<String, String> userOAuthLogin(oAuthLoginDTO loginFormDTO) {
        //第三方用户对象
        QQBo qqBo = null;
        //当用户form为空
        if (ObjectUtil.isNull(loginFormDTO.getAccessToken()) || ObjectUtil.isNull(loginFormDTO.getType()) || ObjectUtil.isNull(loginFormDTO.getOpenId())){
            throw new JinLinkException("非法参数!");
        }
        try {
            URL url = new URL("https://graph.qq.com/user/get_user_info?oauth_consumer_key=102129326&access_token="+loginFormDTO.getAccessToken()+"&openid="+loginFormDTO.getOpenId());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // 读取响应
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    qqBo= JSONObject.parseObject(line, QQBo.class);
                }
            }
            // 关闭连接
            conn.disconnect();
            if (ObjectUtil.isNull(qqBo)){
                throw new Exception();
            }
        } catch (Exception e) {
            throw new JinLinkException("获取第三方用户信息失败");
        }
        //通过用户OpenId 判断用户是否注册过
        SysUser one = sysUserMapper.selectOneByQuery(new QueryWrapper().eq("qq_open_id", loginFormDTO.getOpenId()));
        MonLogsLogin loginLogs = initRegisterLog(loginFormDTO);;
        try {
            //用户不存在 则进行注册
            if (ObjectUtil.isNull(one)) {
                // 注册用户信息
                SysUser sysUser = SysUser.builder()
                        .userName(loginFormDTO.getOpenId())
                        .qqOpenId(loginFormDTO.getOpenId())
                        .nickName(qqBo.getNickname())
                        .avatar(ObjectUtil.isNotNull(qqBo.getFigureurl_qq_2()) ? qqBo.getFigureurl_qq_2() : qqBo.getFigureurl_qq_1())
                        .password(DigestUtils.sha256Hex(qqBo.getNickname() + "VECaJx"))
                        .lastLoginTime(LocalDateTime.now())
                        .build();
                //新建用户
                sysUserMapper.insert(sysUser);
                //为用户分配角色
                for (Long roleId : Constants.USER_DEFAULT_ROLE_LIST) {
                    SysUserRole sysUserRole = SysUserRole.builder()
                            .userId(sysUser.getId())
                            .roleId(roleId)
                            .build();
                    sysUserRoleService.save(sysUserRole);
                }
                // saToken 进行登录
                StpUtil.login(sysUser.getId());
                // 操作用户登录日志
                loginLogs.setUserId(sysUser.getId());
                saveUserToSession(sysUser, false);
                return Map.of("token", StpUtil.getTokenValue(),"refreshToken", StpUtil.getTokenValue());
            }
            //当前用户存在 则返回token
            StpUtil.login(one.getId());
            // 操作用户登录日志
            loginLogs.setUserId(one.getId());
            //保存用户信息
            saveUserToSession(one, false);
            return Map.of("token", StpUtil.getTokenValue(),"refreshToken", StpUtil.getTokenValue());
        }catch (Exception e){
            loginLogs.setStatus(StringPools.ZERO);
            loginLogs.setMessage(e.getMessage());
            throw new JinLinkException("登录失败"+e.getMessage());
        }finally {
            monLogsLoginService.save(loginLogs);
        }
    }

    /**
     * 初始化注册日志
     *
     * @param loginFormDTO 用户对象
     * @return {@linkplain MonLogsLogin} 登录日志对象
     */
    private MonLogsLogin initRegisterLog(oAuthLoginDTO loginFormDTO) {
        String ip = JakartaServletUtil.getClientIP(ServletHolderUtil.getRequest());
        return MonLogsLogin.builder()
                .userName(loginFormDTO.getOpenId())
                .status(StringPools.ONE)
                .userAgent(ServletHolderUtil.getRequest().getHeader(RequestConstant.USER_AGENT))
                .ip(ip)
                .ipAddr(IPUtil.getIpAddr(ip))
                .message("注册成功")
                .build();
    }

    /**
     * 初始化登录日志
     *
     * @param loginFormDTO 用户对象
     * @return {@linkplain MonLogsLogin} 登录日志对象
     */
    private MonLogsLogin initLoginLog(LoginFormDTO loginFormDTO) {
        String ip = JakartaServletUtil.getClientIP(ServletHolderUtil.getRequest());
        return MonLogsLogin.builder()
                .userName(loginFormDTO.getUserName())
                .status(StringPools.ONE)
                .userAgent(ServletHolderUtil.getRequest().getHeader(RequestConstant.USER_AGENT))
                .ip(ip)
                .ipAddr(IPUtil.getIpAddr(ip))
                .message("登陆成功")
                .build();
    }
}
