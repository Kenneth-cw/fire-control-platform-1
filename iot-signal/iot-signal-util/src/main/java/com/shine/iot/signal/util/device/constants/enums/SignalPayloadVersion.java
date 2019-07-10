package com.shine.iot.signal.util.device.constants.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SignalPayloadVersion {
    VERSION_0(0x0), // 版本0，MCUID为8字节（已废弃）；
    VERSION_1(0x8), // 版本1，MCUID为16字节。
    VERSION_2(0x2); // 版本2，无MCUID；

    private int code;

    /**
     * 通过 code 取枚举
     *
     * @param code key值
     * @return 枚举类
     */
    public static SignalPayloadVersion getEumnTypeByKey(int code) {
        for (SignalPayloadVersion type : SignalPayloadVersion.values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        return null;
    }

}
