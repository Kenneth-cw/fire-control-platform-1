package com.shine.iot.signal.util.device.constants.enums;

public enum FieldType {

    UNKNOWN(0, "未知"),
    FIRECONTROL(1, "消防"),
    PERSONAL(2, "个人安全"),
    MEDICAL(3, "医疗");

    private int code;
    private String name;

    FieldType(int code, String name) {
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
    public FieldType getEnumTypeByKey(int code) {
        for (FieldType fieldType : FieldType.values()) {
            if (fieldType.getCode() == code) {
                return fieldType;
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
    public String getValueDesByKey(int code) {
        for (FieldType fieldType : FieldType.values()) {
            if (fieldType.getCode() == code) {
                return fieldType.getName();
            }
        }
        return FieldType.UNKNOWN.getName();
    }

}
