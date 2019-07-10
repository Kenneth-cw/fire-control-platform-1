package com.shine.iot.kafka.producer;

import lombok.Data;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * 生产者配置信息加载
 */
@Component
@PropertySource(value = "classpath:config/producerConfig.yml")
@ConfigurationProperties(prefix = "producer")
@Data
public class ProducerConfig {
    //创建当前类日志对象
    private Log log = LogFactory.getLog(ProducerConfig.class);

    //定义变量
    private String bootstrapservers;
    private String acks;
    private int retries;
    private int batchsize;
    private int lingerms;
    private int buffermemory;
    private String keyserializer;
    private String valueserializer;

    //创建KafkaTemplate对象
    KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * init方法，读取kafka生产者配置信息
     */
    @PostConstruct
    public void init() {
        //调用获取kafkaTemplate方法
        KafkaTemplate<String, Object> kafkaTemplate = kafkaTemplate();
        //赋值变量
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * 获取KafkaTemplate对象
     *
     * @return
     */
    public KafkaTemplate<String, Object> getKafkaTemplate() {
        //返回
        return kafkaTemplate;
    }

    /**
     * 生产者信息配置
     *
     * @return
     */
    public Map<String, Object> producerConfigs() {
        //创建map集合
        Map<String, Object> props = new HashMap<>();
        //配置属性
        props.put("bootstrap.servers", bootstrapservers);
        props.put("acks", acks);
        props.put("retries", retries);
        props.put("batch.size", batchsize);
        props.put("linger.ms", lingerms);
        props.put("buffer.memory", buffermemory);
        props.put("key.serializer", keyserializer);
        props.put("value.serializer", valueserializer);

        //返回
        return props;
    }

    /**
     * producerFactory
     *
     * @return
     */
    public ProducerFactory<String, Object> producerFactory() {
        //返回
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    /**
     * kafkaTemplate对象
     *
     * @return
     */
    public KafkaTemplate<String, Object> kafkaTemplate() {
        //返回
        return new KafkaTemplate<String, Object>(producerFactory());
    }
}
