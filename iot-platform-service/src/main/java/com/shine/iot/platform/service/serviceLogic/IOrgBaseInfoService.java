package com.shine.iot.platform.service.serviceLogic;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.model.ServiceRsObjModel;
import com.shine.iot.model.dto.OrgAreaDevInfoDto;
import com.shine.iot.model.entity.DeviceBaseInfoModel;
import com.shine.iot.model.entity.OrgBaseInfoModel;

import java.util.List;

public interface IOrgBaseInfoService extends IService<OrgBaseInfoModel> {

    /**
     * 查询所有
     *
     * @return
     */
    public List<OrgBaseInfoModel> testList();

    /**
     * 根据实体类属性，查询单位信息
     *
     * @param orgBaseFilter
     * @return
     */
    ServiceRsObjModel<List<OrgBaseInfoModel>> queryListByObj(OrgBaseInfoModel orgBaseFilter);

    /**
     * 分页查询单位信息列表，myBatis Plus自带插件
     *
     * @param pageNo
     * @param orgBaseFilter
     * @return
     */
    ServiceRsObjModel<Page<OrgBaseInfoModel>> pageQuery(int pageNo, OrgBaseInfoModel orgBaseFilter);

    /**
     * 查询当前单位内所有关联区域的设备列表信息列表（符合设备过滤条件）
     *
     * @param orgId            单位ID
     * @param deviceBaseFilter 设备基本信息过滤条件
     * @return 单位区域设备基本信息列表
     */
    ServiceRsObjModel<Page<OrgAreaDevInfoDto>> pageQueryOrgDevList(int pageNum, Long orgId, DeviceBaseInfoModel deviceBaseFilter);

    /**
     * 根据主键ID删除单位信息，此方法为逻辑删除单位信息
     *
     * @param orgId 单位ID
     * @return 修改单位的删除标志，是否修改成功
     */
    OrgBaseInfoModel removeOrg(Long orgId);

    /**
     * 新增一个单位实体信息
     *
     * @param orgBaseInfoModel
     * @return 返回新增的单位信息
     */
    ServiceRsObjModel<OrgBaseInfoModel> saveOrgInfo(OrgBaseInfoModel orgBaseInfoModel);
}
