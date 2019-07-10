/**
 *
 */
package com.shine.iot.signal.model.adapter.aug;

import lombok.Data;

import java.io.Serializable;

/**
 * @author EvanCheung
 *
 * 网关数据适配器
 */
@Data
public class GateWayTLVJSonDataAdapter implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -2396930995145626909L;
    /**
     * start of TLV data bytes
     */
    private String dataFlag;
    /**
     *
     */

    private String version; // 0: v1 ； others: reserved
    private String gwid;
    private int dataLen; //Total Length
    //TLV DATA
    private int tlvId; //tlv的id标识
    private int rfChain; //SX1301中的RF chain编号
    private long frequency;  //接收或发送频点(Hz)
    private String modulation; //调制方式
    private String crcStatus;  //CRC status: 校验码状态
    private String sf; // 扩频因子:
    private String codeRate; //纠错码率
    private String rssi; //接收灵敏度(dBm)
    private String snr; //信噪比(0.1 dB)
    private int rfDataLen; //LoRa数据包数据长度
    private String rfData; //LoRa数据包

    //CRC
    private int crc; // 校验码

    private NodeDataAdapter nodeData;  //节点信息 - LoRa数据报解析

    private byte[] downData;//下行数据
    private byte[] dataSeq;//接收到的数据顺序

}
