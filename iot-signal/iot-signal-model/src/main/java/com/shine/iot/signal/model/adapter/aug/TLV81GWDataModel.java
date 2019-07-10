package com.shine.iot.signal.model.adapter.aug;

import java.io.Serializable;

/**
 * @author EvanCheung
 * 八月十六进制网关数据格式
 * <p>
 * xx xx 属性名
 * xx xx 属性值长度
 * xx xx xx .. 属性值
 */
public class TLV81GWDataModel implements Serializable {

    /**
     * 属性名的字节长度
     */
    private int propertyIdByteNum;

    /**
     * 在byte流中属性的数据真实ID
     */
    private int propertyId;


    /**
     * 属性真实名称
     */
    private String propertyName;

    /**
     * 属性的值（真实值表示），如LoRa
     */
    private Object propertyValue;

    /**
     * 属性值的十六进制字符串 不含任何单位，原始解析的byte字节转换
     */
    private String propertyValueHex;


    /**
     *
     */
}
