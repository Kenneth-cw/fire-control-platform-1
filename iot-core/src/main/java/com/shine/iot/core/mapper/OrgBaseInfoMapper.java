package com.shine.iot.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shine.iot.core.entity.OrgBaseInfoModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface OrgBaseInfoMapper extends BaseMapper<OrgBaseInfoModel> {

    @Select("select * from orgbaseinfo")
    public List<OrgBaseInfoModel> testList();

    Integer queryCount(OrgBaseInfoModel orgBaseFilter);


}
