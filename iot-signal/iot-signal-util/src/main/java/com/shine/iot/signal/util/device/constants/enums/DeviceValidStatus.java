package com.shine.iot.signal.util.device.constants.enums;

public enum DeviceValidStatus {
    INVALID("F", "已作废"),
    INSTALL("W", "已安装"),
    NOTINSTALLED("0", "未安装"),
    USEING("1", "使用中"),
    ACCEPTANCE("2", "已验收");

    private String code;
    private String name;

    DeviceValidStatus(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}
