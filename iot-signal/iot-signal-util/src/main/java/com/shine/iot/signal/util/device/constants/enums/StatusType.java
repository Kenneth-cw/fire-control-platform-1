package com.shine.iot.signal.util.device.constants.enums;

public enum StatusType {

    UNKNOWN(0, "未知"),
    NORMAL(1, "正常"),
    OFFLINE(2, "离线"),
    ALARM(3, "报警"),
    ALARMCANCEL(4, "报警解除"),
    BOOTUP(5, "开机"),
    SHUTDOWN(6, "关机"),
    ACTIVATION(7, "激活"),
    LOWBATTERY(8, "低电量"),
    SYSTEMFAILURE(9, "系统故障"),
    SENSORFAILURE(10, "传感器故障"),
    RFOPEN(11, "射频开启"),
    RFOFF(12, "射频关闭"),
    FAULT(13, "故障"),
    START_UP(14, "启动"),
    FEEDBACK(15, "反馈"),
    SUPERVISE(16, "监管"),
    SHIELD(17, "屏蔽"),
    RESERVE(18, "预留"),
    REGULATORYALARM(19, "监管报警"),
    MODULEBOOT(20, "模块启动"),
    COMMUNICATION_FAILURE_ON_CIRCUIT_BOARD(21, "回路板上通讯故障"),
    SHORTCIRCUIT(22, "短路"),
    OPENCIRCUIT(23, "开路"),
    COMMUNICATION_FAILURE_OF_GAS_CONTROL_BOARD(24, "气体控制板通讯故障"),
    EMERGENCY_BROADCASTING_FAILURE(25, "应急广播故障"),
    GWHEART(26, "网关心跳"),
    BSQALARM(27, "传感器报警"),
    FAULT_LOWBATTERY(28, "设备故障+低电"),
    /* 变送器状态 */
    OCXO_LOWSPEED_ERROR(29, "MCU的外部低速晶振错误"),
    OCXO_HIGHTSPEED_ERROR(30, "MCU的外部高速晶振错误"),
    DEVOWNCHECK(31, "设备正在自检测试"),
    HIGH_TEMPERATURE(32, "温度高"),
    MAIN_SENSOR_ALARM(40, "主传感器发生报警"),
    MAIN_SENSOR_FAULT(41, "主传感器出现故障"),
    SUB_SENSOR_ALARM(42, "次传感器出现报警"),
    SUB_SENSOR_FAULT(43, "次传感器出现故障"),
    /* 电气火灾 */
    BROKEN_LINE(33, "断线"),
    OVERCURRENT_ALARM(34, "电源过流报警"),
    POWER_OVERVOLTAGE(35, "电源过压"),
    POWER_UNDERVOLTAGE(36, "电源欠压"),
    POWER_MISSINGPHASE(37, "电源缺相"),
    POWER_FAILURE(38, "电源断电"),
    REPORTDEVICESTATUS(39, "上报设备状态"),
    EARLYWARN(44, "预警"),
    CONTROLLERRESET(45, "控制器复位"),
    STARTANDFEEDBACK(46, "启动并反馈"),
    PANELSIGNAL(47, "面板信号"),
    SITESIGNAL(48, "现场信号"),
    FIREALARMSIGNAL(49, "火警信号"),
    PANELSPRAYING(50, "面板喷洒"),
    FIELDSPRAYING(51, "现场喷洒"),
    FIRESPRINKLER(52, "火警喷洒"),
    PANELSTARTUP(53, "面板启动"),
    FIELDSTARTUP(54, "现场启动"),
    FIREALARMSTARTUP(55, "火警启动"),
    UNKNOWNSTART(56, "不明启动");

    //定义属性
    private int code;
    private String name;

    //构造方法
    StatusType(int code, String name) {
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
     * 根据key值获取枚举类的描述
     *
     * @param code 枚举类的key值
     * @return 枚举类 code 的描述
     */
    public static String getValueBykey(int code) {
        for (StatusType type : StatusType.values()) {
            if (type.getCode() == code) {
                return type.getName();
            }
        }
        return StatusType.UNKNOWN.getName();
    }

}
