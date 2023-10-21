package com.liuc.server.api.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendBatchSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendBatchSmsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.liuc.server.api.common.C;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

@Slf4j
public class AliyunSMSUtil {

    /**
     * 发送登录短信验证码
     *
     * @param phone
     * @param code
     * @return
     */
    public static String sendSMSLogin(String phone, String code) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        return sendSMS(phone, C.SMS_TEMPLATE_CODE_LOGIN, jsonObject.toString());
    }

    private static String sendSMS(String phone, String templateId, String params) {
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        final String product = "Dysmsapi";
        final String domain = "dysmsapi.aliyuncs.com";
        final String accessKeyId = C.ALIYUN_ACCESS_KEY_ID;
        final String accessKeySecret = C.ALIUIN_ACCESS_KEY_SECRET;
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
                accessKeySecret);
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);
            SendSmsRequest request = new SendSmsRequest();
            request.setMethod(MethodType.POST);
            request.setPhoneNumbers(phone);
            request.setSignName(C.ALIYUN_SMS_SIGN_NAME);
            request.setTemplateCode(templateId);
            request.setTemplateParam(params);
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
                log.error("sendSMS phone: " + phone + " bizid:" + sendSmsResponse.getBizId());
                return sendSmsResponse.getBizId();
            }
        } catch (ClientException e) {
            log.error("phone:" + phone + " error code:" + e.getErrCode() + " msg:" + e.getErrMsg());
        }
        return "";
    }

    public static String sendSMSGroup(String phoneJson, String templateId, String paramsJson, String signJson) {
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        final String product = "Dysmsapi";
        final String domain = "dysmsapi.aliyuncs.com";
        final String accessKeyId = C.ALIYUN_ACCESS_KEY_ID;
        final String accessKeySecret = C.ALIUIN_ACCESS_KEY_SECRET;
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
                accessKeySecret);
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);
            SendBatchSmsRequest request = new SendBatchSmsRequest();
            request.setMethod(MethodType.POST);
            request.setPhoneNumberJson(phoneJson);
            request.setSignNameJson(signJson);
            request.setTemplateCode(templateId);
            request.setTemplateParamJson(paramsJson);
            SendBatchSmsResponse sendBatchSmsResponse = acsClient.getAcsResponse(request);
            if (sendBatchSmsResponse.getCode() != null && sendBatchSmsResponse.getCode().equals("OK")) {
                log.error("sendSMSGroup bizid:" + sendBatchSmsResponse.getBizId());
                return sendBatchSmsResponse.getBizId();
            }
            log.error("sendSMSGroup result:" + sendBatchSmsResponse.getCode() + " msg:" + sendBatchSmsResponse.getMessage());
        } catch (ClientException e) {
            log.error("sendSMSGroup error code:" + e.getErrCode() + " msg:" + e.getErrMsg());
        }
        return "";
    }


}
