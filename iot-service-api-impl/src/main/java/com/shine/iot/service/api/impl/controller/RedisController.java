package com.shine.iot.service.api.impl.controller;

import com.shine.iot.service.api.service.IDeviceBaseInfoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * redis 测试controller
 */
@RestController
@RequestMapping(value = "redis")
public class RedisController {
    //创建当前类日志对象
    private Log log = LogFactory.getLog(RedisController.class);
    @Autowired
    private IDeviceBaseInfoService deviceService;

    //创建redispoo对象
    /*@Autowired
    private RedisConfig redisConfig;


    //测试添加方法
    @GetMapping(value = "setredis")
    public String setRedis() {
        //得到jedisClient对象
        Jedis jedisClient = redisConfig.getJedisClient();

        //存入redis数据库中
        jedisClient.set("name", "cherish");

        //返回
        return "请查看是否存储成功！！！jedisClient：" + jedisClient;
    }

    //测试查询方法
    @GetMapping(value = "queryredis")
    public String queryRedis() {
        //得到jedisClient对象
        Jedis jedisClient = redisConfig.getJedisClient();
        //查询
        String name = jedisClient.get("name");

        //返回
        return "查询成功！！！查询的name为：" + name;
    }

    public DeviceBaseInfoModel getDeviceById(Long deviceId) {
        DeviceBaseInfoModel deviceModel = deviceService.getObjById(deviceId);
        Jedis jedisClient = redisConfig.getJedisClient();
        jedisClient.select(0); //选择哪一个数据库
        //jedisClient.set("device-"+deviceModel.getDeviceId(), deviceModel); //
        return null;
    }*/

}
