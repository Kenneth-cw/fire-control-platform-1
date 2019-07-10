package com.shine.iot.signal.model.dto.aug;

import com.shine.iot.signal.model.adapter.aug.NodeDataAdapter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NodeDataDto implements Serializable {


    private static final long serialVersionUID = -7903015161613312493L;

    private NodeDataAdapter nodeDataAdapter; /* payload数据的适配器 */

    private String rawPayload; /* 原始payload数据 */
    private Date rcvTime; /* 接受payload数据的时间 */
    private String gwIP; /* 网关IP */
    private Integer gwPort; /*  网关Port */
    private String gwId; /* 网关ID，MAC地址 */
    //NB 设备属性
    private String nb_imei; /* IMEI */
    private String nb_imsi; /* IMSI */
    private String nb_iccid; /* ICCID */


}
