package com.shine.iot.platform.service.rest;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.model.ServiceRsObjModel;
import com.shine.iot.model.entity.DeviceBaseInfoModel;
import com.shine.iot.platform.service.serviceLogic.IDeviceBaseInfoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dev-base-data")
public class DeviceBaseInfoController {

    private Logger logger = LogManager.getLogger(DeviceBaseInfoController.class);

    //IDEA不推荐使用field反射注入属性，本项目选择构造器方法注入
    private IDeviceBaseInfoService deviceBaseInfoService;

    @Autowired
    public void setDeviceBaseInfoService(IDeviceBaseInfoService deviceBaseInfoService) {
        this.deviceBaseInfoService = deviceBaseInfoService;
    }

    /**
     * 根据设备ID获取设备信息
     *
     * @param id 设备的唯一标识设备ID
     * @return 返回当前ID的设备信息记录
     */
    @GetMapping("/dev/{id}")
    public DeviceBaseInfoModel getDeviceById(@PathVariable Long id) {
        return deviceBaseInfoService.getById(id);
    }

    /**
     * 根据设备MCUID查询设备信息
     *
     * @param mcuId 设备mcuId
     * @return 设备详细信息
     */
    @GetMapping("/dev/mcuId")
    public DeviceBaseInfoModel getDeviceByMcuId(@RequestParam(value = "mcuId") String mcuId) {
        return deviceBaseInfoService.getDeviceByMcuId(mcuId);
    }

    /**
     * 获取所有设备表中的设备数据
     *
     * @param deviceFilter 有效参数必须为设备实体属性，这些参数作为过滤条件，筛选符合条件的设备列表
     * @return 设备信息列表
     */
    @PostMapping("/devs")
    public List<DeviceBaseInfoModel> getDevices(@RequestBody(required = false) DeviceBaseInfoModel deviceFilter) {
        ServiceRsObjModel<List<DeviceBaseInfoModel>> listResult = deviceBaseInfoService.getDeviceByObj(deviceFilter);
        return listResult.getRsData();
    }

    /**
     * 分页获取所有设备表中的数据
     *
     * @param pageNo       当前页，如果<=0，则查询第一页
     * @param deviceFilter 有效参数必须为设备实体属性
     * @return 设备分页信息，包括总数、当前页、总页、设备列表信息，字段为空的属性忽略
     */
    @PostMapping("/dev/page/{pageNo}")
    public Page<DeviceBaseInfoModel> pageQueryByDevObj(@PathVariable int pageNo, @RequestBody(required = false) DeviceBaseInfoModel deviceFilter) {
        ServiceRsObjModel<Page<DeviceBaseInfoModel>> pageResult = deviceBaseInfoService.pageQueryByDevObj(pageNo, deviceFilter);
        return pageResult.getRsData();
    }

    /**
     * 根据设备ID，更新设备基本信息
     *
     * @param deviceObj 设备实体类信息，必须要有deviceId信息
     * @return 更新后的设备实体信息
     */
    @PatchMapping("/dev/up")
    public DeviceBaseInfoModel updateObjByPK(DeviceBaseInfoModel deviceObj) {
        //boolean upResult = deviceBaseInfoService.update(deviceObj, new UpdateWrapper<DeviceBaseInfoModel>().setEntity(deviceObj));
        ServiceRsObjModel<DeviceBaseInfoModel> upResult = deviceBaseInfoService.updateObjByPK(deviceObj);
        if (upResult.isSuccess()) {
            return upResult.getRsData();
        } else {
            return null;
        }
    }

    /**
     * 新增一个设备信息进入deviceBase表中
     *
     * @param deviceObj 设备实体类信息
     * @return 设备信息
     */
    @PutMapping("/dev/save")
    public DeviceBaseInfoModel insertObj(DeviceBaseInfoModel deviceObj) {
       /* boolean saveResult = deviceBaseInfoService.save(deviceObj);
        if (saveResult) {
            logger.info("新增设备信息成功");
        } else {
            logger.error("新增设备信息失败，请注意检查！");
        }
        return deviceObj;*/

        ServiceRsObjModel<DeviceBaseInfoModel> saveResult = deviceBaseInfoService.saveObj(deviceObj);
        if (saveResult.isSuccess()) {
            return saveResult.getRsData();
        } else {
            return null;
        }
    }

    /**
     * 新增 或 更改 设备信息（存在deviceId则进行数据更新，否则进行数据新增）
     *
     * @param deviceObj 设备实体类信息
     * @return 设备信息
     */
    @PostMapping("/dev/uporsave")
    public DeviceBaseInfoModel saveOrUpdateObj(@RequestBody DeviceBaseInfoModel deviceObj) {
        if (deviceObj != null) {
            boolean saveOrUpdateRs = deviceBaseInfoService.saveOrUpdate(deviceObj);
            if (saveOrUpdateRs) {
                logger.info("新增或更改设备信息成功");
                if (deviceObj.getDeviceId() != null) {
                    deviceObj = deviceBaseInfoService.getById(deviceObj.getDeviceId());
                }
            } else {
                logger.error("新增或更改设备信息失败，请注意检查！");
            }
        }
        return deviceObj;
    }


}
