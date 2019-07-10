package com.shine.iot.core.utils;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum CustomerBindTypeEnum {

    MANUFACTURE("00", "生产商"),
    PROPERTYMANA("01", "物业管理"),
    MAINTENANCE("02", "维保公司"),
    CUSTOMER("03", "个人用户"),
    WATCHER("04", "关注者"),
    PROJECT("05", "工程方");

    @Getter
    String code;
    @Getter
    String name;

    CustomerBindTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }


    private static Map<String, CustomerBindTypeEnum> valueMap = new HashMap<>();

    static {
        for (CustomerBindTypeEnum custBindType : CustomerBindTypeEnum.values()) {
            valueMap.put(custBindType.code, custBindType);
        }
    }

    public static CustomerBindTypeEnum getByValue(String code) {
        CustomerBindTypeEnum custBindType = valueMap.get(code);
        if (custBindType == null) {
            throw new IllegalArgumentException("No element matches " + code);
        }
        return custBindType;
    }

}
