package com.jinlink.controller.system;

import com.jinlink.common.api.Result;
import com.jinlink.modules.system.entity.vo.SysUserRouteVO;
import com.jinlink.modules.system.service.SysMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 路由管理 控制层。
 *
 * @author Summer
 * @since 1.0.0
 */
@RestController
@Tag(name = "路由管理")
@RequiredArgsConstructor
@RequestMapping("/route")
public class SysRouteController {
    @NonNull
    private SysMenuService sysMenuService;

    /**
     * 获取固定的路由数据(不需要权限)
     */
    @GetMapping("getConstantRoutes")
    @Operation(operationId = "1",summary = "获取固定的路由数据(不需要权限)")
    public Result<List<Map<String, Object>>> getConstantRoutes() {
        return sysMenuService.getConstantRoutes();
    }

    /**
     * 获取用户路由数据
     */
    @GetMapping("getUserRoutes")
    @Operation(operationId = "2",summary = "获取用户路由数据")
    public Result<SysUserRouteVO> getUserRoutes() {
        return sysMenuService.getUserRoutes();
    }
}
