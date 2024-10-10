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
        List<Map<String, Object>> routes = new ArrayList<>();
        // 添加login路由
        Map<String, Object> loginRoute = new HashMap<>();
        loginRoute.put("name", "login");
        loginRoute.put("path", "/login/:module(pwd-login|code-login|register|reset-pwd|bind-wechat)?");
        loginRoute.put("component", "layout.blank$view.login");
        Map<String, Object> loginMeta = new HashMap<>();
        loginMeta.put("title", "login");
        loginMeta.put("i18nKey", "route.login");
        loginMeta.put("constant", true);
        loginMeta.put("hideInMenu", true);
        loginRoute.put("meta", loginMeta);
        routes.add(loginRoute);
        // 类似地，添加403, 404, 500路由...
        // 添加403、404、500路由
        for (String statusCode : new String[]{"403", "404", "500"}) {
            Map<String, Object> route = new HashMap<>();
            route.put("name", statusCode);
            route.put("path", "/" + statusCode);
            route.put("component", "layout.blank$view." + statusCode);
            Map<String, Object> meta = new HashMap<>();
            meta.put("title", statusCode);
            meta.put("i18nKey", "route." + statusCode);
            meta.put("constant", true);
            meta.put("hideInMenu", true);
            route.put("meta", meta);
            routes.add(route);
        }
        return Result.success("请求成功",routes);
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
