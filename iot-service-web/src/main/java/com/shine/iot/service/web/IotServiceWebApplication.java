package com.shine.iot.service.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

//@SpringCloudApplication //相当于 @EnableDiscoveryClient +@SpringBootApplication＋@EnableCircuitBreaker
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableCircuitBreaker //启动断路器
public class IotServiceWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(IotServiceWebApplication.class, args);
        System.out.println("----- Eureka server start success! -----http://localhost:8098/");
    }

}
