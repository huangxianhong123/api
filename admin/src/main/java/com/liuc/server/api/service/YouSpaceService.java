package com.liuc.server.api.service;

import com.liuc.server.api.bean.app.SUserInfo;
import com.liuc.server.api.common.C;
import com.liuc.server.api.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
@Slf4j
public class YouSpaceService {

    @Autowired
    private RedisService redisService;

    // 友空间接口文档地址:https://open.diwork.com/#/doc-center/docDes/doc?code=open_jrwd&section=4d98f0c5763b477b95390f25345b3df1
    // 用友YonBIP API:https://open.diwork.com/?from#/doc-center/docDes/api?code=diwork&section=2bac9773-c3fa-437b-89c7-65f25d274e2f
    // 用友协同 API:https://open.diwork.com/#/docs/md2docs/open-api-doc?id=xietong&section=72

    /**
     * 按参数名排序后依次拼接参数名称与数值，之后对该字符串使用 HmacSHA256 加签，加签结果进行 base 64 返回
     *
     * @param params      请求参数 map
     * @param suiteSecret 套件密钥，用作 mac key
     * @return 签名
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     * @throws InvalidKeyException
     */
    private static String sign(Map<String, Object> params, String suiteSecret) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        // use tree map to sort params by name
        Map<String, Object> treeMap;
        if (params instanceof TreeMap) {
            treeMap = params;
        } else {
            treeMap = new TreeMap<>(params);
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Object> entry : treeMap.entrySet()) {
            stringBuilder.append(entry.getKey()).append(entry.getValue());
        }

        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(suiteSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringBuilder.toString().getBytes(StandardCharsets.UTF_8));
        String base64String = Base64.getEncoder().encodeToString(signData);
        return URLEncoder.encode(base64String, "UTF-8");
    }

    /**
     * 获取accessToken
     *
     * @return
     */
    private synchronized String getAccessToken() {
//        String accessToken = null;
//        String expireStr = C.ACCOUNT_TOKEN.get(C.RedisPrefix.YOU_SPACE_ACCESS_TOKEN_EXPIRE);
//        if (!StringUtils.isEmpty(expireStr) && System.currentTimeMillis() / 1000 < Long.parseLong(expireStr)) {
//            accessToken = C.ACCOUNT_TOKEN.get(C.RedisPrefix.YOU_SPACE_ACCESS_TOKEN);
//        }

        String accessToken = "";
        String expireStr = (String) redisService.get(C.RedisPrefix.YOU_SPACE_ACCESS_TOKEN_EXPIRE);
        if (!StringUtils.isEmpty(expireStr) && System.currentTimeMillis() / 1000 < Long.parseLong(expireStr)) {
            accessToken = (String) redisService.get(C.RedisPrefix.YOU_SPACE_ACCESS_TOKEN);
        }
       // String accessToken = (String) redisService.get(C.RedisPrefix.YOU_SPACE_ACCESS_TOKEN);
        if (StringUtils.isEmpty(accessToken)) {
            try {
                Map<String, Object> params = new HashMap<>();
                params.put("appKey", C.getYouSpaceAppkey());
                params.put("timestamp", System.currentTimeMillis());
                params.put("signature", sign(params, C.getYouSpaceAppSecret()));
                String result = HttpClientUtil.get(C.YOU_SPACE_ACCESS_TOKEN_URL1, params);
                JSONObject jsonObject = new JSONObject(result);
                String code = jsonObject.getString("code");
                if ("00000".equals(code)) {
                    JSONObject dataJo = jsonObject.getJSONObject("data");
                    String access_token = dataJo.getString("access_token");
                    Integer expire = dataJo.getInt("expire");
                    log.info("----> 获取有效时间 expire" + expire);
                    redisService.set(C.RedisPrefix.YOU_SPACE_ACCESS_TOKEN, access_token);
                    redisService.set(C.RedisPrefix.YOU_SPACE_ACCESS_TOKEN_EXPIRE,System.currentTimeMillis() / 1000 + expire - 10 + "");
//                    C.ACCOUNT_TOKEN.put(C.RedisPrefix.YOU_SPACE_ACCESS_TOKEN,access_token);
//                    C.ACCOUNT_TOKEN.put(C.RedisPrefix.YOU_SPACE_ACCESS_TOKEN_EXPIRE,System.currentTimeMillis() / 1000 + expire - 10 + "");
//                    redisService.set(C.RedisPrefix.YOU_SPACE_ACCESS_TOKEN, access_token);
//                    redisService.expire(C.RedisPrefix.YOU_SPACE_ACCESS_TOKEN, expire - 10);
                    return access_token;
                } else {
                    log.error("getAccessToken error:" + jsonObject.getString("message"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return accessToken;
    }

    /**
     * 获取基础用户id
     *
     * @param code
     */
    public String getBaseInfoByCode(String code) {
        log.error("------>>>code:" + code);
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("access_token", getAccessToken());
            params.put("code", code);
            String result = HttpClientUtil.get(C.YOU_SPACE_BASE_INFO_URL1, params);
            log.error("---->result" + result);
            JSONObject jsonObject = new JSONObject(result);
            log.error("---->jsonObject" + jsonObject);
            if (jsonObject.getInt("code") ==200) {
                log.error("------>>>获取得信息" + jsonObject.toString());
                String yhtUserId = jsonObject.getJSONObject("data").getString("yhtUserId"); // 用户id
                String tenantId = jsonObject.getJSONObject("data").getString("tenantId"); // 租户Id
                String memberId = jsonObject.getJSONObject("data").getString("memberId"); // 空间用户id
                String qzId = jsonObject.getJSONObject("data").getString("qzId"); // 空间Id
                // TODO 下一步业务逻辑
                log.error("yhtUserId:" + yhtUserId);
                log.error("tenantId:" + tenantId);
                log.error("memberId:" + memberId);
                log.error("qzId:" + qzId);
                return yhtUserId;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据友户通userId批量获取用户信息
     *
     * @param id
     */
    public SUserInfo getUserInfo(String id) {
        Map<String, List<String>> params = new HashedMap();
        List<String> items = new ArrayList<>();
        items.add(id);
        params.put("userIds", items);
        try {
            String result = HttpClientUtil.jsonPost(C.YOU_SPACE_USER_INFO_URL + "?access_token=" + getAccessToken(), params);
            JSONObject jsonObject = new JSONObject(result);
            log.error("------>>>jsonObject:" + jsonObject.toString());
            if (jsonObject.getInt("code") == 0 && jsonObject.getJSONArray("data") != null && jsonObject.getJSONArray("data").length() > 0) {
                String userId = jsonObject.getJSONArray("data").getJSONObject(0).getString("userId"); // 用户id
                String userCode = jsonObject.getJSONArray("data").getJSONObject(0).getString("userCode"); // 用户编码
                String userName = jsonObject.getJSONArray("data").getJSONObject(0).getString("userName"); // 用户名
                String userMobile = jsonObject.getJSONArray("data").getJSONObject(0).getString("userMobile"); // 手机号
                String userMobileCountrycode = jsonObject.getJSONArray("data").getJSONObject(0).getString("userMobileCountrycode"); // 手机号所属国家编码
                String userEmail = jsonObject.getJSONArray("data").getJSONObject(0).getString("userEmail"); // 邮箱
                String userActivate = jsonObject.getJSONArray("data").getJSONObject(0).getString("userActivate"); // 用户是否激活
                String registerDate = jsonObject.getJSONArray("data").getJSONObject(0).getString("registerDate"); // 注册日期
                String loginTs = jsonObject.getJSONArray("data").getJSONObject(0).getString("loginTs"); // 登录时间
                String userAvatorNew = jsonObject.getJSONArray("data").getJSONObject(0).getString("userAvatorNew"); // 头像
                String userBigAvatorNew = jsonObject.getJSONArray("data").getJSONObject(0).getString("userBigAvatorNew"); // 大头像
                String userSmallAvatorNew = jsonObject.getJSONArray("data").getJSONObject(0).getString("userSmallAvatorNew"); // 小头像
                String sysId = jsonObject.getJSONArray("data").getJSONObject(0).getString("sysId"); // 系统编码


                SUserInfo sUserInfo = new SUserInfo();
//                sUserInfo.setUserId(userId); // 用户id
//                sUserInfo.setUserCode(userCode); // 用户编码
//                sUserInfo.setUserName(userName);
//                sUserInfo.setUserMobile(userMobile); // 手机号
//                sUserInfo.setUserMobileCountrycode(userMobileCountrycode);// 手机号所属国家编码
//                sUserInfo.setUserEmail(userEmail); // 邮箱
//                sUserInfo.setUserActivate(userActivate); // 用户是否激活
//                sUserInfo.setRegisterDate(registerDate);  // 注册日期
//                //sUserInfo.setLoginTs(loginTs); // 登录时间
//                sUserInfo.setUserAvatorNew(userAvatorNew); // 头像
//                sUserInfo.setUserBigAvatorNew(userBigAvatorNew); // 大头像
//                sUserInfo.setUserSmallAvatorNew(userSmallAvatorNew);// 小头像
//                sUserInfo.setSysId(sysId);  // 系统编码
                return sUserInfo;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据code获取nc用户信息
     *
     * @param code
     */
    public String getNCInfoByCode(String code) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("access_token", getAccessToken());
            params.put("code", code);
            String result = HttpClientUtil.get(C.YOU_SPACE_NC_INFO_URL, params);
            JSONObject jsonObject = new JSONObject(result);
            log.error("------>>>jsonObject:" + jsonObject.toString());
            if (jsonObject.getInt("code") == 0) {
                String yhtUserId = jsonObject.getJSONObject("data").getString("yhtUserId"); // 用户id
                String tenantId = jsonObject.getJSONObject("data").getString("accountCode"); //
                String integrateType = jsonObject.getJSONObject("data").getString("integrateType");
                String ncVersion = jsonObject.getJSONObject("data").getString("ncVersion"); // 空间用户id
                String pkGroup = jsonObject.getJSONObject("data").getString("pkGroup"); // 空间Id
                String userCode = jsonObject.getJSONObject("data").getString("userCode"); // 绑定的编码
                String bindMobile = jsonObject.getJSONObject("data").getString("bindMobile"); // 绑定的手机号
                String qzId = jsonObject.getJSONObject("data").getString("qzId"); // 绑定的用户编码
                String ubAppId = jsonObject.getJSONObject("data").getString("ubAppId");
                String pkOrg =  jsonObject.getJSONObject("data").getString("pkOrg");
                String userId = jsonObject.getJSONObject("data").getString("userId"); // 绑定的用户ID
                String bindUcode= jsonObject.getJSONObject("data").getString("bindUcode");
                String tenantCode = jsonObject.getJSONObject("data").getString("tenantCode"); // 租户编码
                String userName = jsonObject.getJSONObject("data").getString("userName");
                String bindUid = jsonObject.getJSONObject("data").getString("bindUid");
                String memberId = jsonObject.getJSONObject("data").getString("memberId");
                // TODO 下一步业务逻辑
                return userCode;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据appcode获取应用信息
     *
     * @param appCode
     */
    public void getAppInfoByAppCode(String appCode) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("access_token", getAccessToken());
            params.put("appCode", appCode);
            String result = HttpClientUtil.get(C.YOU_SPACE_APP_INFO_URL, params);
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.getInt("code") == 0) {
                String tenantId = jsonObject.getJSONObject("data").getString("tenantId"); // 租户Id
                // TODO 下一步业务逻辑
                log.error("tenantId:" + tenantId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取jsTicket
     */
    public String getJsTicket() {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("access_token", getAccessToken());
            String result = HttpClientUtil.get(C.YOU_SPACE_JS_TICKET_URL1, params);
            JSONObject jsonObject = new JSONObject(result);
            log.error("------>jsonObject:" + jsonObject.toString());
            if ("200".equals(jsonObject.getString("code"))) {
                log.error("------>获取信息:" + jsonObject.toString());
                String js_ticket = jsonObject.getJSONObject("data").getString("js_ticket");
                return js_ticket;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据用空间用户id获取员工信息
     *
     * @param userId
     */
    public String getInfoByUserId(String userId) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("access_token", getAccessToken());
            params.put("yhtUserId", userId);
            String result = HttpClientUtil.get(C.YOU_SPACE_INFO_BY_USER_ID, params);
            log.error("getInfoByUserId result:" + result);
            JSONObject jsonObject = new JSONObject(result);
            if ("200".equals(jsonObject.getString("code"))) {
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                if (jsonArray != null && jsonArray.length() > 0) {
                    JSONObject jsonObjectItem = jsonArray.getJSONObject(0);
                    String code = jsonObjectItem.getString("code");
                    return code;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过友户通用户Id获取下级员工信息
     *
     * @param userId
     * @return
     */
    public List<String> getChildByUserIds(String userId) {
        try {
            Map<String, Object> params = new HashMap<>();
            List<String> items = new ArrayList<>();
            items.add(userId);
            params.put("userIdList", items);
            String result = HttpClientUtil.jsonPost(C.YOU_SPACE_CHILD_BY_USER_IDS  + "?access_token=" + getAccessToken(), params);
            JSONObject jsonObject = new JSONObject(result);
            if ("200".equals(jsonObject.getString("code"))) {
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                if (jsonArray != null && jsonArray.length() > 0) {
                    List<String> codes = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObjectItem = jsonArray.getJSONObject(i);
                        String code = jsonObjectItem.getString("code");
                        codes.add(code);
                    }
                    return codes;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
