package com.shine.iot.signal.db.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class IotSignalDbRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(IotSignalDbRestApplication.class, args);
        System.out.println("----- LoraLite signal db rest start success! -----http://localhost:" + 8075);
    }

}
