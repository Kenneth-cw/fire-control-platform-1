package com.shine.iot.signal.util.parser.aug.tlv.payload;

import com.shine.iot.signal.model.adapter.aug.NodeDataAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 烟感payload数据解析器：版本2 - 无MCUID；
 */
public class SmokeV2Parser extends SmokeParser {

    private Logger logger = LogManager.getLogger(SmokeV2Parser.class);

    @Override
    public NodeDataAdapter parse(byte[] b_payload) {
        System.out.println("烟感第2版本payload数据解析，即无mcuID的payload。TODO.............");
        return new NodeDataAdapter();
    }
}
