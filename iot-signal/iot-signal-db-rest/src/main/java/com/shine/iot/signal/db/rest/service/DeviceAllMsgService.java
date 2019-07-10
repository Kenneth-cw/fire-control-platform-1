package com.shine.iot.signal.db.rest.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.model.ServiceRsObjModel;
import com.shine.iot.signal.db.rest.entity.DeviceAllMsgInfoModel;
import com.shine.iot.signal.model.dto.aug.NodeDataDto;

public interface DeviceAllMsgService extends IService<DeviceAllMsgInfoModel> {

    /**
     * 保存payload数据、ACK信息
     *
     * @param nodeDataDto NodeDataDto
     * @return 是否成功保存payload数据
     */
    ServiceRsObjModel<Boolean> savePayloadSignal(NodeDataDto nodeDataDto);
}
