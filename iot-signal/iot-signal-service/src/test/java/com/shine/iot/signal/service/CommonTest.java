package com.shine.iot.signal.service;

import com.alibaba.fastjson.JSONObject;
import com.shine.iot.signal.model.adapter.aug.NodeDataAdapter;
import com.shine.iot.signal.model.dto.aug.system.GeneralJsonModel;
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

    @Test
    public void testParseJsonStr() {

        String jsonStr = "{\"isAck\":false,\"ipFrom\":\"127.0.0.1\",\"rcvTime\":1562303449218,\"protocol\":\"3\",\"needAck\":true,\"model\":\"0\",\"direction\":true,\"product\":\"2\",\"portFrom\":9823,\"swversion\":\"5\",\"deviceSN\":\"ffffffffff07000003f7f985ffffffff\",\"command\":\"3\",\"initialData\":\"810100ffffffffff07000003f7f985ffffffff04050000050300f593\",\"jsonData\":{\"ack\":false,\"cmdType\":\"3\",\"crcCorrect\":false,\"dataLength\":0,\"dataTime\":1562303449546,\"deviceType\":\"2\",\"industryType\":\"1\",\"loraFrameCounter\":5,\"lorawanFCtrl\":\"5\",\"lorawanMsgType\":\"4\",\"mcuIDHex\":\"ffffffffff07000003f7f985ffffffff\",\"mcuidLen\":16,\"needAck\":true,\"payloadDataAckBytes\":\"gQGD//////8HAAAD9/mF/////4QABVAO\",\"productModel\":\"0\",\"protocol\":\"3\",\"reserved\":\"0\",\"signalType\":\"3\",\"sourceCrcValue\":62867},\"field\":\"1\",\"upFrameCount\":5,\"status\":\"3\"}";

        GeneralJsonModel generalModel = JSONObject.parseObject(jsonStr, GeneralJsonModel.class);
        System.out.println("--- Aug-TLV ---- 烟感 ---- " + generalModel.getDeviceSN().toUpperCase());
        JSONObject jsonObject = (JSONObject) generalModel.getJsonData(); //解析NodeDataAdapter节点信息
        NodeDataAdapter nodeDataAdapter = jsonObject.toJavaObject(NodeDataAdapter.class); //payload节点信息对象

        System.out.println("----" + nodeDataAdapter.getMcuIDHex());


    }

}
