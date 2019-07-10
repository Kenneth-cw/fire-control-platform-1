package com.shine.iot.signal.parser;

import com.shine.iot.signal.parser.kafkaListener.TLVProcessorChannels;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableBinding(value = {TLVProcessorChannels.class})
public class IotSignalParserApplication {

    public static void main(String[] args) {
        SpringApplication.run(IotSignalParserApplication.class, args);
        System.out.println("----- LoraLite signal parser start success! -----http://localhost:" + 8071);
    }

}
