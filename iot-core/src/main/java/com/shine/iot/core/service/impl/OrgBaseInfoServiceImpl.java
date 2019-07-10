package com.shine.iot.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.model.ServiceRsObjModel;
import com.shine.iot.core.dto.OrgAreaDevInfoDto;
import com.shine.iot.core.entity.CustomerAreaMapInfoModel;
import com.shine.iot.core.entity.DeviceBaseInfoModel;
import com.shine.iot.core.entity.OrgBaseInfoModel;
import com.shine.iot.core.mapper.AreaBaseInfoMapper;
import com.shine.iot.core.mapper.CustomerAreaMapMapper;
import com.shine.iot.core.mapper.DeviceBaseInfoMapper;
import com.shine.iot.core.mapper.OrgBaseInfoMapper;
import com.shine.iot.core.service.IOrgBaseInfoService;
import com.shine.iot.core.utils.CustomerBindTypeEnum;
import com.shine.iot.core.utils.CustomerTypeEnum;
import com.shine.iot.core.utils.PageFieldEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Service("orgBaseInfoService")
public class OrgBaseInfoServiceImpl extends ServiceImpl<OrgBaseInfoMapper, OrgBaseInfoModel> implements IOrgBaseInfoService, Serializable {
    private static final long serialVersionUID = 5028181920392268334L;
    private Logger logger = LogManager.getLogger(OrgBaseInfoServiceImpl.class);

    private OrgBaseInfoMapper orgBaseInfoMapper;
    private AreaBaseInfoMapper areaBaseMapper;
    private CustomerAreaMapMapper custAreaMapMapper;
    private DeviceBaseInfoMapper deviceBaseMapper;

    @Autowired
    private void setOrgBaseInfoMapper(OrgBaseInfoMapper orgBaseInfoMapper) {
        this.orgBaseInfoMapper = orgBaseInfoMapper;
    }

    @Autowired
    private void setAreaBaseMapper(AreaBaseInfoMapper areaBaseMapper) {
        this.areaBaseMapper = areaBaseMapper;
    }

    @Autowired
    private void setCustAreaMapMapper(CustomerAreaMapMapper custAreaMapMapper) {
        this.custAreaMapMapper = custAreaMapMapper;
    }

    @Autowired
    private void setDeviceBaseMapper(DeviceBaseInfoMapper deviceBaseMapper) {
        this.deviceBaseMapper = deviceBaseMapper;
    }

    /**
     * redis缓存的使用
     *
     * @Cacheable 应用到读取数据的方法上，先从缓存中读取，如果没有再从DB获取数据，然后把数据添加到缓存中
     * @CachePut 应用到写数据的方法上，如新增/修改方法，调用方法时会自动把相应的数据放入缓存
     * @CacheEvict 应用到删除数据的方法上，调用方法时会从缓存中删除对应key的数据
     * <p>
     * 参数解释：
     * unless：表示条件表达式成立的话不放入缓存
     * condition：与unless相反,只有表达式为真(成立)才会执行。
     * key 缓存在redis中的key
     */


    @Override
    public List<OrgBaseInfoModel> testList() {
        //查询条件 - QueryWrapper构造器
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
        int row1 = orgBaseInfoMapper.deleteBatchIds(Arrays.asList(1, 2));

        return baseMapper.testList();
    }

    /**
     * @param orgBaseFilter 过滤条件
     * @return 单位列表实体
     * @Cacheable 应用到读取数据的方法上，先从缓存中读取，如果没有再从DB获取数据，然后把数据添加到缓存中
     * key 缓存在redis中的key
     * unless 表示条件表达式成立的话不放入缓存
     * value key的前缀（redis缓存的时候key的生成时如下格式：value::key）
     */
    @Cacheable(value = "org", key = "#root.args[0]", unless = "#result.rsData eq null ")
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

    /**
     * @param pageNo        当前页
     * @param orgBaseFilter 过滤条件
     * @return 单位列表实体的分页信息
     * @Cacheable 应用到读取数据的方法上，先从缓存中读取，如果没有再从DB获取数据，然后把数据添加到缓存中
     * key 缓存在redis中的key
     * unless 表示条件表达式成立的话不放入缓存
     */
    @Cacheable(value = "org", key = "#root.args[0]", unless = "#result.rsData eq null")
    @Override
    public ServiceRsObjModel<IPage<OrgBaseInfoModel>> pageQuery(int pageNo, OrgBaseInfoModel orgBaseFilter) {
        ServiceRsObjModel<IPage<OrgBaseInfoModel>> pageResult = new ServiceRsObjModel<>();
        pageResult.setSuccess(false);
        try {
            /*Page<OrgBaseInfoModel> page = new Page<>(pageNo, 10L);
            page.setCurrent(pageNo);
            page.setSize(10L); //默认10条*/
//        QueryWrapper<OrgBaseInfoModel> queryWrapper = new QueryWrapper<>();
//        queryWrapper.setEntity(orgBaseFilter);
            IPage<OrgBaseInfoModel> pageInfo = baseMapper.selectPage(new Page<>(pageNo, 10L), new QueryWrapper<>(orgBaseFilter));
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

    // @Cacheable(value = "org", key = "#root.args[0]", unless = "#result.rsData eq null")
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

    /**
     * @param orgId 单位ID
     * @return 单位信息是否修改成功
     * @CacheEvict 应用到删除数据的方法上，调用方法时会从缓存中删除对应key的数据
     * condition 与unless相反，只有表达式为真才会执行。
     */
    @CacheEvict(value = "org", key = "#root.args[0]", condition = "#result eq true ")
    @Override
    public Boolean removeOrg(Long orgId) {
        //设置要修改的设备信息
        OrgBaseInfoModel orgBaseInfoModel = new OrgBaseInfoModel();
        orgBaseInfoModel.setDeleteFlag("0");
        orgBaseInfoModel.setDeleteTime(new Date());
        // 过滤条件（根据设备ID修改设备信息）
        UpdateWrapper<OrgBaseInfoModel> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("orgId", orgId);
        int upNum = orgBaseInfoMapper.update(orgBaseInfoModel, updateWrapper);
        return upNum > 0 ? true : false;
    }

    /**
     * @param orgBase 单位实体信息作为过滤条件
     * @return 修改成功后的单位信息
     * @CachePut 应用到写数据的方法上，如新增/修改方法，调用方法时会自动把相应的数据放入缓存
     */
    @CachePut(value = "org", key = "#root.args[0]", unless = "#orgBase eq null")
    @Override
    public ServiceRsObjModel<OrgBaseInfoModel> updateOrgInfo(OrgBaseInfoModel orgBase) {
        ServiceRsObjModel<OrgBaseInfoModel> result = new ServiceRsObjModel<>();
        result.setSuccess(false);
        try {
            UpdateWrapper<OrgBaseInfoModel> upWrapper = new UpdateWrapper<>();
            upWrapper.setEntity(orgBase);
            int upNum = orgBaseInfoMapper.update(orgBase, upWrapper);
            if (upNum > 0) {
                result.setSuccess(true);
                result.setRsData(orgBase);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setRsMsg("Update orgbaseinfo fail!" + e.getMessage());
            logger.debug(result.getRsMsg());
        }
        return result;
    }

    /**
     * @CachePut 应用到写数据的方法上，如新增/修改方法，调用方法时会自动把相应的数据放入缓存
     * #p0：第一个参数，#p1：第二个参数，#p3以此类推
     */
    @CachePut(value = "org", key = "#p0.orgId", unless = "#orgBase eq null")
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