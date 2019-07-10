package com.shine.iot.model;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IotModelApplication {
    public static void main(String[] args) {
        SpringApplication.run(IotModelApplication.class, args);
        System.out.println("----- Eureka server start success! -----http://localhost:8085/");
    }
}
