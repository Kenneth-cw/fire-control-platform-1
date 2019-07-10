package com.shine.iot.signal.util.device.constants.enums;

public enum ModelType {

    UNKNOWN(0, "未知");

    private int code;
    private String name;

    ModelType(int code, String name) {
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
    public ModelType getEnumTypeByKey(int code) {
        for (ModelType modelType : ModelType.values()) {
            if (modelType.getCode() == code) {
                return modelType;
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
        for (ModelType modelType : ModelType.values()) {
            if (modelType.getCode() == code) {
                return modelType.getName();
            }
        }
        return ModelType.UNKNOWN.getName();
    }

}
