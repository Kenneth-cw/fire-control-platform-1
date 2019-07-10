package com.shine.iot.signal.model.adapter.aug;

public enum TLV81DataFormat {

    RF_CHAIN(0x00),
    FREQUENCY(0x01),
    MODULATION(0x02),
    CRC_STATUS(0x03),
    SF(0x04),
    CODE_RATE(0x05),
    RSSI(0x06),
    SNR(0x07),
    RF_DATA(0x08);

    /**
     * 列名的数字表示
     */
    private int nameID;

    /**
     * 英文名
     */
    private String enName;

    TLV81DataFormat(final int _nameID) {
        this.nameID = _nameID;
    }

    public static TLV81DataFormat getByID(int id) {
        switch (id) {
            case 0x00:
                return RF_CHAIN;
            case 0x01:
                return FREQUENCY;
            case 0x02:
                return MODULATION;
            case 0x03:
                return CRC_STATUS;
            case 0x04:
                return SF;
            case 0x05:
                return CODE_RATE;
            case 0x06:
                return RSSI;
            case 0x07:
                return SNR;
            case 0x08:
                return RF_DATA;
            default:
                return null;
        }


    }

}
