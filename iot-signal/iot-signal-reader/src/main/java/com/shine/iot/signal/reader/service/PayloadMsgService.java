package com.shine.iot.signal.reader.service;

import com.alibaba.fastjson.JSONObject;
import com.shine.iot.signal.model.adapter.aug.NodeDataAdapter;
import com.shine.iot.signal.model.dto.aug.NodeDataDto;
import com.shine.iot.signal.reader.feignClient.PayloadSignalOPFeignClient;
import com.shine.iot.signal.util.device.constants.enums.SignalPayloadVersion;
import com.shine.iot.signal.util.device.constants.enums.SignalProductType;
import com.shine.iot.signal.util.parser.aug.tlv.payload.IDevicePayloadParser;
import com.shine.iot.signal.util.parser.aug.tlv.payload.factory.SmokeParserFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Calendar;

@Component
public class PayloadMsgService {
    private static Logger logger = LogManager.getLogger(PayloadMsgService.class);

    //注入FeignClientService
    private PayloadSignalOPFeignClient _payloadSignalOPFeignClient;

    @Autowired
    private void setPayloadSignalOPFeignClient(PayloadSignalOPFeignClient payloadSignalOPFeignClient) {
        this._payloadSignalOPFeignClient = payloadSignalOPFeignClient;
    }

    //构建静态属性
    private static PayloadSignalOPFeignClient payloadSignalOPFeignClient;

    @PostConstruct
    public void init() {
        payloadSignalOPFeignClient = this._payloadSignalOPFeignClient;
    }

    /**
     * 获取payload数据解析器
     *
     * @param deviceType 设备类型
     * @param version    软件版本
     * @return 解析器
     */
    public static IDevicePayloadParser getSmokeParser(Integer deviceType, Integer version) {
        IDevicePayloadParser smokeParser = new SmokeParserFactory().getV1ParserInstance();
        if (deviceType != null && version != null) {
            switch (SignalProductType.getEumnTypeByKey(deviceType)) {
                case SMOKE_DETECTOR_LORA:
                    SmokeParserFactory smokeFactory = new SmokeParserFactory();
                    if (version == SignalPayloadVersion.VERSION_0.getCode()) {
                        smokeParser = smokeFactory.getV0ParserInstance();
                    } else if (version == SignalPayloadVersion.VERSION_1.getCode()) {
                        smokeParser = smokeFactory.getV1ParserInstance();
                    } else {
                        smokeParser = smokeFactory.getV2ParserInstance();
                    }
                    break;
                case SMOKE_DETECTOR_NB:
                    //NB烟感的解析器获取
                    break;
                default:
                    smokeParser = new SmokeParserFactory().getV1ParserInstance();
                    break;
            }
            return smokeParser;
        }
        return smokeParser;
    }

    /**
     * 存储 payload 原始数据
     * 要过滤 重复帧号的payload数据
     * TODO
     *
     * @param nodeDataDto
     */
    public static void saveRawPayloadData(NodeDataDto nodeDataDto) {
        /*String convertJsonStr = convertJSON(nodeDataDto);
        logger.info("统一通用的json数据格式为：" + convertJsonStr);*/
        NodeDataAdapter nodeDataAdapter = nodeDataDto.getNodeDataAdapter();
        String macAddress = nodeDataDto.getGwId();
        String gwIP = nodeDataDto.getGwIP();
        Integer gwPort = nodeDataDto.getGwPort();

    }

