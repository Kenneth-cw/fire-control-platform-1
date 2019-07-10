package com.shine.iot.signal.monitor.server;

import com.shine.iot.signal.monitor.kafkaUtils.LoraLiteAugTLVMsgSender;
import com.shine.iot.signal.monitor.server.datasave.TLVDataService;
import com.shine.iot.signal.monitor.socket.augTlV.AugTLVSocketServer;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.net.DatagramPacket;
import java.util.HashMap;
import java.util.Map;

@Service
public class GW81Server implements Runnable {
    private Logger logger = LogManager.getLogger(GW81Server.class);
    private static final String AUG_SPLIT_FLAG = "7374617274";

    private AugTLVSocketServer augTlvServer; // 监听 接收 LoraLite协议的serverSocket

    public GW81Server() {
        augTlvServer = AugTLVSocketServer.getSingleInstance();
    }

    @Override
    public void run() {
        Integer receivedNum = 0;

        while (true) {
            byte[] buffer = new byte[512];
            DatagramPacket recv_pkt = new DatagramPacket(buffer, buffer.length);
            try {
                augTlvServer.receiveData(recv_pkt);
                logger.info("data from addr:" + recv_pkt.getAddress() + " and port : " + recv_pkt.getPort() + "; No."
                        + receivedNum);
                byte[] recvData = new byte[recv_pkt.getLength()];
                System.arraycopy(recv_pkt.getData(), 0, recvData, 0, recv_pkt.getLength());
                String totalData = Hex.encodeHexString(recvData);

                logger.info("data received HEX STRING : [" + totalData + "];length="
                        + recv_pkt.getData().length + ";offset:" + recv_pkt.getOffset());
                if (!ArrayUtils.isEmpty(recvData)) {
                    if (totalData.startsWith(AUG_SPLIT_FLAG)) {
                        String[] deviceSignals = totalData.split(AUG_SPLIT_FLAG);
                        for (String deviceSignal : deviceSignals) {
                            if (StringUtils.isBlank(deviceSignal)) continue;
                            String tlvData = AUG_SPLIT_FLAG + deviceSignal;
                            ///logger.info("loraLite payload：" + payload);
                            if (recvData.length <= 18) {
                                logger.info("网关心跳信息，暂不处理！");
                            }
                            if (receivedNum >= 0xEFFF) receivedNum = 0;

                            //1. 将信号发送到Kafka消息队列中；（以 厂商+协议 分类发送到kafka）
                            //boolean sendFlag = LoraLiteAugTLVMsgSender.sendMessage(payload);
                            Map<String, Object> mapMsg = new HashMap<>();
                            mapMsg.put("IP", recv_pkt.getAddress());
                            mapMsg.put("port", recv_pkt.getPort());
                            mapMsg.put("content", tlvData);
                            boolean sendFlag = LoraLiteAugTLVMsgSender.sendMapMessage(mapMsg);
                            logger.info("发送到kafka的消息，发送结果：" + sendFlag);

                           /* NodeDataDto nodeDataDto = new NodeDataDto();
                            nodeDataDto.setGwIP(recv_pkt.getAddress().getHostAddress());
                            nodeDataDto.setGwPort(recv_pkt.getPort());
                            nodeDataDto.setRawPayload(tlvData);
                            boolean sendFlag = LoraLiteAugTLVMsgSender.sendObjMessage(nodeDataDto);
                            logger.info("发送到kafka的消息，发送结果：" + sendFlag);*/

                            //2. 存储从终端接收到原始信号
                            // 验证规则: CRC: TODO，怎么验证TLV数据的CRC是否正确，CRC正确再保存TLV数据
                            String gwIP = recv_pkt.getAddress().getHostAddress();
                            TLVDataService.saveTLVRawSignal(gwIP, recv_pkt.getPort(), tlvData);

                            //3. 将信号分发到测试服务器，构建测试环境
                            /* 不使用这种方式构建测试环境，构建测试环境的思路如下：
                             *   1. 构建一个应用：iot-signal-test-parser ：
                             *       订阅主题：iot-signal-monitor产生的；
                             *       当前应用中消费者组与生产环境应用中的消费者组同时订阅相同的主题
                             */
                           /* DatagramPacket datagramPacket = new DatagramPacket(recvData, recvData.length, InetAddress.getByName(testIP), testPort);
                            augTlvServer.sendData(datagramPacket);
                            logger.info("将loraLite的TLV数据，发送到测试服务器，测试服务器的地址为：" + testIP + " and port:" + testPort);*/


                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

}
