package com.shine.iot.signal.service.logic;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shine.iot.model.entity.DeviceBaseInfoModel;
import com.shine.iot.signal.model.adapter.aug.NodeDataAdapter;
import com.shine.iot.signal.model.dto.aug.NodeDataDto;
import com.shine.iot.signal.model.dto.aug.system.GeneralJsonModel;
import com.shine.iot.signal.service.config.SpringWebSocketHandler;
import com.shine.iot.signal.service.feignClient.DeviceBaseFeignClient;
import com.shine.iot.signal.service.feignClient.PayloadSignalOPFeignClient;
import com.shine.iot.signal.util.device.constants.enums.CommandType;
import com.shine.iot.signal.util.device.constants.enums.DeviceValidStatus;
import com.shine.iot.signal.util.device.constants.enums.StatusType;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class GeneralJsonDataDealService {
    private static Logger logger = LogManager.getLogger(GeneralJsonDataDealService.class);

    //注入FeignClientService
    private PayloadSignalOPFeignClient _payloadSignalOPFeignClient;
    private DeviceBaseFeignClient _deviceBaseFeignClient;

    @Autowired
    public void setPayloadSignalOPFeignClient(PayloadSignalOPFeignClient payloadSignalOPFeignClient) {
        this._payloadSignalOPFeignClient = payloadSignalOPFeignClient;
    }

    @Autowired
    public void set_deviceBaseFeignClient(DeviceBaseFeignClient deviceBaseFeignClient) {
        this._deviceBaseFeignClient = deviceBaseFeignClient;
    }

    //构建静态属性，以供静态方法调用
    private static PayloadSignalOPFeignClient payloadSignalOPFeignClient;
    private static DeviceBaseFeignClient deviceBaseFeignClient;

    @PostConstruct
    public void init() {
        payloadSignalOPFeignClient = this._payloadSignalOPFeignClient;
        deviceBaseFeignClient = this._deviceBaseFeignClient;
    }

    /**
     * 处理并保存统一格式的JSON数据
     * 功能有：
     * 1.检查设备表中有无此设备，没有则添加此设备信息到deviceBaseInfo表中
     * 2.保存allMsg信息
     * 3.保存、更改lastedSignal信号、保存signalHistory
     * 4.保存fireConfirmInfo表
     */
    public static DeviceBaseInfoModel dealGeneralJsonData(GeneralJsonModel generalModel, NodeDataAdapter nodeDataAdapter) {


        /*logger.info("统一通用的json数据格式为：" + jsonStr);
        Boolean dealResult = payloadSignalOPFeignClient.dealGeneralJsonData(jsonStr);
        logger.info("保存json数据的结果：" + dealResult);*/

        // 1.保存allMsg信息
        NodeDataDto nodeDataDto = new NodeDataDto();
        nodeDataDto.setRcvTime(generalModel.getRcvTime());
        nodeDataDto.setGwId(generalModel.getGwid());
        nodeDataDto.setGwIP(generalModel.getIpFrom());
        nodeDataDto.setGwPort(generalModel.getPortFrom());
        nodeDataDto.setRawPayload(generalModel.getInitialData());
        nodeDataDto.setNodeDataAdapter(nodeDataAdapter);
        Boolean saveAllMsgResult = payloadSignalOPFeignClient.savePayloadSignal(nodeDataDto);
        logger.info("保存allMsg信息，执行结果：" + saveAllMsgResult);

        DeviceBaseInfoModel deviceBaseInfo = null;
        // 2.检查设备表中有无此设备，没有则添加此设备信息到deviceBaseInfo表中
        DeviceBaseInfoModel queryDevice = deviceBaseFeignClient.getDeviceByMcuId(nodeDataAdapter.getMcuIDHex().toUpperCase());
        int device_status_signal = Integer.parseInt(nodeDataAdapter.getSignalType());
        if (queryDevice != null) {
            int device_status_db = queryDevice.getWorkState().intValue();
            if (device_status_db != device_status_signal) {
                //设备信号的状态 与 数据库中查询出的设备工作状态不一致，修改数据库中当前设备的工作状态
                queryDevice.setWorkState(device_status_signal);
                deviceBaseInfo = deviceBaseFeignClient.updateObjByPK(queryDevice);
            }
        } else {
            // 此 MCUID 的设备，在 deviceBase 表中不存在，插入此设备信息
            DeviceBaseInfoModel deviceModel = new DeviceBaseInfoModel();
            deviceModel.setDeviceModelId(Integer.valueOf(nodeDataAdapter.getProductModel())); //设备型号
            deviceModel.setProtocolType(generalModel.getProtocol()); //设备协议
            deviceModel.setDeviceTypeCode(nodeDataAdapter.getDeviceType()); //设备类型
            deviceModel.setWorkState(device_status_signal); //设备当前状态

            deviceModel.setMcuID(nodeDataAdapter.getMcuIDHex().toUpperCase()); //设备MCUID
            deviceModel.setMcuIDHex(deviceModel.getMcuID());
            deviceModel.setDeviceQRCode(deviceModel.getMcuID());
            deviceModel.setDeviceNumber(deviceModel.getMcuID());
            deviceModel.setDeviceSN(deviceModel.getMcuID());
            deviceModel.setDeviceProducedDate(new Date());
            deviceBaseInfo = deviceBaseFeignClient.insertObj(deviceModel);
        }

        // 3.保存、更改lastedSignal信号、保存signalHistory

        // 4.保存fireConfirmInfo表


        return deviceBaseInfo;
    }

    /**
     * 解析payload数据，发送短信、微信、平台推送消息通知
     */
    public static void sendMessage(String jsonStr) {

        //解析JSON数据
        GeneralJsonModel generalModel = JSONObject.parseObject(jsonStr, GeneralJsonModel.class);
        logger.info("--- Aug-TLV ---- 烟感 ---- " + generalModel.getDeviceSN().toUpperCase());
        JSONObject jsonObject = (JSONObject) generalModel.getJsonData(); //解析NodeDataAdapter节点信息
        NodeDataAdapter nodeDataAdapter = jsonObject.toJavaObject(NodeDataAdapter.class); //payload节点信息对象
        if (nodeDataAdapter != null) {
            int cur_devStatus = generalModel.getStatus(); //当前信号表示的设备状态
            Integer cmdType = generalModel.getCommand(); //当前信号的命令类型
            String mcuId = nodeDataAdapter.getMcuIDHex().toUpperCase();

            //根据 mcuId 查询 数据库中此设备的当前状态
            // TODO：注意点，在查询这个数据库中的设备信息之前，是否已经将此信号的设备状态更新了。。。
            int db_devStatus = 0; //数据库中的设备状态
            DeviceBaseInfoModel db_deviceBase = deviceBaseFeignClient.getDeviceByMcuId(mcuId);
            if (db_deviceBase != null) {
                db_devStatus = db_deviceBase.getWorkState().intValue();
            }
            // 进行信号消息通知
            if (cur_devStatus != db_devStatus) {
                //发送微信、短信
                String signalType = getWarningTypeByCmdType(cmdType).get(0);
                String warningType = getWarningTypeByCmdType(cmdType).get(2);
                if (db_devStatus == StatusType.OFFLINE.getCode() && cur_devStatus == StatusType.NORMAL.getCode()) {
                    //数据库中此设备已经离线，再次收到此设备的心跳包，则此设备重新上线了
                    //sendMsg(device, "ONLINE_RESTORE", "恢复在线状态");
                } else {
                    if (StringUtils.isNotBlank(signalType) && StringUtils.isNotBlank(warningType)) {
                        //sendMsg(device, warningType, signalType);
                    }
                }

                /**
                 * web 端客户消息推送
                 */
                // TODO：查询管理此设备的oplogin的ID信息，先默认为 iotadmin, id: 1
                Set<Integer> userIdSet = new HashSet<>();
                userIdSet.add(1);
                // TODO：要发送到web的json信息
                String deviceJsonStr = JSON.toJSONString(db_deviceBase);
                TextMessage message = new TextMessage(deviceJsonStr);
                SpringWebSocketHandler.sendMessageToBatchUser(userIdSet, message);
            } else {
                logger.info("本次信号和数据库中设备status保持一致，不发送消息通知!");
            }
        }

    }

    /**
     * 烟感CMD命令转换为 烟感有效状态isValid、烟感的短信提醒内容signalType、微信警告标志warningType（微信端可识别）
     *
     * @param cmdType
     */
    private static List<String> getWarningTypeByCmdType(int cmdType) {
        String isValid = "";
        String signalType = "";
        String warningType = "";
        switch (CommandType.getEnumTypeByKey(cmdType)) {
            case CMD_HEART:
                //收到心跳，不进行推送
//				signalType = "恢复在线状态";
//				warningType = "ONLINE_RESTORE";
                break;
            case CMD_FAULT:
                signalType = "发生设备故障，请尽快检查";
                warningType = "FAULT_ALARM";
                break;
            case CMD_LOW_BATTERY:
                signalType = "电池电量低，请尽快更换电池";
                warningType = "LOWBATTERY_ALARM";
                break;
            case CMD_SMOKE_ALARM:
                signalType = "发生烟雾报警";
                warningType = "FIRE_ALARM";
                break;
            case CMD_ALARM_STOPPED:// 警报解除
                signalType = "烟雾报警已解除";
                warningType = "FIRE_ALARM_CANCEL";
                break;
            case CMD_DEV_SWITCH_ON:// 设备开机
                signalType = "恢复在线状态"; //重新开机启动
                warningType = "ONLINE_RESTORE";
                break;
            case CMD_ACTIVATE:// 设备已激活 -- 设备已安装
                isValid = DeviceValidStatus.INSTALL.getCode();
                signalType = "设备已激活";
                warningType = "ACTIVATED";
                break;
			/*case CMD_RF_SWITCH_OFF:// ■射频模块即将关闭
				isValid = SmokeDetectorValidStatus.INSTALL.getStatusCode();
				signalType = "射频模块即将关闭";
				warningType = "";
				break;
			case CMD_DEV_SWITCH_OFF:// ■设备即将关机
				isValid = SmokeDetectorValidStatus.INSTALL.getStatusCode();
				signalType = "设备即将关机";
				warningType = "";
				break;
			case CMD_STATUS_REPORT:// ■上报设备状态
				isValid = SmokeDetectorValidStatus.INSTALL.getStatusCode();
				signalType = "上报设备状态";
				warningType = "";
				break;*/
            default:
                logger.error("未识别的命令:[" + cmdType + "]");
        }
        List<String> list = new ArrayList<>();
        list.add(isValid);
        list.add(signalType);
        list.add(warningType);
        return list;
    }

    /**
     * 发送 短信  和  微信
     * @param device
     */
    /*private static void sendMsg(DeviceBaseInfoModel device, String warningType, String signalType) {
        String msgTo = "";
        String address = "";
        if(StringUtils.isBlank(device.getMcuIDHex()) && StringUtils.isBlank(device.getDevEUI())) {
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
        }
    }*/


}
