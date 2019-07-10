package com.shine.iot.signal.monitor.feignClient;

import com.shine.iot.signal.model.dto.aug.NodeDataDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "iot-signal-db-rest", path = "signal-db/rawtlvsignalrest") // value：表示调用那个服务
public interface RawSignalOPFeignClient {


    @PostMapping(value = "save")
    Boolean saveRawSignal(@RequestBody NodeDataDto nodeDataDto);

}
