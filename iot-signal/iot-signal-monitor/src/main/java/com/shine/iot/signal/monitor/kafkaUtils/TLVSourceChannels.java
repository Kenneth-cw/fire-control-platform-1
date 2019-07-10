package com.shine.iot.signal.monitor.kafkaUtils;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * 自定义 kafka 输出通道接口，并绑定输出通道配置信息
 */
public interface TLVSourceChannels {
    /**
     * 发消息的通道名称
     * 跟yml配置文件里面的通道名称 outLoraLite 保持一致
     */
    String OUTPUT_LORALITE_AUGTLV = "output-augTLV";

    /**
     * 发消息的通道
     *
     * @return 消息通道 MessageChannel
     */
    @Output(TLVSourceChannels.OUTPUT_LORALITE_AUGTLV)
    MessageChannel outputLoraLite();
}
