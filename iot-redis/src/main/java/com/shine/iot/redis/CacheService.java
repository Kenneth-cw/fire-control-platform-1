package com.shine.iot.redis;

/**
 * 缓存服务接口类
 */
public interface CacheService {
    /**
     * 将对象存放到缓存中
     *
     * @param key   存放的key
     * @param value 存放的值
     */
    void putObject(String key, Object value);


    /**
     * 将对象存放到指定数据库中
     *
     * @param key     存放的key
     * @param value   存放的值
     * @param dbIndex 数据库索引(索引从0开始 -- 默认为0)
     */
    void putObjectSelectDBIndex(String key, Object value, int dbIndex);


    /**
     * 设置对象过期时间并存放到缓存中
     *
     * @param key        存放的key
     * @param value      存放的值
     * @param expiration 过期时间，单位秒【过期后将自动删除】
     */
    void putObject(String key, Object value, int expiration);


    /**
     * 设置对象过期时间并存放到指定缓存数据库中
     *
     * @param key        存放的key
     * @param value      存放的值
     * @param expiration 设置的过期时间
     * @param dbIndex    数据库索引(索引从0开始 -- 默认为0)
     */
    void putObject(String key, Object value, int expiration, int dbIndex);


    /**
     * 从缓存中获取对象
     *
     * @param key 要获取对象的key
     * @return 如果存在，返回对象，否则，返回null
     */
    Object pullObject(String key);


    /**
     * 从指定缓存数据库中获取对象
     *
     * @param key
     * @param dbIndex
     * @return
     */
    Object pullObjectSelectDBIndex(String key, int dbIndex);


    /**
     * 给缓存对象键单独设置过期时间
     *
     * @param key          要获取对象的key
     * @param expireSecond 过期秒数
     * @return 如果存在，返回对象，否则，返回null
     */
    boolean setExpireForObject(String key, int expireSecond);


    /**
     * 为指定缓存库中的对象键设置过期时间
     *
     * @param key          存放的key
     * @param expireSecond 设置的过期时间
     * @param dbIndex      指定的数据库索引【从0开始 -- 默认为0】
     * @return
     */
    boolean setExpireForObject(String key, int expireSecond, int dbIndex);


    /**
     * 获取缓存对象过期秒数
     *
     * @param key 要获取对象的key
     * @return 如果对象不存在，返回-2，如果对象没有过期时间，返回-1，否则返回实际过期时间
     */
    Long getExpireObject(String key);


    /**
     * 获取指定缓存库中对象的过期秒数
     *
     * @param key     存放的key
     * @param dbIndex 指定的数据库索引【从0开始 -- 默认为0】
     * @return
     */
    Long getExpireObject(String key, int dbIndex);


    /**
     * 从缓存中删除对象
     *
     * @param key 要删除对象的key
     * @return 如果出现错误，返回 false，否则返回true
     */
    boolean deleteObject(String key);


    /**
     * 删除指定缓存数据库中的对象
     *
     * @param key     要删除的对象key
     * @param dbIndex 数据库索引【从0开始 -- 默认为0】
     * @return
     */
    boolean deleteObject(String key, int dbIndex);


    /**
     * 清除指定缓存数据库中的所有键
     */
    void clearDBSelectIndex(int dbIndex);
}
