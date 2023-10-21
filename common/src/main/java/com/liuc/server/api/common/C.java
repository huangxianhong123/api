package com.liuc.server.api.common;

import com.liuc.server.api.bean.app.SUserInfo;
import com.liuc.server.api.bean.app.SUserInfoTemp;
import org.apache.commons.collections.map.HashedMap;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by jclang on 2017/6/2.
 */
public class C {

    //当前环境
    public static boolean DEBUG = true;
    public static boolean isInit = false;

    //接口返回码
    public static Map<Integer, String> map = new LinkedHashMap<>();
    public static String codeStr = "";
    //APP_KEY
    public static final String SUPER_KEY = "1"; // 超级key，用于测试环境绕过接口基础权限检查
    public static final String APP_APPKEY_WEB_BACK = "67167560c2424f2c92kxkw3c0af958hj";

    //APP_SECRET
    public static final String APP_SECRET_WEB_BACK = "61ecf6012ff44c79aacw72jddfd96f68";

    //服务器
    public static final String SMS_TEMPLATE_CODE_LOGIN = ""; //短信登录验证码
    public static final String ALIYUN_ACCESS_KEY_ID = "";
    public static final String ALIUIN_ACCESS_KEY_SECRET = "";
    public static final String ALIYUN_SMS_SIGN_NAME = "";

    // API请求头字段
    public static final String API_HEADER_PARAM_APPKEY = "LC-Appkey";
    public static final String API_HEADER_PARAM_TIMESTAMP = "LC-Timestamp";
    public static final String API_HEADER_PARAM_SIGN = "LC-Sign";
    public static final String API_HEADER_PARAM_SESSION = "LC-Session";
    //JWT
    private static final String API_JWT_SECRET_DEBUG = "436018sajd76a71jskkvlmrs8w3jj0e2";
    private static final String API_JWT_SECRET_PRD = "436018sajd76a293i1oskwlrs8w3jj02";

    public static final String ALI_OSS_FILE_ROOT_PATH = "yongyou_jishi/";

    // 服务器文件上传本地保存路径
    public static final String MEDIA_UPLOAD_LOCATION_ROOT = "./upload/";
    private static final String MEDIA_UPLOAD_LOCATION_ROOT_DEBUG = "/home/gzlc/workspaces/data/app/yongyou_shuidian/"; // 测试上传文件夹根目录
    private static final String MEDIA_UPLOAD_LOCATION_ROOT_PRD = "/home/lc/work_publish/yongyou_jishi/upload/";// 正式环境上传文件目录
    public static final String MEDIA_UPLOAD_LOCATION_PIC = "pic/"; // 上传图片文件夹
    public static final String MEDIA_UPLOAD_LOCATION_OSS_PIC = "oss_pic/"; // 上传图片文件夹
    public static final String MEDIA_UPLOAD_LOCATION_OTHER = "other/"; // 上传附件文件夹
    public static final String MEDIA_UPLOAD_LOCATION_LOCAL = "local/"; // 上传本地文件夹

    public static final String DOWNLOAD_FILE_PATH_LOC = "I:\\yongyou_shuidian\\upload\\pic\\";
    public static final String DOWNLOAD_FILE_RESOURCE_LOC = "file:I:\\yongyou_shuidian\\upload\\";
    public static final String DOWNLOAD_FILE_PATH_DEV = "/home/gzlc/workspaces/data/app/yongyou_shuidian/upload/pic/";
    public static final String DOWNLOAD_FILE_RESOURCE_DEV = "file:////home/gzlc/workspaces/data/app/yongyou_shuidian/upload/";
    public static final String DOWNLOAD_FILE_PATH_GRAY = "F:\\yongyou_shuidian\\upload\\pic";
    public static final String DOWNLOAD_FILE_RESOURCE_GRAY = "file:F:\\yongyou_shuidian\\upload\\";
    public static final String DOWNLOAD_FILE_PATH_PRD = "F:\\yongyou_shuidian\\upload\\pic";
    public static final String DOWNLOAD_FILE_RESOURCE_PRD = "file:F:\\yongyou_shuidian\\upload\\";

