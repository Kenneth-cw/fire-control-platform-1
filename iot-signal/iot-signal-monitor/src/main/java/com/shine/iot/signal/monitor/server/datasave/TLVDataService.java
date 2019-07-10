package com.shine.iot.signal.monitor.server.datasave;

import com.shine.iot.signal.model.adapter.aug.GateWayTLVJSonDataAdapter;
import com.shine.iot.signal.model.dto.aug.NodeDataDto;
import com.shine.iot.signal.monitor.feignClient.RawSignalOPFeignClient;
import com.shine.iot.signal.util.device.bytemsg.process.aug.AugTLVGWByteReader;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

@Component
public class TLVDataService {
    private static Logger logger = LogManager.getLogger(TLVDataService.class);

    private RawSignalOPFeignClient _rawSignalOPFeignClient;

    @Autowired
    private void setRawSignalOPFeignClient(RawSignalOPFeignClient rawSignalOPFeignClient) {
        this._rawSignalOPFeignClient = rawSignalOPFeignClient;
    }

    //静态变量
    private static RawSignalOPFeignClient rawSignalOPFeignClient;

    @PostConstruct
    public void init() {
        rawSignalOPFeignClient = this._rawSignalOPFeignClient;
    }


    public static void saveTLVRawSignal(String gwIP, int gwPort, String tlvRawData) {
       /* byte[] b_tlv = Hex.decodeHex(tlvRawData);
        byte[] b_tlv_crc = new byte[2];
        System.arraycopy(b_tlv,0, b_tlv_crc, 0, b_tlv_crc.length);
        int tlvCRC = DigitUtil.convert2int(b_tlv_crc);
        logger.info("TLV数据的CRC：" + tlvCRC);*/

        try {
            AugTLVGWByteReader augReader = new AugTLVGWByteReader(Hex.decodeHex(tlvRawData));
            augReader.parse();
            GateWayTLVJSonDataAdapter tlvData = augReader.getResult();
            logger.info("TLV数据的CRC：" + tlvData.getCrc());
            logger.info("TLV数据的CRC Status：" + tlvData.getCrcStatus());

            // rawSignalInfo : rawId  rcvTime  rawSignal  gwIP  gwPort  macAddress  note
            NodeDataDto nodeDataDto = new NodeDataDto();
            nodeDataDto.setRawPayload(tlvRawData);
            nodeDataDto.setRcvTime(new Date());
            nodeDataDto.setGwId(tlvData.getGwid());
            nodeDataDto.setGwIP(gwIP);
            nodeDataDto.setGwPort(gwPort);
            Boolean saveResult = rawSignalOPFeignClient.saveRawSignal(nodeDataDto);
            logger.info("保存原始信号到数据库表中，执行结果：" + saveResult);
        } catch (DecoderException e) {
            logger.error("保存TLV原始数据失败，原因：" + e, e.getMessage());
            e.printStackTrace();
        }
    }

}
