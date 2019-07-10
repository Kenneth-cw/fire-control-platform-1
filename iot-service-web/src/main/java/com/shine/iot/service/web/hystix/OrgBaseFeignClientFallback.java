package com.shine.iot.service.web.hystix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shine.iot.model.dto.OrgAreaDevInfoDto;
import com.shine.iot.model.entity.DeviceBaseInfoModel;
import com.shine.iot.model.entity.OrgBaseInfoModel;
import com.shine.iot.service.web.feignClient.OrgBaseFeignClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "orgBaseFeignClientFallback")
public class OrgBaseFeignClientFallback implements OrgBaseFeignClient {
    private Logger logger = LogManager.getLogger(OrgBaseFeignClientFallback.class);

    @Override
    public OrgBaseInfoModel getOne(Long id) {
        logger.debug("请求超时...");
        return null;
    }

    @Override
    public List<OrgBaseInfoModel> queryListByObj(OrgBaseInfoModel orgBaseFilter) {
        return null;
    }

    @Override
    public Page<OrgBaseInfoModel> pageQuery(int pageNo, OrgBaseInfoModel orgBaseFilter) {
        return null;
    }

    @Override
    public Page<OrgAreaDevInfoDto> pageQueryOrgDevList(int pageNo, Long orgId, DeviceBaseInfoModel deviceBaseFilter) {
        return null;
    }

    @Override
    public OrgBaseInfoModel saveOrgBaseInfo(OrgBaseInfoModel orgBaseInfoModel) {
        return null;
    }

    @Override
    public OrgBaseInfoModel removeOrgBaseInfo(Long orgId) {
        return null;
    }
}
