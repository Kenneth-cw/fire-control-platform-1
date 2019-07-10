package com.shine.iot.signal.db.rest.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.model.ServiceRsObjModel;
import com.shine.iot.signal.db.rest.entity.DeviceAllMsgInfoModel;
import com.shine.iot.signal.db.rest.service.DeviceAllMsgService;
import com.shine.iot.signal.db.rest.service.GeneralJsonDataService;
import com.shine.iot.signal.model.dto.aug.NodeDataDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "payloadsignalrest")
public class PayloadSignalRest {

    private Logger logger = LogManager.getLogger(PayloadSignalRest.class);

    private DeviceAllMsgService devAllMsgService;
    private GeneralJsonDataService generalService;

    @Autowired
    private void setDevAllMsgService(DeviceAllMsgService devAllMsgService) {
        this.devAllMsgService = devAllMsgService;
    }

    @Autowired
    private void setGeneralService(GeneralJsonDataService generalService) {
        this.generalService = generalService;
    }

    @PostMapping(value = "save")
    public Boolean savePayloadSignal(@RequestBody NodeDataDto nodeDataDto) {
        ServiceRsObjModel<Boolean> saveResult = devAllMsgService.savePayloadSignal(nodeDataDto);
        if (saveResult.isSuccess()) {
            return saveResult.getRsData();
        } else {
            logger.error(saveResult.getRsMsg());
            return false;
        }
    }

    @PostMapping(value = "save/generaldata")
    public Boolean dealGeneralJsonData(@RequestParam(value = "generalJsonData") String generalJsonData) {
        ServiceRsObjModel<Boolean> saveResult = generalService.saveGeneralJsonData(generalJsonData);
        if (saveResult.isSuccess()) {
            return saveResult.getRsData();
        } else {
            logger.error(saveResult.getRsMsg());
            return false;
        }
    }

    //*******************  test  ********************

    @GetMapping(value = "get/{mcuId}")
    public Integer testQuery(@PathVariable(value = "mcuId") String mcuId) {

        BaseMapper<DeviceAllMsgInfoModel> baseMapper = devAllMsgService.getBaseMapper();
        List<DeviceAllMsgInfoModel> List = baseMapper.selectList(new QueryWrapper<DeviceAllMsgInfoModel>().eq("mcuID", mcuId));

        return List.size();
    }
}
