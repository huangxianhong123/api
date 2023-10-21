package com.liuc.server.api.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.comm.ResponseMessage;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.liuc.server.api.common.C;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by huangxiaoliang on 2017/7/10.
 */
@Slf4j
public class OSSUtil {

    public static final int FILE_TYPE_PIC = 1;
    public static final int FILE_TYPE_OTHER = 2;

    // 用户相关文件夹
    public static final String ROOT_PATH = C.ALI_OSS_FILE_ROOT_PATH;
    public static final String OSS_TYPE_PIC = ROOT_PATH + "pic/";
    public static final String OSS_TYPE_OTHER = ROOT_PATH + "other/";

    private static OSS ossClientNet = new OSSClientBuilder().build(C.getAliOSSEndPointNet(), C.getAliOSSAccessKeyID(), C.getAliOSSAccessKeySecret());
    private static OSS ossClientPri = new OSSClientBuilder().build(C.getAliOSSEndPointPri(), C.getAliOSSAccessKeyID(), C.getAliOSSAccessKeySecret());


    public static String getOSSObjectKeyPrefix(int type) {
        switch (type) {
            case FILE_TYPE_PIC:
                return OSS_TYPE_PIC;
            case FILE_TYPE_OTHER:
                return OSS_TYPE_OTHER;
            default:
                return "";
        }
    }

    public static boolean uploadFile(String ossObjectKey, byte[] file) throws IOException {

        if (C.isInit) {
            PutObjectResult putObjectResult = null;
            if (C.DEBUG) {
                putObjectResult = ossClientNet.putObject(new PutObjectRequest(C.getAliOSSBucketNamePublic(), ossObjectKey, new ByteArrayInputStream(file)));
            } else {
                putObjectResult = ossClientPri.putObject(new PutObjectRequest(C.getAliOSSBucketNamePublic(), ossObjectKey, new ByteArrayInputStream(file)));
            }
            log.error("putObjectResult" + putObjectResult);
            if (putObjectResult != null) {
                ResponseMessage responseMessage = putObjectResult.getResponse();
                if (responseMessage != null) {
                    log.error(responseMessage.getErrorResponseAsString());
                }
                return true;
            }
        }
        return false;
    }

    public static boolean uploadFile(String ossObjectKey, File file) throws IOException {
        if (file == null) {
            return false;
        }
        if (C.isInit) {
            PutObjectResult putObjectResult = null;
            if (C.DEBUG) {
                putObjectResult = ossClientNet.putObject(new PutObjectRequest(C.getAliOSSBucketNamePublic(), ossObjectKey, new FileInputStream(file)));
            } else {
                putObjectResult = ossClientPri.putObject(new PutObjectRequest(C.getAliOSSBucketNamePublic(), ossObjectKey, new FileInputStream(file)));
            }
            log.error("putObjectResult" + putObjectResult);
            if (putObjectResult != null) {
                ResponseMessage responseMessage = putObjectResult.getResponse();
                if (responseMessage != null) {
                    log.error(responseMessage.getErrorResponseAsString());
                }
                return true;
            }
        }
        return false;
    }

}
