package com.jinlink.controller.monitor;

import cn.dev33.satoken.annotation.SaCheckOr;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.jinlink.common.api.Result;
import com.jinlink.modules.monitor.entity.vo.MonSystemVO;
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
@SaCheckOr(role = @SaCheckRole("R_SUPER"))
public class MonSystemController {

    @NonNull
    private IMonSystemFacade monSystemFacade;

    @GetMapping("/info")
    @Operation(operationId = "1", summary = "获取系统服务器系统信息")
    public Result<MonSystemVO> getServerInfo() {
        return Result.data(monSystemFacade.getServerInfo());
    }
}
