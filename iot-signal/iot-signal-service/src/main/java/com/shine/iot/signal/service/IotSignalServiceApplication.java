package com.shine.iot.signal.service;

import com.shine.iot.signal.service.kafkaListener.GeneralJsonSinkChannels;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableBinding(value = {GeneralJsonSinkChannels.class}) //kafka 通道绑定
@EnableCircuitBreaker //启动断路器、开启熔断
public class IotSignalServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(IotSignalServiceApplication.class, args);
        System.out.println("----- LoraLite signal parser start success! -----http://localhost:" + 8074);
    }

}
