package com.shine.iot.signal.service.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * websocket 自定义拦截器
 */
public class SpringWebSocketInterceptor extends HttpSessionHandshakeInterceptor {

    //    private final String user_info = "userInfo";
    private final String user_name = "userName";
    private final String user_id = "userId";

    /**
     * handler处理前调
     * attributes属性最终在WebSocketSession里,可能通过webSocketSession.getAttributes().get(key值)获得
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest serverHttpRequest = (ServletServerHttpRequest) request;
            // 获取请求路径携带的参数
            /*String user = serverHttpRequest.getServletRequest().getParameter("user");
            attributes.put("user", user);*/

            // 获取 HttpSession
            HttpSession session = serverHttpRequest.getServletRequest().getSession(false);
            if (session != null) {
                // 从 HttpSession中获取登录时保存到 session 的信息
//                String user_info = String.valueOf(session.getAttribute(this.user_info));
                String userId = String.valueOf(session.getAttribute(this.user_id));
                String userName = String.valueOf(session.getAttribute(this.user_name));
                // 将userId、userName信息存储到 webSocketSession 中
                boolean flag = false;
                if (NumberUtils.isDigits(userId)) {
                    //使用 userId 区分 WebSocketHandler，以便定向发送消息
                    attributes.put("WEBSOCKET_USERID", Integer.valueOf(userId));
                    flag = true;
                }
                if (StringUtils.isNotBlank(userName)) {
                    attributes.put("WEBSOCKET_USERNAME", userName);
                    flag = true;
                }
                return flag;
            }
        } else {
            return false;
        }
        return super.beforeHandshake(request, serverHttpResponse, webSocketHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler webSocketHandler, Exception e) {
        super.afterHandshake(request, response, webSocketHandler, e);
    }
}
