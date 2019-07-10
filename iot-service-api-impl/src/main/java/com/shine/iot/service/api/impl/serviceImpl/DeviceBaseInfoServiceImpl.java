package com.shine.iot.service.api.impl.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.model.ServiceRsObjModel;
import com.shine.iot.model.entity.DeviceBaseInfoModel;
import com.shine.iot.model.shine.utils.PageFieldEnum;
import com.shine.iot.service.api.impl.mapper.DeviceBaseInfoMapper;
import com.shine.iot.service.api.service.IDeviceBaseInfoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Primary
@Service(value = "deviceBaseServiceImpl")
public class DeviceBaseInfoServiceImpl extends ServiceImpl<DeviceBaseInfoMapper, DeviceBaseInfoModel> implements IDeviceBaseInfoService, Serializable {
    private static final long serialVersionUID = -3100552805934832234L;
    private Logger logger = LogManager.getLogger(DeviceBaseInfoServiceImpl.class);

    @Autowired
    private DeviceBaseInfoMapper deviceMapper;

    public void setDeviceMapper(DeviceBaseInfoMapper deviceMapper) {
        this.deviceMapper = deviceMapper;
    }

    @Override
    public DeviceBaseInfoModel getObjById(Long deviceId) {
        return deviceMapper.selectById(deviceId);
    }

    @Override
    public DeviceBaseInfoModel getDeviceByEUI(String devEUI) {
        /*QueryWrapper<DeviceBaseInfoModel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("devEUI", devEUI);*/
        LambdaQueryWrapper<DeviceBaseInfoModel> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DeviceBaseInfoModel::getDevEUI, devEUI);
        return deviceMapper.selectOne(lambdaQueryWrapper);
    }

    @Override
    public ServiceRsObjModel<List<DeviceBaseInfoModel>> getDeviceByObj(DeviceBaseInfoModel deviceFilter) {
        ServiceRsObjModel<List<DeviceBaseInfoModel>> listResult = new ServiceRsObjModel<>();
        listResult.setSuccess(false);
        try {
            QueryWrapper<DeviceBaseInfoModel> queryWrapper = new QueryWrapper<>();
            queryWrapper.setEntity(deviceFilter).orderByDesc("deviceId");
            List<DeviceBaseInfoModel> devList = deviceMapper.selectList(queryWrapper);
            listResult.setRsData(devList);
            listResult.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            listResult.setRsMsg(e.getMessage());
            logger.error(e.getMessage());
        }
        return listResult;
    }

    @Override
    public ServiceRsObjModel<Page<DeviceBaseInfoModel>> pageQueryByDevObj(int pageNo, DeviceBaseInfoModel deviceFilter) {
        ServiceRsObjModel<Page<DeviceBaseInfoModel>> pageResult = new ServiceRsObjModel<>();
        pageResult.setSuccess(false);
        try {
            /* 这种方式排序查询，报错
            LambdaQueryWrapper<DeviceBaseInfoModel> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.setEntity(deviceFilter);
            lambdaQueryWrapper.orderByDesc(true, DeviceBaseInfoModel::getDeviceId);
            */
            QueryWrapper<DeviceBaseInfoModel> queryWrapper = new QueryWrapper<>();
            queryWrapper.setEntity(deviceFilter).orderByDesc("deviceId");

            Page<DeviceBaseInfoModel> pageCon = new Page<>();
            pageCon.setSize(PageFieldEnum.SIZE_TEN.getValue()); //默认查询10条记录，可以不写此条件
            pageCon.setCurrent(pageNo);

            //pageCon.setSearchCount(true); // 此条件设置true，则查询分页时查询计数；设置false则不查询总数；默认为true
            Page<DeviceBaseInfoModel> devicePage = (Page<DeviceBaseInfoModel>) deviceMapper.selectPage(pageCon, queryWrapper);
            pageResult.setSuccess(true);
            pageResult.setRsData(devicePage);
        } catch (Exception e) {
            e.printStackTrace();
            pageResult.setRsMsg(e.getMessage());
            logger.error(e.getMessage());
        }
        return pageResult;
    }

}