    // 阿里云OSS(test)
    private static final String ALIOSS_BUCKET_HOST_DEBUG = "https://test-guanbo.oss-cn-shenzhen.aliyuncs.com/";
    private static final String ALIOSS_BUCKETNAME_DEBUG = "test-guanbo";
    private static final String ALIOSS_ENDPOINT_NET_DEBUG = "https://oss-cn-shenzhen.aliyuncs.com";
    private static final String ALIOSS_ENDPOINT_PRI_DEBUG = "https://oss-cn-shenzhen-internal.aliyuncs.com";
    private static final String ALIOSS_ACCESSKEYID_DEBUG = "LTAIhpS9vhNPpCKx";
    private static final String ALIOSS_ACCESSKEYSECRET_DEBUG = "ENxLddn6hK0HNh8ajsPQ16iYn1P0Zg";
    // 阿里云OSS(prd)
    private static final String ALIOSS_BUCKET_HOST = "";
    private static final String ALIOSS_BUCKETNAME = "";
    private static final String ALIOSS_ENDPOINT_NET = "";
    private static final String ALIOSS_ENDPOINT_PRI = "";
    private static final String ALIOSS_ACCESSKEYID = "";
    private static final String ALIOSS_ACCESSKEYSECRET = "";

    //redis key
    public static class RedisPrefix {

        public static final String YOU_SPACE_ACCESS_TOKEN = "you-space-access-token-jishi"; // 友空间
        public static final String YOU_SPACE_ACCESS_TOKEN_EXPIRE = "you-space-access-token-jishi-expire"; // 友空间
        public static final String SESSION_WEB_YONGYOU_SHUIDIAN = "yongyou_session_yongyou_shuidian_";
        public static final String LOCK_KEY = "spring-cloud-lock-yongyoushuidian-finance"; //

    }


    public static final String YOU_SPACE_ACCESS_TOKEN_URL = "https://open.yonyoucloud.com/open-auth/selfAppAuth/getAccessToken";
    public static final String YOU_SPACE_BASE_INFO_URL = "https://api.yonyoucloud.com/open/diwork/freelogin/base_info_by_code"; // 获取用户id
    public static final String YOU_SPACE_USER_INFO_URL = "https://api.yonyoucloud.com/open/diwork/users/list_by_ids"; // 查询用户信息
    public static final String YOU_SPACE_NC_INFO_URL = "https://api.yonyoucloud.com/open/diwork/freelogin/nc_info_by_code"; // 根据code获取nc用户信息
    public static final String YOU_SPACE_APP_INFO_URL = "https://api.yonyoucloud.com/open/diwork/app/info_by_app_code"; // 根据appcode获取应用信息
    public static final String YOU_SPACE_JS_TICKET_URL = "https://api.yonyoucloud.com/open/diwork/jsbridge/jsticket"; // 获取jsTicket
    public static final String YOU_SPACE_INFO_BY_USER_ID = "https://api.diwork.com/yonbip/uspace/staff/info_by_user_id"; // 根据用空间用户id获取员工信息
    public static final String YOU_SPACE_CHILD_BY_USER_IDS = "https://api.diwork.com/yonbip/uspace/staff/child_by_user_ids"; // 通过友户通用户Id获取下级员工信息

    public static final String YOU_SPACE_JS_TICKET_URL1 = "https://api.diwork.com/yonbip/uspace/jsbridge/jsticket"; //
    public static final String YOU_SPACE_BASE_INFO_URL1 = "https://api.diwork.com/yonbip/uspace/freelogin/base_info_by_code";
    public static final String YOU_SPACE_ACCESS_TOKEN_URL1 = "https://api.diwork.com/open-auth/selfAppAuth/getAccessToken";

