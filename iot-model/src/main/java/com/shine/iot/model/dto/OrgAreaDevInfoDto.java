package com.shine.iot.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrgAreaDevInfoDto implements Serializable {
    private static final long serialVersionUID = 4423858528111941442L;

    private Long orgId;    /* 单位标识 */
    private String orgName;    /* 单位名称 */
    private String industryClass;    /* 所属行业 */
    private String orgType;    /* 单位类型 */

    private Long areaID;    /* 区域ID */
    private Long parentAreaID;    /* 所属上级区域，一级区域的上级区域ID为0 */
    private String areaNo;    /* 区域编号 */
    private String areaName;    /* 区域名称 */
    private String areaIDPath;    /* 区域ID路径 从一级到当前的ID的路径，以分割 */
    private String areaLocProv;    /* 位置-省 */
    private String areaLocCity;    /* 位置-市 */
    private String areaLocDist;    /* 位置-区 */
    private String areaLocDetail;    /* 位置-详细地址 */

    // private DeviceBaseInfoModel deviceModel; /* 设备entity信息 */

    private Long deviceId;
    private String deviceTypeCode;
    private Integer protocolType;
    private Integer deviceModelId;
    private String deviceNumber;
    private String devEUI;
    private String deviceSN;
    private String deviceQRCode;
    private String deviceImeiCode;
    private String deviceSimNo;
    private String deviceIccidCode;
    private String mcuID;
    private String mcuIDHex;
    private Integer workState; /* 运行状态 */
    private String isValid; /* 使用状态 */
    private Integer deviceProvider; /* 生产厂商 */
    private Date deviceProducedDate; /* 生产日期 */
    private String deviceAddr; /* 安装地址 */
    private String deviceGpsLati; /* 安装地址安，GPS-经度 */
    private String deviceGpsLong; /* 安装地址安，GPS-纬度 */
    private String deviceGpsAlti; /* 安装地址安，GPS-海拔 */

}
