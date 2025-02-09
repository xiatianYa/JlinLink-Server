package com.jinlink.controller.game;

import com.jinlink.common.api.Result;
import com.jinlink.modules.websocket.entity.vo.UserOnlineInfoVo;
import com.jinlink.modules.websocket.service.GameWebsocketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Websocket 控制层。
 *
 * @author Summer
 * @since 1.0.0
 */
@RestController
@Tag(name = "Websocket管理")
@RequiredArgsConstructor
@RequestMapping("/websocket")
public class GameWebsocketController {
    @Resource
    private GameWebsocketService gameWebsocketService;

    /**
     * 查询用户连接的服务器信息。
     *
     * @return 连接服务器信息
     */
    @GetMapping("getUserServerInfo")
    @Operation(operationId = "1",summary = "查询用户连接的服务器信息")
    public Result<UserOnlineInfoVo> getUserServerInfo(@RequestParam(name = "id") Long id) {
        return Result.success("获取成功",gameWebsocketService.getUserServerInfo(id));
    }
}
