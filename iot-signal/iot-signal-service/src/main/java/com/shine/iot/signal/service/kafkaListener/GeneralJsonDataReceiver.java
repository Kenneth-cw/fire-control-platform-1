package com.shine.iot.signal.service.kafkaListener;

import com.alibaba.fastjson.JSONObject;
import com.shine.iot.signal.model.adapter.aug.NodeDataAdapter;
import com.shine.iot.signal.model.dto.aug.system.GeneralJsonModel;
import com.shine.iot.signal.service.logic.GeneralJsonDataDealService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class GeneralJsonDataReceiver {

    private final Logger logger = LogManager.getLogger(GeneralJsonDataReceiver.class);

    @StreamListener(GeneralJsonSinkChannels.INPUT_GENERAL_JSON)
    public void generalJsonDataReceive(Message<String> message) {
        String jsonStr = message.getPayload();
        logger.info("从 INPUT_PAYLOAD 输入通道得到的msg payload为：" + jsonStr);
        if (StringUtils.isNotBlank(jsonStr)) {
            try {
                GeneralJsonModel generalModel = JSONObject.parseObject(jsonStr, GeneralJsonModel.class);
                generalModel.setRcvTime(new Date()); //设置统一JSON数据的接收时间
                logger.info("--- Aug-TLV ---- 烟感 ---- " + generalModel.getDeviceSN().toUpperCase());
                JSONObject jsonObject = (JSONObject) generalModel.getJsonData(); //解析NodeDataAdapter节点信息
                NodeDataAdapter nodeDataAdapter = jsonObject.toJavaObject(NodeDataAdapter.class); //payload节点信息对象

                // 注意：GeneralJsonDataDealService 最好不要定义太多 static 修饰的方法
                //GeneralJsonDataDealService generalJsonService = new GeneralJsonDataDealService();
                //保存deviceAllMsg信息


                //保存数据
                GeneralJsonDataDealService.dealGeneralJsonData(generalModel, nodeDataAdapter);

            } catch (Exception e) {
                logger.error("保存通用json数据出错，原因：" + e, e.getMessage());
                e.printStackTrace();
            }
            try {
                //消息通知的业务逻辑
                //GeneralJsonDataDealService.sendMessage(jsonStr);


            } catch (Exception e) {
                logger.error("解析通用得JSON数据出错，原因：" + e, e.getMessage());
                e.printStackTrace();
            }

        }


    }

}
