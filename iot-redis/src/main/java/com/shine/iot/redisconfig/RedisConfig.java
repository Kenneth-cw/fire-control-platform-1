package com.shine.iot.redisconfig;

import lombok.Data;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * redis配置信息加载
 */
@Component
@PropertySource(value = "classpath:config/redisConfig.yml")
@ConfigurationProperties(value = "redis")
@Data
public class RedisConfig {
    //创建当前类日志对象
    private Log log = LogFactory.getLog(RedisConfig.class);

    //定义变量
    private String host;
    private int port;

    /**
     * 获取jedis客户端对象
     *
     * @return
     */
    public Jedis getJedisClient() {
        //创建jedispool连接池对象
        JedisPool jedisPool = new JedisPool(host, port);
        //得到jedis客户端对象
        Jedis jedis = jedisPool.getResource();

        //返回获取到的jedis对象
        return jedis;
    }
}
