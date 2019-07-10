package com.shine.iot.signal.service.rest;

import com.shine.iot.signal.service.config.SpringWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/testwebsocket")
public class WebSocketTestController {

    @Bean
    public SpringWebSocketHandler infoHandler() {
        return new SpringWebSocketHandler();
    }

    @RequestMapping("/login")
    public String login_test(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println("用户【" + username + "】登录");
        /*OpLoginModel opLoginModel = new OpLoginModel();
        opLoginModel.setId(1);
        opLoginModel.setLoginName(username);
        opLoginModel.setPassWord(password);*/

        HttpSession session = request.getSession(true);
        session.setAttribute("SESSION_USERNAME", username);
        session.setAttribute("WEBSOCKET_USERID", 1);
        return username;
    }

    @RequestMapping("/websocket/send.html")
    @ResponseBody
    public String send(HttpServletRequest request) {
        String username = request.getParameter("username");
        TextMessage textMessage = new TextMessage("你好，测试！！！！");
        infoHandler().sendMessageToOneUser(1, textMessage);
        return "websocket send message success, message is " + textMessage;
    }

}
