package com.shine.iot.signal.util.device.bytemsg.process.aug;

import com.shine.iot.signal.model.adapter.aug.GateWayTLVJSonDataAdapter;
import com.shine.iot.signal.util.datastream.parser.DigitUtil;
import com.shine.iot.signal.util.device.bytemsg.process.ByteFunction;
import org.apache.commons.codec.binary.Hex;

/**
 * 八月科技网关字节处理方法接口。
 *
 * @author EvanCheung
 */
public interface AugTLVGWByteFunction {

    ByteFunction<GateWayTLVJSonDataAdapter> versionReader = (bytearr, data) -> {
        String version = "reserved";
        if (DigitUtil.convert2int(bytearr) == 0) {
            version = "v_0";//已作废
        } else if (DigitUtil.convert2int(bytearr) == 1) {
            version = "v1";
        }
        data.setVersion(version);
        return version;
    };

    ByteFunction<GateWayTLVJSonDataAdapter> gwidReader = (bytearr, data) -> {
        String gwid = DigitUtil.Byte2HexStr(bytearr);
        data.setGwid(gwid);
        return gwid;
    };

    ByteFunction<GateWayTLVJSonDataAdapter> datalenReader = (bytearr, data) -> {
        int datalen;
        datalen = DigitUtil.convert2int(bytearr);
        data.setDataLen(datalen);
        return datalen;
    };

    ByteFunction<GateWayTLVJSonDataAdapter> rfchainReader = (bytearr, data) -> {
        int rfchain;
        rfchain = DigitUtil.convert2int(bytearr);
        data.setRfChain(rfchain);
        return rfchain;
    };

//    ByteFunction<GateWayTLVJSonDataAdapter> frequencyLengthReader = (bytearr,data) ->{
//        int frequencylen = 0;
//        frequencylen = DigitUtil.convert2int(bytearr);
//        data.setFrequencyLen(frequencylen);
//        return frequencylen;
//    };

    ByteFunction<GateWayTLVJSonDataAdapter> frequencyReader = (bytearr, data) -> {
        long frequency;
        frequency = DigitUtil.convert2long(bytearr);
        data.setFrequency(frequency);
        return frequency;
    };

    ByteFunction<GateWayTLVJSonDataAdapter> modulationReader = (bytearr, data) -> {
        String modulation = "UNDEFINED";
        if (0x10 == bytearr[0]) {
            modulation = "LORA";
        } else if (0x20 == bytearr[0]) {
            modulation = "FSK";
        }
        data.setModulation(modulation);
        return modulation;

    };

    ByteFunction<GateWayTLVJSonDataAdapter> crcStatusReader = (bytearr, data) -> {
        String crcStatus = "CRC ok"; //CRC 状态
        if (0x00 == bytearr[0]) {
            crcStatus = "CRC undefined";
        } else if (0x01 == bytearr[0]) {
            crcStatus = "no CRC";
        } else if (0x10 == bytearr[0]) {
            crcStatus = "CRC OK";
        } else if (0x11 == bytearr[0]) {
            crcStatus = "CRC BAD";
        }
        data.setCrcStatus(crcStatus);
        return crcStatus;
    };

    ByteFunction<GateWayTLVJSonDataAdapter> sfReader = (bytearr, data) -> {
        String sf = "UNDEFINED"; //CRC 状态
        switch (bytearr[0]) {
            case 0x02:
                sf = "SF7";
                break;
            case 0x04:
                sf = "SF8";
                break;
            case 0x08:
                sf = "SF9";
                break;
            case 0x10:
                sf = "SF10";
                break;
            case 0x20:
                sf = "SF11";
                break;
            case 0x40:
                sf = "SF12";
                break;
        }
        data.setSf(sf);
        return sf;

    };

    ByteFunction<GateWayTLVJSonDataAdapter> codeRateReader = (bytearr, data) -> {
        String coderate = "UNDEFINED"; //CRC 状态
        switch (bytearr[0]) {
            case 0x01:
                coderate = "4/5";
                break;
            case 0x02:
                coderate = "4/6";
                break;
            case 0x03:
                coderate = "4/7";
                break;
            case 0x04:
                coderate = "4/8";
                break;
        }
        data.setCodeRate(coderate);
        return coderate;
    };

    ByteFunction<GateWayTLVJSonDataAdapter> rssiReader = (bytearr, data) -> {
        int rssi; //接收灵敏度(dBm)
        rssi = DigitUtil.convert2int(bytearr);
        data.setRssi(rssi + "dBm");
        return rssi;
    };

    ByteFunction<GateWayTLVJSonDataAdapter> snrReader = (bytearr, data) -> {
        int snr; //信噪比(0.1 dB)
        snr = DigitUtil.convert2int(bytearr);
        data.setSnr(snr * 0.1 + "dB");
        return snr;
    };

    ByteFunction<GateWayTLVJSonDataAdapter> rfDataLenReader = (bytearr, data) -> {
        int rfDataLen;  //LORA 数据包
        rfDataLen = DigitUtil.convert2int(bytearr);

        return rfDataLen;
    };

    ByteFunction<GateWayTLVJSonDataAdapter> rfdataValueReader = (bytearr, data) -> {
        data.setRfData(Hex.encodeHexString(bytearr));//LORA数据包
        return data.getRfData();
    };


    ByteFunction<GateWayTLVJSonDataAdapter> crcReader = (bytearr, data) -> {
        data.setCrc(DigitUtil.convert2int(bytearr)); //CRC 校验码
        return data.getCrc();
    };

    ByteFunction<GateWayTLVJSonDataAdapter> tlvIdReader = (bytearr, data) -> {
        int tlvId = DigitUtil.convert2int(bytearr);
        data.setTlvId(tlvId);
        return data.getTlvId();
    };
}
