package com.shine.iot.signal.util.parser.aug.tlv.payload;

import com.shine.iot.signal.model.adapter.aug.NodeDataAdapter;
import com.shine.iot.signal.util.device.bytemsg.process.aug.NodeDataByteReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 烟感payload数据解析器：版本0 - MCUID为8字节；（已废弃）
 */
public class SmokeV0Parser extends SmokeParser {

    private Logger logger = LogManager.getLogger(SmokeV0Parser.class);

    @Override
    public NodeDataAdapter parse(byte[] b_payload) {
        logger.info("烟感第0版本payload数据解析，即mcuID为8位的payload。");
        NodeDataByteReader nodeDataByteReader = new NodeDataByteReader(b_payload);
        nodeDataByteReader.parse();
        return nodeDataByteReader.getResult();
    }

}
