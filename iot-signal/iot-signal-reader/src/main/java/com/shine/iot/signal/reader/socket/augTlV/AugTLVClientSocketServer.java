package com.shine.iot.signal.reader.socket.augTlV;

import com.shine.iot.signal.reader.config.LoraLiteYmlReadUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.*;

//@NoArgsConstructor //创建无参构造函数
public class AugTLVClientSocketServer {

    private static Logger logger = LogManager.getLogger(AugTLVClientSocketServer.class);
    private static DatagramSocket dsock;
    private static AugTLVClientSocketServer instance;

    private static final int MAXNUM = 5; // 设置重发数据的最多次数
    //@Value("${loralite.netserver.timeout}")
    //private static final int TIMEOUT;  //设置接收数据的超时时间

    public AugTLVClientSocketServer() {
        try {
            String serverIP = (String) LoraLiteYmlReadUtils.getCommonYml("loralite.clientnetserver.ipaddr");
            String port = (String) LoraLiteYmlReadUtils.getCommonYml("loralite.clientnetserver.ipaddr");
            if (StringUtils.isBlank(serverIP)) {
                serverIP = "127.0.0.1";
                logger.warn("读取配置文件configs/loralite-config.yml中 ipaddr 为null，则使用默认的127.0.0.1");
            }
            int _port = 18001;
            if (NumberUtils.isParsable(port) && NumberUtils.isDigits(port)) {
                _port = Integer.parseInt(port);
                logger.warn("读取配置文件configs/loralite-config.yml中 port 为null，则使用默认的18001");
            }
            logger.info("构建下发ACK的Socket，serverIP:" + serverIP + ",port:" + _port);
            InetSocketAddress socketAddress = new InetSocketAddress(InetAddress.getByName(serverIP), _port);
            dsock = new DatagramSocket(socketAddress);
            //socket.setSoTimeout(TIMEOUT); //设置接收数据时阻塞的最长时间

        } catch (UnknownHostException e) {
            e.printStackTrace();

        } catch (SocketException e) {
            e.printStackTrace();
        }

    }

    // 懒汉式实现 单例对象 构建
    // 线程安全，调用效率不高，但是能延时加载
    /*public static AugTLVClientSocketServer getSingleInstance() {
        if(instance == null) {
            instance = new AugTLVClientSocketServer();
        }
        return instance;
    }*/

    /**
     * 静态内部类实现模式 单例对象 构建
     * 优点：线程安全，调用效率高，可以延时加载
     */
    private static class SingletonClassInstance {
        private static final AugTLVClientSocketServer instance = new AugTLVClientSocketServer();
    }

    /**
     * 根据静态内部类，得到单例模式
     *
     * @return UDPClientSocket
     */
    public static AugTLVClientSocketServer getInstance() {
        return SingletonClassInstance.instance;
    }

    /**
     * 发送数据报包
     *
     * @param sendData
     * @throws IOException
     */
    public void sendData(DatagramPacket sendData) throws IOException {
        dsock.send(sendData);
    }

}
