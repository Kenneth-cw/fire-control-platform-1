package com.shine.iot.signal.db.rest.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@TableName(value = "rawtlvsignalinfo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RawTLVSignalInfoModel extends Model<RawTLVSignalInfoModel> {


    private static final long serialVersionUID = -2593634239091946734L;
    @TableId(type = IdType.AUTO)
    private Long rawId;
    private Date rcvTime;
    private String rawSignal; /* 原始信号 */
    private String gwIP; /* 接收原始信号的网关IP地址 */
    private Integer gwPort; /* 接收原始信号的网关端口 */
    private String macAddress; /* 网关MAC地址 */
    private String note;

}
