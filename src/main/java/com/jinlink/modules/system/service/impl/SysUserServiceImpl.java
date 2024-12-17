package com.jinlink.modules.system.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.servlet.JakartaServletUtil;
import com.alibaba.fastjson2.JSONObject;
import com.jinlink.common.constants.Constants;
import com.jinlink.common.constants.RequestConstant;
import com.jinlink.common.domain.LoginUser;
import com.jinlink.common.domain.Options;
import com.jinlink.common.exception.JinLinkException;
import com.jinlink.common.util.StringUtils;
import com.jinlink.core.config.redis.service.RedisService;
import com.jinlink.core.page.PageQuery;
import com.jinlink.common.pool.StringPools;
import com.jinlink.common.util.IPUtils;
import com.jinlink.core.util.ServletHolderUtil;
import com.jinlink.modules.monitor.entity.MonLogsLogin;
import com.jinlink.modules.monitor.service.MonLogsLoginService;
import com.jinlink.modules.system.entity.SysRole;
import com.jinlink.modules.system.entity.SysUserRole;
import com.jinlink.modules.system.entity.bo.QQBo;
import com.jinlink.modules.system.entity.dto.*;
import com.jinlink.modules.system.entity.vo.SysUserInfoVo;
import com.jinlink.modules.system.entity.vo.SysUserVo;
import com.jinlink.modules.system.service.SysRolePermissionService;
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
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
    @Resource
    private SysRolePermissionService sysRolePermissionService;
    @Resource
    private RedisService redisService;

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
            throw new JinLinkException(e.getMessage());
        }finally {
            try {
                monLogsLoginService.save(loginLogs);
            }catch (Exception e){
                log.error("登录日志记录失败：{}", e.getMessage());
            }
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
        return new Page<>(sysUserVos, paginate.getPageNumber(), paginate.getPageSize(), paginate.getTotalRow());
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

    /**
     * 获取所有用户昵称
     */
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

    /**
     * 第三方登录
     */
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
            // 读取响应，并指定InputStreamReader使用UTF-8编码
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                qqBo = JSONObject.parseObject(response.toString(), QQBo.class);
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
        if (ObjectUtil.isNotNull(one) && StringPools.ZERO.equals(one.getStatus())) {
            throw new JinLinkException("当前用户 %s 已被禁止登录".formatted(one.getUserName()));
        }
        try {
            //用户不存在 则进行注册
            if (ObjectUtil.isNull(one)) {
                // 注册用户信息
                SysUser sysUser = SysUser.builder()
                        .userName(loginFormDTO.getOpenId())
                        .qqOpenId(loginFormDTO.getOpenId())
                        .nickName(qqBo.getNickname())
                        .userGender("1")
                        .salt("VECaJx")
                        .status("1")
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
                saveUserToSession(sysUser, false);
                return Map.of("token", StpUtil.getTokenValue(),"refreshToken", StpUtil.getTokenValue());
            }
            //当前用户存在 则返回token
            StpUtil.login(one.getId());
            //保存用户信息
            saveUserToSession(one, false);
            return Map.of("token", StpUtil.getTokenValue(),"refreshToken", StpUtil.getTokenValue());
        }catch (Exception e){
            throw new JinLinkException("登录失败"+e.getMessage());
        }
    }

    /**
     * 获取用户基本信息
     */
    @Override
    public SysUserInfoVo getUserInfo() {
        long loginIdAsLong = StpUtil.getLoginIdAsLong();
        if (ObjectUtil.isNull(loginIdAsLong)){
            throw new JinLinkException("用户未登录!");
        }
        SysUser sysUser = sysUserMapper.selectOneById(loginIdAsLong);
        if (ObjectUtil.isNull(sysUser)){
            throw new JinLinkException("用户不存在!");
        }
        SysUserInfoVo sysUserInfoVo = new SysUserInfoVo();
        sysUserInfoVo.setUserId(sysUser.getId());
        sysUserInfoVo.setUserName(sysUser.getNickName());
        sysUserInfoVo.setUserPhone(sysUser.getUserPhone());
        sysUserInfoVo.setUserEmail(sysUser.getUserEmail());
        sysUserInfoVo.setUserGender(sysUser.getUserGender());
        sysUserInfoVo.setAvatar(sysUser.getAvatar());

        //获取用户角色
        List<String> userRoles = sysUserRoleService.getUserRoleCodes(sysUser.getId());
        sysUserInfoVo.setRoles(userRoles);
        //用户拥有角色的按钮权限
        List<String> buttons = sysRolePermissionService.getUserPermissions(sysUser.getId());
        sysUserInfoVo.setButtons(buttons);
        return  sysUserInfoVo;
    }

    /**
     * 用户退出登录
     */
    @Override
    public String logout() {
        StpUtil.logout();
        return "退出成功";
    }

    /**
     * 刷新用户Token
     */
    @Override
    public String refreshToken(String  refreshToken) {
        if (ObjectUtil.isNull(refreshToken)){
            StpUtil.stpLogic.updateLastActiveToNow(refreshToken);
            return "刷新成功";
        }else{
            throw new JinLinkException("token不存在");
        }
    }

    /**
     * 获取用户注册验证码
     */
    @Override
    public byte[] getRegisterCode(String userName) {
        if (StringUtils.isEmpty(userName)){
            throw new JinLinkException("请先填写账号名!");
        }
        //定义图形验证码的长和宽
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
        //验证码保存120s
        redisService.deleteObject(userName);
        redisService.setCacheObject(userName,lineCaptcha.getCode(),120L, TimeUnit.SECONDS);
        return lineCaptcha.getImageBytes();
    }

    /**
     * 用户注册
     */
    @Override
    public Boolean userRegister(RegisterFormDTO registerFormDTO) {
        //参数校验
        if (ObjectUtil.isEmpty(registerFormDTO.getUserName())
                | ObjectUtil.isEmpty(registerFormDTO.getNickName())
                | ObjectUtil.isEmpty(registerFormDTO.getPassword())
                | ObjectUtil.isEmpty(registerFormDTO.getPassword())
                | ObjectUtil.isEmpty(registerFormDTO.getConfirmPassword())){
            throw new JinLinkException("非法参数!");
        }
        //校验用户是否注册
        SysUser one = sysUserMapper.selectOneByQuery(new QueryWrapper().eq("user_name", registerFormDTO.getUserName()));
        if (ObjectUtil.isNotNull(one)){
            throw new JinLinkException("用户名已被注册!");
        }
        //校验密码是否一致
        if (!registerFormDTO.getPassword().equals(registerFormDTO.getConfirmPassword())){
            throw new JinLinkException("两次密码不一致!");
        }
        //校验密码长度
        if (registerFormDTO.getPassword().length() < 6 || registerFormDTO.getPassword().length() > 18){
            throw new JinLinkException("密码过短 | 密码过长");
        }
        //校验验证是否正确
        String code = registerFormDTO.getCode();
        String entryCode = redisService.getCacheObject(registerFormDTO.getUserName());
        if(ObjectUtil.isNull(entryCode) | !code.equals(entryCode)){
            throw new JinLinkException("验证码错误!");
        }
        try {
            //注册用户信息
            SysUser sysUser = SysUser.builder()
                    .userName(registerFormDTO.getUserName())
                    .nickName(registerFormDTO.getNickName())
                    .userGender("1")
                    .salt("VECaJx")
                    .status("1")
                    .avatar(Constants.USER_DEFAULT_AVATAR)
                    .password(DigestUtils.sha256Hex(registerFormDTO.getPassword() + "VECaJx"))
                    .lastLoginTime(LocalDateTime.now())
                    .build();
            sysUserMapper.insert(sysUser);
            //为用户分配角色
            for (Long roleId : Constants.USER_DEFAULT_ROLE_LIST) {
                SysUserRole sysUserRole = SysUserRole.builder()
                        .userId(sysUser.getId())
                        .roleId(roleId)
                        .build();
                sysUserRoleService.save(sysUserRole);
            }
        }catch (Exception e){
            throw new JinLinkException("用户注册失败");
        }
        return true;
    }

    /**
     * 根据主键更新自己用户信息。
     */
    @Override
    public Boolean updateOneSelf(SysUserOneSelfDTO sysUserOneSelfDTO) {
        long loginIdAsLong = StpUtil.getLoginIdAsLong();
        SysUser sysUser = sysUserMapper.selectOneById(loginIdAsLong);
        if (ObjectUtil.isNull(sysUser)) throw new JinLinkException("用户不存在");
        //设置用户名称
        if (ObjectUtil.isNotNull(sysUserOneSelfDTO.getUserName())){
            sysUser.setNickName(sysUserOneSelfDTO.getUserName());
        }
        //设置用户邮箱
        if (ObjectUtil.isNotNull(sysUserOneSelfDTO.getUserEmail())){
            sysUser.setUserEmail(sysUserOneSelfDTO.getUserEmail());
        }
        //设置用户手机号
        if (ObjectUtil.isNotNull(sysUserOneSelfDTO.getUserPhone())){
            sysUser.setUserPhone(sysUserOneSelfDTO.getUserPhone());
        }
        //设置用户性别
        if (ObjectUtil.isNotNull(sysUserOneSelfDTO.getUserGender())){
            sysUser.setUserGender(sysUserOneSelfDTO.getUserGender());
        }
        return updateById(sysUser);
    }

    /**
     * 更新自己密码。
     */
    @Override
    public Boolean updatePassword(SysUserPasswordDTO sysUserPasswordDTO) {
        long loginIdAsLong = StpUtil.getLoginIdAsLong();
        SysUser sysUser = sysUserMapper.selectOneById(loginIdAsLong);
        if (ObjectUtil.isNull(sysUser)) throw new JinLinkException("用户不存在!");
        if (ObjectUtil.isNull(sysUserPasswordDTO)
                || ObjectUtil.isNull(sysUserPasswordDTO.getOldPassword())
                || ObjectUtil.isNull(sysUserPasswordDTO.getNewPassword())
                || ObjectUtil.isNull(sysUserPasswordDTO.getConfirmPassword()))
            throw new JinLinkException("参数异常!");
        //校验两个密码是否一致
        if (!sysUserPasswordDTO.getNewPassword().equals(sysUserPasswordDTO.getConfirmPassword()))
            throw new JinLinkException("两次密码不一致!");
        if (sysUserPasswordDTO.getOldPassword().equals(sysUserPasswordDTO.getNewPassword()))
            throw new JinLinkException("旧密码不能和新密码一致!");
        //校验密码长度
        if (sysUserPasswordDTO.getNewPassword().length() < 6 || sysUserPasswordDTO.getNewPassword().length() > 18){
            throw new JinLinkException("密码过短 | 密码过长");
        }
        String oldPassword = DigestUtils.sha256Hex(sysUserPasswordDTO.getOldPassword() + sysUser.getSalt());
        //校验 新老密码是否一致
        if (!sysUser.getPassword().equals(oldPassword))
            throw new JinLinkException("旧密码错误!");
        //校验通过 修改用户密码
        sysUser.setPassword(DigestUtils.sha256Hex(sysUserPasswordDTO.getNewPassword() + sysUser.getSalt()));
        return updateById(sysUser);
    }

    /**
     * 用户账号信息重置
     */
    @Override
    public Boolean reset(SysUserResetDTO sysUserResetDTO) {
        long loginIdAsLong = StpUtil.getLoginIdAsLong();
        SysUser sysUser = sysUserMapper.selectOneById(loginIdAsLong);
        if (ObjectUtil.isNull(sysUser)) throw new JinLinkException("用户不存在!");
        if (ObjectUtil.isNotNull(sysUser.getIsReset()) && sysUser.getIsReset().equals(StringPools.ONE)){
            throw new JinLinkException("你已重置过账户,无法再次重置!");
        }
        //校验用户是否注册
        SysUser one = sysUserMapper.selectOneByQuery(new QueryWrapper().eq("user_name", sysUserResetDTO.getUserName()));
        if (ObjectUtil.isNotNull(one)){
            throw new JinLinkException("用户名已被注册!");
        }
        //校验密码长度
        if (ObjectUtil.isNull(sysUserResetDTO.getPassword()) || sysUserResetDTO.getPassword().length() < 6 || sysUserResetDTO.getPassword().length() > 18 || ObjectUtil.isNull(sysUserResetDTO.getUserName())){
            throw new JinLinkException("参数非法 | 密码长度过短 | 密码长度过长");
        }
        //校验用户名称是否过短
        if (sysUserResetDTO.getUserName().length() < 2){
            throw new JinLinkException("用户名过短!");
        }
        //重置用户信息
        sysUser.setUserName(sysUserResetDTO.getUserName());
        sysUser.setPassword(DigestUtils.sha256Hex(sysUserResetDTO.getPassword() + sysUser.getSalt()));
        sysUser.setIsReset(StringPools.ONE);
        return updateById(sysUser);
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
                .ipAddr(IPUtils.getIpAddr(ip))
                .message("登陆成功")
                .build();
    }
}
