package com.shine.iot.signal.monitor.testListen;

import com.shine.iot.signal.monitor.config.LoraLiteYmlReadUtils;
import com.shine.iot.signal.monitor.socket.ISocketServer;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.*;

/**
 * 测试：构建用于接受和发送loraLite烟感数据的Socket，此socket为单例
 */
public class AugTLVTestNetSocket implements ISocketServer {
    private Logger logger = LogManager.getLogger(AugTLVTestNetSocket.class);
    private AugTLVTestNetSocket instance;
    private DatagramSocket testNetSocket;

    public AugTLVTestNetSocket() {
        // 读取YML配置文件中 测试服务器的 IP 和 port
        String testNetIP = (String) LoraLiteYmlReadUtils.getCommonYml("test.netserver.ipaddr");
        String testNetPort = (String) LoraLiteYmlReadUtils.getCommonYml("test.netserver.port");
        int _testNetPort = 1801;
        try {
            if (StringUtils.isBlank(testNetIP) || StringUtils.isEmpty(testNetIP)) {
                testNetIP = "127.0.0.1";
                logger.warn("没有设置测试网络服务器的IP，将使用默认127.0.0.1");
            }
            if (NumberUtils.isParsable(testNetPort) || NumberUtils.isDigits(testNetPort)) {
                _testNetPort = Integer.parseInt(testNetPort);
            } else {
                logger.warn("没有设置测试网络服务器的端口，将使用默认端口1801.");
            }
        } catch (Exception e) {
            logger.error("测试环境的网络服务器的端口设置不正确，系统将使用默认端口1801.错误原因：" + e.getMessage(), e);
            e.printStackTrace();
        }
        //构建 向 测试服务器发送LoraLite烟感信号的 UDP 的 socket
        try {
            InetSocketAddress socketAddress = new InetSocketAddress(InetAddress.getByName(testNetIP), _testNetPort);
            testNetSocket = new DatagramSocket(socketAddress);

            logger.info("\n******************************************************************************************\n"
                    + "Test Environment ：Aug TLV TEST SOCKET SERVER INITIALIZED SUCCESSFULLY WITH ADDRESS: [" + testNetIP + ":" + _testNetPort + "]\n");
        } catch (UnknownHostException e) {
            logger.fatal("Test Environment ：FAILED to initialize Aug TLV socket server,Please reset HOST ADDRESS for socket IN CONFIGURATION FILES !" + e.getMessage(), e);
        } catch (SocketException e) {
            logger.fatal("Test Environment ：FAILED to initialize Aug TLV socket server because of SOCKET EXCEPTION ,please CHECK SOCKET CONFIGURATION!" + e.getMessage(), e);
        }
    }

    /**
     * 使用静态内部类实现单例类构建
     */
    private static class SingletonClassInstance {
        private static final AugTLVTestNetSocket augTLVTestNetSocket = new AugTLVTestNetSocket();
    }

    /**
     * 暴露此方法，获取单例
     *
     * @return 向测试服务器发送数据报的Socket
     */
    public static AugTLVTestNetSocket getInstance() {
        return SingletonClassInstance.augTLVTestNetSocket;
    }

    /**
     * 发送UDP数据
     *
     * @param sendPacket DatagramPacket数据报
     * @throws IOException 抛出异常
     */
    public void sendData(DatagramPacket sendPacket) throws IOException {
        testNetSocket.send(sendPacket);
    }

    /**
     * 接受UDP数据
     *
     * @param sendPacket
     * @throws IOException
     */
    @Override
    public void receiveData(DatagramPacket sendPacket) throws IOException {
        testNetSocket.receive(sendPacket);
    }

}
