package com.shine.iot.signal.util.device.bytemsg.process;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.ByteBuffer;

public abstract class DefaultByteReader<T> implements ByteReader {
    protected T data;
    protected byte[] dataSource;
    private int pointer = 0;//skip the flag: "start"
    protected Logger logger = LogManager.getLogger(DefaultByteReader.class);

    public DefaultByteReader(byte[] _source) {
        assert (_source != null);
        this.dataSource = _source;
    }

    @Override
    public T getResult() {
        return data;
    }

    @Override
    public DefaultByteReader skip(int step) {
        pointer = pointer + step;
        if (pointer < 0) pointer = 0;
        if (pointer > dataSource.length) pointer = dataSource.length;
        return this;
    }

    @Override
    public DefaultByteReader next(int length, ByteFunction fun) throws Exception {
        return read(pointer, length, fun);
    }

    @Override
    public DefaultByteReader read(int offset, int len, ByteFunction fun) throws Exception {
        if (dataSource.length >= offset + len) {//
            ByteBuffer objbuffer = ByteBuffer.allocate(len);
            objbuffer.put(dataSource, offset, len);
            fun.convertBytes2Obj(objbuffer.array(), data);
            pointer = offset + len;
        } else {
            throw new Exception("Data source byte array is not long enough! "
                    + "source bytes' length [" + dataSource.length + "] "
                    + "but try to read [" + len + "] bytes from position [" + offset + "] at function [" + fun + "]");
        }
        return this;
    }

    public int getCurrentPosition() {
        return pointer;
    }

    @Override
    public abstract void parse();

}
