package com.shine.iot.signal.parser;

import com.shine.iot.signal.parser.kafkaListener.LoraLiteMsgReceiver;
import com.shine.iot.signal.parser.kafkaListener.TLVProcessorChannels;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableBinding(value = {TLVProcessorChannels.class})
public class IotSignalParserApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private LoraLiteMsgReceiver loraLiteMsgReceiver;

    @Test
    public void testRcv() {
        //loraLiteMsgReceiver.
        /*Message<String> msg = new Me
        testRcvMsg();*/
    }

    @StreamListener(TLVProcessorChannels.INPUT_AUGTLV)
    public void testRcvMsg(Message<String> message) {
        String payload = message.getPayload();
        System.out.println("payload：" + payload);
    }


}
