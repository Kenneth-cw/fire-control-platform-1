package com.shine.iot.signal.model.adapter.claa;

import com.alibaba.fastjson.JSONObject;
import com.shine.iot.signal.model.adapter.aug.NodeDataAdapter;
import lombok.Data;

import java.io.Serializable;

@Data
public class ClaaJsonDataModel implements Serializable {
    private String cmd;
    private String commandResult;
    private String resultJsonStr;
    private int resultLen;
    private JSONObject resultJson;
    private NodeDataAdapter payloadData;
    private byte[] payloadBytes;

    private int cmdseq;

    private String ver;
    private int len;
    private String endFlag;
    private String data;

}
