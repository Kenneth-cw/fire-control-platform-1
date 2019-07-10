package com.shine.iot.service.api.rest;

import com.shine.iot.model.entity.DeviceBaseInfoModel;
import com.shine.iot.service.api.fegin.DeviceBaseFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dev-base")
public class DeviceBaseRestController {

    //注入deviceService feign
    @Autowired
    private DeviceBaseFeignService deviceBaseFeignService;

    private void setDevBaseFeignService(DeviceBaseFeignService devBaseFeignService) {
        this.deviceBaseFeignService = devBaseFeignService;
    }

    @GetMapping("/{id}")
    public DeviceBaseInfoModel queryDeviceById(@PathVariable Long id) {
        return deviceBaseFeignService.getObjById(id);
    }

}
