/**
 *
 */
package com.shine.iot.signal.model.adapter.aug;

/**
 * @author EvanCheung
 *
 */
public class GateWayTLV81DataPack {

    private byte[] dataReceived;//接收的字节流
    private byte[] dataSeq;//字节流的顺序号

    /**
     * @return the dataReceived
     */
    public byte[] getDataReceived() {
        return dataReceived;
    }

    /**
     * @param dataReceived the dataReceived to set
     */
    public void setDataReceived(byte[] dataReceived) {
        this.dataReceived = dataReceived;
    }

    /**
     * @return the dataSeq
     */
    public byte[] getDataSeq() {
        return dataSeq;
    }

    /**
     * @param dataSeq the dataSeq to set
     */
    public void setDataSeq(byte[] dataSeq) {
        this.dataSeq = dataSeq;
    }
}
