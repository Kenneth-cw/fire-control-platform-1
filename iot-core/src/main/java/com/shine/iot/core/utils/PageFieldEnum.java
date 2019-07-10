package com.shine.iot.core.utils;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum PageFieldEnum {

    SIZE_FIVE(5),
    SIZE_TEN(10),
    SIZE_TWENTY(20);

    @Getter
    private int value;

    private static Map<Integer, PageFieldEnum> valueMap = new HashMap<>();

    static {
        for (PageFieldEnum pageFieldEnum : PageFieldEnum.values()) {
            valueMap.put(pageFieldEnum.value, pageFieldEnum);
        }
    }

    PageFieldEnum(int value) {
        this.value = value;
    }

    public static PageFieldEnum getByValue(int value) {
        PageFieldEnum pageFieldEnum = valueMap.get(value);
        if (pageFieldEnum == null) {
            throw new IllegalArgumentException("No element matches " + value);
        }
        return pageFieldEnum;
    }

}
