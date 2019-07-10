package com.shine.iot.kafka.util;

import kafka.admin.AdminUtils;
import kafka.admin.BrokerMetadata;
import kafka.server.ConfigType;
import kafka.utils.ZkUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.common.security.JaasUtils;
import scala.collection.Map;
import scala.collection.Seq;

import java.util.Properties;

/**
 * Topic 主题管理【ZkUtils工具类，AdminUtils方式】
 */
public class TopicOperationUtil {
    //创建当前类日志对象
    private static Log log = LogFactory.getLog(TopicOperationUtil.class);

    //系统参数定义
    private final static String ZK_CONNECT = "192.168.2.113:2181";
    //session 过期时间
    private final static int SESSION_TIMEOUT = 30000;
    //连接超时时间
    private final static int CONNECT_TIMEOUT = 30000;

    /**
     * 创建topic
     *
     * @param topic      主题名称
     * @param partition  分区数量
     * @param repilca    副本数量
     * @param properties 配置参数
     * @return
     */
    public static boolean createTopic(String topic, int partition, int repilca, Properties properties) {
        //定义bool值
        boolean bool = false;
        //初始化 ZkUtils 工具对象，方便主题管理
        ZkUtils zkUtils = null;

        //捕获并处理异常
        try {
            //尝试创建主题【实例化 主题  按照 当前配置的参数】
            zkUtils = ZkUtils.apply(ZK_CONNECT, SESSION_TIMEOUT, CONNECT_TIMEOUT, JaasUtils.isZkSecurityEnabled());
            //AdminUtils 管理工具
            if (!AdminUtils.topicExists(zkUtils, topic)) {
                //不存在主题 具体进行创建
                AdminUtils.createTopic(zkUtils, topic, partition, repilca, properties, AdminUtils.createTopic$default$6());
                //赋值
                bool = true;
            } else {
                //提示已经存在
                log.info("topic" + topic + "already exists！");
            }
        } catch (Exception e) {
            //打印输出
            log.info("topic " + topic + " creation failed！" + e.getMessage(), e);
        } finally {
            //关闭ZkUtils工具类对象
            zkUtils.close();
        }

        //返回
        return bool;
    }

    /**
     * 修改主题
     *
     * @param topic      修改的主题名称
     * @param properties 主题的新配置信息
     * @return
     */
    public static boolean updateTopic(String topic, Properties properties) {
        //定义bool值
        boolean bool = false;
        //初始化 ZkUtils 工具对象，方便主题管理
        ZkUtils zkUtils = null;

        //捕获并处理异常
        try {
            //实例化 ZkUtils
            zkUtils = ZkUtils.apply(ZK_CONNECT, SESSION_TIMEOUT, CONNECT_TIMEOUT, JaasUtils.isZkSecurityEnabled());
            //首先获取当前已有的配置，这里是查询主题级别的配置，因此指定配置类型为Topic
            Properties curProp = AdminUtils.fetchEntityConfig(zkUtils, ConfigType.Topic(), topic);
            //融合最新的主题配置
            curProp.putAll(properties);
            //替换当前 主题配置 执行信息
            AdminUtils.changeTopicConfig(zkUtils, topic, curProp);
            //赋值
            bool = true;
        } catch (Exception e) {
            //打印输出
            log.info("topic " + topic + " update failed！" + e.getMessage(), e);
        } finally {
            //关闭ZkUtils工具类对象
            zkUtils.close();
        }

        //返回
        return bool;
    }

    /**
     * 删除topic
     *
     * @param topic 删除的topic
     * @return
     */
    public static boolean deleteTopic(String topic) {
        //定义bool值
        boolean bool = false;
        //初始化 ZkUtils 工具对象，方便主题管理
        ZkUtils zkUtils = null;

        //捕获并处理异常
        try {
            //实例化 ZkUtils 关键是 连接地址
            zkUtils = ZkUtils.apply(ZK_CONNECT, SESSION_TIMEOUT, CONNECT_TIMEOUT, JaasUtils.isZkSecurityEnabled());
            //执行删除指定主题动作
            AdminUtils.deleteTopic(zkUtils, topic);
            //赋值
            bool = true;
        } catch (Exception e) {
            //打印输出
            log.info("topic " + topic + " delete failed！" + e.getMessage(), e);
        } finally {
            //关闭ZkUtils工具类对象
            zkUtils.close();
        }

        //返回
        return bool;
    }

