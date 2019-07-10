package com.shine.iot.signal.reader.quartz.job;

import com.shine.iot.signal.model.adapter.aug.NodeDataAdapter;
import com.shine.iot.signal.reader.socket.augTlV.AugTLVClientSocketServer;
import org.apache.commons.codec.binary.Hex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class DownAckJob implements Job {
    private Logger logger = LogManager.getLogger(DownAckJob.class);

    private AugTLVClientSocketServer augTlvClientSocket = AugTLVClientSocketServer.getInstance();

    //    private AugTLVClientSocketServer augTlvClientSocket = AugTLVClientSocketServer.getSingleInstance();
    public DownAckJob() {

    }

    /* (non-Javadoc)
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @Override
    public void execute(JobExecutionContext ackCtx) throws JobExecutionException {
        DatagramPacket send_pkt_ack;
        JobDataMap jobData = ackCtx.getJobDetail().getJobDataMap();

        byte[] ackBytes = (byte[]) jobData.get("ackbytes");
        InetAddress deviceAddr = (InetAddress) jobData.get("deviceAddress");
        Integer devicePort = (Integer) jobData.get("devicePort");
        NodeDataAdapter nodeData = (NodeDataAdapter) jobData.get("nodeData");
        send_pkt_ack = new DatagramPacket(ackBytes, ackBytes.length, deviceAddr, devicePort);
        try {
            logger.info("Send to Gateway :" + deviceAddr.getHostAddress() + ":" + devicePort);
            augTlvClientSocket.sendData(send_pkt_ack);
            //InfoNotifier.saveNodeMsg(nodeData, "D","1");//下行ack
            // 存储下行信号发送记录（原始数据保存）
            // CentralDataDeal.saveRawNodeMsg(nodeData, Hex.encodeHexString(ackBytes), "D","1");//下行ack
        } catch (IOException e) {
            logger.error("Failed to send ack info :" + e.getMessage(), e);
        }
        logger.debug("ack msg [" + Hex.encodeHexString(ackBytes) + "] sended");
    }
}
