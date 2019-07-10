package com.shine.iot.signal.monitor.socket.augTlV;

import com.shine.iot.signal.monitor.config.LoraLiteYmlReadUtils;
import com.shine.iot.signal.monitor.socket.ISocketServer;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.*;

/**
 * 收发LoraLite协议数据的SocketServer，此socket为单例模式
 */

public class AugTLVSocketServer implements ISocketServer {

    private static Logger logger = LogManager.getLogger(AugTLVSocketServer.class);
    private static DatagramSocket dsock;
    private static AugTLVSocketServer instance;

    //@Value方式在 初始化就加载的类中yml属性注入不进来
	/* @Value("${loralite.netserver.ipaddr}")
	private String serverIp;
	@Value("${loralite.netserver.port}")
	private String _serverPort;*/

    private AugTLVSocketServer() {
        String serverIp = (String) LoraLiteYmlReadUtils.getCommonYml("loralite.netserver.ipaddr");
        Integer serverPort = (Integer) LoraLiteYmlReadUtils.getCommonYml("loralite.netserver.port");
        try {
            if (StringUtils.isBlank(serverIp)) {
                serverIp = "127.0.0.1";
                logger.warn("没有设置网络服务器的IP，将使用默认127.0.0.1");
            }
            if (serverPort == null || serverPort <= 0) {
                serverPort = 1700;
                logger.warn("没有设置网络服务器的端口，将使用默认端口1700.");
            }
        } catch (Exception e) {
            serverIp = "127.0.0.1";
            serverPort = 1700;
            logger.error("网络服务器的端口设置不正确，系统将使用默认端口1700.错误原因：" + e.getMessage(), e);
        }
        try {
            InetSocketAddress socketAddress = new InetSocketAddress(InetAddress.getByName(serverIp), serverPort);
            dsock = new DatagramSocket(socketAddress);

            logger.info("\n******************************************************************************************\n"
                    + "Aug TLV SOCKET SERVER INITIALIZED SUCCESSFULLY WITH ADDRESS: [" + serverIp + ":" + serverPort + "]\n"
                    + "******************************************************************************************");
        } catch (UnknownHostException e) {
            logger.fatal("FAILED to initialize Aug TLV socket server,Please reset HOST ADDRESS for socket IN CONFIGURATION FILES !" + e.getMessage(), e);
        } catch (SocketException e) {
            logger.fatal("FAILED to initialize Aug TLV socket server because of SOCKET EXCEPTION ,please CHECK SOCKET CONFIGURATION!" + e.getMessage(), e);
        }
    }

    public static AugTLVSocketServer getSingleInstance() {
        if (instance == null) {
            instance = new AugTLVSocketServer();
        }
        return instance;
    }

    /* (non-Javadoc)
     * @see com.shineiot.server.standard.ISocketServer#receiveData(java.net.DatagramPacket)
     */
    @Override
    public void receiveData(DatagramPacket data) throws IOException {
        dsock.receive(data);
    }

    /* (non-Javadoc)
     * @see com.shineiot.server.standard.ISocketServer#sendData(java.net.DatagramPacket)
     */
    @Override
    public void sendData(DatagramPacket sendData) throws IOException {
        dsock.send(sendData);
    }

}
