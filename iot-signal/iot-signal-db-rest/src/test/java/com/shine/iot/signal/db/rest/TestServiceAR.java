package com.shine.iot.signal.db.rest;

import com.platform.model.ServiceRsObjModel;
import com.shine.iot.signal.db.rest.service.DeviceAllMsgService;
import com.shine.iot.signal.db.rest.service.impl.RawTLVSignalServiceImpl;
import com.shine.iot.signal.db.rest.web.PayloadSignalRest;
import com.shine.iot.signal.db.rest.web.RawTLVSignalRest;
import com.shine.iot.signal.model.adapter.aug.NodeDataAdapter;
import com.shine.iot.signal.model.dto.aug.NodeDataDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestServiceAR {

    @Autowired
    private DeviceAllMsgService allMsgService;

    @Test
    public void testGetAllMsgById() {
        System.out.println("根据allmsg表的ID获取表记录：" + allMsgService.getById(1));
    }

    @Autowired
    private RawTLVSignalServiceImpl rawSignalService;

    @Test
    public void testSaveRawSignalInfo() {
        String payload = "737461727402003dcacbb8010000031001002d00070408050106ffd107007a081f810100ffff0c1f120900000072838dffffffff04050000000503020cdeff0e493c";
        String gwIP = "127.0.0.1";
        Integer gwPort = 10000;
        NodeDataDto nodeDataDto = new NodeDataDto();
        nodeDataDto.setGwIP(gwIP);
        nodeDataDto.setGwPort(gwPort);
        nodeDataDto.setRcvTime(new Date());
        nodeDataDto.setRawPayload(payload);

        ServiceRsObjModel<Boolean> rs = rawSignalService.saveRawSignal(nodeDataDto);
        System.out.println("保存结果：" + rs.getRsData());
    }

    @Autowired
    private RawTLVSignalRest rawSignalRest;

    @Test
    public void testSaveRawSignalInfo_1() {
        String payload = "737461727402003dcacbb8010000031001002d00070408050106ffd107007a081f810100ffff0c1f120900000072838dffffffff04050000000503020cdeff0e493c";
        String gwIP = "127.0.0.1";
        Integer gwPort = 10000;
        NodeDataDto nodeDataDto = new NodeDataDto();
        nodeDataDto.setGwIP(gwIP);
        nodeDataDto.setGwPort(gwPort);
        nodeDataDto.setRcvTime(new Date());
        nodeDataDto.setRawPayload(payload);
        Boolean rs = rawSignalRest.saveRawSignal(nodeDataDto);
        System.out.println("保存结果：" + rs);
    }


    /********************  周一做测试   ***************************/
    @Autowired
    private PayloadSignalRest payloadSignalRest;

    @Test
    public void testSavePayloadSignalInfo() {
        String payload = "810100ffffffffff07000003f7f985ffffffff04050000050300f593";
        NodeDataDto nodeDataDto = new NodeDataDto();
//        nodeDataDto.setGwIP("127.0.0.1");
//        nodeDataDto.setGwPort(10000);
        NodeDataAdapter nodeDataAdapter = new NodeDataAdapter();
        nodeDataAdapter.setAck(true);
        nodeDataDto.setRawPayload(payload);
        nodeDataDto.setRcvTime(new Date());
        nodeDataAdapter.setCmdType("3");
        nodeDataAdapter.setLoraFrameCounter(5);
        nodeDataAdapter.setMcuIDHex("ffffffffff07000003f7f985ffffffff");
        nodeDataDto.setNodeDataAdapter(nodeDataAdapter);
        Boolean saveResult = payloadSignalRest.savePayloadSignal(nodeDataDto);
        System.out.println("-------" + saveResult);
    }

    @Test
    public void testSaveGeneralJsonData() {
        String jsonStr = "{\"isAck\":false,\"ipFrom\":\"127.0.0.1\",\"rcvTime\":1562057066111,\"protocol\":\"3\",\"needAck\":true,\"model\":\"0\",\"direction\":true,\"product\":\"2\",\"portFrom\":9823,\"swversion\":\"5\",\"deviceSN\":\"ffffffffff07000003f7f985ffffffff\",\"command\":\"3\",\"initialData\":\"810100ffffffffff07000003f7f985ffffffff04050000050300f593\",\"jsonData\":{\"ack\":false,\"cmdType\":\"3\",\"crcCorrect\":false,\"dataLength\":0,\"dataTime\":1562057066112,\"deviceType\":\"2\",\"industryType\":\"1\",\"loraFrameCounter\":5,\"lorawanFCtrl\":\"5\",\"lorawanMsgType\":\"4\",\"mcuIDHex\":\"ffffffffff07000003f7f985ffffffff\",\"mcuidLen\":16,\"needAck\":true,\"payloadDataAckBytes\":\"gQGD//////8HAAAD9/mF/////4QABVAO\",\"productModel\":\"0\",\"protocol\":\"3\",\"reserved\":\"0\",\"signalType\":\"3\",\"sourceCrcValue\":62867},\"field\":\"1\",\"upFrameCount\":5,\"status\":\"3\"}";
        Boolean saveRs = payloadSignalRest.dealGeneralJsonData(jsonStr);
        System.out.println("----" + saveRs);
    }


}
