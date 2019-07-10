package com.shine.iot.signal.monitor.testListen;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * UDP进程，创建UDPSocket接收数据报
 */
public class UDPProcess implements Runnable {
    private Logger logger = LogManager.getLogger(UDPProcess.class);

    public static final int MAX_UDP_DATA_SIZE = 4096;

    DatagramSocket socket = null;

    public UDPProcess(final int port) throws SocketException {
        socket = new DatagramSocket(port);
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        logger.info("=======UDPProcess start======");
        while (true) {
            byte[] buffer = new byte[MAX_UDP_DATA_SIZE];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
