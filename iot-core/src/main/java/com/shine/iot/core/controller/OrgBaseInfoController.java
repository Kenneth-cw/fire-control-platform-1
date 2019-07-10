package com.shine.iot.core.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.model.ServiceRsObjModel;
import com.shine.iot.core.dto.OrgAreaDevInfoDto;
import com.shine.iot.core.entity.DeviceBaseInfoModel;
import com.shine.iot.core.entity.OrgBaseInfoModel;
import com.shine.iot.core.service.IOrgBaseInfoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/org-base-data")
public class OrgBaseInfoController {
    private Logger logger = LogManager.getLogger(OrgBaseInfoController.class);

    private IOrgBaseInfoService orgBaseInfoService;

    @Autowired
    private void setOrgBaseInfoService(IOrgBaseInfoService orgBaseInfoService) {
        this.orgBaseInfoService = orgBaseInfoService;
    }

    @GetMapping("/test")
    public String testController() {
        return "test devtool 1";
    }

    /**
     * 根据单位Id，查询单位信息
     *
     * @param id 单位Id
     * @return 当前单位的信息记录
     */
    @Cacheable(value = "org", key = "#id", unless = "#result eq null")
    @GetMapping("/org/{id}")
    public OrgBaseInfoModel getOne(@PathVariable Long id) {
        return orgBaseInfoService.getById(id);
    }

    /**
     * 根据实体属性值，查询单位信息列表
     *
     * @param orgBaseFilter orgbaseInfoModel属性
     * @return 单位信息列表记录
     */
    @GetMapping("/orgs")
    public List<OrgBaseInfoModel> queryListByObj(@ModelAttribute OrgBaseInfoModel orgBaseFilter) {
        ServiceRsObjModel<List<OrgBaseInfoModel>> result = orgBaseInfoService.queryListByObj(orgBaseFilter);
        if (result.isSuccess()) {
            return result.getRsData();
        } else {
            logger.error(result.getRsMsg());
            return null;
        }
    }


    /**
     * 分页查询单位信息列表
     *
     * @param pageNo        当前页码
     * @param orgBaseFilter 单位实体属性
     * @return 分页信息（当前页码、总页数、总记录数、每页的记录数）、单位信息记录
     */
    @GetMapping("/org/page/{pageNo}")
    public IPage<OrgBaseInfoModel> pageQuery(@PathVariable int pageNo, @ModelAttribute OrgBaseInfoModel orgBaseFilter) {
        ServiceRsObjModel<IPage<OrgBaseInfoModel>> pageResult = orgBaseInfoService.pageQuery(pageNo, orgBaseFilter);
        if (pageResult.isSuccess()) {
            return pageResult.getRsData();
        } else {
            logger.error(pageResult.getRsMsg());
            return null;
        }
    }

    /**
     * 查询当前单位内所有关联区域的设备列表信息列表（符合设备过滤条件）
     *
     * @param pageNum          当前页码
     * @param orgId            单位ID
     * @param deviceBaseFilter 设备基本信息过滤条件
     * @return 单位内所有关联区域的，符合设备过滤出条件的设备信息（包含单位区域基本信息）列表
     */
    @GetMapping("/org/{orgId}/devs/{pageNum}")
    public IPage<OrgAreaDevInfoDto> pageQueryOrgDevList(@PathVariable int pageNum, @PathVariable Long orgId, @ModelAttribute DeviceBaseInfoModel deviceBaseFilter) {
        ServiceRsObjModel<Page<OrgAreaDevInfoDto>> pageResult = orgBaseInfoService.pageQueryOrgDevList(pageNum, orgId, deviceBaseFilter);
        if (pageResult.isSuccess()) {
            return pageResult.getRsData();
        } else {
            logger.error(pageResult.getRsMsg());
            return null;
        }
    }

    /**
     * 根据单位ID修改单位信息
     *
     * @param orgId 单位ID
     * @return true false 是否修改成功
     */
    @PutMapping("/org/{orgId}")
    public boolean updateOrgInfo(@PathVariable Long orgId) {
        boolean upFlag = false;
        if (orgId != null && orgId > 0) {
            OrgBaseInfoModel orgBase = new OrgBaseInfoModel();
            orgBase.setOrgId(orgId);
            ServiceRsObjModel<OrgBaseInfoModel> updateResult = orgBaseInfoService.updateOrgInfo(orgBase);
            if (updateResult.isSuccess()) {
                upFlag = true;
            }
        }
        return upFlag;
    }

    @DeleteMapping("/org/{orgId}")
    public boolean removeOrg(@PathVariable Long orgId) {
        return orgBaseInfoService.removeOrg(orgId);
    }

}
