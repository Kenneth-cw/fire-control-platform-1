package com.shine.iot.platform.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shine.iot.model.entity.CustomerAreaMapInfoModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface CustomerAreaMapMapper extends BaseMapper<CustomerAreaMapInfoModel> {
}
