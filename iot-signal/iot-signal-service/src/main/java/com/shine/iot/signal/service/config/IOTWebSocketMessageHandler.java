package com.shine.iot.signal.service.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.text.SimpleDateFormat;
import java.util.concurrent.ConcurrentHashMap;

public class IOTWebSocketMessageHandler extends TextWebSocketHandler {
    private static Logger logger = LogManager.getLogger(SpringWebSocketHandler.class);

    public static final String USERFLAG = "username";
    public SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final ConcurrentHashMap<String, WebSocketSession> notifiedUsers;//已经通知到的用户
    private static ConcurrentHashMap<String, WebSocketSession> validUsers;//这个会出现性能问题，最好用Map来存储，key用userid

    static {
        notifiedUsers = new ConcurrentHashMap<String, WebSocketSession>();
        validUsers = new ConcurrentHashMap<String, WebSocketSession>();
    }


}
