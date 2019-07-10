package com.shine.iot.signal.util.parser.aug.tlv.payload;

import com.shine.iot.signal.model.adapter.aug.NodeDataAdapter;

/**
 * 抽象类：烟感产品解析器
 */
public abstract class SmokeParser implements IDevicePayloadParser {

    @Override
    public abstract NodeDataAdapter parse(byte[] b_payload);
}
