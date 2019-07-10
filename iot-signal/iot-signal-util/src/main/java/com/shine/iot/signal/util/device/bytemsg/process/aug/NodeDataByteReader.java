package com.shine.iot.signal.util.device.bytemsg.process.aug;


import com.shine.iot.signal.model.adapter.aug.NodeDataAdapter;
import com.shine.iot.signal.util.datastream.parser.DigitUtil;
import com.shine.iot.signal.util.device.bytemsg.process.DefaultByteReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NodeDataByteReader extends DefaultByteReader<NodeDataAdapter> {

    private Logger logger = LogManager.getLogger(NodeDataByteReader.class);

    public NodeDataByteReader(byte[] _source) {
        super(_source);
        data = new NodeDataAdapter();
    }

    public void parse() {
        try {
            this.read(dataSource.length - 2, 2, NodeDataByteFunction.crcReader) //payload的CRC校验码
                    .read(0, dataSource.length - 2, NodeDataByteFunction.crcCorrectReader) // 判断并设置CRC的校验码是否正确
                    .read(0, 1, NodeDataByteFunction.industryReader)
                    .read(0, 1, NodeDataByteFunction.mcuidLenReader)
                    .next(1, NodeDataByteFunction.deviceTypeReader);
            if (data.getMcuidLen() == 16) { //16位MCUID
                this.next(1, NodeDataByteFunction.productModelReader)
                        .next(data.getMcuidLen(), NodeDataByteFunction.mcuidHexReader)
                        .next(1, NodeDataByteFunction.msgTypeReader)//confirm ack at this step for 16 bytes mcu
                        .next(1, NodeDataByteFunction.fctrlReader)
                        .next(1, NodeDataByteFunction.reservedReader);
            } else { //8位mcuID
                this.next(data.getMcuidLen(), NodeDataByteFunction.mcuidHexReader)
                        .next(1, NodeDataByteFunction.msgTypeReader)
                        .next(1, NodeDataByteFunction.fctrlReader);
            }
            if (data.isAck()) return; //数据为ACK，暂不处理
            next(2, NodeDataByteFunction.fcntReader)
                    .next(1, NodeDataByteFunction.cmdReader);//confirm ack at this step for 8 bytes mcu

            next(1, NodeDataByteFunction.rfDataLenReader)
                    .next(data.getDataLength(), NodeDataByteFunction.appendixdataReader);
			/*if() {
				
			}*/
            //read(0, dataSource.length, NodeDataByteFunction.ackReader);

            /**
             * ACK & CRC of payload data
             */
            byte[] payloadDataAckBytes = null;
            //对于16byte的MCUID：如果命令类型为 消警且为质检命令（dataLength为1，dataContent为"01"或"11"）   或者 命令类型为开机 且 设备为烟感（LORA/NB） ， 则发送0x07命令（蜂鸣器）
            if ((data.getMcuidLen() == 16 && data.getCmdType().equals("4") && data.getDataLength() == 1 && data.getAppendixData() != null
                    && (data.getAppendixData().equals("01") || data.getAppendixData().equals("11")))
                    || (data.getMcuidLen() == 16 && (data.getCmdType().equals("5") && (data.getDeviceType().equals("0") || data.getDeviceType().equals("1"))))) {
                // AS 主动向节点下发的指令（携带指令内容）
                byte[] payloaddata = new byte[26];
                payloadDataAckBytes = new byte[28]; // + CRC (2 bytes)
                System.arraycopy(dataSource, 0, payloaddata, 0, 19);
                payloaddata[2] = (byte) (Integer.valueOf(data.getCmdType()) | 0x80); // 命令类型
                payloaddata[19] = (byte) (Integer.valueOf(data.getLorawanMsgType()) | 0x80); // ACK 指令类型（例如：0x04 需要确认的数据上行,变为0x84）
                System.arraycopy(DigitUtil.unsignedShortToByte2(data.getLoraFrameCounter()), 0, payloaddata, 20, 2); // 仍然是，发送方的帧计数器高低字节
                //AS服务器的帧计数器（没做）
                payloaddata[22] = (byte) 0x00;
                payloaddata[23] = (byte) 0x00;
                // 服务器下发的指令
                payloaddata[24] = (byte) 0x07;
                // 指令附带的数据长度
                payloaddata[25] = (byte) 0x00;
                //此处数据长度为0，所以没有数据内容

                byte[] payloaddataRtnCrc = DigitUtil.intCrc2Bytes2(DigitUtil.do_crc16(payloaddata));

                System.arraycopy(payloaddata, 0, payloadDataAckBytes, 0, 26);
                System.arraycopy(payloaddataRtnCrc, 0, payloadDataAckBytes, 26, 2);

                data.setPayloadDataAckBytes(payloadDataAckBytes);
            }

            if (data.getMcuidLen() == 8 && payloadDataAckBytes == null) { //mcuId为8字节的ack
                byte[] payloaddata = new byte[16];
                payloadDataAckBytes = new byte[18];//rfDataValueByte.length + 1//from payload data 0 .. 8 , plus ACK(3 bytes) + CRC (2 bytes)
                System.arraycopy(dataSource, 0, payloaddata, 0, 16);// rfDataValueByte.length
                System.arraycopy(dataSource, 0, payloadDataAckBytes, 0, 16);// rfDataValueByte.length
                payloaddata[14] = (byte) (payloaddata[14] | 0x80);

                payloaddata[15] = 0x00;
                byte[] payloaddataRtnCrc = DigitUtil.intCrc2Bytes2(DigitUtil.do_crc16(payloaddata));//Hex.decodeHex(hexStrOfPayloadDataCRC);
                System.arraycopy(payloaddata, 0, payloadDataAckBytes, 0, 16);// rfDataValueByte.length
                System.arraycopy(payloaddataRtnCrc, 0, payloadDataAckBytes, 16, 2);// rfDataValueByte.length

            } else if (data.getMcuidLen() == 16 && payloadDataAckBytes == null) { //mcuId为16字节的ack
                byte[] payloaddata = new byte[22];
                payloadDataAckBytes = new byte[24]; //+ CRC (2 bytes)
                System.arraycopy(dataSource, 0, payloaddata, 0, 19);
                payloaddata[2] = (byte) (Integer.valueOf(data.getCmdType()) | 0x80);
                payloaddata[19] = (byte) (Integer.valueOf(data.getLorawanMsgType()) | 0x80); //ACK
                System.arraycopy(DigitUtil.unsignedShortToByte2(data.getLoraFrameCounter()), 0, payloaddata, 20, 2);

                byte[] payloaddataRtnCrc = DigitUtil.intCrc2Bytes2(DigitUtil.do_crc16(payloaddata));

                System.arraycopy(payloaddata, 0, payloadDataAckBytes, 0, 22);
                System.arraycopy(payloaddataRtnCrc, 0, payloadDataAckBytes, 22, 2);
            }
            data.setPayloadDataAckBytes(payloadDataAckBytes);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }
}
