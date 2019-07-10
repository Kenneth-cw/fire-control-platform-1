package com.shine.iot.signal.util.device.constants.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SignalProductType {

    UNKNOWN(-1, "未知"),
    SMOKE_DETECTOR_NB(0x00, "NB烟感"),
    SMOKE_DETECTOR_LORA(0x01, "LORA烟感"),
    RING(0x02, "警铃"),
    MANUAL_ALARM(0x03, "手动报警器"),
    TEMPERATURE_DETECTOR(0x04, "温感"),
    NOISE_LIGHT_ALARM(0x05, "声光报警器"),
    BSQ(0x06, "变送器"), //（支持液压、液位检测）
    ULTRASONIC_DISTANCE(0x07, "超声波距离检测器"),
    VOICE_SPEAKER(0x09, "语音提示器"),
    BURN_GAS_DETECTOR(0x0B, "可燃气体探测器"),
    SMOKE_SUCTION_DETECTOR(0x0D, "控烟探测器");


    private Integer code;
    private String name;

    // 通过 code 取枚举
    public static SignalProductType getEumnTypeByKey(int code) {
        for (SignalProductType type : SignalProductType.values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        return null;
    }

    // 通过 code 取 value描述
    public static String getValueDesByKey(int code) {
        try {
            for (SignalProductType type : SignalProductType.values()) {
                if (type.getCode() == code) {
                    return type.getName();
                }
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

}
