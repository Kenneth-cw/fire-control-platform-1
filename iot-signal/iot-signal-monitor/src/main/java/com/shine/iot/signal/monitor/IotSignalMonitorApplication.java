package com.shine.iot.signal.monitor;

import com.shine.iot.signal.monitor.kafkaUtils.TLVSourceChannels;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableEurekaClient
@EnableFeignClients //开启使用feign接口模拟Http客户端，远程调用服务
@ServletComponentScan //扫描通过@WebServlet、@WebFilter、@WebListener注解表示的Servlet、Filter、Listener，可以自动注册
@EnableBinding(value = {TLVSourceChannels.class})
//开启绑定功能（绑定通道） //@EnableBinding注解： 将Spring应用转成Spring Cloud Stream应用，接受一个或多个接口类作为参数，通常为消息通道的方法。eg：@EnableBinding(value={Channel-1.class, Channel-2.class})
public class IotSignalMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(IotSignalMonitorApplication.class, args);
        System.out.println("----- LoraLite signal monitor start success! -----http://localhost:" + 8070);
    }

    /**
     *
     */

}
