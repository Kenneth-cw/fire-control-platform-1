package com.shine.iot.signal.util.parser.aug.tlv.payload;

import com.shine.iot.signal.model.adapter.aug.NodeDataAdapter;
import com.shine.iot.signal.util.device.bytemsg.process.aug.NodeDataByteReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 烟感payload数据解析器：版本1 - MCUID为16字节；
 */
public class SmokeV1Parser extends SmokeParser {
    private Logger logger = LogManager.getLogger(SmokeV1Parser.class);

    @Override
    public NodeDataAdapter parse(byte[] b_payload) {
        logger.info("烟感第1版本payload数据解析，即mcuID为16位的payload。");
        NodeDataByteReader nodeDataByteReader = new NodeDataByteReader(b_payload);
        nodeDataByteReader.parse();
        return nodeDataByteReader.getResult();
    }

}
