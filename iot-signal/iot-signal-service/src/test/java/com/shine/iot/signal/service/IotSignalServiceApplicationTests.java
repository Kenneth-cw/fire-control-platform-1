package com.shine.iot.signal.service;

import com.shine.iot.model.entity.DeviceBaseInfoModel;
import com.shine.iot.signal.service.feignClient.DeviceBaseFeignClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IotSignalServiceApplicationTests {

    @Test
    public void contextLoads() {
    }

    private DeviceBaseFeignClient deviceBaseFeignClient;

    @Autowired
    public void setDeviceBaseFeignClient(DeviceBaseFeignClient deviceBaseFeignClient) {
        this.deviceBaseFeignClient = deviceBaseFeignClient;
    }

    @Test
    public void testSaveOrUpDevice() {
        DeviceBaseInfoModel deviceUp = new DeviceBaseInfoModel();
        deviceUp.setWorkState(2);
        deviceUp.setDeviceId(318l);
        try {
            DeviceBaseInfoModel deviceModel = deviceBaseFeignClient.updateObjByPK(deviceUp);
            if (deviceModel != null) {
                System.out.println("---更新设备信息---" + deviceModel.getMcuID());
            } else {
                System.out.println("----返回结果为空----");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("--- error ---" + e.getMessage());
        }
    }

}
