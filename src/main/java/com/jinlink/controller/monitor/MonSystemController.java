package com.jinlink.controller.monitor;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.jinlink.common.api.Result;
import com.jinlink.modules.monitor.entity.vo.MonSystemVo;
import com.jinlink.modules.monitor.service.IMonSystemFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统服务监控 Controller 控制层
 */

@RestController
@Tag(name = "系统服务监控")
@RequiredArgsConstructor
@RequestMapping("/monSystem")
public class MonSystemController {

    @NonNull
    private IMonSystemFacade monSystemFacade;

    @GetMapping("/info")
    @Operation(operationId = "1", summary = "获取系统服务器系统信息")
    @SaCheckPermission("mon:monSystem:info")
    public Result<MonSystemVo> getServerInfo() {
        return Result.data(monSystemFacade.getServerInfo());
    }
}
