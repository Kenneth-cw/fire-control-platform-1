package com.shine.iot.service.api.fegin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shine.iot.model.entity.DeviceBaseInfoModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "iot-service-api-impl", path = "dev-base-data")
@Component
public interface DeviceBaseFeignService {

    /**
     * 根据设备ID获取设备信息
     *
     * @param id 设备的唯一标识设备ID
     * @return 设备详细信息
     */
    @GetMapping(value = "/dev/{id}")
    DeviceBaseInfoModel getObjById(@PathVariable("id") Long id);

    /**
     * 获取所有设备表中的设备数据
     *
     * @param deviceFilter 设备实体属性
     * @return 设备信息列表
     */
    @PostMapping("/devs")
    List<DeviceBaseInfoModel> getDevices(@RequestBody(required = false) DeviceBaseInfoModel deviceFilter);

    /**
     * 分页获取设备表中所有的数据
     *
     * @param pageNo       当前页，如果<=0，则查询第一页
     * @param deviceFilter 设备实体属性
     * @return 设备分页信息，包括总数、当前页、总页、设备列表信息，字段为空的属性忽略
     */
    @PostMapping("/dev/page/{pageNo}")
    Page<DeviceBaseInfoModel> pageQueryByDevObj(@PathVariable("pageNo") int pageNo, @RequestBody(required = false) DeviceBaseInfoModel deviceFilter);

}
