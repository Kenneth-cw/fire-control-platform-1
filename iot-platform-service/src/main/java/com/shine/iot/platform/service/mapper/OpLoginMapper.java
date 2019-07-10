package com.shine.iot.platform.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shine.iot.model.entity.OpLoginModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface OpLoginMapper extends BaseMapper<OpLoginModel> {

    /**
     * 根据 OpLoginModel 实体类作为参数，查询一个登陆者信息
     * @param para OpLoginModel
     * @return OpLoginModel
     */
    //OpLoginModel queryObject(OpLoginModel para);

}
