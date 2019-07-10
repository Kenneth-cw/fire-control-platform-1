package com.shine.iot.signal.monitor.config;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    //可取值，读取yml配置文件的值(必须是application.yml 或 application-*.yml)
    /*@Value("${loralite.netserver.ipaddr}")
    private String netIP;
    @Value("${loralite.netserver.port}")
    private String netPort;
    @Value("${loralite.netserver.ipaddr}")
    private String ackDelayed;

    @RequestMapping("/getbyvalue")
    public void testGetValueByValue(){
        System.out.println("--netIP--" + netIP);
        System.out.println("--netPort--" + netPort);
        System.out.println("--ackDelayed--" + ackDelayed);
    }*/

}
