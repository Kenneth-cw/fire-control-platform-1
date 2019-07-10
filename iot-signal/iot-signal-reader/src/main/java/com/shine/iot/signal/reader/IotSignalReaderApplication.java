package com.shine.iot.signal.reader;

import com.shine.iot.signal.reader.kafkaListener.PayloadProcessorChannels;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableBinding(value = {PayloadProcessorChannels.class}) //开启绑定功能，绑定kafka输入通道
public class IotSignalReaderApplication {

    public static void main(String[] args) {
        SpringApplication.run(IotSignalReaderApplication.class, args);
        System.out.println("----- LoraLite signal parser start success! -----http://localhost:" + 8072);
    }

}