    public static final String INSERT_APPLY_COST_ADD_URL_DEV = "http://192.168.50.128:1903/uapws/rest/ermrest/insertbxzb";
    public static final String INSERT_APPLY_COST_ADD_URL_PRD = "http://192.168.0.238:1903/uapws/rest/ermrest/insertbxzb";
    public static final String INSERT_REPAYMENT_ADD_URL_DEV = "http://192.168.50.128:1903/uapws/rest/ermrest/inserthkzb";
    public static final String INSERT_REPAYMENT_ADD_URL_PRD = "http://192.168.0.238:1903/uapws/rest/ermrest/inserthkzb";
    public static final String INSERT_TRAVLLING_ADD_URL_DEV = "http://192.168.50.128:1903/uapws/rest/ermrest/insertjkzb";
    public static final String INSERT_TRAVLLING_ADD_URL_PRD = "http://192.168.0.238:1903/uapws/rest/ermrest/insertjkzb";

    public static final String SUBMIT_APPLY_COST_ADD_URL_DEV = "http://192.168.50.128:1903/uapws/rest/ermrest/submitbxzb";
    public static final String SUBMIT_APPLY_COST_ADD_URL_PRD = "http://192.168.0.238:1903/uapws/rest/ermrest/submitbxzb";
    public static final String SUBMIT_REPAYMENT_ADD_URL_DEV = "http://192.168.50.128:1903/uapws/rest/ermrest/submithkzb";
    public static final String SUBMIT_REPAYMENT_ADD_URL_PRD = "http://192.168.0.238:1903/uapws/rest/ermrest/submithkzb";
    public static final String SUBMIT_TRAVLLING_ADD_URL_DEV = "http://192.168.50.128:1903/uapws/rest/ermrest/submitjkzb";
    public static final String SUBMIT_TRAVLLING_ADD_URL_PRD = "http://192.168.0.238:1903/uapws/rest/ermrest/submitjkzb";

    public static final String DELETE_APPLY_COST_ADD_URL_DEV = "http://192.168.50.128:1903/uapws/rest/ermrest/deletebxzb";
    public static final String DELETE_APPLY_COST_ADD_URL_PRD = "http://192.168.0.238:1903/uapws/rest/ermrest/deletebxzb";
    public static final String DELETE_REPAYMENT_ADD_URL_DEV = "http://192.168.50.128:1903/uapws/rest/ermrest/deletehkzb";
    public static final String DELETE_REPAYMENT_ADD_URL_PRD = "http://192.168.0.238:1903/uapws/rest/ermrest/deletehkzb";
    public static final String DELETE_TRAVLLING_ADD_URL_DEV = "http://192.168.50.128:1903/uapws/rest/ermrest/deletejkzb";
    public static final String DELETE_TRAVLLING_ADD_URL_PRD = "http://192.168.0.238:1903/uapws/rest/ermrest/deletejkzb";


    public static final String UPDATE_APPLY_COST_ADD_URL_DEV = "http://192.168.50.128:1903/uapws/rest/ermrest/updatebxzb";
    public static final String UPDATE_APPLY_COST_ADD_URL_PRD = "http://192.168.0.238:1903/uapws/rest/ermrest/updatebxzb";
    public static final String UPDATE_REPAYMENT_ADD_URL_DEV = "http://192.168.50.128:1903/uapws/rest/ermrest/updatehkzb";
    public static final String UPDATE_REPAYMENT_ADD_URL_PRD = "http://192.168.0.238:1903/uapws/rest/ermrest/updatehkzb";
    public static final String UPDATE_TRAVLLING_ADD_URL_DEV = "http://192.168.50.128:1903/uapws/rest/ermrest/updatejkzb";
    public static final String UPDATE_TRAVLLING_ADD_URL_PRD = "http://192.168.0.238:1903/uapws/rest/ermrest/updatejkzb";

