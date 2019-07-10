package com.shine.iot.redis.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 序列化，反序列化工具类
 */
public class SerializeUtil {
    //创建当前类日志对象
    private static Log log = LogFactory.getLog(SerializeUtil.class);

    /**
     * 将一个对象序列化为二进制数组
     *
     * @param object 要序列化的对象，该必须实现java.io.Serializable接口
     * @return 被序列化后的二进制数组
     */
    public static byte[] serialize(Object object) {
        //捕获并处理异常
        try {
            //输出流【写数据】
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            //将object写成二进制数据
            oos.writeObject(object);
            //返回
            return baos.toByteArray();
        } catch (Exception e) {
            //打印输出
            log.info("e：" + e.getMessage());
        }
        //返回
        return null;
    }

    /**
     * 将一个二进制数组反序列化为一个对象。程序不检查反序列化过程中的对象类型。
     *
     * @param bytes 要反序列化的二进制数
     * @return 反序列化后的对象
     */
    public static Object unserialize(byte[] bytes) {
        //捕获并处理异常
        try {
            //输入流【读数据】
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            //返回读取的数据
            return ois.readObject();
        } catch (Exception e) {
            //打印输出
            log.info("e：" + e.getMessage());
        }
        //返回
        return null;
    }
}
