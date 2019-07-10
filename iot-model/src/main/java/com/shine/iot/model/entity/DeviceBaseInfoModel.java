package com.shine.iot.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 消防设备基本信息表
 */
@TableName("devicebase") //表名
@Data //生成getter,setter
@AllArgsConstructor //创建一个构造函数，含有所有已声明字段属性参数
@NoArgsConstructor //创建一个无参构造函数
@JsonInclude(JsonInclude.Include.NON_NULL) //值为null的不传递
public class DeviceBaseInfoModel implements Serializable {

    private static final long serialVersionUID = -695761689469869524L;

    @TableId(value = "deviceId", type = IdType.AUTO)
    private Long deviceId;
    private String deviceTypeCode;
    private Integer protocolType;
    private Integer deviceModelId;
    private String deviceNumber;
    //@TableField(value = "devEUI", strategy = FieldStrategy.NOT_NULL)
    private String devEUI;
    private String deviceSN;
    private String deviceQRCode;
    private String deviceImeiCode;
    private String deviceSimNo;
    private String deviceIccidCode;
    //@TableField(strategy = FieldStrategy.NOT_NULL)
    private String mcuID;
    private String mcuIDHex;
    private Integer workState; /* 运行状态 */
    private String isValid; /* 使用状态 */
    private Integer deviceProvider; /* 生产厂商 */
    private Date deviceProducedDate; /* 生产日期 */
    //SqlCondition.LIKE：查找安装地址时，默认查找策略为模糊匹配。select 表 where deviceAddr LIKE CONCAT('%',值,'%')
    @TableField(condition = SqlCondition.LIKE)
    private String deviceAddr; /* 安装地址 */
    private String deviceGpsLati; /* 安装地址安，GPS-经度 */
    private String deviceGpsLong; /* 安装地址安，GPS-纬度 */
    private String deviceGpsAlti; /* 安装地址安，GPS-海拔 */
    private String deviceFixUser; /* 安装人员 */
    private Date deviceFixDate; /* 安装时间 */
    private String areaName; /* 所属区域 */
    @TableField(condition = SqlCondition.LIKE)
    private String deviceNote; /* 备注 */

    private Integer placeid; /* 场所ID */

//    @Data注解在类上，会为类的所有属性自动生成:
//           setter/getter、equals、canEqual、hashCode、toString方法,如为final属性，则不会为该属性生成setter方法。
}