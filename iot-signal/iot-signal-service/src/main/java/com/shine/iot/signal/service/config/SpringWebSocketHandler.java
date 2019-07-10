package com.shine.iot.signal.service.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * websocket 处理器代码
 */
public class SpringWebSocketHandler extends TextWebSocketHandler {

    private static Logger logger = LogManager.getLogger(SpringWebSocketHandler.class);
    private final String socket_user_name = "WEBSOCKET_USERNAME";
    private final String socket_user_id = "WEBSOCKET_USERID";

    /**
     * 存储sessionId和webSocketSession
     * 需要注意的是，webSocketSession没有提供无参构造，不能进行序列化，也就不能通过redis存储
     * 在分布式系统中，要想别的办法实现webSocketSession共享
     */
    private static Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>(); // sessionId 和 webSocketSession
    private static Map<Integer, String> userMap = new ConcurrentHashMap<>(); // userId 和 session.getId()

    /*static {
        sessionMap = new ConcurrentHashMap<>();
        userMap = new ConcurrentHashMap<>();
    }*/

    /**
     * webSocket连接创建后调用
     * socket连接成功时，会触发h5页面上的onopen方法
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        // 获取参数
//        String username = String.valueOf(session.getAttributes().get(socket_user_name));
        Integer userId = (Integer) session.getAttributes().get(socket_user_id);
        userMap.put(userId, session.getId()); // 存储 userId 和 session.getId()
        sessionMap.put(session.getId(), session); //存储socketSession.getId 和 WebSocketSession

        // 检查当前用户是否已经登录
        logger.info("connect to the websocket success......当前数量:" + userMap.size());
    }

    /**
     * 接收到消息会调用
     * js调用websocket.send时候，会调用该方法
     */
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        String rcvMessage = message.getPayload();
        logger.info("服务器的 webSocket 接受到的消息为：" + rcvMessage);
        //保持websocket的心跳连接
        if (StringUtils.isNotBlank(rcvMessage) && rcvMessage.equals("ping")) {
            TextMessage returnMessage = new TextMessage("pong");
            session.sendMessage(returnMessage);
        }
    }

    /**
     * 连接出错会调用
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        logger.debug("websocket connection error to closed......");
        sessionMap.remove(session.getId());
        if (session.isOpen()) {
            try {
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 连接关闭会调用
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        logger.debug("websocket connection closed......");
        sessionMap.remove(session.getId());
        Integer userId = (Integer) session.getAttributes().get(socket_user_id);
        if (userId != null) {
            userMap.remove(userId);
        }
        String username = (String) session.getAttributes().get(socket_user_name);
        logger.debug("用户【" + username + "】已退出！");
        logger.debug("剩余在线用户数量：" + userMap.size());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 后端发送消息 -- 给所有在线用户发送消息
     */
    public static void sendMessageToAllUser(TextMessage message) {
        //遍历Map集合的方式：通过Map.entrySet遍历key和value（）
        // 优点：Map集合容量大时，遍历效率高
        for (Map.Entry<Integer, String> entry : userMap.entrySet()) {
            try {
                WebSocketSession session = sessionMap.get(entry.getValue());
                if (session.isOpen()) {
                    session.sendMessage(message);
                    logger.info("webSocket 给所有在线发送消息到页面，消息：" + message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 后端发送消息 -- 给某个在线的用户发送消息
     */
    public static void sendMessageToOneUser(Integer userId, TextMessage message) {
        String sessionId = userMap.get(userId); //根据 userId 获取 webSocketSession的ID
        WebSocketSession session = sessionMap.get(sessionId); // 根据sessionId 获取 WebSocketSession
        try {
            if (session.isOpen()) {
                session.sendMessage(message);
                logger.info("webSocket 发送消息到页面，消息：" + message);
            } else {
                logger.debug("webSocketSession 是close状态，不推送消息到页面！");
            }
        } catch (IOException e) {
            logger.error("websocket message send fail, cause:" + e, e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 后端发送消息 -- 给指定的批量用户发送消息（用户在线就发送，不在线，不进行发送）
     *
     * @param userIds 要推送消息的用户ID集合（Set<Integer>）
     * @param message 推送到页面的消息
     */
    public static void sendMessageToBatchUser(Set<Integer> userIds, TextMessage message) {
        if (!userIds.isEmpty()) {
            for (Map.Entry<Integer, String> entry : userMap.entrySet()) {
                if (userIds.contains(entry.getKey())) {
                    try {
                        //判断登录者集合中，是否有 要推送消息的当前用户
                        String socketSessionId = entry.getValue();
                        WebSocketSession session = sessionMap.get(socketSessionId);
                        if (session.isOpen()) {
                            session.sendMessage(message);
                            logger.info("webSocket 发送消息到页面，消息：" + message);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            logger.debug("要推送消息的用户ID集合为空，即此设备的用户没有在线的，不进行消息推送！");
        }
    }

    /*public void sendMessage(TextMessage message){

    }*/

}
