package com.shine.iot.platform.service.rest;

import com.alibaba.fastjson.JSONObject;
import com.platform.common.encryption.Md5;
import com.platform.model.ServiceRsObjModel;
import com.shine.iot.model.entity.OpLoginModel;
import com.shine.iot.platform.service.serviceLogic.IOpLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class IndexController {

    private IOpLoginService opLoginService;

    @Autowired
    private void setOpLoginService(IOpLoginService opLoginService) {
        this.opLoginService = opLoginService;
    }

    //登录逻辑编写
    @RequestMapping(value = "login")
    public String login(HttpServletRequest request) {
        // code: 0 失败 1成功；token：字符串（计算得出）； message:登录失败的原因
        String code = "0";
        String message = null;
        String token = "token";

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (username == null || password == null) {
            code = "0";
            message = "请输入用户名和密码！";
            token = null;
        } else {
            //调用根据用户名、密码，查询oplogin表中有无此用户，由此用户，则表示登陆成功，反之失败
            ServiceRsObjModel<OpLoginModel> loginRs = opLoginService.login(username, Md5.encode(password));
            if (loginRs.isSuccess()) {
                OpLoginModel opLogin = loginRs.getRsData();
                // 将用户信息存储到 session
                HttpSession httpSession = request.getSession(true);
                //setMaxInactiveInterval设置的是当前会话的失效时间，不是整个web的时间，单位为以秒计算。如果设置的值为零或负数，则表示会话将永远不会超时。常用于设置当前会话时间。
                httpSession.setMaxInactiveInterval(60 * 30); //30min
                httpSession.setAttribute("userInfo", opLogin);
                httpSession.setAttribute("userId", opLogin.getId());
                httpSession.setAttribute("userName", username);
                // 组装返回数据，TODO：生成Token
                code = "1";
            }
        }
        //构建返回的数据格式
        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        map.put("message", message);
        map.put("token", token);
        JSONObject rootJson = new JSONObject();
        rootJson.put("data", map);
//        rootJson.toJSONString();
        return rootJson.toJSONString();
    }


}
