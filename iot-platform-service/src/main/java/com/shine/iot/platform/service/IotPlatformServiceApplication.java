package com.shine.iot.platform.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class IotPlatformServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(IotPlatformServiceApplication.class, args);
        System.out.println("----- Eureka server start success! -----http://localhost:8097/");
    }

}
