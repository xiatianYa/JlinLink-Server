package com.jinlink.controller.system;

import com.jinlink.common.api.Result;
import com.jinlink.modules.system.entity.dto.LoginFormDTO;
import com.jinlink.modules.system.entity.vo.SysUserInfoVO;
import com.jinlink.modules.system.facade.IAuthenticationFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Tag(name = "Auth")
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

    @GetMapping("/getUserInfo")
    @Operation(operationId = "2",summary = "获取用户权限信息")
    public Result<SysUserInfoVO> getUserInfo(){
        return Result.success("请求成功",authenticationFacade.getUserInfo());
    }

    @PostMapping("/refreshToken")
    @Operation(operationId = "3",summary = "刷新Token过期时间")
    public Result<String> refreshToken(){
        return Result.success("请求成功",null);
    }
}