    /**
     * 增加分区
     *
     * @param topic      主题名称
     * @param partition  分区总数，这个直接是最终的数量
     * @param partitions 副本分配方案 格式为 "2:1,3:1" 这个表示的是2个分区分别对应的副本情况，0分区对应brokerid为2,1的，1分区对应的副本ID为 3,1
     *                   不同的分区的副本用逗号分隔，同一个分区的多个副本之间用冒号分隔
     *                   同时需要注意的是，副本分配方案要包括已有分区的副本分配信息，根据分配顺序从左到右依次与分区对应，分区编号递增
     * @return
     */
    public static boolean addPartition(String topic, int partition, String partitions) {
        //定义bool值
        boolean bool = false;
        //初始化 ZkUtils 工具对象，方便主题管理
        ZkUtils zkUtils = null;

        //捕获并处理异常
        try {
            /*//实例化 ZkUtils 关键是 连接地址
            zkUtils = ZkUtils.apply(ZK_CONNECT, SESSION_TIMEOUT,CONNECT_TIMEOUT,JaasUtils.isZkSecurityEnabled());
            //执行删除指定主题动作
            AdminUtils.addPartitions(zkUtils, topic, partition, partitions, true, AdminUtils.addPartitions$default$6());*/

        } catch (Exception e) {
            //打印输出
            log.info("partition " + topic + " add failed！" + e.getMessage(), e);
        } finally {
            //关闭ZkUtils工具类对象
            zkUtils.close();
        }

        //返回
        return bool;
    }

    /**
     * 分区副本重新配置
     *
     * @param topic     主题
     * @param partition 分区数
     * @param repilca   副本数
     *                  通过修改，达成重新分配的目的
     *                  步骤：
     *                  1 实例化 ZkUtils
     *                  2 获取代理元数据 (BrokerMetadata) 信息
     *                  3 生成分区副本分配方案，当然也可以自定义分配方案
     *                  4 调用 createOrUpdateTopicPartitionAssignmentPathInZK()方法完成副本分配
     *                  5 释放与 zookeeper的连接
     * @return
     */
    public static boolean modifyPartition(String topic, int partition, int repilca) {
        //定义bool值
        boolean bool = false;
        //初始化 ZkUtils 工具对象，方便主题管理
        ZkUtils zkUtils = null;

        //捕获并处理异常
        try {
            //1 实例化 ZkUtils
            zkUtils = ZkUtils.apply(ZK_CONNECT, SESSION_TIMEOUT, CONNECT_TIMEOUT, JaasUtils.isZkSecurityEnabled());
            //2 获取代理元数据 (BrokerMetadata) 信息
            Seq<BrokerMetadata> brokerMetadata = AdminUtils.getBrokerMetadatas(zkUtils, AdminUtils.getBrokerMetadatas$default$2(), AdminUtils.getBrokerMetadatas$default$3());
            //3 生成分区副本分配方案，当然也可以自定义分配方案
            Map<Object, Seq<Object>> replicaAssign = AdminUtils.assignReplicasToBrokers(brokerMetadata, partition, repilca, AdminUtils.assignReplicasToBrokers$default$4(), AdminUtils.assignReplicasToBrokers$default$5());
            //4 调用 createOrUpdateTopicPartitionAssignmentPathInZK()方法完成副本分配
            AdminUtils.createOrUpdateTopicPartitionAssignmentPathInZK(zkUtils, topic, replicaAssign, null, true);
            //赋值
            bool = true;
        } catch (Exception e) {
            //打印输出
            log.info("partition " + topic + " add failed！" + e.getMessage(), e);
        } finally {
            //释放与 zookeeper的连接
            zkUtils.close();
        }

        //返回
        return bool;
    }
}
