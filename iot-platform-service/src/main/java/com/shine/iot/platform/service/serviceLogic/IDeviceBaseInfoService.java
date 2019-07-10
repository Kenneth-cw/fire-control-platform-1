package com.shine.iot.platform.service.serviceLogic;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.model.ServiceRsObjModel;
import com.shine.iot.model.entity.DeviceBaseInfoModel;

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
     * 根据设备McuID查询DeviceBase信息
     *
     * @param mcuId McuId
     * @return
     */
    DeviceBaseInfoModel getDeviceByMcuId(String mcuId);

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
    ServiceRsObjModel<Page<DeviceBaseInfoModel>> pageQueryByDevObj(int pageNo, DeviceBaseInfoModel deviceFilter);

    /**
     * 根据设备ID更新设备信息
     *
     * @param deviceObj 设备实体类
     * @return 更新后的设备信息
     */
    ServiceRsObjModel<DeviceBaseInfoModel> updateObjByPK(DeviceBaseInfoModel deviceObj);

    ServiceRsObjModel<DeviceBaseInfoModel> saveObj(DeviceBaseInfoModel deviceObj);
}
