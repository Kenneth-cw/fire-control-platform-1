package com.shine.iot.signal.util.device.bytemsg.process.aug;

import com.shine.iot.signal.model.adapter.aug.NodeDataAdapter;
import com.shine.iot.signal.util.datastream.parser.DigitUtil;
import com.shine.iot.signal.util.device.bytemsg.process.ByteFunction;
import org.apache.commons.codec.binary.Hex;

/**
 * 八月科技网关字节处理方法接口。
 *
 * @author EvanCheung
 */
public interface NodeDataByteFunction {

    ByteFunction<NodeDataAdapter> crcReader = (bytearr, data) -> {
        int crcValue = DigitUtil.convert2int(bytearr);
        data.setSourceCrcValue(crcValue);
        return crcValue;
    };

    ByteFunction<NodeDataAdapter> mcuidHexReader = (bytearr, data) -> {
        String mcuidHex = Hex.encodeHexString(bytearr);
        data.setMcuIDHex(mcuidHex);
        return mcuidHex;
    };

    ByteFunction<NodeDataAdapter> appendixdataReader = (bytearr, data) -> {
        if (bytearr.length > 0) {
            String appendixdata = Hex.encodeHexString(bytearr);
            data.setAppendixData(appendixdata);
            return appendixdata;
        } else {
            return null;
        }
    };

    ByteFunction<NodeDataAdapter> industryReader = (bytearr, data) -> {
        if ((bytearr[0] & 0x80) != 0) {
            bytearr[0] = (byte) (bytearr[0] & 0x7F);
        }
        String industry = "" + DigitUtil.convert2int(bytearr);
        data.setIndustryType(industry);
        return industry;
    };

    ByteFunction<NodeDataAdapter> productModelReader = (bytearr, data) -> {
        String productModel = "UNDEFINED";
        productModel = "" + DigitUtil.convert2int(bytearr);
        data.setProductModel(productModel);
        return productModel;
    };

    ByteFunction<NodeDataAdapter> crcCorrectReader = (bytearr, data) -> {
        boolean crcCorrect; //CRC 状态
        int calculatedCrc = DigitUtil.do_crc16(bytearr);
        crcCorrect = calculatedCrc == data.getSourceCrcValue();
        data.setCrcCorrect(crcCorrect);
        return crcCorrect;
    };

    ByteFunction<NodeDataAdapter> deviceTypeReader = (bytearr, data) -> {
        String deviceType = "" + DigitUtil.convert2int(bytearr);
        data.setDeviceType(deviceType);
        return deviceType;
    };

    ByteFunction<NodeDataAdapter> reservedReader = (bytearr, data) -> {
        String reserved = "" + DigitUtil.convert2int(bytearr);
        data.setReserved(reserved);
        return reserved;
    };

    ByteFunction<NodeDataAdapter> fctrlReader = (bytearr, data) -> {
        String fctrl = "" + DigitUtil.convert2int(bytearr);
        data.setLorawanFCtrl(fctrl);
        return fctrl;
    };

    ByteFunction<NodeDataAdapter> msgTypeReader = (bytearr, data) -> {
        //协议及消息类型
        String msgType = "" + DigitUtil.convert2int(bytearr);
        data.setLorawanMsgType(msgType);
        //是否为应答消息：最高位为1，表示为应答消息
        if ((bytearr[0] & 0x80) != 0) data.setAck(true);
        //解析协议类型
        bytearr[0] &= 0x7F; //将高位置0
        int protocol = bytearr[0] >> 4; //右移4位，将高4位变为低4位
        data.setProtocol(protocol + "");
        //是否需要接收方给应答消息ACK 0-Need ACK  1-No need ACK
        boolean needAck = DigitUtil.bytePositionOn1(bytearr[0], 3);
        data.setNeedAck(!needAck);
        return msgType;
    };

    ByteFunction<NodeDataAdapter> rfDataLenReader = (bytearr, data) -> {
        int rfDataLen = bytearr[0] & 0xFF;//DigitUtil.convert2int(bytearr);
        data.setDataLength(rfDataLen);
        return rfDataLen;
    };

    ByteFunction<NodeDataAdapter> fcntReader = (bytearr, data) -> {
        int fcnt = DigitUtil.convert2int(bytearr);
        data.setLoraFrameCounter(fcnt);
        return fcnt;
    };

    ByteFunction<NodeDataAdapter> cmdReader = (bytearr, data) -> {
        int cmd = (bytearr[0] & 0xFF);
        data.setCmdType("" + cmd);
        data.setSignalType("" + cmd);
        if (data.getMcuidLen() == 8 && ((bytearr[0] & 0x80) != 0)) data.setAck(true);
        return cmd;
    };

    ByteFunction<NodeDataAdapter> mcuidLenReader = (bytearr, data) -> {
        data.setMcuidLen(8);
        if ((bytearr[0] & 0x80) != 0) {
            data.setMcuidLen(16);
        } else {
            byte[] productModel = {(byte) 0xff};//产品型号  1byte No.2
            byte[] reserved = {(byte) 0xff};// 字节保留，以供扩展  1byte No.3
            data.setProductModel("" + DigitUtil.convert2int(productModel));
            data.setReserved("" + DigitUtil.convert2int(reserved));
        }
        return data.getMcuidLen();
    };
	
	/*@Autowired
	IDeviceDownParamInfoService deviceDownParamService;*/
    /**
     * 构建ACK数据  bytearr：字节数组；data：NodeDataAdapter
     */
	/*ByteFunction<NodeDataAdapter> ackReader = (bytearr,data) ->{
		//查询该节点有无配置参数
	    int mcuLen = data.getMcuidLen();
	    // 
	    if(mcuLen == 8) {
	    	//
	    	
	    } else if(mcuLen == 16){
	    	
	    }
	    returrn null;
	    //
	};*/

}
