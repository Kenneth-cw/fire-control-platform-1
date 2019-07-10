package com.shine.iot.signal.util.device.bytemsg.process;

import java.nio.ByteBuffer;

public final class ByteWriter {

    private static final int MIN_LEN = 16;
    private ByteBuffer _data = null;
    private int curPos = 0;
    private byte[] _result;

    public ByteWriter(int defaultLen) {
        int _defaultlen = 1024 / 2;
        if (defaultLen <= 0)
            _defaultlen = MIN_LEN;
        else
            _defaultlen = defaultLen;
        _result = new byte[_defaultlen];

    }

    public ByteWriter addByte(byte _byte) throws Exception {
        return addByte(curPos, _byte);
    }

    public ByteWriter addBytes(byte[] _bytes) throws Exception {
        return addBytes(curPos, _bytes);
    }

    public ByteWriter addByte(int index, byte _byte) throws Exception {
//        if(_data.capacity() < index + 1){
//            throw  new Exception("The position is greater than the container's capacity! ");
//        }else{
        byte[] tba = new byte[1];
        tba[0] = _byte;
//            _data.put(index,_byte);
//            System.arraycopy((byte[]){_byte},0,_result,curPos,1);

//            curPos = index + 1;
        return addBytes(index, tba);
//        }
//        return this;
    }

    public ByteWriter addBytes(int index, byte[] _bytes) throws Exception {
        if (_result.length < index + _bytes.length) {
            throw new Exception("The position is greater than the container's capacity! ");
        } else {
            // _data.put(_bytes,index,_bytes.length);
            System.arraycopy(_bytes, 0, _result, index, _bytes.length);
            curPos = index + _bytes.length;
        }
        return this;
    }

    public byte[] getResult() {
//        return _data.array();
        return _result;
    }
}
