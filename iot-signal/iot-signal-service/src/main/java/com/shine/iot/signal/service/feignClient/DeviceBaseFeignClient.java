package com.shine.iot.signal.service.feignClient;

import com.shine.iot.model.entity.DeviceBaseInfoModel;
import com.shine.iot.signal.service.feignClient.hystrix.DeviceBaseFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "iot-platform-service", path = "dev-base-data", fallback = DeviceBaseFallBack.class)
@Component
public interface DeviceBaseFeignClient {

    /**
     * 根据设备ID获取设备信息
     *
     * @param id 设备的唯一标识设备ID
     * @return 设备详细信息
     */
    @GetMapping(value = "/dev/{id}")
    DeviceBaseInfoModel getObjById(@PathVariable("id") Long id);

    /**
     * 根据设备MCUID查询设备信息
     *
     * @param mcuId 设备mcuId
     * @return 设备详细信息
     */
    @GetMapping("/dev/mcuId")
    DeviceBaseInfoModel getDeviceByMcuId(@RequestParam(value = "mcuId") String mcuId);

    /**
     * 获取所有设备表中的设备数据
     *
     * @param deviceFilter 设备实体属性
     * @return 设备信息列表
     */
    @PostMapping("/devs")
    List<DeviceBaseInfoModel> getDevices(@RequestBody(required = false) DeviceBaseInfoModel deviceFilter);

    @PatchMapping("/dev/up")
    DeviceBaseInfoModel updateObjByPK(DeviceBaseInfoModel deviceObj);

    @PutMapping("/dev/save")
    DeviceBaseInfoModel insertObj(DeviceBaseInfoModel deviceObj);

    /**
     * 新增 或 修改设备基本信息
     *
     * @param deviceObj 设备实体类信息
     * @return 设备信息
     */
    @PostMapping("/dev/uporsave")
    DeviceBaseInfoModel saveOrUpdateObj(@RequestBody DeviceBaseInfoModel deviceObj);

}
