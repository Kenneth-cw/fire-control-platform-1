package com.shine.iot.service.api.impl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shine.iot.model.entity.AreaInfoModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
@Mapper
public interface AreaBaseInfoMapper extends BaseMapper<AreaInfoModel> {
    /**
     * 获取当前区域的后代区域ID集合
     *
     * @param areaId 区域ID
     * @return 后代区域ID集合，包含当前节点
     */
    Set<Long> getOrgAreaIds(@Param(value = "areaId") Long areaId);


}
