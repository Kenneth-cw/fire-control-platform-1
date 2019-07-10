package com.shine.iot.platform.service.rest;

import com.platform.common.encryption.Md5;
import com.platform.model.ServiceRsObjModel;
import com.shine.iot.model.entity.OpLoginModel;
import com.shine.iot.platform.service.serviceLogic.IOpLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oplogin")
public class OpLoginController {

    private IOpLoginService opLoginService;

    @Autowired
    private void setOpLoginService(IOpLoginService opLoginService) {
        this.opLoginService = opLoginService;
    }

    @GetMapping("/login")
    public OpLoginModel login(String userName, String password) {

        ServiceRsObjModel<OpLoginModel> loginRs = opLoginService.login(userName, Md5.encode(password));
        if (loginRs.isSuccess()) {
            return loginRs.getRsData();
        } else {
            return null;
        }
    }


}
