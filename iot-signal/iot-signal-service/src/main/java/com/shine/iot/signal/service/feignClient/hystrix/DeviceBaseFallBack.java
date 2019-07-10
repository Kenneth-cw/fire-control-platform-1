package com.shine.iot.signal.service.feignClient.hystrix;

import com.shine.iot.model.entity.DeviceBaseInfoModel;
import com.shine.iot.signal.service.feignClient.DeviceBaseFeignClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DeviceBaseFallBack implements DeviceBaseFeignClient {

    private Logger logger = LogManager.getLogger(DeviceBaseFallBack.class);

    @Override
    public DeviceBaseInfoModel getObjById(Long id) {
        logger.debug("根据device的ID【" + id + "】获取deviceInfo，请求超时...");
        return null;
    }

    @Override
    public DeviceBaseInfoModel getDeviceByMcuId(String mcuId) {
        logger.debug("根据device的mcuId【" + mcuId + "】获取deviceInfo，请求超时...");
        return null;
    }

    @Override
    public List<DeviceBaseInfoModel> getDevices(DeviceBaseInfoModel deviceFilter) {
        return null;
    }

    @Override
    public DeviceBaseInfoModel updateObjByPK(DeviceBaseInfoModel deviceObj) {
        logger.debug("更新数据库deviceBase表，请求超时...");
        return null;
    }

    @Override
    public DeviceBaseInfoModel insertObj(DeviceBaseInfoModel deviceObj) {
        logger.debug("新增数据到数据库的deviceBase表，请求超时...");
        return null;
    }

    @Override
    public DeviceBaseInfoModel saveOrUpdateObj(DeviceBaseInfoModel deviceObj) {
        logger.debug("保存或者更改deviceBase信息，请求超时...");
        return null;
    }
}
