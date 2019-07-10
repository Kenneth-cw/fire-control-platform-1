package com.shine.iot.signal.util.device.bytemsg.process.claa;

import com.shine.iot.signal.model.adapter.claa.ClaaJsonDataModel;
import com.shine.iot.signal.util.device.bytemsg.process.DefaultByteReader;

public class ClaaJsonByteReader extends DefaultByteReader<ClaaJsonDataModel> {

    public ClaaJsonByteReader(byte[] _source) {
        super(_source);
        data = new ClaaJsonDataModel();
    }

    @Override
    public void parse() {
        try {
            this.read(1, 2, ClaaJsonByteFunction.versionReader)
                    .next(2, ClaaJsonByteFunction.lenReader)
                    .next(data.getLen(), ClaaJsonByteFunction.dataReader);


        } catch (Exception e) {
            // logger.error(e.getMessage(),e);
        }
        System.out.println(data.getData() + "\n" + data.getResultJson() + "\n" + data.getResultJsonStr());
    }
}
