package com.shine.iot.service.api.impl.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.springframework.kafka.annotation.KafkaListener;

/**
 * 消费者监听【测试】
 */
@Component
public class ConsumerListener extends Thread {
    //创建当前类日志对象
    private Log log = LogFactory.getLog(ConsumerListener.class);

    //监听string前缀的数据
    /*@KafkaListener(topics = "fire")
    public void listenasData(ConsumerRecord<String, String> cr) {
        //打印输出
        log.info("消费数据为：" + cr.value());
    }*/
}
