package com.shine.iot.signal.reader.kafkaListener;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * 自定义输入通道，订阅payload数据
 */
public interface PayloadProcessorChannels {

    //输入通道
    String INPUT_PAYLOAD_TYPE_VERSION = "input-payload-type-version";
    //输出通道
    String OUTPUT_GENERAL_JSON = "output-general-json";

    @Input(PayloadProcessorChannels.INPUT_PAYLOAD_TYPE_VERSION)
    SubscribableChannel inputPayload();

    @Output(PayloadProcessorChannels.OUTPUT_GENERAL_JSON)
    MessageChannel outputGeneralJsonData();

}
