package com.shine.iot.signal.db.rest.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.model.ServiceRsObjModel;
import com.shine.iot.signal.db.rest.dao.RawTLVSignalDao;
import com.shine.iot.signal.db.rest.entity.RawTLVSignalInfoModel;
import com.shine.iot.signal.db.rest.service.RawTLVSignalService;
import com.shine.iot.signal.model.dto.aug.NodeDataDto;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service(value = "rawTLVSignalServiceImpl")
public class RawTLVSignalServiceImpl extends ServiceImpl<RawTLVSignalDao, RawTLVSignalInfoModel> implements RawTLVSignalService {

    private Logger logger = LogManager.getLogger(RawTLVSignalServiceImpl.class);

    //保存原始信号信息到表rawSignalInfo中
    @Override
    public ServiceRsObjModel<Boolean> saveRawSignal(NodeDataDto nodeDataDto) {
        ServiceRsObjModel<Boolean> saveResult = new ServiceRsObjModel<>();
        saveResult.setSuccess(false);

        if (nodeDataDto != null && StringUtils.isNotBlank(nodeDataDto.getRawPayload())) {
            // 封装信息到实体bean中
            RawTLVSignalInfoModel rawModel = new RawTLVSignalInfoModel();
            rawModel.setRawSignal(nodeDataDto.getRawPayload());
            rawModel.setGwIP(nodeDataDto.getGwIP());
            rawModel.setGwPort(nodeDataDto.getGwPort());
            rawModel.setMacAddress(nodeDataDto.getGwId());
            if (nodeDataDto.getRcvTime() != null) {
                rawModel.setRcvTime(nodeDataDto.getRcvTime());
            } else {
                rawModel.setRcvTime(new Date());
                rawModel.setNote("监听原始信号时传递的接收时间参数为null，数据库进行保存时，new Date");
            }
            // 将原始信号信息记录，新增到数据库表中
            try {
                int insertNum = baseMapper.insert(rawModel);
                if (insertNum > 0) {
                    saveResult.setSuccess(true);
                    saveResult.setRsData(true);
                }
            } catch (Exception e) {
                logger.error("操作数据库新增原始数据报错，错误信息：" + e.getMessage(), e);
                e.printStackTrace();
            }
        } else {
            logger.debug("保存原始信号信息时，发现参数rawSignal为null，所以不进行原始数据存储！");
        }
        return saveResult;
    }


}