    /**
     * 将payload数据转换为平台系统通用的统一json格式的数据
     *
     * @param nodeDataDto 节点信号传输的DTO
     * @return 通用json字符串信息
     */
    public static String convertJSON(NodeDataDto nodeDataDto) {
        //节点信号数据
        NodeDataAdapter nodeDataAdapter = nodeDataDto.getNodeDataAdapter();
        //节点信号转换为平台统一的属性
        FieldConvertUtil fieldConvert = new FieldConvertUtil();
        String field = fieldConvert.fieldTypeConvert(nodeDataAdapter.getIndustryType()); // 行业类型
        String productType = fieldConvert.deviceTypeConvert(nodeDataAdapter.getDeviceType()); // 产品类型
        String modelType = fieldConvert.deviceModelConvert(nodeDataAdapter.getProductModel()); // 产品型号
        String protocol = fieldConvert.deviceProtocolConvert(nodeDataAdapter.getProtocol()); // payload 数据的协议
        String status = fieldConvert.statusCmdConvert(nodeDataAdapter.getSignalType()).get(0); // 设备当前工作状态
        String command = fieldConvert.statusCmdConvert(nodeDataAdapter.getSignalType()).get(1); // 设备控制指令
        //将转换后的属性设置到节点信息中
        nodeDataAdapter.setIndustryType(field);
        nodeDataAdapter.setProductModel(modelType);
        nodeDataAdapter.setDeviceType(productType);
        nodeDataAdapter.setProtocol(protocol);
        nodeDataAdapter.setCmdType(command);
        nodeDataAdapter.setSignalType(status);

        //组装Json数据
        JSONObject rootJson = new JSONObject();
        // 行业类型
        rootJson.put("field", field);
        // 设备（产品）类型
        rootJson.put("product", productType);
        // 产品型号
        rootJson.put("model", modelType);
        // TLV-Payload数据的协议有多种
        rootJson.put("protocol", protocol);
        // 网关信息
        rootJson.put("gwid", null); //网关ID，即MAC地址
        rootJson.put("ipFrom", nodeDataDto.getGwIP());
        rootJson.put("portFrom", nodeDataDto.getGwPort());
        rootJson.put("rcvTime", nodeDataDto.getRcvTime());
        //payload数据信号
        rootJson.put("direction", true); // true：上行；false：下行
        rootJson.put("upFrameCount", nodeDataAdapter.getLoraFrameCounter());
        rootJson.put("downFrameCount", null);
        rootJson.put("isAck", nodeDataAdapter.isAck());  // true：是ack；false：否
        rootJson.put("needAck", nodeDataAdapter.isNeedAck());
        rootJson.put("swversion", nodeDataAdapter.getLorawanFCtrl()); // 1.0
        rootJson.put("deviceSN", nodeDataAdapter.getMcuIDHex()); //此处存储MCUID
        rootJson.put("status", status); //当前状态
        rootJson.put("value", null); //状态值
        rootJson.put("command", command); //控制指令
        rootJson.put("content", nodeDataAdapter.getAppendixData()); //十六进制字符串，控制指令对应的内容
        rootJson.put("timeToSend", null); //服务器须向设备发送数据的时间
        rootJson.put("imei", nodeDataDto.getNb_imei()); //IMEI（NB设备专有的）
        rootJson.put("imsi", nodeDataDto.getNb_imsi()); //SIM卡号（NB设备专有的）
        rootJson.put("iccid", nodeDataDto.getNb_iccid()); //ICCID号码（NB设备专有的）
        rootJson.put("initialData", nodeDataDto.getRawPayload()); //payload原始数据
        //设置NodeDataAdapter节点信息对象（即DeviceAllMsg信息表）
        rootJson.put("jsonData", nodeDataAdapter);
        return rootJson.toJSONString();
    }

    /**
     * 处理并保存统一格式的JSON数据
     * 功能有：
     * 1.保存allMsg信息
     *
     * @param nodeDataDto
     */
    public static void dealGeneralJsonData(NodeDataDto nodeDataDto) {
        String convertJsonStr = convertJSON(nodeDataDto);
        logger.info("统一通用的json数据格式为：" + convertJsonStr);
        Boolean dealResult = payloadSignalOPFeignClient.dealGeneralJsonData(convertJsonStr);
        logger.info("保存json数据的结果：" + dealResult);

    }


    public static void saveAllMsgInfo(NodeDataDto nodeDataDto) {
        FieldConvertUtil fieldConvert = new FieldConvertUtil();
        nodeDataDto.setRcvTime(Calendar.getInstance().getTime());
        NodeDataAdapter nodeDataAdapter = nodeDataDto.getNodeDataAdapter();
        nodeDataAdapter.getCmdType();
//        String status = fieldConvert.statusCmdConvert(nodeDataAdapter.getSignalType()).get(0);
        String command = fieldConvert.statusCmdConvert(nodeDataAdapter.getSignalType()).get(1);
        nodeDataAdapter.setCmdType(command);

        Boolean saveResult = payloadSignalOPFeignClient.savePayloadSignal(nodeDataDto);
        logger.info("payload的allMsg信息保存：" + saveResult);

    }


    /**
     * 存储ACK消息
     *
     * @param IP
     * @param port
     * @param ackHexStr
     */
    public static void saveACKMsg(String IP, int port, String ackHexStr) {

    }

}
