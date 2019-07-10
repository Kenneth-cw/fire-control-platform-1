package com.shine.iot.signal.parser.kafkaListener;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * LoraLite TLV消息的 输入通道
 */
public interface TLVProcessorChannels {
    // 接收队列
    String INPUT_AUGTLV = "input-augTLV";
    // 输出队列
    String OUTPUT_PAYLOAD = "output_payload";

    @Input(TLVProcessorChannels.INPUT_AUGTLV)
    SubscribableChannel inputAugTlv();

    @Output(TLVProcessorChannels.OUTPUT_PAYLOAD)
    MessageChannel outputPayload();


}
