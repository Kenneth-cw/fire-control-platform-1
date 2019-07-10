package com.shine.iot.platform.service;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class CommonTest {

    @Test
    public void testLoginPwd_Md5() {
        //数据库加密规则
        //import com.platform.common.encryption.Encrypter;
        /*System.out.println(Encrypter.encrypt("123456"));
        System.out.println(Encrypter.decrypt("nlmIWmhjieE."));*/
        //用户名加密
        //import com.platform.common.encryption.Md5;
        /*System.out.println(Md5.MD5("tengzhuo", "utf-8"));
        System.out.println(Md5.MD5("huanying", "utf-8"));*/
    }

    @Test
    public void testJsonConvert() {
        Map<String, String> p_map = new HashMap<>();
        p_map.put("code", "0");
        p_map.put("message", "请输入用户名和密码！");
        p_map.put("token", "token");

        JSONObject rootJson = new JSONObject();
        rootJson.put("data", p_map);
        rootJson.toJSONString();
        System.out.println("login return data: " + rootJson.toJSONString());
        //{"data":{"code":"0","message":"请输入用户名和密码！","token":"token"}}
    }

}
