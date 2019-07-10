package com.shine.iot.core;

import com.shine.iot.core.entity.OrgBaseInfoModel;
import com.shine.iot.core.service.IOrgBaseInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IotWebFireApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IOrgBaseInfoService orgBaseInfoService;

    @Test
    public void redisTest() {
        //redis存储数据
        String key = "myName";
        redisTemplate.opsForValue().set(key, "ChangWei-Redis");
        // 获取数据
        String value = (String) redisTemplate.opsForValue().get(key);
        System.out.println("value:" + value);

        OrgBaseInfoModel orgInfo = orgBaseInfoService.getById(22);
        String orgKey = "orgInfo-" + orgInfo.getOrgId();
        redisTemplate.opsForValue().set(orgKey, orgInfo);
        OrgBaseInfoModel redis_orgInfo = (OrgBaseInfoModel) redisTemplate.opsForValue().get(orgKey);
        System.out.println("获取缓存中key为:" + orgKey + "的值为：" + redis_orgInfo);

        //redisTemplate.opsForList().set();
    }

    public void redisGetData() {
        //redisTemplate.opsForList().
    }

}
