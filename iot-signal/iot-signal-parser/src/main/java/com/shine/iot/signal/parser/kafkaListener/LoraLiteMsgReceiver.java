package com.shine.iot.signal.parser.kafkaListener;

import com.alibaba.fastjson.JSONObject;
import com.shine.iot.signal.model.adapter.aug.GateWayTLVJSonDataAdapter;
import com.shine.iot.signal.model.dto.aug.NodeDataDto;
import com.shine.iot.signal.util.datastream.parser.DigitUtil;
import com.shine.iot.signal.util.device.bytemsg.process.aug.AugTLVGWByteReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class LoraLiteMsgReceiver {

    private static Logger logger = LogManager.getLogger(LoraLiteMsgReceiver.class);

    /**
     * 接受通道Input_loraLite的消息，返回处理结果到output通道
     *
     * @param message 消息体
     * @return 消息体信息
     */
    @StreamListener(TLVProcessorChannels.INPUT_AUGTLV)
    @SendTo(TLVProcessorChannels.OUTPUT_PAYLOAD)
    public String receiveMsg(Message<Map<String, Object>> message) {
        logger.info("从输入通道得到的TLV Message为：" + message);
        Map<String, Object> msgMap = message.getPayload();
        String tlvMsg = (String) msgMap.get("content");
        /*String ipAddress = (String) msgMap.get("IP");
        Integer port = (Integer) msgMap.get("port");*/
        try {
            //解析TLV数据
            AugTLVGWByteReader augReader = new AugTLVGWByteReader(DigitUtil.decodeHex(tlvMsg));
            augReader.parse();
            GateWayTLVJSonDataAdapter tlvData = augReader.getResult();

            //2. 存储从终端接收到原始 payload 信号
            // 验证规则: CRC: TODO，验证CRC、数据去重
            //      不合理：如果验证CRC、数据去重，则需要解析payload数据


            //解析出payload、IP地址、port端口转发到另外一组topic
            NodeDataDto nodeDataDto = new NodeDataDto();
            nodeDataDto.setGwIP((String) msgMap.get("IP"));
            nodeDataDto.setGwPort((Integer) msgMap.get("port"));
            nodeDataDto.setRawPayload(tlvData.getRfData()); //payload 数据
            nodeDataDto.setGwId(tlvData.getGwid());

            String jsonStr = JSONObject.toJSONString(nodeDataDto);

//            String payload = tlvData.getRfData();
//            msgMap.put("content", payload);
            logger.info("将从TLV中解析的payload数据发送到kafka中，发送数据为：" + nodeDataDto);
            return jsonStr;
        } catch (Exception e) {
            logger.error("解析TLV数据出错，原因：" + e);
            e.printStackTrace();
            return null;
        }
    }


   /* // 注入订阅通道信息
    private static SubscribableChannel subscribableChannel;
    @Autowired
    public void setSubscribableChannel( @Qualifier(value = TLVProcessorChannels.INPUT_AUGTLV) SubscribableChannel subscribableChannel){
        LoraLiteMsgReceiver.subscribableChannel = subscribableChannel;
    }*/

  /*  // 1. 当subscribableChannel注入完成后完成回调
    @PostConstruct
    public void init(){
        subscribableChannel.subscribe(message -> {
            logger.info("init...." + message.getPayload());

        });
    }
    // 2. @ServiceActivator 注解 和 @StreamListener 都实现了对消息的监听，ServiceActivator 没有内置消息转换，需要自己实现转换
    //  @StreamListener 不需要自己实现，只需要在配置文件增加spring.cloud.stream.bindings.input.content-type=application/json 属性(默认支持json，json格式的可以不用配置)
    // ServiceActivator实现了一个onMessage（）方法，该方法在新消息到达时调用。
    //它实现了javax .jms.MessageListener接口，该接口由JMS规范定义。
    //JMS（Java Message Service）是一种API，它提供了创建，发送和读取消息的工具。它提供松散耦合的可靠和异步通信。
    @ServiceActivator(inputChannel = TLVProcessorChannels.Input_loraLite)
    public void message(Message<String> message){
        logger.info("@ServiceActivator -- " + message.getPayload());
    }
    //接受通道Input_loraLite的消息
    @StreamListener(TLVProcessorChannels.Input_loraLite) // 监听 Input_loraLite 通道
    public void StreamListenerMsg(Message<String> message){
        logger.info("@StreamListener -- " + message.getPayload());
    }*/

}
