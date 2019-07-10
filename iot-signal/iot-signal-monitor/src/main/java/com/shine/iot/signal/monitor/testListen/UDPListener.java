package com.shine.iot.signal.monitor.testListen;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

//@WebListener
public class UDPListener implements ServletContextListener {
    private Logger logger = LogManager.getLogger(LoraliteGW81Server.class);
    public static final int UDP_PORT = 1700;

    //容器初始化
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("========UDPListener Initialized=========");
        try {
            // 启动一个线程，监听UDP数据报
            new Thread(new UDPProcess(UDP_PORT)).start();
            // 可以启动其他的线程。。。。
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //容器销毁
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("========UDPListener Destroyed=========");
    }
}
