package com.shine.iot.signal.util.device.bytemsg.process;


public interface ByteReader {

    ByteReader next(int length, ByteFunction fun) throws Exception;

    ByteReader read(int offset, int len, ByteFunction fun) throws Exception;

    ByteReader skip(int step);

    int getCurrentPosition();

    Object getResult();

    void parse();

}
