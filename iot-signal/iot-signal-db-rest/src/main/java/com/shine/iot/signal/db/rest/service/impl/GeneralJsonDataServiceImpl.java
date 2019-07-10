package com.shine.iot.signal.db.rest.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.platform.model.ServiceRsObjModel;
import com.shine.iot.signal.db.rest.dao.DeviceAllMsgDao;
import com.shine.iot.signal.db.rest.entity.DeviceAllMsgInfoModel;
import com.shine.iot.signal.db.rest.service.GeneralJsonDataService;
import com.shine.iot.signal.model.adapter.aug.NodeDataAdapter;
import com.shine.iot.signal.model.dto.aug.system.GeneralJsonModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service(value = "generalJsonDataServiceImpl")
public class GeneralJsonDataServiceImpl implements GeneralJsonDataService {

    private Logger logger = LogManager.getLogger(GeneralJsonDataServiceImpl.class);

    private DeviceAllMsgDao deviceAllMsgDao;

    @Autowired
    private void setDeviceAllMsgDao(DeviceAllMsgDao deviceAllMsgDao) {
        this.deviceAllMsgDao = deviceAllMsgDao;
    }

    @Override
    public ServiceRsObjModel<Boolean> saveGeneralJsonData(String generalJsonData) {
        ServiceRsObjModel<Boolean> saveResult = new ServiceRsObjModel<>();
        saveResult.setSuccess(false);
        try {
            //转换json字符串为JavaBean
            GeneralJsonModel jsonModel = JSONObject.parseObject(generalJsonData, GeneralJsonModel.class);
            Date rcvTime = jsonModel.getRcvTime();
            JSONObject jsonObject = (JSONObject) jsonModel.getJsonData();
            NodeDataAdapter nodeDataAdapter = jsonObject.toJavaObject(NodeDataAdapter.class); //payload节点信息对象
            if (nodeDataAdapter != null) {
                // 1.存储AllMsg信息
                DeviceAllMsgInfoModel allMsgModel = new DeviceAllMsgInfoModel();
                allMsgModel.setMcuID(nodeDataAdapter.getMcuIDHex().toUpperCase());
                allMsgModel.setMsgType(nodeDataAdapter.getCmdType());
                allMsgModel.setLoraFcnt(nodeDataAdapter.getLoraFrameCounter());
                allMsgModel.setPayloadData(jsonModel.getInitialData());
                if (nodeDataAdapter.isAck()) {
                    allMsgModel.setIsAck("1");
                    allMsgModel.setMsgDirect("D");
                } else {
                    allMsgModel.setIsAck("0");
                    allMsgModel.setMsgDirect("U");
                }
                if (rcvTime != null) {
                    allMsgModel.setMsgTime(rcvTime);
                } else {
                    allMsgModel.setMsgTime(new Date());
                }
                int insertNum = deviceAllMsgDao.insert(allMsgModel);
                if (insertNum > 0) {
                    saveResult.setSuccess(true);
                    saveResult.setRsData(true);
                }

                // 2.检查设备表中有无此设备，没有则添加此设备信息到deviceBaseInfo表中


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return saveResult;
    }

}
