package com.shine.iot.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class IotWebFireApplication {

    public static void main(String[] args) {
        SpringApplication.run(IotWebFireApplication.class, args);
        System.out.println("----- Eureka server start success! -----http://localhost:8098/");
    }

}
