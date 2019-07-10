package com.shine.iot.signal.model.dto.aug.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeneralJsonModel implements Serializable {

    private static final long serialVersionUID = -65222679254780081L;

    private Integer field; /* 行业类型*/
    private Integer product; /* 产品类型*/
    private Integer productModel; /* 产品型号*/
    private Integer protocol; /* 协议类型*/
    private String gwid; /* 网关/网卡Id*/
    private String ipFrom; /* 服务器接受到设备数据的IP地址*/
    private Integer portFrom; /* 服务器接受到设备数据的端口号*/
    private Date rcvTime; /* 服务器接受到设备数据的时间*/
    private Boolean direction; /* true：上行；false：下行*/
    private Integer upFrameCount; /* 上行帧号*/
    private Integer downFrameCount; /* 下行帧号*/
    private Boolean isAck; /* 是否为应答消息：【true：是，false：否】*/
    private Boolean isNeedAck; /* 是否需要应答：【true：需要，false：不需要】*/
    private String swversion; /* 设备软件版本号*/
    private String deviceSN; /* 设备唯一标识*/
    private Integer status; /* 当前设备状态*/
    private Integer value; /* 状态值*/
    private Integer command; /* 控制指令*/
    private String content; /* 十六进制字符串，控制指令对应的内容*/
    private Date timeToSend; /* 服务器向设备发送数据的时间*/
    private String imei; /* IMEI */
    private String imsi; /* SIM卡号 */
    private String iccid; /* ICCID号码 */
    private String initialData; /*服务器接受到设备的原始数据*/
    private Object jsonData; /* 不同设备的json数据*/

}
