package com.shine.iot.platform.service.serviceLogic.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.model.ServiceRsObjModel;
import com.shine.iot.model.entity.OpLoginModel;
import com.shine.iot.model.shine.utils.system.Constants;
import com.shine.iot.platform.service.mapper.OpLoginMapper;
import com.shine.iot.platform.service.serviceLogic.IOpLoginService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class OpLoginServiceImpl extends ServiceImpl<OpLoginMapper, OpLoginModel> implements IOpLoginService {

    private Logger logger = LogManager.getLogger(OpLoginServiceImpl.class);

    private MessageSource messageSource;

    @Autowired
    @Qualifier("messageSource")
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    private OpLoginMapper opLoginMapper;

    @Autowired
    public void setOpLoginMapper(OpLoginMapper opLoginMapper) {
        this.opLoginMapper = opLoginMapper;
    }


    @Override
    public ServiceRsObjModel<OpLoginModel> login(String userName, String password) {
        ServiceRsObjModel<OpLoginModel> result = new ServiceRsObjModel<OpLoginModel>();
        if (userName == null || password == null) {
            result.setSuccess(false);
            result.setRsMsg(messageSource.getMessage(Constants.SYSTEMERROR_E000002, null,
                    Locale.getDefault().CHINESE));
            return result;
        }
        try {
            OpLoginModel para = new OpLoginModel();
            para.setLoginName(userName);
            para.setPassWord(password);
            para.setIsDelete(1);
            QueryWrapper<OpLoginModel> queryWrapper = new QueryWrapper<>();
            queryWrapper.setEntity(para);
            OpLoginModel resultTemp = opLoginMapper.selectOne(queryWrapper);
//            OpLoginModel resultTemp = opLoginMapper.queryObject(para);
            if (resultTemp != null) {
                result.setSuccess(true);
                result.setRsCode(Constants.SYSTEM_000000);
                result.setRsData(resultTemp);
            } else {
                result.setSuccess(false);
                result.setRsCode(Constants.SYSTEMERROR_E000003);
                result.setRsMsg(messageSource.getMessage(Constants.SYSTEMERROR_E000003, null, Locale.getDefault().CHINESE));
            }
        } catch (Exception e) {
            logger.error("login service is exception========" + e);
            result.setRsCode(Constants.SYSTEMERROR_E000001);
            result.setRsMsg(messageSource.getMessage(Constants.SYSTEMERROR_E000001, null, Locale.getDefault().CHINESE));
            result.setSuccess(false);
        }
        return result;
    }
}
