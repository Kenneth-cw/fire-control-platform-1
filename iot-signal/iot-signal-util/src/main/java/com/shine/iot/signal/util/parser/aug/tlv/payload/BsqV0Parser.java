package com.shine.iot.signal.util.parser.aug.tlv.payload;

import com.shine.iot.signal.model.adapter.aug.NodeDataAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BsqV0Parser extends BsqPayloadParser {
    private Logger logger = LogManager.getLogger(BsqV0Parser.class);

    @Override
    public NodeDataAdapter parse(byte[] b_payload) {
        logger.info("变送器第0版本payload数据解析，TODO..........");
        return null;
    }
}
