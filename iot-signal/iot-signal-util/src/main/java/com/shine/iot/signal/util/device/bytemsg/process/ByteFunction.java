package com.shine.iot.signal.util.device.bytemsg.process;

/**
 * 字节处理方法接口。
 *
 * @author EvanCheung
 */
@FunctionalInterface
public interface ByteFunction<T> {

    Object convertBytes2Obj(byte[] byteArr, T data);

}
