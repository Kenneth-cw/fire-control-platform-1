package com.shine.iot.signal.db.rest.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shine.iot.signal.db.rest.entity.DeviceAllMsgInfoModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository(value = "deviceAllMsgDao")
@Mapper
public interface DeviceAllMsgDao extends BaseMapper<DeviceAllMsgInfoModel> {


}
