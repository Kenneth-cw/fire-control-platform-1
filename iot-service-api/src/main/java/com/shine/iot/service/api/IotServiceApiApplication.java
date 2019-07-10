package com.shine.iot.service.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.shine.iot.service.api"})
@EnableEurekaClient
@EnableFeignClients //开启feign客户端实现RPC调用
@EnableCircuitBreaker //开启断路器
//@SpringCloudApplication //相当于 @EnableDiscoveryClient +@SpringBootApplication＋@EnableCircuitBreaker
public class IotServiceApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(IotServiceApiApplication.class, args);
        System.out.println("----- Eureka server start success! -----http://localhost:8096/");
    }

}
