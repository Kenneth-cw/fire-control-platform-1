package com.shine.iot.signal.payload.different.kafkaListener;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface PayloadProcessorChannels {

    //输入队列
    String INPUT_LORALITE_PAYLOAD = "input-loralite-payload";
    //输出队列
    String OUTPUT_PAYLOAD_TYPE_VERSION = "output-payload-type-version";


    @Input(PayloadProcessorChannels.INPUT_LORALITE_PAYLOAD)
    SubscribableChannel inputLoraLitePayload();

    @Output(PayloadProcessorChannels.OUTPUT_PAYLOAD_TYPE_VERSION)
    MessageChannel outputPayloadDevTypeVersion();

}
