package com.shine.iot.signal.util.parser.aug.tlv.payload.factory;

import com.shine.iot.signal.util.parser.aug.tlv.payload.IDevicePayloadParser;

/**
 * 抽象工厂  获取设备不同版本解析器
 */
public interface IDeviceParserFactory {

    IDevicePayloadParser getV0ParserInstance();

    IDevicePayloadParser getV1ParserInstance();

    IDevicePayloadParser getV2ParserInstance();

}
