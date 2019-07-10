package com.shine.iot.service.api.rest;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shine.iot.model.dto.OrgAreaDevInfoDto;
import com.shine.iot.model.entity.DeviceBaseInfoModel;
import com.shine.iot.model.entity.OrgBaseInfoModel;
import com.shine.iot.service.api.fegin.OrgBaseFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/org-base")
public class OrgBaseRestController {

    private OrgBaseFeignService orgBaseFeign;

    @Autowired
    private void setOrgBaseFeignService(OrgBaseFeignService orgBaseFeignService) {
        this.orgBaseFeign = orgBaseFeignService;
    }

    /*
     * 根据单位Id，查询单位信息
     * @param id
     * @return
     */
    @GetMapping("/org/{id}")
    public OrgBaseInfoModel getOneById(@PathVariable Long id) {
        return orgBaseFeign.getOne(id);
    }

    /**
     * 查询单位信息列表，以单位实体属性作为过滤条件，不传递过滤条件则查询所有单位信息列表
     *
     * @param orgBaseFilter 单位实体属性
     * @return 单位信息列表
     */
    @GetMapping("/orgs")
    public List<OrgBaseInfoModel> queryListByObj(@ModelAttribute OrgBaseInfoModel orgBaseFilter) {
        return orgBaseFeign.queryListByObj(orgBaseFilter);
    }

    /**
     * 分页查询单位信息列表
     *
     * @param pageNo        当前页码
     * @param orgBaseFilter 单位实体属性
     * @return 分页信息（当前页码、总页数、总记录数、每页的记录数）、单位信息记录
     */
    @GetMapping("/org/page/{pageNo}")
    public Page<OrgBaseInfoModel> pageQuery(@PathVariable int pageNo, @ModelAttribute OrgBaseInfoModel orgBaseFilter) {
        return orgBaseFeign.pageQuery(pageNo, orgBaseFilter);
    }

    /**
     * 新增单位信息
     *
     * @param orgBaseInfoModel 单位实体信息
     * @return 新增的单位信息
     */
    @PutMapping("/org")
    public OrgBaseInfoModel saveOrgBaseInfo(@ModelAttribute OrgBaseInfoModel orgBaseInfoModel) {
        return orgBaseFeign.saveOrgBaseInfo(orgBaseInfoModel);
    }

    /**
     * 根据单位ID，删除单位信息（逻辑删除，修改删除标志）
     *
     * @param orgId 单位Id
     * @return true false 修改删除标志是否成功
     */
    @PatchMapping("/org/{orgId}")
    public OrgBaseInfoModel removeOrgBaseInfo(@PathVariable Long orgId) {
        return orgBaseFeign.removeOrgBaseInfo(orgId);
    }

    /**
     * 分页查询当前单位内所有关联区域的设备列表信息列表（以设备实体信息作为过滤条件）
     *
     * @param pageNo           当前页码
     * @param orgId            单位ID
     * @param deviceBaseFilter 设备基本信息过滤条件
     * @return 分页信息、单位内的设备信息列表（包含单位ID、单位Name、区域ID、区域Name）
     */
    @GetMapping("/org/{orgId}/devs/{pageNo}")
    public Page<OrgAreaDevInfoDto> pageQueryOrgDevList(@PathVariable int pageNo, @PathVariable Long orgId, @ModelAttribute DeviceBaseInfoModel deviceBaseFilter) {
        return orgBaseFeign.pageQueryOrgDevList(pageNo, orgId, deviceBaseFilter);
    }

}
