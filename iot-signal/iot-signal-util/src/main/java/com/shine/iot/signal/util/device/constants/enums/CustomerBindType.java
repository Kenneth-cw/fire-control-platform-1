/**
 *
 */
package com.shine.iot.signal.util.device.constants.enums;

/**
 * @author EvanCheung
 *
 */
public enum CustomerBindType {
    MANUFACTURE("00", "生产商"), PROPERTYMANA("01", "物业管理"), MAINTENANCE("02", "维保"), CUSTOMER("03", "使用用户"), WATCHER("04", "关注者"), PROJECT("05", "工程方");


    String code;
    String name;

    CustomerBindType(String bindCode, String bindName) {
        this.code = bindCode;
        this.name = bindName;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}
