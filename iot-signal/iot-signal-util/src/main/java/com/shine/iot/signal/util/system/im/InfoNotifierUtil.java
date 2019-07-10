package com.shine.iot.signal.util.system.im;

import com.shine.iot.model.entity.DeviceBaseInfoModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InfoNotifierUtil {

    private static Logger logger = LogManager.getLogger(InfoNotifierUtil.class);

    /**
     * 发送 短信  和  微信
     *
     * @param device
     */
    private static void sendMsg(DeviceBaseInfoModel device, String warningType, String signalType) {
        String msgTo = "";
        String address = "";
        /*if(StringUtils.isBlank(device.getMcuIDHex()) && StringUtils.isBlank(device.getDevEUI())) {
            logger.debug("设备id【"+device.getDeviceId()+"】的MCUID与DevEUI为空，不进行发送微信短信的服务！");
            return;
        }
        UserDet deviceFilter = new UserDeviceWO();
        deviceFilter.setMcuIDHex(device.getMcuIDHex());
        deviceFilter.setDevEUI(device.getDevEUI());
        deviceFilter.setCustType(CustomerType.PRIVACY.getCode());
        PageInfo<UserDeviceWO> pageCon = new PageInfo<UserDeviceWO>();
        pageCon.setCurrentPage(1);
        pageCon.setPageSize(500);
        ServiceRsObjModel<PageInfo<UserDeviceWO>> pageResultRs = deviceService.queryUserDevices(pageCon, deviceFilter);
        if(pageResultRs.isSuccess() && !pageResultRs.getRsData().getResult().isEmpty()) {
            PageInfo<UserDeviceWO> pageResult = pageResultRs.getRsData();
            //定义变量用于存储当前设备所属的类型名称
            String deviceTypeName = pageResult.getResult().get(0).getDcTypeName();

            String allPhone = "";
            String areaName = "";
            String devAddr = "";
            if(StringUtils.isNotBlank(device.getAreaName())) {
                areaName = device.getAreaName();
            }
            if(StringUtils.isNotBlank(device.getDeviceAddr())) {
                devAddr = device.getDeviceAddr();
            }
            address = areaName + " - " + devAddr;
            for(UserDeviceWO userDevice : pageResult.getResult()) {
                msgTo = userDevice.getUserPhone();
                if(StringUtils.isNotBlank(msgTo)) {
                    if(StringUtils.isNotBlank(allPhone)) {
                        allPhone += "," + msgTo;
                    }else {
                        allPhone += msgTo;
                    }
                }
            }
            if(StringUtils.isNotBlank(address) && StringUtils.isNotBlank(allPhone)) {
                String result = "";
                try {
                    //组装短信参数内容(此参数必须为UTF-8编码)
                    String templateData = URLEncoder.encode(CustomTemplate.smokeDetectorTemplate(address, device.getDeviceNumber(), deviceTypeName, signalType), "UTF-8");
                    //发送短信通知
                    result = SendMessageUtil.sendMessage(templateData, allPhone);
                } catch (UnsupportedEncodingException e1) {
                    logger.info("UnsupportedEncodingException："+e1.getMessage(),e1);
                } catch (IOException e) {
                    logger.info("IOException："+e.getMessage(),e);
                }
                logger.info("系统已经向如下手机【"+allPhone+"】发送了关于设备【devEUI="+device.getDevEUI()+"】的短信通知！\t" +result);
            } else {
                logger.info("【devEUI="+device.getDevEUI()+"】的设备，address为空，或者，绑定此设备的电话号码为空！所以未发送短信通知！");
            }

            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            for (UserDeviceWO userDevice : pageResult.getResult()) {
                if (StringUtils.isNotBlank(userDevice.getWechatID())
                        && StringUtils.isNotBlank(address)) {
                    //配置微信参数
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("OpenID", userDevice.getWechatID());
                    params.put("Address", address);
                    params.put("WarningType", warningType);
                    params.put("Time", time);
                    params.put("deviceNumber", userDevice.getDeviceNumber());
                    params.put("deviceID", userDevice.getMcuIDHex().toUpperCase());
                    params.put("deviceQRCode", userDevice.getDeviceQRCode());
                    params.put("deviceType", userDevice.getDcTypeName()); //params.put("deviceType", deviceTypeName);
                    logger.info("post parameters: " + params);
                    wechatService.sendMsg(wechat_smokealert_service_url, params);
                }
            }
        }else {
            logger.warn("没有找到该设备[mcuid="+device.getMcuID()+"]的关联用户，系统不会自动发送报警信息！");
        }*/
    }

    /**
     * 只发送微信
     *
     * @param device
     * @param warningType
     */
    private static void sendWechatMsg(DeviceBaseInfoModel device, String warningType) {
        String address = "";

        // 分页查询当前设备的微信关注者信息
       /* UserDeviceDto deviceFilter = new UserDeviceDto();
        PageInfo<UserDeviceWO> pageCon = new PageInfo<UserDeviceWO>();
        pageCon.setCurrentPage(1);
        pageCon.setPageSize(500);
        deviceFilter.setMcuIDHex(device.getMcuIDHex());
        deviceFilter.setCustType(CustomerType.PRIVACY.getCode());
        deviceFilter.setBindType(CustomerBindType.WATCHER.getCode());
        ServiceRsObjModel<PageInfo<UserDeviceWO>> deviceResult = deviceService.queryUserDevices(pageCon, deviceFilter);
        if(deviceResult.isSuccess()) {
            if(deviceResult.getRsData() != null && deviceResult.getRsData().getResult() != null
                    && deviceResult.getRsData().getResult().size() > 0) {

                String areaName = "";
                String devAddr = "";
                if(StringUtils.isNotBlank(device.getAreaName())) {
                    areaName = device.getAreaName();
                }
                if(StringUtils.isNotBlank(device.getDeviceAddr())) {
                    devAddr = device.getDeviceAddr();
                }
                address = areaName + " - " + devAddr;

                String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

                for (UserDeviceWO userDevice : deviceResult.getRsData().getResult()) {
                    if (StringUtils.isNotBlank(userDevice.getWechatID())) {
                        //查询设备类型
                        String deviceType = "未知设备类型";
                        DeviceTypeDefModel defModel = new DeviceTypeDefModel();
                        defModel.setDcTypeCode(userDevice.getDeviceTypeCode());
                        ServiceRsObjModel<DeviceTypeDefModel> queryObj = deviceTypeDefService.getDetailByTypeCode(defModel);
                        if(queryObj.isSuccess() && queryObj.getRsData() != null) {
                            DeviceTypeDefModel deviceTypeDefModel = queryObj.getRsData();
                            if(StringUtils.isNotBlank(deviceTypeDefModel.getDcTypeName())) {
                                deviceType = deviceTypeDefModel.getDcTypeName();
                            }
                        }
                        //配置微信参数
                        Map<String, Object> params = new HashMap<String, Object>();
                        params.put("OpenID", userDevice.getWechatID());
                        params.put("Address", address);
                        params.put("WarningType", warningType);
                        params.put("Time", time);
                        params.put("deviceNumber", userDevice.getDeviceNumber());
                        params.put("deviceID", userDevice.getMcuID().toUpperCase());
                        params.put("deviceQRCode", userDevice.getDeviceQRCode());
                        params.put("deviceType", deviceType);
                        logger.info("post parameters: " + params);
                        wechatService.sendMsg(wechat_smokealert_service_url, params);
                    } else {
                        logger.warn("用户["+userDevice.getUserId()+"]的WeChatID为null，不能进行微信发送！");
                    }
                }
            } else {
                logger.warn("查询微信关注设备[mcuid="+device.getMcuID()+"]的用户，结果为null！");
            }
        }else {
            logger.warn("没有找到该设备[mcuid="+device.getMcuID()+"]的关联用户，系统不会自动发送报警信息！");
        }*/
    }


}
