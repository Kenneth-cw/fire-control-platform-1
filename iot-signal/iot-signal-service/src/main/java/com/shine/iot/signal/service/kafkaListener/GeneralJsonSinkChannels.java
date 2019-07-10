package com.shine.iot.signal.service.kafkaListener;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface GeneralJsonSinkChannels {

    //输入通道
    String INPUT_GENERAL_JSON = "input-general-json";

    @Input(GeneralJsonSinkChannels.INPUT_GENERAL_JSON)
    SubscribableChannel inputGeneralJson();

}
