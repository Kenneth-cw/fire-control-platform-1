package com.shine.iot.signal.payload.different;

import com.shine.iot.signal.payload.different.kafkaListener.PayloadProcessorChannels;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableBinding(value = {PayloadProcessorChannels.class})
public class IotSignalPayloadDifferentApplication {

    public static void main(String[] args) {
        SpringApplication.run(IotSignalPayloadDifferentApplication.class, args);
        System.out.println("----- LoraLite signal monitor start success! -----http://localhost:" + 8072);
    }

}
