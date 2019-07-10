package com.shine.iot.service.api.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.model.ServiceRsObjModel;
import com.shine.iot.model.dto.OrgAreaDevInfoDto;
import com.shine.iot.model.entity.DeviceBaseInfoModel;
import com.shine.iot.model.shine.utils.PageFieldEnum;
import com.shine.iot.service.api.impl.mapper.AreaBaseInfoMapper;
import com.shine.iot.service.api.impl.mapper.DeviceBaseInfoMapper;
import com.shine.iot.service.api.service.IOrgBaseInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IotServiceApiImplApplicationTests {

    @Autowired
    private AreaBaseInfoMapper areaMapper;
    @Autowired
    private DeviceBaseInfoMapper deviceMapper;
    @Autowired
    private IOrgBaseInfoService orgService;

    @Test
    public void areaNodelTest() {
        Set<Long> orgAreaIds = areaMapper.getOrgAreaIds(2L);
        orgAreaIds.forEach(System.out::println);
    }

    @Test
    public void areaDeviceTest() {
        Set<Long> areaIds = areaMapper.getOrgAreaIds(112L);
        areaIds.forEach(System.out::print);

        Page<OrgAreaDevInfoDto> page = new Page<>(1, PageFieldEnum.SIZE_TEN.getValue());
        List<OrgAreaDevInfoDto> pageList = deviceMapper.pageQueryAreaDevList(page, areaIds, new DeviceBaseInfoModel());
        pageList.forEach(System.out::println);
    }

    @Test
    public void pageQueryOrgDevList() {
        ServiceRsObjModel<Page<OrgAreaDevInfoDto>> rs = orgService.pageQueryOrgDevList(1, 112L, new DeviceBaseInfoModel());
        Page<OrgAreaDevInfoDto> page = rs.getRsData();
        System.out.println("cur:" + page.getCurrent());
        System.out.println("total:" + page.getTotal());
        System.out.println("pageSize:" + page.getSize());
        System.out.println("pages:" + page.getPages());
        page.getRecords().forEach(System.out::println);
    }

    @Test
    public void contextLoads() {
    }

}
