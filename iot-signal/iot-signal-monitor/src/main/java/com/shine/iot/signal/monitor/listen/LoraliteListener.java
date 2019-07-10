package com.shine.iot.signal.monitor.listen;

import com.shine.iot.signal.monitor.server.GW81Server;
import com.shine.iot.signal.monitor.testListen.LoraliteGW81Server;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * 初始化监听器，开启接收loraLite协议信号的
 */
@WebListener
public class LoraliteListener implements ServletContextListener {
    private Logger logger = LogManager.getLogger(LoraliteGW81Server.class);

    Thread gw81server;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        gw81server = new Thread(new GW81Server());
        gw81server.start();
        /*int port = 1700;
        try {
            if(StringUtils.isBlank(IP)){
                IP = "127.0.0.1";
                logger.warn("读取application-loralite.yml配置文件失败，IP属性读取为null");
            }
            if (NumberUtils.isDigits(UDP_PORT)){
                port = Integer.parseInt(UDP_PORT);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            logger.error("网关服务器的端口设置不正确，默认使用1700，接受loraLite协议：" + e.getMessage(),e);
        }
        logger.info("========UDP LoraliteListener Initialized========= "+IP+":"+UDP_PORT);

        gw81server = new Thread(new GW81Server(IP, port));
        gw81server.start();*/
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("========UDP LoraliteListener Destroyed=========");
        if (gw81server.isAlive()) {
            gw81server.interrupt();
            logger.info("========UDP LoraliteListener interrupted=========");
        }
    }
}
