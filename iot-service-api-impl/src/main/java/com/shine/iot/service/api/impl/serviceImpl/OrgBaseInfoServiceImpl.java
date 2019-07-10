package com.shine.iot.service.api.impl.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.model.ServiceRsObjModel;
import com.shine.iot.model.dto.OrgAreaDevInfoDto;
import com.shine.iot.model.entity.CustomerAreaMapInfoModel;
import com.shine.iot.model.entity.DeviceBaseInfoModel;
import com.shine.iot.model.entity.OrgBaseInfoModel;
import com.shine.iot.model.shine.utils.CustomerBindTypeEnum;
import com.shine.iot.model.shine.utils.CustomerTypeEnum;
import com.shine.iot.model.shine.utils.PageFieldEnum;
import com.shine.iot.service.api.impl.mapper.AreaBaseInfoMapper;
import com.shine.iot.service.api.impl.mapper.CustomerAreaMapMapper;
import com.shine.iot.service.api.impl.mapper.DeviceBaseInfoMapper;
import com.shine.iot.service.api.impl.mapper.OrgBaseInfoMapper;
import com.shine.iot.service.api.service.IOrgBaseInfoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Service("orgBaseInfoService")
public class OrgBaseInfoServiceImpl extends ServiceImpl<OrgBaseInfoMapper, OrgBaseInfoModel> implements IOrgBaseInfoService, Serializable {
    private static final long serialVersionUID = 5028181920392268334L;
    private Logger logger = LogManager.getLogger(OrgBaseInfoServiceImpl.class);

    @Autowired
    private OrgBaseInfoMapper orgBaseInfoMapper;
    @Autowired
    private AreaBaseInfoMapper areaBaseMapper;
    private CustomerAreaMapMapper custAreaMapMapper;
    private DeviceBaseInfoMapper deviceBaseMapper;

    @Autowired
    public void setOrgBaseInfoMapper(OrgBaseInfoMapper orgBaseInfoMapper) {
        this.orgBaseInfoMapper = orgBaseInfoMapper;
    }

    @Autowired
    public void setAreaBaseMapper(AreaBaseInfoMapper areaBaseMapper) {
        this.areaBaseMapper = areaBaseMapper;
    }

    @Autowired
    public void setCustAreaMapMapper(CustomerAreaMapMapper custAreaMapMapper) {
        this.custAreaMapMapper = custAreaMapMapper;
    }

    @Autowired
    public void setDeviceBaseMapper(DeviceBaseInfoMapper deviceBaseMapper) {
        this.deviceBaseMapper = deviceBaseMapper;
    }

    @Override
    public List<OrgBaseInfoModel> testList() {
      /*  //查询条件 - QueryWrapper构造器
        QueryWrapper<OrgBaseInfoModel> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("orgName", "海声").eq("orgNational", "中国");
        List<Object> orgList = orgBaseInfoMapper.selectObjs(queryWrapper);
        //查询条件 - LambdaQueryWrapper构造器
        LambdaQueryWrapper<OrgBaseInfoModel> lambdaQuery = new LambdaQueryWrapper<>();
        lambdaQuery.like(OrgBaseInfoModel::getOrgName, "海声").eq(OrgBaseInfoModel::getOrgNational, "中国");
        List<Object> orgLambdaList = orgBaseInfoMapper.selectObjs(lambdaQuery);

        //修改条件 - UpdateWrapper构造器
        UpdateWrapper<OrgBaseInfoModel> updateWrapper = new UpdateWrapper<>();
        updateWrapper.likeLeft("orgContactPhone", "2318").likeRight("orgContact", "罗").
                ge("orgLati", "30.0");
        int update_x = orgBaseInfoMapper.update(null, updateWrapper);
        //修改条件 - UpdateWrapper构造器
        LambdaUpdateWrapper<OrgBaseInfoModel> lambdaUpdate = new LambdaUpdateWrapper<>();
        lambdaUpdate.likeRight(OrgBaseInfoModel::getOrgContact, "罗").likeLeft(OrgBaseInfoModel::getOrgContactPhone, "2318");
        int update_2 = orgBaseInfoMapper.update(null, updateWrapper);

        //删除条件 - De
        LambdaQueryWrapper<OrgBaseInfoModel> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OrgBaseInfoModel::getOrgId, "0");
        int de_row = orgBaseInfoMapper.delete(lambdaQueryWrapper);
        int row = orgBaseInfoMapper.deleteById(0);
        int row1 = orgBaseInfoMapper.deleteBatchIds(Arrays.asList(1, 2));*/
        return baseMapper.testList();
    }

