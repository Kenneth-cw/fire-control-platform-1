package com.shine.iot.signal.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * webSocket配置
 */
@Configuration
@EnableWebSocket
public class WebSocketAutoConfig implements WebSocketConfigurer {

    @Bean
    public TextWebSocketHandler webSocketHandler() {
        return new SpringWebSocketHandler();
    }

    @Bean
    public SpringWebSocketInterceptor webSocketInterceptor() {
        return new SpringWebSocketInterceptor();
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // webSocket通道
        // 指定处理器和路径
        registry.addHandler(webSocketHandler(), "/websocket/socketServer.do")
                // 指定自定义拦截器
                .addInterceptors(webSocketInterceptor())
                // 允许跨域
                .setAllowedOrigins("*");
        // sockJs通道
        registry.addHandler(webSocketHandler(), "/sockjs/socketServer.do")
                .addInterceptors(webSocketInterceptor())
                .setAllowedOrigins("*")
                // 开启sockJs支持
                .withSockJS();
    }

    /*@Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // webSocket通道
                // 指定处理器和路径
        registry.addHandler(new SpringWebSocketHandler(), "/websocket/socketServer.do")
                // 指定自定义拦截器
                .addInterceptors(new SpringWebSocketInterceptor())
                // 允许跨域
                .setAllowedOrigins("*");
        // sockJs通道
        registry.addHandler(new SpringWebSocketHandler(), "/sockjs/socketServer.do")
                .addInterceptors(new SpringWebSocketInterceptor())
                .setAllowedOrigins("*")
                // 开启sockJs支持
                .withSockJS();
    }*/

}
