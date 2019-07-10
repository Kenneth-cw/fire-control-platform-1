package com.shine.iot.core.utils;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum CustomerTypeEnum {

    PRIVACY("1", "个人用户"),
    COMPANY("2", "公司用户");

    @Getter
    String code;
    String name;

    private static Map<Integer, CustomerTypeEnum> valueMap = new HashMap<>();

    CustomerTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

}
