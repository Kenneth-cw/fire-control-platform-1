package com.shine.iot.platform.service.serviceLogic;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.model.ServiceRsObjModel;
import com.shine.iot.model.entity.OpLoginModel;

public interface IOpLoginService extends IService<OpLoginModel> {

    ServiceRsObjModel<OpLoginModel> login(String userName, String password);
}
