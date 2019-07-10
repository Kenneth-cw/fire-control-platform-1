package com.shine.iot.redis.impl;

import com.shine.iot.redis.CacheService;
import com.shine.iot.redis.util.SerializeUtil;
import com.shine.iot.redisconfig.RedisConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

/**
 * 缓存服务实现类
 */
@Component
public class CacheServiceRedisImpl implements CacheService {
    //创建当前类日志对象
    private Log log = LogFactory.getLog(CacheServiceRedisImpl.class);

    //获取redis客户端对象
    @Autowired
    private RedisConfig redisConfig;

    /**
     * 将对象存放到缓存中
     *
     * @param key   存放的key
     * @param value 存放的值
     */
    @Override
    public void putObject(String key, Object value) {
        //获取jedis对象
        Jedis jedisClient = redisConfig.getJedisClient();

        //捕获并处理异常
        try {
            //存入缓存库中
            jedisClient.set(key.getBytes(), SerializeUtil.serialize(value));

        } catch (Exception e) {
            //打印输出
            log.info("Exception：" + e.getMessage(), e);
        } finally {
            //判断当前jedis是否为空
            if (jedisClient != null) {
                //关闭
                jedisClient.close();
            }
        }
    }


    /**
     * 将对象存放到指定数据库中
     *
     * @param key     存放的key
     * @param value   存放的值
     * @param dbIndex 数据库索引(索引从0开始 -- 默认为0)
     */
    @Override
    public void putObjectSelectDBIndex(String key, Object value, int dbIndex) {
        //获取jedis对象
        Jedis jedisClient = redisConfig.getJedisClient();

        //捕获并处理异常
        try {
            //指定数据库
            jedisClient.select(dbIndex);
            //存入缓存库中
            jedisClient.set(key.getBytes(), SerializeUtil.serialize(value));

        } catch (Exception e) {
            //打印输出
            log.info("Exception：" + e.getMessage(), e);
        } finally {
            //判断当前jedis是否为空
            if (jedisClient != null) {
                //关闭
                jedisClient.close();
            }
        }
    }


    /**
     * 设置对象过期时间并存放到缓存中
     *
     * @param key        存放的key
     * @param value      存放的值
     * @param expiration 过期时间，单位秒【过期后将自动删除】
     */
    @Override
    public void putObject(String key, Object value, int expiration) {
        //获取jedis对象
        Jedis jedisClient = redisConfig.getJedisClient();

        //捕获并处理异常
        try {
            //存入缓存库中
            jedisClient.setex(key.getBytes(), expiration, SerializeUtil.serialize(value));

        } catch (Exception e) {
            //打印输出
            log.info("Exception：" + e.getMessage(), e);
        } finally {
            //判断当前jedis是否为空
            if (jedisClient != null) {
                //关闭
                jedisClient.close();
            }
        }
    }


    /**
     * 设置对象过期时间并存放到指定缓存数据库中
     *
     * @param key        存放的key
     * @param value      存放的值
     * @param expiration 设置的过期时间
     * @param dbIndex    数据库索引(索引从0开始 -- 默认为0)
     */
    @Override
    public void putObject(String key, Object value, int expiration, int dbIndex) {
        //获取jedis对象
        Jedis jedisClient = redisConfig.getJedisClient();

        //捕获并处理异常
        try {
            //指定数据库
            jedisClient.select(dbIndex);
            //存入缓存库中
            jedisClient.setex(key.getBytes(), expiration, SerializeUtil.serialize(value));

        } catch (Exception e) {
            //打印输出
            log.info("Exception：" + e.getMessage(), e);
        } finally {
            //判断当前jedis是否为空
            if (jedisClient != null) {
                //关闭
                jedisClient.close();
            }
        }
    }


    /**
     * 从缓存中获取对象
     *
     * @param key 要获取对象的key
     * @return
     */
    @Override
    public Object pullObject(String key) {
        //获取jedis对象
        Jedis jedisClient = redisConfig.getJedisClient();

        //捕获并处理异常
        try {
            //存入缓存库中
            byte[] bytes = jedisClient.get(key.getBytes());
            //判断当前内容是否为空
            if (bytes != null) {
                //反序列化后返回当前对象
                return SerializeUtil.unserialize(bytes);
            } else {
                //返回空
                return null;
            }

        } catch (Exception e) {
            //打印输出
            log.info("Exception：" + e.getMessage(), e);
        } finally {
            //判断当前jedis是否为空
            if (jedisClient != null) {
                //关闭
                jedisClient.close();
            }
        }
        //返回
        return null;
    }


    /**
     * 从指定缓存数据库中获取对象
     *
     * @param key
     * @param dbIndex
     * @return
     */
    @Override
    public Object pullObjectSelectDBIndex(String key, int dbIndex) {
        //获取jedis对象
        Jedis jedisClient = redisConfig.getJedisClient();

        //捕获并处理异常
        try {
            //指定数据库
            jedisClient.select(dbIndex);
            //存入缓存库中
            byte[] bytes = jedisClient.get(key.getBytes());
            //判断当前内容是否为空
            if (bytes != null) {
                //反序列化后返回当前对象
                return SerializeUtil.unserialize(bytes);
            } else {
                //返回空
                return null;
            }

        } catch (Exception e) {
            //打印输出
            log.info("Exception：" + e.getMessage(), e);
        } finally {
            //判断当前jedis是否为空
            if (jedisClient != null) {
                //关闭
                jedisClient.close();
            }
        }
        //返回
        return null;
    }


