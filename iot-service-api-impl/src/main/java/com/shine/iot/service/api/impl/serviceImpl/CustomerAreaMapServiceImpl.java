package com.shine.iot.service.api.impl.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shine.iot.model.entity.CustomerAreaMapInfoModel;
import com.shine.iot.service.api.impl.mapper.CustomerAreaMapMapper;
import com.shine.iot.service.api.service.ICustomerAreaMapService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service("cutomerAreaMapServiceImpl")
public class CustomerAreaMapServiceImpl extends ServiceImpl<CustomerAreaMapMapper, CustomerAreaMapInfoModel> implements ICustomerAreaMapService, Serializable {

    private static final long serialVersionUID = 1796893387950158021L;
    private Logger logger = LogManager.getLogger(CustomerAreaMapServiceImpl.class);

    @Autowired
    private CustomerAreaMapMapper custAreaMapMapper;

    private void setCustAreaMapMapper(CustomerAreaMapMapper custAreaMapMapper) {
        this.custAreaMapMapper = custAreaMapMapper;
    }

}
