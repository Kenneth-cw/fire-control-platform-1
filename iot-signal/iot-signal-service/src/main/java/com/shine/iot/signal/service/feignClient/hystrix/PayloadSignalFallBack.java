package com.shine.iot.signal.service.feignClient.hystrix;

import com.shine.iot.signal.model.dto.aug.NodeDataDto;
import com.shine.iot.signal.service.feignClient.PayloadSignalOPFeignClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class PayloadSignalFallBack implements PayloadSignalOPFeignClient {

    private Logger logger = LogManager.getLogger(PayloadSignalFallBack.class);

    @Override
    public Boolean dealGeneralJsonData(String generalJsonData) {
        logger.debug("保存通用统一的JSON数据时，请求数据库操作模块iot-signal-db-rest超时...");
        return null;
    }

    @Override
    public Boolean savePayloadSignal(NodeDataDto nodeDataDto) {
        return null;
    }
}
