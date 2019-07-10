package com.shine.iot.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class IotEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(IotEurekaApplication.class, args);
        System.out.println("----- Eureka server start success! -----http://localhost:8090/");
    }

}
