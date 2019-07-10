package com.shine.iot.signal.db.rest.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.model.ServiceRsObjModel;
import com.shine.iot.signal.db.rest.dao.DeviceAllMsgDao;
import com.shine.iot.signal.db.rest.entity.DeviceAllMsgInfoModel;
import com.shine.iot.signal.db.rest.service.DeviceAllMsgService;
import com.shine.iot.signal.model.adapter.aug.NodeDataAdapter;
import com.shine.iot.signal.model.dto.aug.NodeDataDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service(value = "deviceAllMsgServiceImpl")
public class DeviceAllMsgServiceImpl extends ServiceImpl<DeviceAllMsgDao, DeviceAllMsgInfoModel> implements DeviceAllMsgService {

    private Logger logger = LogManager.getLogger(DeviceAllMsgServiceImpl.class);

    @Override
    public ServiceRsObjModel<Boolean> savePayloadSignal(NodeDataDto nodeDataDto) {
        ServiceRsObjModel<Boolean> saveResult = new ServiceRsObjModel<>();
        saveResult.setSuccess(false);

        NodeDataAdapter nodeDataAdapter = nodeDataDto.getNodeDataAdapter();
        if (nodeDataDto != null && nodeDataAdapter != null) {
            DeviceAllMsgInfoModel allMsgModel = new DeviceAllMsgInfoModel();
            allMsgModel.setMcuID(nodeDataAdapter.getMcuIDHex().toUpperCase());
            if (nodeDataAdapter.isAck()) {
                allMsgModel.setIsAck("1");
                allMsgModel.setMsgDirect("D");
            } else {
                allMsgModel.setIsAck("0");
                allMsgModel.setMsgDirect("U");
            }
            allMsgModel.setMsgType(nodeDataAdapter.getCmdType());
            allMsgModel.setLoraFcnt(nodeDataAdapter.getLoraFrameCounter());
            if (nodeDataDto.getRcvTime() != null) {
                allMsgModel.setMsgTime(nodeDataDto.getRcvTime());
            } else {
                allMsgModel.setMsgTime(Calendar.getInstance().getTime());
            }
            allMsgModel.setPayloadData(nodeDataDto.getRawPayload());
            int insertNum = baseMapper.insert(allMsgModel);
            if (insertNum > 0) {
                saveResult.setSuccess(true);
                saveResult.setRsData(true);
            }
        } else {
            logger.debug("payload节点信号数据为null，不进行payload数据-allmsg信息表保存！");
        }
        return saveResult;
    }
}
