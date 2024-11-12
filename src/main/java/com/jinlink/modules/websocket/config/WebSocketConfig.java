package com.jinlink.modules.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/**
 * webSocket的配置类
 * @author summer
 */

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new MyWebSocketHandler(), "/ws/*")
                .addInterceptors(new HttpSessionHandshakeInterceptor())
                .setAllowedOrigins("*")
                // 设置心跳间隔和超时时间
                .withSockJS()
                .setHeartbeatTime(60000L) // 心跳间隔，单位毫秒
                .setDisconnectDelay(30000L); // 断开延迟，单位毫秒
    }

    // 自定义的WebSocket处理器
    public static class MyWebSocketHandler extends TextWebSocketHandler {
        // 实现你的处理逻辑
    }
}

