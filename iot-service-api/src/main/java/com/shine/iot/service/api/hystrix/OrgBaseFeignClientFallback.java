package com.shine.iot.service.api.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shine.iot.model.dto.OrgAreaDevInfoDto;
import com.shine.iot.model.entity.DeviceBaseInfoModel;
import com.shine.iot.model.entity.OrgBaseInfoModel;
import com.shine.iot.service.api.fegin.OrgBaseFeignService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrgBaseFeignClientFallback implements OrgBaseFeignService {

    @Override
    public OrgBaseInfoModel getOne(Long id) {

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
