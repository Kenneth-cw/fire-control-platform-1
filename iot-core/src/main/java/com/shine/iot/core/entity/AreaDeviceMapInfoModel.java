package com.shine.iot.core.entity;import com.baomidou.mybatisplus.annotation.IdType;import com.baomidou.mybatisplus.annotation.TableId;import com.baomidou.mybatisplus.annotation.TableName;import com.fasterxml.jackson.annotation.JsonInclude;import lombok.AllArgsConstructor;import lombok.Data;import lombok.NoArgsConstructor;import java.io.Serializable;@Data@NoArgsConstructor@AllArgsConstructor@TableName(value = "areadevicemapinfo")@JsonInclude(JsonInclude.Include.NON_NULL)public class AreaDeviceMapInfoModel implements Serializable {    private static final long serialVersionUID = -5166735207729022090L;    @TableId(value = "areadevicemapid", type = IdType.AUTO)    private Long areadevicemapid;    /* ID */    private Long areaid;    /* 区域ID */    private Long deviceId;    /* 设备ID */    private String bindType;    /* 绑定关系 */    private String deviceNo;    /* 设备编号 */    private String note;    /* 备注 */}