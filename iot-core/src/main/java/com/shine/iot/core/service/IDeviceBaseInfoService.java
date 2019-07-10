package com.shine.iot.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.model.ServiceRsObjModel;
import com.shine.iot.core.entity.DeviceBaseInfoModel;

import java.util.List;

public interface IDeviceBaseInfoService extends IService<DeviceBaseInfoModel> {

    /**
     * 根据设备Id获取设备信息
     *
     * @param devId
     * @return
     */
    DeviceBaseInfoModel getObjById(Long devId);

    /**
     * 根据设备DevEUI获取设备信息
     *
     * @param devEUI
     * @return
     */
    DeviceBaseInfoModel getDeviceByEUI(String devEUI);

    /**
     * 根据设备条件获取设备信息，根据deviceId倒序排序
     *
     * @param deviceFilter
     * @return
     */
    ServiceRsObjModel<List<DeviceBaseInfoModel>> getDeviceByObj(DeviceBaseInfoModel deviceFilter);

    /**
     * 根据设备条件分页查询设备列表信息
     *
     * @param pageNo
     * @param deviceFilter
     * @return
     */
    ServiceRsObjModel<IPage<DeviceBaseInfoModel>> pageQueryByDevObj(int pageNo, DeviceBaseInfoModel deviceFilter);

}
