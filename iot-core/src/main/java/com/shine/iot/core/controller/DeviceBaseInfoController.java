package com.shine.iot.core.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.model.ServiceRsObjModel;
import com.shine.iot.core.entity.DeviceBaseInfoModel;
import com.shine.iot.core.service.IDeviceBaseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dev-base-data")
public class DeviceBaseInfoController {

    //IDEA不推荐使用field反射注入属性，本项目选择构造器方法注入
    @Autowired
    private IDeviceBaseInfoService deviceBaseInfoService;

    public void setDeviceBaseInfoService(IDeviceBaseInfoService deviceBaseInfoService) {
        this.deviceBaseInfoService = deviceBaseInfoService;
    }
    /*public DeviceBaseInfoController(IDeviceBaseInfoService deviceBaseInfoService) {
        this.deviceBaseInfoService = deviceBaseInfoService;
    }*/

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
     * 获取所有设备表中的设备数据
     *
     * @param deviceFilter 有效参数必须为设备实体属性，这些参数作为过滤条件，筛选符合条件的设备列表
     * @return 设备信息列表
     */
    @GetMapping("/devs")
    public List<DeviceBaseInfoModel> getDevices(@ModelAttribute DeviceBaseInfoModel deviceFilter) {
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
    @GetMapping("/dev/page/{pageNo}")
    public IPage<DeviceBaseInfoModel> pageQueryByDevObj(@PathVariable int pageNo, @ModelAttribute DeviceBaseInfoModel deviceFilter) {
        ServiceRsObjModel<IPage<DeviceBaseInfoModel>> pageResult = deviceBaseInfoService.pageQueryByDevObj(pageNo, deviceFilter);
        return pageResult.getRsData();
    }

    /*public List<OrgBaseInfoModel> getLogOrgList(){
        HttpServletRequest request = ServletRequestAttributes.
    }*/
}
