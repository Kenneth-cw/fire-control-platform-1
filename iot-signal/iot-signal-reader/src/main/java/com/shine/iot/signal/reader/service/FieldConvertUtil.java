package com.shine.iot.signal.reader.service;

import com.shine.iot.signal.util.device.constants.enums.*;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 将 payload 数据转换为统一JSON数据的 属性转换器
 */
class FieldConvertUtil {

    /**
     * 行业类型转换
     *
     * @param field payload数据中行业类型值
     * @return 转换成统一格式的行业类型值
     */
    String fieldTypeConvert(String field) {
        int type = FieldType.UNKNOWN.getCode();
        if (NumberUtils.isParsable(field) && NumberUtils.isDigits(field)) {
            int fieldType = Integer.parseInt(field);
            switch (fieldType) {
                case 1:
                    type = FieldType.FIRECONTROL.getCode();
                    break;
                case 2:
                    type = FieldType.PERSONAL.getCode();
                    break;
                case 3:
                    type = FieldType.MEDICAL.getCode();
                    break;
                default:
                    type = FieldType.UNKNOWN.getCode();
                    break;
            }
        }
        return type + "";
    }

    /**
     * 设备类型转换
     *
     * @param deviceType payload 数据 的设备类型值
     * @return 转换为统一格式的设备类型值
     */
    String deviceTypeConvert(String deviceType) {
        String _deviceType = ProductType.UNKNOWN.getCode() + "";
        switch (Integer.parseInt(deviceType)) {
            case 0x00:
                _deviceType = ProductType.SMOKE_DETECTOR.getCode() + "";
                break;
            case 0x01:
                _deviceType = ProductType.SMOKE_DETECTOR.getCode() + "";
                break;
            case 0x02:
                _deviceType = ProductType.RING_ALARM.getCode() + "";
                break;
            case 0x03:
                _deviceType = ProductType.MANUAL_ALARM.getCode() + "";
                break;
            case 0x04:
                _deviceType = ProductType.TEMPERATURE_DETECTOR.getCode() + "";
                break;
            case 0x05:
                _deviceType = ProductType.NOISE_LIGHT_ALARM.getCode() + "";
                break;
            case 0x06:
                _deviceType = ProductType.TRANSMITTER.getCode() + "";
                break;
            case 0x09:
                _deviceType = ProductType.VOICE_SPEAKER.getCode() + "";
                break;
            case 0x0B:
                _deviceType = ProductType.GAS_DETECTOR.getCode() + "";
                break;
            case 0x0D:
                _deviceType = ProductType.SMOKE_SUCTION_DETECTOR.getCode() + "";
                break;
        }
        return _deviceType;
    }

    /**
     * 设备产品型号转换
     *
     * @param devModel
     * @return
     */
    String deviceModelConvert(String devModel) {
        String _devModel = ModelType.UNKNOWN.getCode() + "";
        return _devModel;
    }

    /**
     * 设备协议转换
     *
     * @param protocol
     * @return
     */
    String deviceProtocolConvert(String protocol) {
        String _protocol = ProductType.UNKNOWN.getCode() + "";
        switch (Integer.parseInt(protocol)) {
            case 0:
                _protocol = ProtocolType.LORALITE.getCode() + "";
                break;
            case 1:
                _protocol = ProtocolType.LORAWAN.getCode() + "";
                break;
            case 2:
                _protocol = ProtocolType.NB_IOT.getCode() + "";
                break;
        }
        return _protocol;
    }

    /**
     * 设备状态和命令的转换
     *
     * @param signalType
     * @return list.get(0)-status 、list.get(1)-command
     */
    List<String> statusCmdConvert(String signalType) {
        List<String> list = new ArrayList<>();
        String _status = ProductType.UNKNOWN.getCode() + "";
        String _controlCmd = CommandType.UNKNOWN.getCode() + "";
        switch (Integer.parseInt(signalType)) {
            //设备
            case 0x00:
                _status = StatusType.NORMAL.getCode() + "";
                _controlCmd = CommandType.CMD_HEART.getCode() + "";
                break;
            case 0x01:
                _status = StatusType.FAULT.getCode() + "";
                _controlCmd = CommandType.CMD_FAULT.getCode() + "";
                break;
            case 0x02:
                _status = StatusType.LOWBATTERY.getCode() + "";
                _controlCmd = CommandType.CMD_LOW_BATTERY.getCode() + "";
                break;
            case 0x03:
                _status = StatusType.ALARM.getCode() + "";
                _controlCmd = CommandType.CMD_SMOKE_ALARM.getCode() + "";
                break;
            case 0x04:
                _status = StatusType.NORMAL.getCode() + "";
                _controlCmd = CommandType.CMD_ALARM_STOPPED.getCode() + "";
                break;
            case 0x05:
                _status = StatusType.NORMAL.getCode() + "";
                _controlCmd = CommandType.CMD_DEV_SWITCH_ON.getCode() + "";
                break;
            case 0x0A:
                _status = StatusType.NORMAL.getCode() + "";
                _controlCmd = CommandType.CMD_ACTIVATE.getCode() + "";
                break;
            case 0x0F: //射频模块即将关闭
                _status = StatusType.RFOFF.getCode() + "";
                _controlCmd = CommandType.CMD_RF_SWITCH_OFF.getCode() + "";
                break;
            case 0x10: //设备即将关机
                _status = StatusType.SHUTDOWN.getCode() + "";
                _controlCmd = CommandType.CMD_DEV_SWITCH_OFF.getCode() + "";
                break;
            case 0x11: //上报设备状态
                _status = StatusType.NORMAL.getCode() + ""; // StatusType.REPORTDEVICESTATUS.getCode()+"";
                _controlCmd = CommandType.CMD_STATUS_REPORT.getCode() + "";
                break;
        }
        list.add(_status);
        list.add(_controlCmd);
        return list;
    }

}
