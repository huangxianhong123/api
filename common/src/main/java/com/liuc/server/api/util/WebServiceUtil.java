package com.liuc.server.api.util;

import com.alibaba.fastjson.JSONObject;
import com.liuc.server.api.common.C;
import lombok.extern.slf4j.Slf4j;
import org.apache.axis.client.Service;
import org.apache.axis.client.Call;
import javax.xml.rpc.ServiceException;
import org.apache.axis.encoding.XMLType;
import org.springframework.beans.factory.annotation.Value;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import java.io.IOException;
import java.net.URL;

@org.springframework.stereotype.Service
@Slf4j
public class WebServiceUtil {

    @Value("${spring.profiles.active}")
    private String string;


    public String addPass(String beanList) throws ClassNotFoundException, ServiceException {
        String result = "";
        try {

            String s = "";
            if ("loc".equals(string)) {
                s = C.INSERT_TRAVLLING_ADD_URL_DEV;
            } else if ("dev".equals(string)){
                s = "http://192.168.50.128:1903/uapws/service/IJkzbInerstService";
            }else if("gray".equals(string)){
                s = C.INSERT_TRAVLLING_ADD_URL_PRD;
            }else{
                s =C.INSERT_TRAVLLING_ADD_URL_PRD;
            }
            //String wsdlURL = C.getNCPushUrlHost()+"/uapws/service/IInsertPurchaseinService";
            String namespace = "http://bxbll.erm.itf.nc/IJkzbInerstService";
            String operationName ="insertJkzb";
            String parameterName="string";
            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(new URL(s));
            call.setUseSOAPAction(true);
            call.setSOAPActionURI(namespace + operationName);
            call.setOperationName(new QName(namespace, operationName));
            call.addParameter(parameterName, XMLType.XSD_STRING, ParameterMode.IN);
            call.setEncodingStyle("UTF-8");
            call.setReturnType(XMLType.XSD_STRING);
            log.info("传入nc参数:---------------------------------------------------");
            log.info(beanList);
            result = (String) call.invoke(new Object[] {beanList});
            log.info("nc返回结果:---------------------------------------------------");
            log.info(result);
        }  catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


}