    /**
     * 给缓存对象键设置过期秒数
     *
     * @param key          要获取对象的key
     * @param expireSecond 过期秒数
     * @return
     */
    @Override
    public boolean setExpireForObject(String key, int expireSecond) {
        //获取jedis对象
        Jedis jedisClient = redisConfig.getJedisClient();

        //捕获并处理异常
        try {
            //为对象设置过期时间【1：代表过期时间设置成功，0：代表没有设置成功，可能键不存在】
            Long expire = jedisClient.expire(key, expireSecond);
            //判断是否设置成功
            if (expire == 1) {
                //成功
                return true;
            }

        } catch (Exception e) {
            //打印输出
            log.info("Exception：" + e.getMessage(), e);
        } finally {
            //判断当前jedis是否为空
            if (jedisClient != null) {
                //关闭
                jedisClient.close();
            }
        }
        //返回
        return false;
    }


    /**
     * 为指定缓存库中的对象键设置过期时间
     *
     * @param key          存放的key
     * @param expireSecond 设置的过期时间
     * @param dbIndex      指定的数据库索引【从0开始 -- 默认为0】
     * @return
     */
    @Override
    public boolean setExpireForObject(String key, int expireSecond, int dbIndex) {
        //获取jedis对象
        Jedis jedisClient = redisConfig.getJedisClient();

        //捕获并处理异常
        try {
            //指定数据库
            jedisClient.select(dbIndex);
            //为对象设置过期时间【1：代表过期时间设置成功，0：代表没有设置成功，可能键不存在】
            Long expire = jedisClient.expire(key, expireSecond);
            //判断是否设置成功
            if (expire == 1) {
                //成功
                return true;
            }

        } catch (Exception e) {
            //打印输出
            log.info("Exception：" + e.getMessage(), e);
        } finally {
            //判断当前jedis是否为空
            if (jedisClient != null) {
                //关闭
                jedisClient.close();
            }
        }
        //返回
        return false;
    }


    /**
     * 获取缓存对象过期秒数
     *
     * @param key 要获取对象的key
     * @return
     */
    @Override
    public Long getExpireObject(String key) {
        //获取jedis对象
        Jedis jedisClient = redisConfig.getJedisClient();

        //捕获并处理异常
        try {
            //获取过期秒数
            Long second = jedisClient.ttl(key);
            //返回
            return second;

        } catch (Exception e) {
            //打印输出
            log.info("Exception：" + e.getMessage(), e);
        } finally {
            //判断当前jedis是否为空
            if (jedisClient != null) {
                //关闭
                jedisClient.close();
            }
        }
        //返回
        return null;
    }


    /**
     * 获取指定缓存库中对象的过期秒数
     *
     * @param key     存放的key
     * @param dbIndex 指定的数据库索引【从0开始 -- 默认为0】
     * @return
     */
    @Override
    public Long getExpireObject(String key, int dbIndex) {
        //获取jedis对象
        Jedis jedisClient = redisConfig.getJedisClient();

        //捕获并处理异常
        try {
            //指定数据库
            jedisClient.select(dbIndex);
            //获取过期秒数
            Long second = jedisClient.ttl(key);
            //返回
            return second;

        } catch (Exception e) {
            //打印输出
            log.info("Exception：" + e.getMessage(), e);
        } finally {
            //判断当前jedis是否为空
            if (jedisClient != null) {
                //关闭
                jedisClient.close();
            }
        }
        //返回
        return null;
    }


    /**
     * 从缓存中删除对象
     *
     * @param key 要删除对象的key
     * @return
     */
    @Override
    public boolean deleteObject(String key) {
        //获取jedis对象
        Jedis jedisClient = redisConfig.getJedisClient();

        //捕获并处理异常
        try {
            //根据key删除对象
            Long del = jedisClient.del(key);
            //判断是否删除成功
            if (del == 1) {
                //返回
                return true;
            }

        } catch (Exception e) {
            //打印输出
            log.info("Exception：" + e.getMessage(), e);
        } finally {
            //判断当前jedis是否为空
            if (jedisClient != null) {
                //关闭
                jedisClient.close();
            }
        }
        //返回
        return false;
    }


    /**
     * 删除指定缓存数据库中的对象
     *
     * @param key     要删除的对象key
     * @param dbIndex 数据库索引【从0开始 -- 默认为0】
     * @return
     */
    @Override
    public boolean deleteObject(String key, int dbIndex) {
        //获取jedis对象
        Jedis jedisClient = redisConfig.getJedisClient();

        //捕获并处理异常
        try {
            //选择数据库
            jedisClient.select(dbIndex);
            //根据key删除对象
            Long del = jedisClient.del(key);
            //判断是否删除成功
            if (del == 1) {
                //返回
                return true;
            }

        } catch (Exception e) {
            //打印输出
            log.info("Exception：" + e.getMessage(), e);
        } finally {
            //判断当前jedis是否为空
            if (jedisClient != null) {
                //关闭
                jedisClient.close();
            }
        }
        //返回
        return false;
    }


    /**
     * 清除指定缓存数据库中的所有键
     */
    @Override
    public void clearDBSelectIndex(int dbIndex) {
        //获取jedis对象
        Jedis jedisClient = redisConfig.getJedisClient();

        //捕获并处理异常
        try {
            //选择数据库
            jedisClient.select(dbIndex);
            //清除对象
            jedisClient.flushDB();

        } catch (Exception e) {
            //打印输出
            log.info("Exception：" + e.getMessage(), e);
        } finally {
            //判断当前jedis是否为空
            if (jedisClient != null) {
                //关闭
                jedisClient.close();
            }
        }
    }
}
