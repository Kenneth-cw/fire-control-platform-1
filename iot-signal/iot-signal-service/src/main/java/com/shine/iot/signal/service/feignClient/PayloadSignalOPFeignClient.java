package com.shine.iot.signal.service.feignClient;

import com.shine.iot.signal.model.dto.aug.NodeDataDto;
import com.shine.iot.signal.service.feignClient.hystrix.PayloadSignalFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(value = "iot-signal-db-rest", path = "signal-db/payloadsignalrest", fallback = PayloadSignalFallBack.class)
public interface PayloadSignalOPFeignClient {

    @PostMapping(value = "save")
    Boolean savePayloadSignal(@RequestBody NodeDataDto nodeDataDto);

    @PostMapping(value = "save/generaldata")
    Boolean dealGeneralJsonData(@RequestParam(value = "generalJsonData") String generalJsonData);


}
