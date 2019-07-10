package com.shine.iot.signal.monitor;

import com.shine.iot.signal.monitor.kafkaUtils.LoraLiteAugTLVMsgSender;
import com.shine.iot.signal.monitor.kafkaUtils.TLVSourceChannels;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableBinding(value = {TLVSourceChannels.class}) //开启绑定功能
public class IotSignalMonitorApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void testUDPListen() {
        try {
            byte[] buffer = new byte[4096]; //数据报包的大小
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            DatagramSocket socket = new DatagramSocket(1670);
            socket.receive(packet);
            // new Thread(new ).start();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试向自定义的消息通道发送消息是否可以成功
     */
    @Autowired
    @Qualifier("outputLoraLite")
    private MessageChannel messageChannel;

    @Test
    public void testSendToKafka() {
        //向管道发送消息
        boolean flag = messageChannel.send(MessageBuilder.withPayload("produce by multiple pipe").build());
        System.out.println("flag：" + flag);
    }

    @Autowired
    private LoraLiteAugTLVMsgSender loraLiteAugTLVMsgSender;

    @Test
    public void testKafkaLoraLiteSender() {
        //向管道发送消息
        String payload = "1111111111111111111111111";
        boolean flag = loraLiteAugTLVMsgSender.sendMessage(payload);
        System.out.println("flag：" + flag);
    }


}
