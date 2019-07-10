package com.shine.iot.signal.util.device.bytemsg.process.claa;


import com.alibaba.fastjson.JSONObject;
import com.shine.iot.signal.model.adapter.aug.NodeDataAdapter;
import com.shine.iot.signal.model.adapter.claa.ClaaJsonDataModel;
import com.shine.iot.signal.util.datastream.parser.DigitUtil;
import com.shine.iot.signal.util.device.bytemsg.process.ByteFunction;
import com.shine.iot.signal.util.device.bytemsg.process.aug.NodeDataByteReader;
import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;


public interface ClaaJsonByteFunction {
    String UPFLAG = "updata";
    Logger logger = LogManager.getLogger(ClaaJsonByteReader.class);
    /**
     * 判断是否中兴克拉的起始标志
     */
    ByteFunction<ClaaJsonDataModel> beginReader = (bytearr, data) -> {
        boolean beginFlag = (DigitUtil.convert2int(bytearr) == 0x0A);

        return beginFlag;
    };

    ByteFunction<ClaaJsonDataModel> versionReader = (bytearr, data) -> {
        String version = DigitUtil.convert2int(bytearr[0]) + "." + DigitUtil.convert2int(bytearr[1]);
        data.setVer(version);

        return version;
    };

    ByteFunction<ClaaJsonDataModel> lenReader = (bytearr, data) -> {
        int len = DigitUtil.convert2int(bytearr);
        data.setLen(len);
        return len;
    };

    /**
     * json数据读取方法
     */
    ByteFunction<ClaaJsonDataModel> dataReader = (bytearr, data) -> {
        String jsonData = new String(bytearr, StandardCharsets.UTF_8);
        data.setData(jsonData);
        try {
            JSONObject jsondata = JSONObject.parseObject(jsonData);

            data.setResultJson(jsondata);//设置json格式的数据

            if (UPFLAG.equalsIgnoreCase(jsondata.getString("cmd"))) {//设备上传数据，需要解析数据
                String payloadBase64 = data.getResultJson().getString("payload");
                data.setPayloadBytes(Base64.decodeBase64(payloadBase64.getBytes()));
                NodeDataByteReader payloadReader = new NodeDataByteReader(data.getPayloadBytes());
                payloadReader.parse();
                NodeDataAdapter nodeData = payloadReader.getResult();
                nodeData.setPayloadfrom("CLAA");
                data.setPayloadData(nodeData);
                // InfoNotifier.sendMessage(nodeData);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return jsonData;
    };


}
