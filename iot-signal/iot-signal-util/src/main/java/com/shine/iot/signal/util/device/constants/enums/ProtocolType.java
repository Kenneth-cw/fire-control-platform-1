package com.shine.iot.signal.util.device.constants.enums;

public enum ProtocolType {

    UNKNOWN(0, "未知"),
    INTERNET(1, "internet"),
    NETWORKFLOW(2, "网络流量 2/3/4G"),
    LORALITE(3, "LoRaLite"),
    LORAWAN(4, "LoRaWan"),
    NB_IOT(5, "NB-IoT"),
    ZIGBEE(6, "zigbee"),
    BLUETOOTH(7, "蓝牙"),
    WIRED(8, "有线");

    private int code;
    private String name;

    ProtocolType(int code, String name) {
        //为属性赋值
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    /**
     * 根据key值获取枚举类
     *
     * @param code 枚举类的key值
     * @return 枚举类
     */
    public ProtocolType getEnumTypeByKey(int code) {
        for (ProtocolType protocolType : ProtocolType.values()) {
            if (protocolType.getCode() == code) {
                return protocolType;
            }
        }
        return null;
    }

    /**
     * 根据key值获取枚举类的描述
     *
     * @param code 枚举类的key值
     * @return 枚举类 code 的描述
     */
    public String getValueByKey(int code) {
        for (ProtocolType protocolType : ProtocolType.values()) {
            if (protocolType.getCode() == code) {
                return protocolType.getName();
            }
        }
        return ProtocolType.UNKNOWN.getName();
    }

}