    @Override
    public ServiceRsObjModel<List<OrgBaseInfoModel>> queryListByObj(OrgBaseInfoModel orgBaseFilter) {
        ServiceRsObjModel<List<OrgBaseInfoModel>> result = new ServiceRsObjModel<>();
        result.setSuccess(false);
        try {
            QueryWrapper<OrgBaseInfoModel> queryWrapper = new QueryWrapper<>();
            queryWrapper.setEntity(orgBaseFilter);
            List<OrgBaseInfoModel> orgBaseList = baseMapper.selectList(queryWrapper);
            result.setSuccess(true);
            result.setRsData(orgBaseList);
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setRsMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ServiceRsObjModel<Page<OrgBaseInfoModel>> pageQuery(int pageNo, OrgBaseInfoModel orgBaseFilter) {
        ServiceRsObjModel<Page<OrgBaseInfoModel>> pageResult = new ServiceRsObjModel<>();
        pageResult.setSuccess(false);
        try {
            Page<OrgBaseInfoModel> pageInfo = (Page<OrgBaseInfoModel>) baseMapper.selectPage(new Page<>(pageNo, 10L), new QueryWrapper<>(orgBaseFilter));
            pageInfo.setTotal(baseMapper.selectCount(new QueryWrapper<>(orgBaseFilter)));
            pageResult.setSuccess(true);
            pageResult.setRsData(pageInfo);
        } catch (Exception e) {
            logger.error(e.getMessage());
            pageResult.setRsMsg(e.getMessage());
            e.printStackTrace();
        }
        return pageResult;
    }

    @Override
    public ServiceRsObjModel<Page<OrgAreaDevInfoDto>> pageQueryOrgDevList(int pageNum, Long orgId, DeviceBaseInfoModel deviceBaseFilter) {
        ServiceRsObjModel<Page<OrgAreaDevInfoDto>> pageResult = new ServiceRsObjModel<>();
        pageResult.setSuccess(false);
        try {
            //根据业主单位的ID，获取单位与顶级区域关联关系的记录
            LambdaQueryWrapper<CustomerAreaMapInfoModel> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(CustomerAreaMapInfoModel::getCustomerID, orgId)
                    .eq(CustomerAreaMapInfoModel::getCustType, CustomerTypeEnum.COMPANY.getCode())
                    .eq(CustomerAreaMapInfoModel::getBindType, CustomerBindTypeEnum.PROPERTYMANA.getCode());
            CustomerAreaMapInfoModel custAreaModel = custAreaMapMapper.selectOne(queryWrapper);
            if (custAreaModel != null) {
                Set<Long> areaIds = areaBaseMapper.getOrgAreaIds(custAreaModel.getAreaID());
                Page<OrgAreaDevInfoDto> page = new Page<>(pageNum, PageFieldEnum.SIZE_TEN.getValue());
                List<OrgAreaDevInfoDto> pageList = deviceBaseMapper.pageQueryAreaDevList(page, areaIds, deviceBaseFilter);
                page.setRecords(pageList);

                pageResult.setSuccess(true);
                pageResult.setRsData(page);
            } else {
                logger.info("单位【" + orgId + "】没有设备可以获得（没有绑定区域）");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            pageResult.setRsMsg(e.getMessage());
            e.printStackTrace();
        }
        return pageResult;
    }

    @Override
    public OrgBaseInfoModel removeOrg(Long orgId) {
        // 修改值    -- 设置要修改的字段信息
        OrgBaseInfoModel orgBaseInfoModel = new OrgBaseInfoModel();
        orgBaseInfoModel.setDeleteFlag("0");
        orgBaseInfoModel.setDeleteTime(new Date());

        // 修改条件   -- 过滤条件，用于生成 where 语句
        /*UpdateWrapper<OrgBaseInfoModel> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("orgId", orgId);*/
        LambdaUpdateWrapper<OrgBaseInfoModel> up = new LambdaUpdateWrapper<>();
        up.eq(true, OrgBaseInfoModel::getOrgId, orgId);

        int upNum = orgBaseInfoMapper.update(orgBaseInfoModel, up);
        // return upNum > 0 ? true : false;
        return orgBaseInfoMapper.selectById(orgId);
    }

    @Override
    public ServiceRsObjModel<OrgBaseInfoModel> saveOrgInfo(OrgBaseInfoModel orgBaseInfoModel) {
        ServiceRsObjModel<OrgBaseInfoModel> result = new ServiceRsObjModel<>();
        result.setSuccess(false);
        try {
            if (orgBaseInfoModel != null) {
                int insertNum = orgBaseInfoMapper.insert(orgBaseInfoModel);
                if (insertNum > 0) {
                    result.setSuccess(true);
                    result.setRsData(orgBaseInfoModel);
                } else {
                    result.setRsMsg("Attention!!! Failed to insert orgbaseinfo information into database! ");
                    logger.debug(result.getRsMsg());
                }
            } else {
                result.setRsMsg("Attention!!! OrgBaseInfo is null，request database is not allowed! ");
                logger.debug(result.getRsMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setRsMsg(e.getMessage());
            logger.error(e.getMessage());
        }
        return result;
    }

}