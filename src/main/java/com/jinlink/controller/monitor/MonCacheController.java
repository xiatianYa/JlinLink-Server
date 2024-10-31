package com.jinlink.controller.monitor;

import cn.dev33.satoken.annotation.SaCheckOr;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.jinlink.common.api.Result;
import com.jinlink.modules.monitor.entity.vo.MonCacheRedisVO;
import com.jinlink.modules.monitor.service.IMonCacheFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统服务监控
 */
@RestController
@Tag(name = "缓存服务监控")
@RequiredArgsConstructor
@RequestMapping("/monCache")
@SaCheckOr(role = @SaCheckRole("R_SUPER"))
public class MonCacheController {

    @NonNull
    private IMonCacheFacade monCacheFacade;

    @GetMapping("/redis")
    @Operation(operationId = "1", summary = "获取 Redis 信息")
    public Result<MonCacheRedisVO> getRedisInfo() {
        return Result.data(monCacheFacade.redisInfo());
    }
}
