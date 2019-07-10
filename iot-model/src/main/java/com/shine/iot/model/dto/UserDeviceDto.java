package com.shine.iot.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserDeviceDto implements Serializable {

    private static final long serialVersionUID = -2035204954307887756L;
    // userDetail详细信息
    private String mapType;    /* 人员设备对应关系类型 */
    private Long userId;    /* 人员标识 */
    private Long oplogId;    /* 登录账号标识 */
    private String userName;    /* 人员姓名 */
    private String userGender;    /* 人员性别 */
    private String userType;    /* 人员类型 */
    private String userLevel;    /* 用户等级 */
    private String userPhone;    /* 联系电话 */
    private String userTel;    /* 固定电话 */
    private String userAddrCountry;    /* 国籍 */
    private String userAddrProv;    /* 地址-省 */
    private String userAddrCity;    /* 地址-市 */
    private String userAddrProvCode;    /* 地址-省 */
    private String userAddrCityCode;    /* 地址-市 */
    private String userAddrDist;    /* 地址-县/区 */
    private String userAddrDistCode;    /* 地址-县/区 */
    private String userAddrDetail;    /* 地址-详情 */
    private String wechatID;    /* 微信标识 */
    private String nickName;    /* 呢称 */
    private String regWay;    /* 登记途径 */
    private Date regDate;    /* 登记日期 */
    private String isAuthenticated;    /* 是否认证 */
    private String userNote;

    // userDetail 和 deviceBase 表 的关联关系信息
    private Long mapid;    /* 关联标识 */
    private String custType;    /* 用户类型 */
    private Long customerId;    /* 客户标识 */
    private int deviceId;       /* 设备ID标识 */
    private String bindType;    /* 关系类型：所属单位、管理单位、维护单位 */
    private Date bindDate;    /* 绑定日期 */
    private String manaRights;    /* 管理权限 */
    private String isBoundAuthed;    /* 绑定关系是否已认证 */
    private Date authDate;    /* 绑定关系认证日期 */
    private String authOperName;    /* 认证操作人员姓名 */
    private String mapNote;    /* 关系备注 */

    // deviceBase信息
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
    private Integer workState; /* 运行状态 */
    private String isValid; /* 使用状态 */
    private String deviceAddr; /* 安装地址 */
    private String deviceGpsLati; /* 安装地址安，GPS-经度 */
    private String deviceGpsLong; /* 安装地址安，GPS-纬度 */
    private String deviceGpsAlti; /* 安装地址安，GPS-海拔 */

    private String[] workStateList;//设备工作状态

    private String dcTypeName; /* 类型名称*/
    private String modelName; /* 型号名称*/
}
