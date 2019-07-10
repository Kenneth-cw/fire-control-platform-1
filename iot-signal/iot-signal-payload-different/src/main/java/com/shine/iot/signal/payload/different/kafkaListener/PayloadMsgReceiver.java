package com.shine.iot.signal.payload.different.kafkaListener;

import com.alibaba.fastjson.JSONObject;
import com.shine.iot.signal.model.dto.aug.NodeDataDto;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
public class PayloadMsgReceiver {

    private final Logger logger = LogManager.getLogger(PayloadMsgReceiver.class);

    @StreamListener(PayloadProcessorChannels.INPUT_LORALITE_PAYLOAD)
    @SendTo(PayloadProcessorChannels.OUTPUT_PAYLOAD_TYPE_VERSION)
    public String receivePayloadMsg(Message<String> message) {
        logger.info("接受到的message：" + message.getPayload());
        String json = message.getPayload();
        NodeDataDto nodeDataDto = JSONObject.parseObject(json, NodeDataDto.class);
        logger.info("payload 原始数据：" + nodeDataDto.getRawPayload());
        if (nodeDataDto != null && StringUtils.isNotBlank(nodeDataDto.getRawPayload())) {
            try {
                /**
                 * payload数据第1个字节的，高半字节（高4位），代表协议的版本；
                 * 版本：0x0，版本0，MCUID为8字节（已废弃）；
                 * 0x2，版本2，无MCUID；
                 * 0x8，版本1，MCUID为16字节。
                 * 设备类型：NB烟感 = 0x00；LORA烟感  = 0x01；警铃 = 0x02；手动报警器 = 0x03；温感 = 0x04；
                 * 声光报警器 = 0x05；变送器 = 0x06（支持液压、液位检测）；超声波距离检测器 = 0x07；
                 * 语音提示器 = 0x09；可燃气体探测器 = 0x0B；控烟探测器 = 0x0D
                 */
                byte[] b_payload = Hex.decodeHex(nodeDataDto.getRawPayload());
                int b_version = (b_payload[0] >> 4) & 0X0F; //payload版本
                int b_type = b_payload[1] & 0x0F; //设备类型

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("deviceType", b_type);
                jsonObject.put("version", b_version);
                jsonObject.put("nodeDataDto", nodeDataDto);
                return jsonObject.toJSONString();
            } catch (DecoderException e) {
                logger.error("payload数据转换为byte[]失败，原因：" + e.getMessage(), e);
                e.printStackTrace();
            }
        } else {
            logger.debug("kafka监听输入通道得到的payload数据为null，暂不处理！");
        }
        return null;
    }

}
