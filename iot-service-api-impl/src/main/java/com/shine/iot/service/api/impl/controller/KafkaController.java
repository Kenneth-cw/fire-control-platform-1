package com.shine.iot.service.api.impl.controller;

//import com.shine.iot.kafka.producer.ProducerConfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import org.springframework.kafka.core.KafkaTemplate;

/**
 * kafka 测试controller
 */
@RestController
@RequestMapping(value = "kafka")
public class KafkaController {
    //创建当前类日志对象
    private Log log = LogFactory.getLog(KafkaController.class);

  /*  //实例化
    @Autowired
    private ProducerConfig config;

    //测试方法
    @GetMapping(value = "test")
    public String test() {
        //获取kafkaTemplate对象
        KafkaTemplate<String, Object> kafkaTemplate = config.getKafkaTemplate();

        //写数据
        kafkaTemplate.send("fire", "01000103003608181226155616402F3F0310031003111A25FFFFFFFFFFFFFFFF");

        return "查看是否调用成功！！！";
    }*/
}
