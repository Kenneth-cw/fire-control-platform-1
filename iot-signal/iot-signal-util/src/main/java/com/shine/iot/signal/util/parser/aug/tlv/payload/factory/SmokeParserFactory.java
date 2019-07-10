package com.shine.iot.signal.util.parser.aug.tlv.payload.factory;

import com.shine.iot.signal.util.parser.aug.tlv.payload.*;

/**
 * 烟感解析器的工厂类，获取具体解析payload不同版本的烟感解析器
 */
public class SmokeParserFactory implements IDeviceParserFactory {

    @Override
    public SmokeParser getV0ParserInstance() {
        return new SmokeV0Parser();
    }

    @Override
    public IDevicePayloadParser getV1ParserInstance() {
        return new SmokeV1Parser();
    }

    @Override
    public IDevicePayloadParser getV2ParserInstance() {
        return new SmokeV2Parser();
    }

}
