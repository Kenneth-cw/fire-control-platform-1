package com.shine.iot.service.api.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shine.iot.model.entity.DeviceBaseInfoModel;
import com.shine.iot.model.entity.OrgBaseInfoModel;
import com.shine.iot.service.api.impl.mapper.DeviceBaseInfoMapper;
import com.shine.iot.service.api.impl.mapper.OrgBaseInfoMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisPlusMethodTest {

    @Autowired
    private OrgBaseInfoMapper orgBaseMapper;

    @Autowired
    private DeviceBaseInfoMapper deviceBaseMapper;

    /**
     * 根据根据 entity 条件，删除记录,QueryWrapper实体对象封装操作类（可以为 null）
     * 下方发，获取到queryWrapper后删除的查询条件为orgName字段为null的and deleteFlag等于0的 and orgNational字段不为中国的
     */
    @Test
    public void delete() {
        QueryWrapper<OrgBaseInfoModel> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNull("orgName")
                .eq("deleteFlag", "0")
                .ne("orgNational", "中国");
        int delete = orgBaseMapper.delete(queryWrapper);
        System.out.println("删除记录数：" + delete);
    }

    /**
     * 根据 entity 条件，查询一条记录,
     * 构造条件是查询devEUI为1000FF000B0003E0的设备记录，只是seletOne返回的是一条实体记录，当出现多条时会报错
     */
    @Test
    public void selectOne() {
        QueryWrapper<DeviceBaseInfoModel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("devEUI", "1000FF000B0003E0");

        DeviceBaseInfoModel device = deviceBaseMapper.selectOne(queryWrapper);
        System.out.println(device);
    }

    /**
     * 根据 Wrapper 条件，查询总记录数
     * devEUI like '1000FF000B000%' order by deviceId desc
     */
    @Test
    public void selectCount() {
        QueryWrapper<DeviceBaseInfoModel> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("devEUI", "1000FF000B000")
                .orderByDesc("deviceId");

        Integer count = deviceBaseMapper.selectCount(queryWrapper);
        System.out.println(count);
    }

    /**
     * 根据 entity 条件，查询全部记录
     * <p>
     * 实体对象封装操作类（可以为 null）为null查询全部
     */
    @Test
    public void selectList() {
        List<DeviceBaseInfoModel> list = deviceBaseMapper.selectList(null);

        System.out.println(list);
    }

    /**
     * 根据 Wrapper 条件，查询全部记录
     * <p>
     * 实体对象封装操作类（可以为 null）
     */
    @Test
    public void selectMaps() {
        QueryWrapper<DeviceBaseInfoModel> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNotNull("devEUI");
        List<Map<String, Object>> maps = deviceBaseMapper.selectMaps(queryWrapper);
        for (Map<String, Object> map : maps) {
            System.out.println(map); //json类型的键值对模式
        }
    }

    /**
     * 根据 entity 条件，查询全部记录（并翻页）
     */
    @Test
    public void selectPage() {
        //分页查询条件（可以为 RowBounds.DEFAULT）
        Page<DeviceBaseInfoModel> page = new Page<>(1, 5);
        //实体对象封装操作类（可以为 null）
        QueryWrapper<DeviceBaseInfoModel> queryWrapper = new QueryWrapper<>();

        IPage<DeviceBaseInfoModel> userIPage = deviceBaseMapper.selectPage(page, queryWrapper);
        //Page<DeviceBaseInfoModel> userPage = (Page<DeviceBaseInfoModel>) deviceBaseMapper.selectPage(page, queryWrapper);
        System.out.println(userIPage);

        //这里需要在项目中加入分页插件
        //     @Bean
        //     public PaginationInterceptor paginationInterceptor() {
        //         return new PaginationInterceptor();
        //     }
    }

    /**
     * 根据 Wrapper 条件，查询全部记录（并翻页）
     */
    @Test
    public void selectMapsPage() {
        //分页查询条件
        Page<DeviceBaseInfoModel> page = new Page<>(1, 5);
        //实体对象封装操作类
        QueryWrapper<DeviceBaseInfoModel> queryWrapper = new QueryWrapper<>();

        IPage<Map<String, Object>> mapIPage = deviceBaseMapper.selectMapsPage(page, queryWrapper);
        System.out.println(mapIPage); //和上个分页同理只是返回类型不同
    }


    /**
     * 根据 whereEntity 条件，更新记录
     */
    @Test
    public void update() {

        //修改值  -- 实体对象 (set 条件值,不能为 null)
        OrgBaseInfoModel orgBase = new OrgBaseInfoModel();
        orgBase.setDeleteFlag("0");
        orgBase.setDeleteTime(new Date());

        //修改条件  -- 实体对象封装操作类（可以为 null,里面的 entity 用于生成 where 语句）
        UpdateWrapper<OrgBaseInfoModel> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("orgId", "8");

        int update = orgBaseMapper.update(orgBase, updateWrapper);

        System.out.println(update);
    }


}
