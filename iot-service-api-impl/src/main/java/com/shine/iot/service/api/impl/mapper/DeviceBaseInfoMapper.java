package com.shine.iot.service.api.impl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shine.iot.model.dto.OrgAreaDevInfoDto;
import com.shine.iot.model.entity.DeviceBaseInfoModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
@Mapper
public interface DeviceBaseInfoMapper extends BaseMapper<DeviceBaseInfoModel> {
    /**
     * 获取当前区域集合关联的设备信息列表
     *
     * @param page             分页对象，xml中可以从里面进行取值，传递参数page即可实现自动分页，放在一个参数
     * @param areaIds          区域ID集合，Set类型
     * @param deviceBaseFilter 设备实体属性
     * @return 区域设备基本信息列表
     */
    List<OrgAreaDevInfoDto> pageQueryAreaDevList(Page page, @Param(value = "areaIds") Set<Long> areaIds,
                                                 @Param(value = "device") DeviceBaseInfoModel deviceBaseFilter);
}
