package com.shine.iot.signal.reader.kafkaListener;

import com.alibaba.fastjson.JSONObject;
import com.shine.iot.signal.model.adapter.aug.NodeDataAdapter;
import com.shine.iot.signal.model.dto.aug.NodeDataDto;
import com.shine.iot.signal.reader.quartz.IotScheduleServer;
import com.shine.iot.signal.reader.service.PayloadMsgService;
import com.shine.iot.signal.util.parser.aug.tlv.payload.IDevicePayloadParser;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;

@Component
public class PayloadMsgReceiver {
    private final Logger logger = LogManager.getLogger(PayloadMsgReceiver.class);

    private IotScheduleServer scheduler; // 定时任务

    //    private NodeDataByteReader nodeDataByteReader; //解析payload数据
    public PayloadMsgReceiver() {
        scheduler = IotScheduleServer.getInstance(); //延时下发ACK
//        nodeDataByteReader = new NodeDataByteReader();
    }

    @StreamListener(PayloadProcessorChannels.INPUT_PAYLOAD_TYPE_VERSION)
    @SendTo(PayloadProcessorChannels.OUTPUT_GENERAL_JSON)
    public String payloadReceive(Message<String> message) {
        logger.info("从 INPUT_PAYLOAD 输入通道得到的msg payload为：" + message.getPayload());
        JSONObject parseJsonObject = JSONObject.parseObject(message.getPayload());
        Integer deviceType = parseJsonObject.getInteger("deviceType");
        Integer version = parseJsonObject.getInteger("version");
        NodeDataDto nodeDataDto = parseJsonObject.getObject("nodeDataDto", NodeDataDto.class);

        nodeDataDto.setRcvTime(new Date()); //设置消息接收时间
        String IP = nodeDataDto.getGwIP(); //InetAddress
        Integer port = nodeDataDto.getGwPort();
        String payload = nodeDataDto.getRawPayload();
        logger.info("kafka 监听到的payload数据为：" + payload);

        if (StringUtils.isNotBlank(payload)) {
            try {
                // 解析payload数据
                IDevicePayloadParser payloadParser = PayloadMsgService.getSmokeParser(deviceType, version);
                NodeDataAdapter nodeData = payloadParser.parse(Hex.decodeHex(payload));

                //    TODO：payload去重、验证CRC

                //下发ACK
                HashMap<String, Object> ackDataMap = new HashMap<String, Object>();
                ackDataMap.put("nodeData", nodeData);
                ackDataMap.put("ackbytes", nodeData.getPayloadDataAckBytes());
                ackDataMap.put("deviceAddress", InetAddress.getByName(IP));
                ackDataMap.put("devicePort", port);
                scheduler.addLoraLiteAckTask(ackDataMap);
                logger.info("payload 数据的 ACK为：" + Hex.encodeHexString(nodeData.getPayloadDataAckBytes()));

                //存储消息 -- payload信息、ACK信息
                // 存储原始的 payload 数据，存储规则：验证CRC、去重
                nodeDataDto.setNodeDataAdapter(nodeData);
                PayloadMsgService.saveRawPayloadData(nodeDataDto);
                // 存储ACK信息 TODO
                String ackHexStr = Hex.encodeHexString(nodeData.getPayloadDataAckBytes());
                PayloadMsgService.saveACKMsg(IP, port, ackHexStr);

                //转换payload信息为统一的JSON数据信息
                // 先不过滤重复帧号
                String convertJSON = PayloadMsgService.convertJSON(nodeDataDto);
                return convertJSON;

                //处理统一格式的JSON数据
                //PayloadMsgService.dealGeneralJsonData(nodeDataDto);

                //存储AllMsg
                //PayloadMsgService.saveAllMsgInfo(nodeDataDto);

            } catch (DecoderException e) {
                logger.error("payload 数据解析出错（kafka监听输入通道得到的payload数据），暂不处理！");
                e.printStackTrace();
            } catch (UnknownHostException e) {
                logger.error("payload 数据解析出错（kafka监听输入通道得到的payload数据），暂不处理！");
                e.printStackTrace();
            }
        } else {
            logger.debug("kafka监听输入通道得到的payload数据为null，暂不处理！");
        }
        return null;
    }


}
