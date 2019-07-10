package com.shine.iot.signal.util.parser.aug.tlv.payload;

import com.shine.iot.signal.model.adapter.aug.NodeDataAdapter;

/**
 * 抽象payload数据解析器
 */
public interface IDevicePayloadParser {

    NodeDataAdapter parse(byte[] b_payload);

}
