package com.shine.iot.signal.util.device.bytemsg.process.aug;

import com.shine.iot.signal.model.adapter.aug.GateWayTLVJSonDataAdapter;
import com.shine.iot.signal.util.device.bytemsg.process.DefaultByteReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 解析TLV数据，其中rfData即LoraLite的payload数据
 */
public class AugTLVGWByteReader extends DefaultByteReader<GateWayTLVJSonDataAdapter> {
    private Logger logger = LogManager.getLogger(AugTLVGWByteReader.class);

    public AugTLVGWByteReader(byte[] _source) {
        super(_source);
        data = new GateWayTLVJSonDataAdapter();
    }

    public void parse() {
        try {
            this.read(5, 1, AugTLVGWByteFunction.versionReader)
                    .next(2, AugTLVGWByteFunction.datalenReader);
            if ("v_0".equals(data.getVersion())) {// 已作废的GW
                this.skip(4)// rfchain's code bytes (2)
                        .next(1, AugTLVGWByteFunction.rfchainReader).skip(4)
                        .next(4, AugTLVGWByteFunction.frequencyReader).skip(4)// modulation's code bytes :2
                        .next(1, AugTLVGWByteFunction.modulationReader).skip(4)// crc status code bytes : 2
                        .next(1, AugTLVGWByteFunction.crcStatusReader).skip(4)// SF bytes TODO
                        .next(1, AugTLVGWByteFunction.sfReader).skip(4)// TODO code rate bytes?
                        .next(1, AugTLVGWByteFunction.codeRateReader).skip(4)// TODO ,rssi bytes ;2 bytes
                        .next(2, AugTLVGWByteFunction.rssiReader).skip(4)// TODO snr's 2 bytes
                        .next(2, AugTLVGWByteFunction.snrReader).skip(2)// TODO: rfdata's 2 bytes
                        .next(2, AugTLVGWByteFunction.rfDataLenReader)
                        .next(data.getRfDataLen(), AugTLVGWByteFunction.rfdataValueReader)
                        .next(2, AugTLVGWByteFunction.crcReader);
            } else {//新版V1的数据格式
                this.next(8, AugTLVGWByteFunction.gwidReader)
                        // .next(2, AugTLVGWByteFunction.tlvIdReader)
                        .skip(1)
                        .next(1, AugTLVGWByteFunction.rfchainReader)
                        .skip(1)
                        .next(4, AugTLVGWByteFunction.frequencyReader)
                        .skip(1)
                        .next(1, AugTLVGWByteFunction.modulationReader)
                        .skip(1)
                        .next(1, AugTLVGWByteFunction.crcStatusReader)
                        .skip(1)
                        .next(1, AugTLVGWByteFunction.sfReader)
                        .skip(1)
                        .next(1, AugTLVGWByteFunction.codeRateReader)
                        .skip(1)
                        .next(2, AugTLVGWByteFunction.rssiReader)
                        .skip(1)
                        .next(2, AugTLVGWByteFunction.snrReader)
                        .skip(1)
                        .read(this.getCurrentPosition(), this.dataSource.length - this.getCurrentPosition() - 2, AugTLVGWByteFunction.rfdataValueReader)
                        .next(2, AugTLVGWByteFunction.crcReader);
            }
           /* 对 payload进行解析
            NodeDataByteReader nodeDataByteReader = new NodeDataByteReader(Hex.decodeHex(data.getRfData()));
            nodeDataByteReader.parse();
            data.setNodeData(nodeDataByteReader.getResult());*/

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }
}
