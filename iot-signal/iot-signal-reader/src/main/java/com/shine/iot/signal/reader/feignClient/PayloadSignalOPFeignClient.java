package com.shine.iot.signal.reader.feignClient;

import com.shine.iot.signal.model.dto.aug.NodeDataDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "iot-signal-db-rest", path = "signal-db/payloadsignalrest")
public interface PayloadSignalOPFeignClient {

    @PostMapping(value = "save")
    Boolean savePayloadSignal(@RequestBody NodeDataDto nodeDataDto);

    @PostMapping(value = "save/generaldata")
    Boolean dealGeneralJsonData(@RequestParam(value = "generalJsonData") String generalJsonData);
}
