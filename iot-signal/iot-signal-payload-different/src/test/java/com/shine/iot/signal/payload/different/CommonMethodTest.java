package com.shine.iot.signal.payload.different;

import com.alibaba.fastjson.JSONObject;
import com.shine.iot.signal.model.adapter.aug.NodeDataAdapter;
import com.shine.iot.signal.model.dto.aug.NodeDataDto;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

public class CommonMethodTest {

    @Test
    public void testHexPayload() {
        String payload = "810100ffffffffff07000003f7f985ffffffff04050000050300f593";
        byte[] b_payload = null;
        try {
            b_payload = Hex.decodeHex(payload);
            // 0x0，版本0，MCUID为8字节；（已废弃）
            // 0x2，版本2，无MCUID；
            // 0x8，版本1，MCUID为16字节。
            int b_version = (b_payload[0] >> 4) & 0X0F; //payload版本
            System.out.println("b_version: " + b_version);

            // NB烟感 = 0x00，■LORA烟感  = 0x01,■警铃 = 0x02,■手动报警器 = 0x03,■温感 = 0x04,■声光报警器 = 0x05,
            //■变送器 = 0x06, （支持液压、液位检测）■超声波距离检测器 = 0x07 ■语音提示器 = 0x09,■可燃气体探测器 = 0x0B,
            // ■控烟探测器 = 0x0D,
            int b_devType = b_payload[1] & 0x0F;
            System.out.println("b_devType: " + b_devType);

        } catch (DecoderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAliJSONObject() {
        NodeDataAdapter adapter = new NodeDataAdapter();
        adapter.setMcuIDHex("mcuID");
        adapter.setLoraFrameCounter(520);
        adapter.setCmdType("3");

        NodeDataDto nodeDataDto = new NodeDataDto();
        nodeDataDto.setGwId("gwId");
        nodeDataDto.setGwIP("127.0.0.1");
        nodeDataDto.setGwPort(1780);
        nodeDataDto.setRawPayload("111222233334444555566666");
        nodeDataDto.setNodeDataAdapter(adapter);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("deviceType", 8);
        jsonObject.put("version", 1);
        jsonObject.put("nodeDataDto", nodeDataDto);
        String jsonStr = jsonObject.toJSONString();
        System.out.println("json string: " + jsonStr);

        JSONObject parseJsonObject = JSONObject.parseObject(jsonStr);
        Integer deviceType = parseJsonObject.getInteger("deviceType");
        Integer version = parseJsonObject.getInteger("version");
        NodeDataDto dataDto = parseJsonObject.getObject("nodeDataDto", NodeDataDto.class);
        if (dataDto != null) {
            System.out.println("---------" + nodeDataDto.getRawPayload());
        }

    }
}
