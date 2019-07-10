package com.shine.iot.platform.service.rest;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.model.ServiceRsObjModel;
import com.shine.iot.model.dto.OrgAreaDevInfoDto;
import com.shine.iot.model.entity.DeviceBaseInfoModel;
import com.shine.iot.model.entity.OrgBaseInfoModel;
import com.shine.iot.platform.service.serviceLogic.IOrgBaseInfoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * 根据单位Id，查询单位信息
     *
     * @param id 单位Id
     * @return 当前单位的信息记录
     */
    @GetMapping("/org/{id}")
    public OrgBaseInfoModel getOne(@PathVariable Long id) {
        return orgBaseInfoService.getById(id);
    }

    /**
     * 查询单位信息列表，以单位实体属性作为过滤条件，不传递过滤条件则查询所有单位信息列表
     *
     * @param orgBaseFilter 单位实体属性
     * @return 单位信息列表
     */
    @PostMapping("/orgs")
    public List<OrgBaseInfoModel> queryListByObj(@RequestBody(required = false) OrgBaseInfoModel orgBaseFilter) {
        ServiceRsObjModel<List<OrgBaseInfoModel>> result = orgBaseInfoService.queryListByObj(orgBaseFilter);
        if (result.isSuccess()) {
            return result.getRsData();
        } else {
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
    @PostMapping("/org/page/{pageNo}")
    public Page<OrgBaseInfoModel> pageQuery(@PathVariable("pageNo") int pageNo, @RequestBody(required = false) OrgBaseInfoModel orgBaseFilter) {
        ServiceRsObjModel<Page<OrgBaseInfoModel>> pageResult = orgBaseInfoService.pageQuery(pageNo, orgBaseFilter);
        if (pageResult.isSuccess()) {
            return pageResult.getRsData();
        } else {
            return null;
        }
    }

    /**
     * 分页查询当前单位内所有关联区域的设备列表信息列表（符合设备过滤条件）
     *
     * @param pageNo           当前页码
     * @param orgId            单位ID
     * @param deviceBaseFilter 设备基本信息过滤条件
     * @return 分页信息、单位内的设备信息列表（包含单位ID、单位Name、区域ID、区域Name）
     */
    @PostMapping("/org/{orgId}/devs/{pageNo}")
    public Page<OrgAreaDevInfoDto> pageQueryOrgDevList(@PathVariable int pageNo, @PathVariable Long orgId
            , @RequestBody(required = false) DeviceBaseInfoModel deviceBaseFilter) {
        ServiceRsObjModel<Page<OrgAreaDevInfoDto>> pageResult = orgBaseInfoService.pageQueryOrgDevList(pageNo, orgId, deviceBaseFilter);
        if (pageResult.isSuccess()) {
            return pageResult.getRsData();
        } else {
            return null;
        }
    }

    /**
     * 新增单位信息
     *
     * @param orgBaseInfoModel 单位实体信息
     * @return 新增的单位信息
     */
    @PostMapping("/org")
    public OrgBaseInfoModel saveOrgBaseInfo(@RequestBody OrgBaseInfoModel orgBaseInfoModel) {
        ServiceRsObjModel<OrgBaseInfoModel> orgBaseResult = orgBaseInfoService.saveOrgInfo(orgBaseInfoModel);
        if (orgBaseResult.isSuccess()) {
            return orgBaseResult.getRsData();
        } else {
            return null;
        }
    }

    /**
     * 根据单位ID，删除单位信息（逻辑删除，修改删除标志）
     *
     * @param orgId 单位Id
     * @return true false 修改删除标志是否成功
     */
    @PostMapping("/org/{orgId}")
    public OrgBaseInfoModel removeOrgBaseInfo(@PathVariable Long orgId) {
        return orgBaseInfoService.removeOrg(orgId);
    }


}