    public static final String INSERT_UPLOAD_FILE_URL_DEV = "http://192.168.50.128:1903/uapws/rest/ermrest/insertfile";
    public static final String INSERT_UPLOAD_FILE_URL_PRD = "http://192.168.0.238:1903/uapws/rest/ermrest/insertfile";
    public static final String DELETE_UPLOAD_FILE_URL_DEV = "http://192.168.50.128:1903/uapws/rest/ermrest/deletefile";
    public static final String DELETE_UPLOAD_FILE_URL_PRD = "http://192.168.0.238:1903/uapws/rest/ermrest/deletefile";


    public static final String WORK_TRAVLLING_URL_DEV = "http://192.168.50.128:1903/uapws/rest/ermrest/workjkzb";
    public static final String WORK_TRAVLLING_URL_PRD = "http://192.168.0.238:1903/uapws/rest/ermrest/workjkzb";

    public static final String WORK_APPLY_URL_DEV = "http://192.168.50.128:1903/uapws/rest/ermrest/workbxzb";
    public static final String WORK_APPLY_URL_PRD = "http://192.168.0.238:1903/uapws/rest/ermrest/workbxzb";

    public static final String WORK_REPAYMENT_URL_DEV = "http://192.168.50.128:1903/uapws/rest/ermrest/workhkzb";
    public static final String WORK_REPAYMENT_URL_PRD = "http://192.168.0.238:1903/uapws/rest/ermrest/workhkzb";

    public static final String APPROVE_TRAVLLING_URL_DEV ="http://192.168.50.128:1903/uapws/rest/ermrest/approvejkzb";
    public static final String APPROVE_TRAVLLING_URL_PRD ="http://192.168.0.238:1903/uapws/rest/ermrest/approvejkzb";

    public static final String APPROVE_APPLY_URL_DEV ="http://192.168.50.128:1903/uapws/rest/ermrest/approvebxzb";
    public static final String APPROVE_APPLY_URL_PRD ="http://192.168.0.238:1903/uapws/rest/ermrest/approvebxzb";

    public static final String APPROVE_REPAYMENT_URL_DEV ="http://192.168.50.128:1903/uapws/rest/ermrest/approvehkzb";
    public static final String APPROVE_REPAYMENT_URL_PRD ="http://192.168.0.238:1903/uapws/rest/ermrest/approvehkzb";


    public static final String REJECT_TRAVLLING_URL_DEV ="http://192.168.50.128:1903/uapws/rest/ermrest/unapprovejkzb";
    public static final String REJECT_TRAVLLING_URL_PRD ="http://192.168.0.238:1903/uapws/rest/ermrest/unapprovejkzb";

    public static final String REJECT_APPLY_URL_DEV ="http://192.168.50.128:1903/uapws/rest/ermrest/unapprovebxzb";
    public static final String REJECT_APPLY_URL_PRD ="http://192.168.0.238:1903/uapws/rest/ermrest/unapprovebxzb";

    public static final String REJECT_REPAYMENT_URL_DEV ="http://192.168.50.128:1903/uapws/rest/ermrest/unapprovehkzb";
    public static final String REJECT_REPAYMENT_URL_PRD ="http://192.168.0.238:1903/uapws/rest/ermrest/unapprovehkzb";

    public static final String BACK_TRAVLLING_URL_DEV ="http://192.168.50.128:1903/uapws/rest/ermrest/backjkzb";
    public static final String BACK_TRAVLLING_URL_PRD ="http://192.168.0.238:1903/uapws/rest/ermrest/backjkzb";

    public static final String BACK_APPLY_URL_DEV ="http://192.168.50.128:1903/uapws/rest/ermrest/backbxzb";
    public static final String BACK_APPLY_URL_PRD ="http://192.168.0.238:1903/uapws/rest/ermrest/backbxzb";

