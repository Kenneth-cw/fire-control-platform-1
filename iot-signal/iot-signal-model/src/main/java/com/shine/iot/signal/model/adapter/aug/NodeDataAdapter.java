package com.shine.iot.signal.model.adapter.aug;

import lombok.Data;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@Data
public class NodeDataAdapter implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -7870975113854176391L;

    private String industryType;//行业类型
    private String deviceType;//设备类型
    private String productModel;//产品型号
    private String reserved;//字段保留，以供后续使用
    private String mcuIDHex;//芯片ID
    private String lorawanMsgType;//lorawan的消息类型
    private String lorawanFCtrl;//lorawan的软件版本
    private Integer loraFrameCounter;//帧计数器
    private String signalType;//信号类型-工作状态
    private Integer dataLength;//数据长度
    private String appendixData;//指令附属数据，如烟雾值
    private String crcData;//校验字节

    private int sourceCrcValue;//校验字节 - Lambda表达式解析payload数据得到的的CRC，与crcData作比较：
    private boolean crcCorrect; //server计算出的交验字CRC，用来和CRC作比对
    private boolean ack;//是否来自于设备的ack数据
    private boolean needAck;//是否需要接收方恢复应答消息ACK
    private int mcuidLen;//mcuid 长度

    private String cmdType; //命令类型
    private String protocol; //协议

    private byte[] payloadDataAckBytes; //ACK payload数据
    private byte[] dataSeqNo;//接收的数字顺序号

    /*private InetAddress gwAddr;
    private int gwPort;*/

    private String deveui;
    private String payloadfrom;

    //数据发生的时间
    private Date dataTime = Calendar.getInstance().getTime();


}
