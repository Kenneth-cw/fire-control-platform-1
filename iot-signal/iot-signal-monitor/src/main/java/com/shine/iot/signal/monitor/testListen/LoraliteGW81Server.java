package com.shine.iot.signal.monitor.testListen;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * 一个端口只接受一个厂商的一种协议的信号
 * 接收LoraLite信号
 */
public class LoraliteGW81Server implements Runnable {

    private Logger logger = LogManager.getLogger(LoraliteGW81Server.class);
    private static final String AUG_SPLIT_FLAG = "7374617274";
    public static final int MAX_UDP_DATA_SIZE = 1024;

    DatagramSocket socket = null;

    public LoraliteGW81Server(final int port) throws SocketException {
        socket = new DatagramSocket(port);
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        logger.info("=======LoraliteGW81Server start======");
        byte[] compBuffer = new byte[1024];
        while (true) {
            byte[] buffer = new byte[MAX_UDP_DATA_SIZE];
            DatagramPacket recv_pkt = new DatagramPacket(buffer, buffer.length);
            try {
                socket.receive(recv_pkt);
                logger.info("data from IP:" + recv_pkt.getAddress() + " and port : " + recv_pkt.getPort());
                byte[] recv_data = new byte[recv_pkt.getLength()];
                System.arraycopy(recv_pkt.getData(), 0, recv_data, 0, recv_pkt.getLength());

                String totalData = Hex.encodeHexString(recv_data);
                logger.info("data received HEX STRING : [" + totalData + "];length=" + recv_pkt.getData().length
                        + ";offset:" + recv_pkt.getOffset());

                if (!ArrayUtils.isEmpty(recv_data)) {
                    if (totalData.startsWith(AUG_SPLIT_FLAG)) {
                        String[] deviceSignals = totalData.split(AUG_SPLIT_FLAG);
                        for (String deviceSignal : deviceSignals) {
                            if (StringUtils.isBlank(deviceSignal)) continue;
                            logger.info("loralite signal : " + deviceSignal);
                            //将接受到的消息发送到kafka队列，以 厂商+协议 分类
                        }
                    }
                } else {
                    //logger.debug("接受到的数据报为空");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


   /* class UDPProcess implements Runnable {
        DatagramSocket socket = null;

        public UDPProcess(final int port) throws SocketException {
            socket = new DatagramSocket(port);
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            System.out.println("=======UDPProcess======");
            while (true) {
                byte[] buffer = new byte[MAX_UDP_DATA_SIZE];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                try {
                    socket.receive(packet);
                    new Thread(new Process(packet)).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    class Process implements Runnable {

        public Process(DatagramPacket packet) throws UnsupportedEncodingException {
            // TODO Auto-generated constructor stub
            byte[] buffer = packet.getData();// 接收到的UDP信息，然后解码
            String srt1 = new String(buffer,"GBK").trim();
            String srt2 = new String(buffer, "UTF-8").trim();
            String srt3 = new String(buffer,"ISO-8859-1").trim();
            System.out.println("=======Process srt1 GBK======" + srt1);
            System.out.println("=======Process srt2 UTF-8======" + srt2);
            System.out.println("=======Process srt3 ISO-8859-1======" + srt3);
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            System.out.println("====Process run=====");
        }
    }*/


}

