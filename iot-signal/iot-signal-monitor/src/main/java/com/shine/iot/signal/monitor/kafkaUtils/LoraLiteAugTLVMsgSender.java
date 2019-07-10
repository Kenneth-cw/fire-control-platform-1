package com.shine.iot.signal.monitor.kafkaUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class LoraLiteAugTLVMsgSender {
    private final static Logger logger = LogManager.getLogger(LoraLiteAugTLVMsgSender.class);

    /**
     * 访问绑定通道，分为两种方式：
     * 1.
     * 2. 通过直接注入通道的方式 发送kafka消息
     */

    //通过直接注入通道的方式 发送kafka消息

    private static MessageChannel messageChannel;

    @Autowired
    public void setSendSourceChannel(@Qualifier(TLVSourceChannels.OUTPUT_LORALITE_AUGTLV) MessageChannel messageChannel) {
        //通过@Qualifier指定消息通道的名称，通道的名字在声明的注解SourceChannels上自定义了,为outLoraLite
        LoraLiteAugTLVMsgSender.messageChannel = messageChannel;
    }

    /**
     * 向kafka发送String类型消息
     *
     * @param message 发送到kafka的消息体
     * @return 是否发送成功
     */
    public static boolean sendMessage(String message) {
        logger.info("Sending kafka message " + message);
        return messageChannel.send(MessageBuilder.withPayload(message).build());
    }

    /**
     * 向kafka发送 Map集合 类型消息
     *
     * @param message map集合参数
     * @return 是否发送成功
     */
    public static boolean sendMapMessage(Map<String, Object> message) {
        logger.info("Sending kafka TLV message：" + message);
        boolean flag = false;
        try {
            flag = messageChannel.send(MessageBuilder.withPayload(message).build());
        } catch (Exception e) {
            logger.error("Kafka message sent failed, because:" + e);
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 向kafka发送 Object 类型消息
     *
     * @param objModel 消息体
     * @return 是否发送成功
     */
    public static boolean sendObjMessage(Object objModel) {
        logger.info("Sending kafka Object message：" + objModel);
        return messageChannel.send(MessageBuilder.withPayload(objModel).build());
    }

}
