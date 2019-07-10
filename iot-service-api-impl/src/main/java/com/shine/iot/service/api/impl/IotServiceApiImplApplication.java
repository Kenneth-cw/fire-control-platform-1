package com.shine.iot.service.api.impl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

//@SpringBootApplication(scanBasePackages = {"com.shine.iot"})
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class IotServiceApiImplApplication {

    public static void main(String[] args) {
        SpringApplication.run(IotServiceApiImplApplication.class, args);
        System.out.println("----- Service API implement start success! -----http://localhost:" + 8097);
    }

}
