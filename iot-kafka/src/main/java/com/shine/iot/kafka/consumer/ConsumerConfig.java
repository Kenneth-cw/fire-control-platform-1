package com.shine.iot.kafka.consumer;

import lombok.Data;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 消费者配置信息加载
 */
@Component
@PropertySource(value = "classpath:config/consumerConfig.yml")
@ConfigurationProperties(prefix = "consumer")
@Data
public class ConsumerConfig {
    //创建当前类日志对象
    private Log log = LogFactory.getLog(ConsumerConfig.class);

    //定义变量
    private String bootstrapservers;
    private String groupid;
    private String enableautocommit;
    private String autocommitintervalms;
    private String sessiontimeoutms;
    private String keydeserializer;
    private String valuedeserializer;
    private String autooffsetreset;

    /**
     * kafkaListenerContainerFactory监听容器工厂对象
     *
     * @return
     */
    @Bean
    ConcurrentKafkaListenerContainerFactory<Integer, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Integer, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    /**
     * consumerFactory
     *
     * @return
     */
    @Bean
    public ConsumerFactory<Integer, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    /**
     * 启动加载消费者信息配置
     *
     * @return
     */
    @Bean
    public Map<String, Object> consumerConfigs() {
        //创建map集合
        Map<String, Object> props = new HashMap<>();
        //配置属性
        props.put("bootstrap.servers", bootstrapservers);
        props.put("group.id", groupid);
        props.put("enable.auto.commit", enableautocommit);
        props.put("auto.commit.interval.ms", autocommitintervalms);
        props.put("session.timeout.ms", sessiontimeoutms);
        props.put("key.deserializer", keydeserializer);
        props.put("value.deserializer", valuedeserializer);
        props.put("auto.offset.reset", autooffsetreset);

        //返回
        return props;
    }
}