    public static final String BACK_REPAYMENT_URL_DEV ="http://192.168.50.128:1903/uapws/rest/ermrest/backhkzb";
    public static final String BACK_REPAYMENT_URL_PRD ="http://192.168.0.238:1903/uapws/rest/ermrest/backhkzb";


    public static final String CHECK_APPLY_URL_DEV ="http://192.168.50.128:1903/uapws/rest/ermrest/checkbxzb";
    public static final String CHECK_APPLY_URL_PRD ="http://192.168.0.238:1903/uapws/rest/ermrest/checkbxzb";


    public static final String XPDL_TRAVLLING_URL_DEV ="http://192.168.50.128:1903/uapws/rest/ermrest/xpdljkzb";
    public static final String XPDL_TRAVLLING_URL_PRD ="http://192.168.0.238:1903/uapws/rest/ermrest/xpdljkzb";

    public static final String XPDL_APPLY_URL_DEV ="http://192.168.50.128:1903/uapws/rest/ermrest/xpdlbxzb";
    public static final String XPDL_APPLY_URL_PRD ="http://192.168.0.238:1903/uapws/rest/ermrest/xpdlbxzb";

    public static final String XPDL_REPAYMENT_URL_DEV ="http://192.168.50.128:1903/uapws/rest/ermrest/xpdlhkzb";
    public static final String XPDL_REPAYMENT_URL_PRD ="http://192.168.0.238:1903/uapws/rest/ermrest/xpdlhkzb";


    public static final String ASSIGN_TRAVLLING_URL_DEV ="http://192.168.50.128:1903/uapws/rest/ermrest/assignjkzb";
    public static final String ASSIGN_TRAVLLING_URL_PRD ="http://192.168.0.238:1903/uapws/rest/ermrest/assignjkzb";

    public static final String ASSIGN_APPLY_URL_DEV ="http://192.168.50.128:1903/uapws/rest/ermrest/assignbxzb";
    public static final String ASSIGN_APPLY_URL_PRD ="http://192.168.0.238:1903/uapws/rest/ermrest/assignbxzb";

    public static final String ASSIGN_REPAYMENT_URL_DEV ="http://192.168.50.128:1903/uapws/rest/ermrest/assignhkzb";
    public static final String ASSIGN_REPAYMENT_URL_PRD ="http://192.168.0.238:1903/uapws/rest/ermrest/assignhkzb";

    public static final String UNAUDIT_URL_DEV ="http://192.168.50.128:1903/uapws/rest/ermrest/unauditjkbx";
    public static final String UNAUDIT_URL_PRD ="http://192.168.0.238:1903/uapws/rest/ermrest/unauditjkbx";

    public static final String QUERYFILE_URL_DEV ="http://192.168.50.128:1903/uapws/rest/ermrest/queryfile";
    public static final String QUERYFILE_URL_PRD ="http://192.168.0.238:1903/uapws/rest/ermrest/queryfile";


    private static final String YOU_SPACE_APPKEY_DEBUG = "2ddc330e18a045f7a2c6f597af0e5581";
    private static final String YOU_SPACE_APPKEY_PRD = "067989fe48a44cfa99c8775ecf187f77";

    public static String getYouSpaceAppkey() {
        if (DEBUG) {
            return YOU_SPACE_APPKEY_DEBUG;
        }
        return YOU_SPACE_APPKEY_PRD;
    }


    private static final String YOU_SPACE_APPSECRET_DEBUG = "13d5c46f49ea4736aefe681f61de5a93";
    private static final String YOU_SPACE_APPSECRET_PRD = "4d9bfdc0aac24ce0a61af4d0686a5d4d";

    public static String getYouSpaceAppSecret() {
        if (DEBUG) {
            return YOU_SPACE_APPSECRET_DEBUG;
        }
        return YOU_SPACE_APPSECRET_PRD;
    }

