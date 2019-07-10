package com.shine.iot.signal.util.device.constants.enums;

public enum CustomerType {

    PRIVACY("1", "个人用户"), COMPANY("2", "公司用户");

    String code;
    String name;

    CustomerType(String custType, String custName) {
        this.code = custType;
        this.name = custName;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
