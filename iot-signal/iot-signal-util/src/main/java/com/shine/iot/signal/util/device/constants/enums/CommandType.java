package com.shine.iot.signal.util.device.constants.enums;

public enum CommandType {

    UNKNOWN(0, "未知命令"),
    //上行信号指令
    CMD_HEART(1, "设备心跳"),
    CMD_FAULT(2, "设备故障警告"),
    CMD_SMOKE_ALARM(3, "传感器报警"), //（数据为1字节时，0x01为自检开始）
    CMD_ALARM_STOPPED(4, "报警解除"), //（数据为1字节时，0x01为自检结束）
    CMD_LOW_BATTERY(5, "设备低电量警告"),
    CMD_DEV_SWITCH_ON(6, "设备已开机"), //重启设备
    CMD_ACTIVATE(7, "设备已激活"),
    CMD_RF_SWITCH_OFF(8, "射频模块即将关闭"),
    CMD_DEV_SWITCH_OFF(9, "设备即将关机"),
    CMD_STATUS_REPORT(10, "上报设备状态"),
    //下行控制指令
    CMD_POWER_ON(11, "重启终端设备"),
    CMD_POWER_OFF(12, "关闭终端设备"),
    CMD_SET_PARAM(13, "配置设备参数"),
    CMD_SET_BUZZER(14, "配置蜂鸣器"),
    CMD_BUZZER_ON(15, "打开蜂鸣器"),
    CMD_BUZZER_OFF(16, "关闭蜂鸣器"),
    CMD_VOICELIGHT_ON(17, "打开声光报警器"),
    CMD_VOICELIGHT_OFF(18, "关闭声光报警器"),
    CMD_VOICE_ON(19, "控制扬声器响"),
    CMD_VOICE_OFF(20, "关闭扬声器");

    private int code;
    private String name;

    CommandType(int code, String name) {
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
    public static CommandType getEnumTypeByKey(int code) {
        for (CommandType type : CommandType.values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        return null;
    }
}