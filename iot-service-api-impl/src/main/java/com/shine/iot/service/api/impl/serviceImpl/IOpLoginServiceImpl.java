package com.shine.iot.service.api.impl.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shine.iot.model.entity.OpLoginModel;
import com.shine.iot.service.api.impl.mapper.OpLoginMapper;
import com.shine.iot.service.api.service.IOpLoginService;
import org.springframework.stereotype.Service;

@Service
public class IOpLoginServiceImpl extends ServiceImpl<OpLoginMapper, OpLoginModel> implements IOpLoginService {

}
