package com.shine.iot.service.api.fegin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shine.iot.model.dto.OrgAreaDevInfoDto;
import com.shine.iot.model.entity.DeviceBaseInfoModel;
import com.shine.iot.model.entity.OrgBaseInfoModel;
import com.shine.iot.service.api.hystrix.OrgBaseFeignClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

// 设置请求的应用名
@FeignClient(value = "iot-service-api-impl", path = "org-base-data", fallback = OrgBaseFeignClientFallback.class)
@Component
public interface OrgBaseFeignService {

    /**
     * 根据单位Id，查询单位信息
     *
     * @param id 单位Id
     * @return 当前单位的信息记录
     */
    @GetMapping("/org/{id}")
    OrgBaseInfoModel getOne(@PathVariable("id") Long id);

    /**
     * 查询单位信息列表，以单位实体属性作为过滤条件，不传递过滤条件则查询所有单位信息列表
     *
     * @param orgBaseFilter 单位实体属性
     * @return 单位信息列表
     */
    @PostMapping("/orgs")
    List<OrgBaseInfoModel> queryListByObj(@RequestBody OrgBaseInfoModel orgBaseFilter);

    /**
     * 分页查询单位信息列表
     *
     * @param pageNo        当前页码
     * @param orgBaseFilter 单位实体属性
     * @return 分页信息（当前页码、总页数、总记录数、每页的记录数）、单位信息记录
     */
    @PostMapping("/org/page/{pageNo}")
    Page<OrgBaseInfoModel> pageQuery(@PathVariable(value = "pageNo") int pageNo
            , @RequestBody(required = false) OrgBaseInfoModel orgBaseFilter);

    /**
     * 分页查询当前单位内所有关联区域的设备列表信息列表（以设备实体信息作为过滤条件）
     *
     * @param pageNo           当前页码
     * @param orgId            单位ID
     * @param deviceBaseFilter 设备基本信息过滤条件
     * @return 分页信息、单位内的设备信息列表（包含单位ID、单位Name、区域ID、区域Name）
     */
    @PostMapping("/org/{orgId}/devs/{pageNo}")
    Page<OrgAreaDevInfoDto> pageQueryOrgDevList(@PathVariable("pageNo") int pageNo, @PathVariable("orgId") Long orgId
            , @RequestBody(required = false) DeviceBaseInfoModel deviceBaseFilter);

    /**
     * 新增单位信息
     *
     * @param orgBaseInfoModel 单位实体信息
     * @return 新增的单位信息
     */
    @PostMapping("/org")
    OrgBaseInfoModel saveOrgBaseInfo(@RequestBody OrgBaseInfoModel orgBaseInfoModel);

    /**
     * 根据单位ID，删除单位信息（逻辑删除，修改删除标志）
     *
     * @param orgId 单位Id
     * @return true false 修改删除标志是否成功
     */
    @PostMapping(value = "/org/{orgId}")
    OrgBaseInfoModel removeOrgBaseInfo(@PathVariable("orgId") Long orgId);


}
