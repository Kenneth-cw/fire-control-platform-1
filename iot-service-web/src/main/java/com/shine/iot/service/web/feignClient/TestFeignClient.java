package com.shine.iot.service.web.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "iot-signal-db-rest", path = "signal-db/payloadsignalrest")
public interface TestFeignClient {

    @GetMapping(value = "get/{mcuId}")
    Integer testQuery(@PathVariable(value = "mcuId") String mcuId);


}
