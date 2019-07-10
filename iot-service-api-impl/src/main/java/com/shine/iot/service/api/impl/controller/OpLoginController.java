package com.shine.iot.service.api.impl.controller;

import com.shine.iot.service.api.service.IOpLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oplogin-base-data")
public class OpLoginController {

    private IOpLoginService opLoginService;

    @Autowired
    private void setOpLoginService(IOpLoginService opLoginService) {
        this.opLoginService = opLoginService;
    }


}
