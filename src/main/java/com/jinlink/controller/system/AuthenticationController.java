package com.jinlink.controller.system;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.jinlink.common.api.Result;
import com.jinlink.modules.system.entity.dto.LoginFormDTO;
import com.jinlink.modules.system.entity.dto.RefreshTokenDTO;
import com.jinlink.modules.system.entity.dto.RegisterFormDTO;
import com.jinlink.modules.system.entity.dto.oAuthLoginDTO;
import com.jinlink.modules.system.entity.vo.SysUserInfoVo;
import com.jinlink.modules.system.facade.IAuthenticationFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 权限管理 控制层。
 *
 * @author Summer
 * @since 1.0.0
 */
@RestController
@Tag(name = "权限管理")
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
    @NonNull
    private IAuthenticationFacade authenticationFacade;

    @PostMapping("/login")
    @Operation(operationId = "1",summary = "用户登录")
    public Result<Map<String, String>> login(@Parameter(description = "登录对象") @RequestBody LoginFormDTO loginFormDTO){
        return Result.success("请求成功",authenticationFacade.userNameLogin(loginFormDTO));
    }

    @PostMapping("/logout")
    @Operation(operationId = "2",summary = "退出登录")
    public Result<String> logout(){
        return Result.success("请求成功",authenticationFacade.logout());
    }

    @GetMapping("/getUserInfo")
    @Operation(operationId = "3",summary = "获取用户权限信息")
    @SaCheckLogin
    public Result<SysUserInfoVo> getUserInfo(){
        return Result.success("请求成功",authenticationFacade.getUserInfo());
    }

    @PostMapping("/refreshToken")
    @Operation(operationId = "4",summary = "刷新Token过期时间")
    @SaCheckLogin
    public Result<String> refreshToken(@Parameter(description = "刷新TOKEN") @RequestBody RefreshTokenDTO refreshToken){
        return Result.success("请求成功",authenticationFacade.refreshToken(refreshToken.getRefreshToken()));
    }

    @PostMapping("/oauth2/qq/login")
    @Operation(operationId = "5",summary = "用户第三方登录")
    public Result<Map<String, String>> oauthLogin(@RequestBody oAuthLoginDTO loginFormDTO) {
        return Result.success("请求成功",authenticationFacade.userOAuthLogin(loginFormDTO));
    }

    @GetMapping("/getCode")
    @Operation(operationId = "6",summary = "获取用户注册验证码")
    public Result<byte[]> getRegisterCode(@RequestParam String userName){
        byte[] responseData = authenticationFacade.getRegisterCode(userName);
        return Result.data(responseData);
    }

    @PostMapping("/register")
    @Operation(operationId = "7",summary = "用户注册")
    public Result<Boolean> userRegister(@RequestBody RegisterFormDTO registerFormDTO){
        return Result.success("请求成功",authenticationFacade.userRegister(registerFormDTO));
    }
}
