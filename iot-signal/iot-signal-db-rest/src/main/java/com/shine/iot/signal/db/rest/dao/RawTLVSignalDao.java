package com.shine.iot.signal.db.rest.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shine.iot.signal.db.rest.entity.RawTLVSignalInfoModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository(value = "rawTLVSignalDao")
@Mapper
public interface RawTLVSignalDao extends BaseMapper<RawTLVSignalInfoModel> {


}
