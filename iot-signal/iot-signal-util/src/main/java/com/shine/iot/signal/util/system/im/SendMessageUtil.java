package com.shine.iot.signal.util.system.im;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 发送短信工具类
 *
 * @author JAVA1
 */
public class SendMessageUtil {
    //创建当前类的日志对象
    private static Logger log = LogManager.getLogger(SendMessageUtil.class);

    //定义变量
    private static String messageURL;
    private static String paramFormat;

    //生成静态属性的set方法
    @Value("${messageURL}")
    public void setMessageURL(String messageURL) {
        SendMessageUtil.messageURL = messageURL;
    }

    @Value("${paramFormat}")
    public void setParamFormat(String paramFormat) {
        SendMessageUtil.paramFormat = paramFormat;
    }

    /**
     * 短信消息发送
     *
     * @param allPhone     (将发送消息的手机号)
     * @param templateData (组装好的模板数据)
     * @return
     * @throws IOException
     */
    public static String sendMessage(String templateData, String allPhone) throws IOException {
        //定义变量
        String result = "";

        //直接发送短信
        result = httpRequest(templateData, allPhone);

        //返回
        return result;
    }


    /**
     * 发起http请求
     *
     * @throws IOException
     */
    public static String httpRequest(String templateData, String allPhone) throws IOException {
        //定义变量
        String result = "";
        log.debug("paramFormat " + paramFormat + " messageURL  " + messageURL);

        //得到模板接口路径及参数设置
        String requestURL = String.format(paramFormat, messageURL, templateData, allPhone, "", "");

        //打印输出
        log.info("requestURL为：" + requestURL);

        //创建url对象
        URL url = new URL(requestURL);

        //打开链接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        //设置请求方式
        connection.setRequestMethod("POST");
        //发送短信
        InputStream is = url.openStream();
        //转换返回值
        String returnStr = convertStreamToString(is);

        //分割响应内容字符串
        String[] str = returnStr.split(",");

        //调用方法判断当前响应状态
        result = judgeSendStatus(str[0]);

        //打印输出返回结果
        log.info("接口响应返回结果为：" + returnStr);

        //返回
        return result;
    }


    /**
     * 转换返回值类型为UTF-8格式.
     *
     * @param is
     * @return
     */
    public static String convertStreamToString(InputStream is) {
        //创建Stringbuilder对象
        StringBuilder sb1 = new StringBuilder();
        //定义byte数组
        byte[] bytes = new byte[4096];
        //定义变量(用于接收存放每次读取的数据大小)
        int size = 0;

        //捕获并处理异常
        try {
            //while循环
            while ((size = is.read(bytes)) > 0) {
                //读取定长的数据大小
                String str = new String(bytes, 0, size, "UTF-8");
                //追加至StringBuilder
                sb1.append(str);
            }
        } catch (IOException e) {
            //打印输出
            log.info("IOException：" + e.getMessage(), e);
        } finally {
            //捕获并处理异常
            try {
                //释放资源
                is.close();
            } catch (IOException e) {
                //打印输出
                log.info("IOException：" + e.getMessage(), e);
            }
        }
        //返回
        return sb1.toString();
    }


    /**
     * 根据响应结果判断当前短信发送状态
     */
    public static String judgeSendStatus(String status) {
        //定义字符串变量
        String result = "";

        //判断当前状态
        switch (status) {
            case "0":
                result = "短信发送成功！";
                break;
            case "1":
                result = "含有敏感词汇！";
                break;
            case "2":
                result = "余额不足！";
                break;
            case "3":
                result = "没有号码！";
                break;
            case "4":
                result = "包含sql语句！";
                break;
            case "10":
                result = "账号不存在！";
                break;
            case "11":
                result = "账号注销！";
                break;
            case "12":
                result = "账号停用！";
                break;
            case "13":
                result = "IP鉴权失败！";
                break;
            case "14":
                result = "格式错误！";
                break;
            case "-1":
                result = "系统异常！";
                break;
            default:
                log.info("没有当前响应状态！");
                break;
        }

        //返回
        return result;
    }
}



















