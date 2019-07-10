package com.shine.iot.signal.db.rest.web;

import com.platform.model.ServiceRsObjModel;
import com.shine.iot.signal.db.rest.service.RawTLVSignalService;
import com.shine.iot.signal.model.dto.aug.NodeDataDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rawtlvsignalrest")
public class RawTLVSignalRest {

    private Logger logger = LogManager.getLogger(RawTLVSignalRest.class);

    private RawTLVSignalService rawSignalService;

    @Autowired
    private void setRawSignalService(RawTLVSignalService rawSignalService) {
        this.rawSignalService = rawSignalService;
    }

    //保存监听到的原始信号信息
   /* @PostMapping(value = "save")
    public Boolean saveRawSignal(Date rcvTime, String rawSignal, String gwIP, Integer gwPort) {
        ServiceRsObjModel<Boolean> saveResult = rawSignalService.saveRawSignal(rcvTime, rawSignal, gwIP, gwPort);
        if (saveResult.isSuccess()) {
            return saveResult.getRsData();
        } else {
            return false;
        }
    }*/

    @PostMapping(value = "save")
    public Boolean saveRawSignal(@RequestBody NodeDataDto nodeDataDto) {
        ServiceRsObjModel<Boolean> saveResult = rawSignalService.saveRawSignal(nodeDataDto);
        if (saveResult.isSuccess()) {
            return saveResult.getRsData();
        } else {
            return false;
        }
    }

}
