package com.shine.iot.signal.db.rest.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.model.ServiceRsObjModel;
import com.shine.iot.signal.db.rest.entity.RawTLVSignalInfoModel;
import com.shine.iot.signal.model.dto.aug.NodeDataDto;

public interface RawTLVSignalService extends IService<RawTLVSignalInfoModel> {

    /**
     * 保存原始信号信息到表rawSignalInfo中
     *
     * @param nodeDataDto 原始信号的数据传输类
     * @return 原始数据是否存储成功
     */
    ServiceRsObjModel<Boolean> saveRawSignal(NodeDataDto nodeDataDto);

}
