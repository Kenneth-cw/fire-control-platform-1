package com.shine.iot.signal.util.parser.aug.tlv.payload;

import com.shine.iot.signal.model.adapter.aug.NodeDataAdapter;

public abstract class BsqPayloadParser implements IDevicePayloadParser {

    @Override
    public abstract NodeDataAdapter parse(byte[] b_payload);
}
