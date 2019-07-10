package com.shine.iot.signal.util.datastream.parser;


import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DigitUtil {
    private static Logger logger = LogManager.getLogger(DigitUtil.class);

    public static long convert2long(byte[] bytearr) {
        long num = 0;
        num = Long.parseLong(Hex.encodeHexString(bytearr), 16);
        return num;
    }

    @Deprecated
    public static long convert2Long(byte[] bytearr) {
        long num = 0;
        for (int ix = 0; ix < bytearr.length; ++ix) {
            num <<= 8;
            num |= (bytearr[ix] & 0xff);
        }
        return num;

    }

    public static int convert2int(byte[] bytearr) {
        int result = 0;
        if (bytearr != null && bytearr.length > 0 && bytearr.length <= 4) {
            for (int ix = 0; ix < bytearr.length; ++ix) {
                result <<= 8;
                result |= (bytearr[ix] & 0xff);
            }
        }

        return result;
    }

    public static boolean[] getBitsAsBoolean(byte b) {
        boolean[] result = new boolean[8];
        byte[] array = new byte[8];
        for (int i = 0; i <= 7; i++) {
            array[i] = (byte) (b & 1);
            b = (byte) (b >> 1);
            result[i] = (array[i] > 0);
        }

        return result;
    }

    public static int convert2int(byte byteValue) {
        int result = 0;
        result = (byteValue & 0xff);
        return result;
    }

    /**
     * 将16进制字符串转换为byte[]
     *
     * @param hex
     * @return
     */
    public static byte[] hexStringToByte(String hex) {

        byte[] result = null;
        result = decodeHex(hex);

        return result;
    }

    public static byte[] decodeHex(String hex) {

        byte[] result = null;
        try {
            result = Hex.decodeHex(hex.toCharArray());
        } catch (DecoderException e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    public static String getBitsAsString(byte by) {
        StringBuffer sb = new StringBuffer();
        sb.append((by >> 7) & 0x1)
                .append((by >> 6) & 0x1)
                .append((by >> 5) & 0x1)
                .append((by >> 4) & 0x1)
                .append((by >> 3) & 0x1)
                .append((by >> 2) & 0x1)
                .append((by >> 1) & 0x1)
                .append((by >> 0) & 0x1);

        return sb.toString();
    }

    /*	private static int toByte(char c) {
            byte b = (byte) "0123456789ABCDEF".indexOf(c);
            return b;
        }
    */
    public static String getCrc(byte[] data) {
        int high;
        int flag;

        // 16位寄存器，所有数位均为1  
        int wcrc = 0xffff;
        for (int i = 0; i < data.length; i++) {
            // 16 位寄存器的高位字节  
            high = wcrc >> 8;
            // 取被校验串的一个字节与 16 位寄存器的高位字节进行“异或”运算  
            wcrc = high ^ data[i];

            for (int j = 0; j < 8; j++) {
                flag = wcrc & 0x0001;
                // 把这个 16 寄存器向右移一位  
                wcrc = wcrc >> 1;
                // 若向右(标记位)移出的数位是 1,则生成多项式 1010 0000 0000 0001 和这个寄存器进行“异或”运算  
                if (flag == 1)
                    wcrc ^= 0xa001;
            }
        }

        return Integer.toHexString(wcrc);
    }

    public static String make_CRC(byte[] data) {
        byte[] buf = new byte[data.length];// 存储需要产生校验码的数据
        for (int i = 0; i < data.length; i++) {
            buf[i] = data[i];
        }
        int len = buf.length;
        int crc = 0xFFFF;
        for (int pos = 0; pos < len; pos++) {
            if (buf[pos] < 0) {
                crc ^= (int) buf[pos] + 256; // XOR byte into least sig. byte of crc
            } else {
                crc ^= (int) buf[pos]; // XOR byte into least sig. byte of crc
            }
            for (int i = 8; i != 0; i--) { // Loop over each bit
                if ((crc & 0x0001) != 0) { // If the LSB is set
                    crc >>= 1; // Shift right and XOR 0xA001
                    crc ^= 0xA001;
                } else
                    // Else LSB is not set
                    crc >>= 1; // Just shift right
            }
        }
        String c = Integer.toHexString(crc);
        if (c.length() == 4) {
            c = c.substring(2, 4) + c.substring(0, 2);
        } else if (c.length() == 3) {
            c = "0" + c;
            c = c.substring(2, 4) + c.substring(0, 2);
        } else if (c.length() == 2) {
            c = "0" + c.substring(1, 2) + "0" + c.substring(0, 1);
        }
        return c;
    }

    /**
     * char型数字转成字节流
     */
    public static byte[] convertChar2Byte(char data) {
        byte[] bytearr = new byte[2];
        bytearr[0] = (byte) (data >> 8);
        bytearr[1] = (byte) (data >> 0);
        return bytearr;
    }

    public static int do_crc16_ext(int reg_init, byte[] message) {
        int i;
        int crc_reg = reg_init;

        for (i = 0; i < message.length; i++) {
            crc_reg = (crc_reg >> 8) ^ crc16_ccitt_table[(crc_reg ^ message[i]) & 0xFF];
        }

        return crc_reg;
    }

    public static int do_crc16(byte[] message) {
        int crc_reg = 0x0000; //计算CRC校验时初值从0x0000开始。

        return do_crc16_ext(crc_reg, message);
    }

    public static int do_crc(int reg_init, int[] message) {
        int crc_reg = reg_init;
        for (int i = 0; i < message.length; i++) {
            crc_reg = (crc_reg >> 8) ^ crc16_ccitt_table[(crc_reg ^ message[i]) & 0xff];
        }
        return crc_reg;
    }

    public static byte[] intCrc2Bytes2(int num) {
        byte[] byteNum = new byte[2];
        for (int ix = 0; ix < 2; ++ix) {
            int offset = 16 - (ix + 1) * 8;
            byteNum[ix] = (byte) ((num >> offset) & 0xff);
        }
        return byteNum;
    }  
  /*  public static int do_crc16_desc(byte[]  message)
    {
        int crc_reg = 0x0000; //计算CRC校验时初值从0x0000开始。

        return do_crc_desc(crc_reg, message);
    }*/

/*    public static int do_crc_desc(int reg_init, byte[] message) {  
        int crc_reg = reg_init;  
        for (int i = message.length - 1 ; i >= 0; i--) {  
            crc_reg = (crc_reg >> 8) ^ crc16_ccitt_table[(crc_reg ^ message[i]) & 0xff];  
        }  
        return crc_reg;  
    }  */


    public static boolean do_crc(int[] message, int[] crc) {
        // 计算CRC校验时初值从0x0000开始。  
        int crc_reg = 0x0000;
        int crc_value = (crc[0] & 0xff) * 256 + (crc[1] & 0xff);
        return crc_value == do_crc(crc_reg, message);
    }

    private static final char[] crc16_ccitt_table = {
            0x0000, 0x1021, 0x2042, 0x3063, 0x4084, 0x50a5, 0x60c6, 0x70e7, 0x8108, 0x9129, 0xa14a, 0xb16b, 0xc18c, 0xd1ad, 0xe1ce, 0xf1ef,
            0x1231, 0x0210, 0x3273, 0x2252, 0x52b5, 0x4294, 0x72f7, 0x62d6, 0x9339, 0x8318, 0xb37b, 0xa35a, 0xd3bd, 0xc39c, 0xf3ff, 0xe3de,
            0x2462, 0x3443, 0x0420, 0x1401, 0x64e6, 0x74c7, 0x44a4, 0x5485, 0xa56a, 0xb54b, 0x8528, 0x9509, 0xe5ee, 0xf5cf, 0xc5ac, 0xd58d,
            0x3653, 0x2672, 0x1611, 0x0630, 0x76d7, 0x66f6, 0x5695, 0x46b4, 0xb75b, 0xa77a, 0x9719, 0x8738, 0xf7df, 0xe7fe, 0xd79d, 0xc7bc,
            0x48c4, 0x58e5, 0x6886, 0x78a7, 0x0840, 0x1861, 0x2802, 0x3823, 0xc9cc, 0xd9ed, 0xe98e, 0xf9af, 0x8948, 0x9969, 0xa90a, 0xb92b,
            0x5af5, 0x4ad4, 0x7ab7, 0x6a96, 0x1a71, 0x0a50, 0x3a33, 0x2a12, 0xdbfd, 0xcbdc, 0xfbbf, 0xeb9e, 0x9b79, 0x8b58, 0xbb3b, 0xab1a,
            0x6ca6, 0x7c87, 0x4ce4, 0x5cc5, 0x2c22, 0x3c03, 0x0c60, 0x1c41, 0xedae, 0xfd8f, 0xcdec, 0xddcd, 0xad2a, 0xbd0b, 0x8d68, 0x9d49,
            0x7e97, 0x6eb6, 0x5ed5, 0x4ef4, 0x3e13, 0x2e32, 0x1e51, 0x0e70, 0xff9f, 0xefbe, 0xdfdd, 0xcffc, 0xbf1b, 0xaf3a, 0x9f59, 0x8f78,
            0x9188, 0x81a9, 0xb1ca, 0xa1eb, 0xd10c, 0xc12d, 0xf14e, 0xe16f, 0x1080, 0x00a1, 0x30c2, 0x20e3, 0x5004, 0x4025, 0x7046, 0x6067,
            0x83b9, 0x9398, 0xa3fb, 0xb3da, 0xc33d, 0xd31c, 0xe37f, 0xf35e, 0x02b1, 0x1290, 0x22f3, 0x32d2, 0x4235, 0x5214, 0x6277, 0x7256,
            0xb5ea, 0xa5cb, 0x95a8, 0x8589, 0xf56e, 0xe54f, 0xd52c, 0xc50d, 0x34e2, 0x24c3, 0x14a0, 0x0481, 0x7466, 0x6447, 0x5424, 0x4405,
            0xa7db, 0xb7fa, 0x8799, 0x97b8, 0xe75f, 0xf77e, 0xc71d, 0xd73c, 0x26d3, 0x36f2, 0x0691, 0x16b0, 0x6657, 0x7676, 0x4615, 0x5634,
            0xd94c, 0xc96d, 0xf90e, 0xe92f, 0x99c8, 0x89e9, 0xb98a, 0xa9ab, 0x5844, 0x4865, 0x7806, 0x6827, 0x18c0, 0x08e1, 0x3882, 0x28a3,
            0xcb7d, 0xdb5c, 0xeb3f, 0xfb1e, 0x8bf9, 0x9bd8, 0xabbb, 0xbb9a, 0x4a75, 0x5a54, 0x6a37, 0x7a16, 0x0af1, 0x1ad0, 0x2ab3, 0x3a92,
            0xfd2e, 0xed0f, 0xdd6c, 0xcd4d, 0xbdaa, 0xad8b, 0x9de8, 0x8dc9, 0x7c26, 0x6c07, 0x5c64, 0x4c45, 0x3ca2, 0x2c83, 0x1ce0, 0x0cc1,
            0xef1f, 0xff3e, 0xcf5d, 0xdf7c, 0xaf9b, 0xbfba, 0x8fd9, 0x9ff8, 0x6e17, 0x7e36, 0x4e55, 0x5e74, 0x2e93, 0x3eb2, 0x0ed1, 0x1ef0
    };

    public static String convertHexToString(byte[] byData) {
        StringBuffer buf = new StringBuffer();
        for (byte x : byData) {
            char ch = (char) x;
            buf.append(ch + "");
        }
        return buf.toString();
    }


    /**
     * 将16进制字符串转换为byte[]
     *
     * @param str
     * @return
     */
    public static byte[] toBytes(String str) {
        if (str == null || str.trim().equals("")) {
            return new byte[0];
        }

        byte[] bytes = new byte[str.length() / 2];
        for (int i = 0; i < str.length() / 2; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }
        return bytes;
    }

    /**
     * 将16进制字符串转换为byte[]
     *
     * @param hex
     * @return
     */
    public static byte[] hexStringToByteArray(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (hexCharToByte(achar[pos]) << 4 | hexCharToByte(achar[pos + 1]));
        }
        return result;
    }

    private static int hexCharToByte(char c) {
        byte b = (byte) "0123456789ABCDEF".indexOf(c);
        if (b <= 0) {
            b = (byte) "0123456789abcdef".indexOf(c);
        }
        return b;
    }

    /**
     * 字符串转化成为16进制字符串
     *
     * @param s
     * @return
     */
    public static String strTo16(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }

    /**
     * 16进制直接转换成为字符串(无需Unicode解码)
     *
     * @param hexStr
     * @return
     */
    public static String hexStr2Str(String hexStr) {
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;
        for (int i = 0; i < bytes.length; i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }

    /**
     * @param buf
     * @return
     */
    public static String Byte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    public static String Int32ToHexStr(int input) {
        StringBuffer sb = new StringBuffer();

        String hex = Integer.toHexString((input >> 24) & 0xFF);
        if (hex.length() == 1) {
            hex = '0' + hex;
        }
        sb.append(hex.toUpperCase());

        hex = Integer.toHexString((input >> 16) & 0xFF);
        if (hex.length() == 1) {
            hex = '0' + hex;
        }
        sb.append(hex.toUpperCase());

        hex = Integer.toHexString((input >> 8) & 0xFF);
        if (hex.length() == 1) {
            hex = '0' + hex;
        }
        sb.append(hex.toUpperCase());

        hex = Integer.toHexString((input >> 0) & 0xFF);
        if (hex.length() == 1) {
            hex = '0' + hex;
        }
        sb.append(hex.toUpperCase());

        return sb.toString();
    }

    /**
     * int整数转换为4字节的byte数组
     *
     * @param i 整数
     * @return byte数组
     */
    public static byte[] intToByte4(int i) {
        byte[] targets = new byte[4];
        targets[3] = (byte) (i & 0xFF);
        targets[2] = (byte) (i >> 8 & 0xFF);
        targets[1] = (byte) (i >> 16 & 0xFF);
        targets[0] = (byte) (i >> 24 & 0xFF);
        return targets;
    }

    /**
     * long整数转换为8字节的byte数组
     *
     * @param lo long整数
     * @return byte数组
     */
    public static byte[] longToByte8(long lo) {
        byte[] targets = new byte[8];
        for (int i = 0; i < 8; i++) {
            int offset = (targets.length - 1 - i) * 8;
            targets[i] = (byte) ((lo >>> offset) & 0xFF);
        }
        return targets;
    }

    /**
     * short整数转换为2字节的byte数组
     *
     * @param s short整数
     * @return byte数组
     */
    public static byte[] unsignedShortToByte2(int s) {
        byte[] targets = new byte[2];
        targets[0] = (byte) (s >> 8 & 0xFF);
        targets[1] = (byte) (s & 0xFF);
        return targets;
    }

    /**
     * byte数组转换为无符号short整数
     *
     * @param bytes byte数组
     * @return short整数
     */
    public static int byte2ToUnsignedShort(byte[] bytes) {
        return byte2ToUnsignedShort(bytes, 0);
    }

    /**
     * byte数组转换为无符号short整数
     *
     * @param bytes byte数组
     * @param off   开始位置
     * @return short整数
     */
    public static int byte2ToUnsignedShort(byte[] bytes, int off) {
        int high = bytes[off];
        int low = bytes[off + 1];
        return (high << 8 & 0xFF00) | (low & 0xFF);
    }

    /**
     * byte数组转换为int整数
     *
     * @param bytes byte数组
     * @param off   开始位置
     * @return int整数
     */
    public static int byte4ToInt(byte[] bytes, int off) {
        int b0 = bytes[off] & 0xFF;
        int b1 = bytes[off + 1] & 0xFF;
        int b2 = bytes[off + 2] & 0xFF;
        int b3 = bytes[off + 3] & 0xFF;
        return (b0 << 24) | (b1 << 16) | (b2 << 8) | b3;
    }

    /**
     * 判断byte第几位是否为1
     *
     * @param b   byte
     * @param pos 第几位：从低位0开始（判断最高位，则pos为7）
     * @return
     */
    public static boolean bytePositionOn1(byte b, int pos) {
        if ((b & (0x1 << pos)) == (0x1 << pos)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 将16进制数值转换成ASCII码对应的控制字符
     *
     * @param hex
     * @return
     */
    public static String convertHexToString(String hex) {
        //创建StringBuilder对象
        StringBuilder stringBuilder = new StringBuilder();
        //循环
        for (int i = 0; i < hex.length() - 1; i += 2) {
            //依次截取hex内容
            String output = hex.substring(i, (i + 2));
            //将16进制内容转成10进制
            int decimal = Integer.parseInt(output, 16);
            //将10进制转成对应字符并追加到StringBuilder中
            stringBuilder.append((char) decimal);
        }
        //返回
        return stringBuilder.toString();
    }
}
