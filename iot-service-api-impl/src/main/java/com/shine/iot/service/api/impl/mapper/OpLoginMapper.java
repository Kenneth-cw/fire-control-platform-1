package com.shine.iot.service.api.impl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shine.iot.model.entity.OpLoginModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface OpLoginMapper extends BaseMapper<OpLoginModel> {


}
