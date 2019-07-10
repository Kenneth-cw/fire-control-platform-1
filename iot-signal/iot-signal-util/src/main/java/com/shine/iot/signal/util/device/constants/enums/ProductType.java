package com.shine.iot.signal.util.device.constants.enums;

public enum ProductType {
    UNKNOWN(0, "无效的设备类型"), //不可识别的设备类型信号
    SMOKE_DETECTOR(002, "点型感烟火灾探测器"),
    SMOKE_SUCTION_DETECTOR(014, "吸入式控烟探测器"),
    MANUAL_ALARM(2, "手动火灾报警器"),
    NOISE_LIGHT_ALARM(3, "火灾声光报警器"),
    RING_ALARM(4, "火灾警铃"),
    VOICE_SPEAKER(5, "语音提示器"),
    GAS_DETECTOR(6, "可燃气体探测器"),
    TEMPERATURE_DETECTOR(8, "温度探测器"),
    TRANSMITTER(9, "变送器"), /* 支持液压、液位检 */
    WATER_LEVEL(10, "液位探测器"),
    WATER_PRESSURE(11, "液压探测器"),

    ELECTRIC_MONITOR(12, "智慧用电主机"),
    ELECTRIC_RESIDUAL_CURRENT(13, "剩余电流检测器"),
    ELECTRIC_CURRENT_TRANSFORMER(14, "电流互感器"),
    ELECTRIC_VOLTAGE_DETECTOR(15, "电压检测器"),
    ELECTRIC_TEMP_DETECTOR(16, "电路温度传感器"),
    ELECTRIC_ARC_FAULT_MONITOR(17, "故障电弧检测器"),

    FIRE_ALARM_MACHINE(18, "火灾报警控制器"),
    ULTRASONIC_DISTANCE(19, "超声波距离检测器");

    private int code;
    private String name;

    ProductType(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