    private static final String YOU_SPACE_APPCODE_DEBUG = "2b0cd937-0db2-42f0-88df-17723609b570";
    private static final String YOU_SPACE_APPCODE_PRD = "c321693b-ee68-46d4-9e8e-be62f665c0a7";

    public static String getJWTSecret() {
        if (DEBUG) {
            return API_JWT_SECRET_DEBUG;
        } else {
            return API_JWT_SECRET_PRD;
        }
    }

    /**
     * 附件保存路径
     *
     * @return
     */
    public static String getUploadLocationRoot() {
        if (DEBUG) {
            return MEDIA_UPLOAD_LOCATION_ROOT_DEBUG;
        } else {
            return MEDIA_UPLOAD_LOCATION_ROOT_PRD;
        }
    }

    /**
     * 阿里OSS
     *
     * @return
     */
    public static String getAliOSSBucketPublicHost() {
        if (DEBUG) {
            return ALIOSS_BUCKET_HOST_DEBUG;
        } else {
            return ALIOSS_BUCKET_HOST;
        }
    }

    public static String getAliOSSEndPointNet() {
        if (DEBUG) {
            return ALIOSS_ENDPOINT_NET_DEBUG;
        } else {
            return ALIOSS_ENDPOINT_NET;
        }
    }

    public static String getAliOSSEndPointPri() {
        if (DEBUG) {
            return ALIOSS_ENDPOINT_PRI_DEBUG;
        } else {
            return ALIOSS_ENDPOINT_NET;
        }
    }

    public static String getAliOSSAccessKeyID() {
        if (DEBUG) {
            return ALIOSS_ACCESSKEYID_DEBUG;
        } else {
            return ALIOSS_ACCESSKEYID;
        }
    }

    public static String getAliOSSAccessKeySecret() {
        if (DEBUG) {
            return ALIOSS_ACCESSKEYSECRET_DEBUG;
        } else {
            return ALIOSS_ACCESSKEYSECRET;
        }
    }

    public static String getAliOSSBucketNamePublic() {
        if (DEBUG) {
            return ALIOSS_BUCKETNAME_DEBUG;
        } else {
            return ALIOSS_BUCKETNAME;
        }
    }

    // 错误码
    public static final int ERROR_CODE_COM = 501;
    public static final String ERROR_MSG_COM = "常规错误拦截提示(服务端自定义提示)";

    public static final int ERROR_CODE_INVALID_PARAM = 1001;
    public static final String ERROR_MSG_INVALID_PARAM = "无效的请求参数";
    public static final int ERROR_CODE_REQUEST_FAIL = 1002;
    public static final String ERROR_MSG_REQUEST_FAIL = "操作失败";
    public static final int ERROR_CODE_SERVER_ERROR = 1003;
    public static final String ERROR_MSG_SERVER_ERROR = "程序异常";
    public static final int ERROR_CODE_INVALID_USER = 1004;
    public static final String ERROR_MSG_INVALID_USER = "无效的用户";
    public static final int ERROR_CODE_APPPASS = 1006;
    public static final String ERROR_MSG_APPPASS = "appkey不匹配";

    static {
        map.put(ERROR_CODE_COM, ERROR_MSG_COM);
        map.put(ERROR_CODE_INVALID_PARAM, ERROR_MSG_INVALID_PARAM);
        map.put(ERROR_CODE_REQUEST_FAIL, ERROR_MSG_REQUEST_FAIL);
        map.put(ERROR_CODE_SERVER_ERROR, ERROR_MSG_SERVER_ERROR);
        map.put(ERROR_CODE_INVALID_USER, ERROR_MSG_INVALID_USER);

        StringBuilder sb = new StringBuilder();

        for (Map.Entry<Integer, String> m : map.entrySet()) {
            sb.append(m.getKey());
            sb.append("\t\t\t\t\t\t\t");
            sb.append(m.getValue());
            sb.append("<br>");
        }

        codeStr = sb.toString();

    }

}