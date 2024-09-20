package com.xblteam.lease.common.utils;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;

import java.util.Random;

public class AliyunSmsUtil {

    public static void sendcode(Client client,String phone, String code) {
        //创建请求
        SendSmsRequest sendSmsRequest = new SendSmsRequest();
        sendSmsRequest.setPhoneNumbers(phone);                          //设置手机号
        sendSmsRequest.setSignName("阿里云短信测试");                      //设置签名
        sendSmsRequest.setTemplateCode("SMS_154950909");                //设置模板编码
        sendSmsRequest.setTemplateParam("{\"code\":\"" + code + "\"}"); //传入验证码

        //发送请求
        try {
            client.sendSms(sendSmsRequest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getRandomCode(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
