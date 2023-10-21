package com.liuc.server.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liuc.server.api.annotation.ValidateApiNotAuth;
import com.liuc.server.api.bean.TResult;
import com.liuc.server.api.bean.app.*;
import com.liuc.server.api.common.C;
import com.liuc.server.api.common.SqlProvider;
import com.liuc.server.api.service.CommonService;
import com.liuc.server.api.service.RedisService;
import com.liuc.server.api.service.YouSpaceService;
import com.liuc.server.api.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

;

/**
 * @author huangxianhong
 * @date 2021/1/27 15:28
 */
@RestController
@RequestMapping("/api/v1/personalDetail")
@Api(value = "personalDetail", description = "个人信息", tags = "个人信息")
@Slf4j
public class PersonalDetailsController {
    @Autowired
    private RedisService redisService;
    @Autowired
    private YouSpaceService youSpaceService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CommonService commonService;

    @GetMapping(value = "/getUserInfo")
    @ApiOperation(value = "获取用户信息", notes = "获取用户信息", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @ValidateApiNotAuth
    public TResult<SUserInfo> getUserInfo(@ApiParam(required = true, value = "code") @RequestParam(required = true) String code) throws JsonProcessingException {

//            String userId = youSpaceService.getBaseInfoByCode(code);
        log.info("打印code---->" + code);
        String userCode = youSpaceService.getNCInfoByCode(code);

        if (StringUtils.isEmpty(userCode)) {
            return new TResult<>(C.ERROR_CODE_COM, "友空间员工信息未找到");
        }
        //List<BasicMessageBean> basicMessageBean = ncService.getNCUserInfoList2(userCode, null, null, null, null, null, null, null, null, null, null);
        String sql = SqlProvider.getUserInfoByCode(userCode, "");
        SUserInfo sUserInfo = commonService.selectObjectBySql(sql, SUserInfo.class);

        if (sUserInfo == null) {
            return new TResult<>(C.ERROR_CODE_COM, "账号不存在");
        }
        sUserInfo.setSessionId(StringUtil.getUUID() + sUserInfo.getCode());

        //C.sessionInfo.put("userId_"+sUserInfo.getCode(),sUserInfo);
        redisService.set(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + sUserInfo.getCode(), sUserInfo, 7 * 24 * 60 * 60);
        return new TResult(0, sUserInfo);
    }

    @GetMapping(value = "/getUserInfoByCode")
    @ApiOperation(value = "测试获取用户信息", notes = "测试获取用户信息", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @ValidateApiNotAuth
    public TResult<SUserInfo> getUserInfoByCode(@ApiParam(required = true, value = "code") @RequestParam(required = true) String code) throws JsonProcessingException {

        log.info("打印code---->" + code);
        if (!C.DEBUG || StringUtils.isEmpty(code)) {
            return new TResult(C.ERROR_CODE_COM, "用户不存在");
        }
        if (code.length() > 10) {
            String yhtUserId = youSpaceService.getBaseInfoByCode(code);
            if (StringUtils.isEmpty(yhtUserId)) {
                return new TResult<>(C.ERROR_CODE_COM, "友空间员工信息未找到");
            }
            if ("99aeb9b9-9595-49ab-a066-ba7f5f2d4df0".equals(yhtUserId)) {
                // 晓亮账号
                SUserInfo sUserInfo = new SUserInfo();

                sUserInfo.setCode("013496");
                sUserInfo.setSessionId(StringUtil.getUUID() + sUserInfo.getCode());
                sUserInfo.setName("庄志榕");
                sUserInfo.setDeptcode("06");
                sUserInfo.setDeptname("工程部");
                sUserInfo.setOrgid("00011110000000000PJF");
                sUserInfo.setOrgname("广东水电二局汕头大围达标加固工程项目经理部");
                sUserInfo.setOrgcode("01002069");
                sUserInfo.setUsercode("013496");
                sUserInfo.setUserId("1001A2100000000A31II");
                sUserInfo.setPk_group("00011110000000000HT7");


                redisService.set(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + sUserInfo.getCode(), sUserInfo, 7 * 24 * 60 * 60);
                return new TResult(sUserInfo);
            }else if("baa33ec3-9b19-4189-acee-dabf8e1e8fcf".equals(yhtUserId)){
                // 尔东账号
                SUserInfo sUserInfo = new SUserInfo();

                sUserInfo.setCode("lc1");
                sUserInfo.setSessionId(StringUtil.getUUID() + sUserInfo.getCode());
                sUserInfo.setName("测试用户");
                sUserInfo.setDeptcode("03");
                sUserInfo.setDeptname("测试部门");
                sUserInfo.setOrgid("00011110000000000PJF");
                sUserInfo.setOrgname("广东水电二局汕头大围达标加固工程项目经理部");
                sUserInfo.setOrgcode("01002069");
                sUserInfo.setUsercode("lc1");
                sUserInfo.setUserId("1001A2100000000A4WNO");
                sUserInfo.setPk_group("00011110000000000HT7");


                redisService.set(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + sUserInfo.getCode(), sUserInfo, 7 * 24 * 60 * 60);
                return new TResult(sUserInfo);
            }else if("c9fc4c17-cec5-4f10-b1c4-dde5a52fc6f4".equals(yhtUserId)){

                SUserInfo sUserInfo = new SUserInfo();

                sUserInfo.setCode("002652");
                sUserInfo.setSessionId(StringUtil.getUUID() + sUserInfo.getCode());
                sUserInfo.setName("伍加波");
                sUserInfo.setDeptcode("04");
                sUserInfo.setDeptname("领导班子");
                sUserInfo.setOrgid("00011110000000000PJF");
                sUserInfo.setOrgname("广东水电二局汕头大围达标加固工程项目经理部");
                sUserInfo.setOrgcode("01002069");
                sUserInfo.setUsercode("002652");
                sUserInfo.setUserId("1001A2100000000A31I1");
                sUserInfo.setPk_group("00011110000000000HT7");
                redisService.set(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + sUserInfo.getCode(), sUserInfo, 7 * 24 * 60 * 60);
                return new TResult(sUserInfo);
            }else if("14c484d6-124a-4035-91e9-9336839a2dbf".equals(yhtUserId)){
                SUserInfo sUserInfo = new SUserInfo();

                sUserInfo.setCode("123");
                sUserInfo.setSessionId(StringUtil.getUUID() + sUserInfo.getCode());
                sUserInfo.setName("测试报销冲销用户");
                sUserInfo.setDeptcode("03");
                sUserInfo.setDeptname("测试部门");
                sUserInfo.setOrgid("00011110000000000PJF");
                sUserInfo.setOrgname("广东水电二局汕头大围达标加固工程项目经理部");
                sUserInfo.setOrgcode("01002069");
                sUserInfo.setUsercode("123");
                sUserInfo.setUserId("1001OO100000000A3TBR");
                sUserInfo.setPk_group("00011110000000000HT7");
                redisService.set(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + sUserInfo.getCode(), sUserInfo, 7 * 24 * 60 * 60);
                return new TResult(sUserInfo);
            }else if("3a0cc367-138a-4f43-af3a-d119f919faf4".equals(yhtUserId)){
                SUserInfo sUserInfo = new SUserInfo();

                sUserInfo.setCode("002967");
                sUserInfo.setSessionId(StringUtil.getUUID() + sUserInfo.getCode());
                sUserInfo.setName("肖芳");
                sUserInfo.setDeptcode("001");
                sUserInfo.setDeptname("办公室");
                sUserInfo.setOrgid("00011110000000000PJF");
                sUserInfo.setOrgname("广东粤水电建设投资有限公司");
                sUserInfo.setOrgcode("01002603");
                sUserInfo.setUsercode("002967");
                sUserInfo.setUserId("10011110000000094U5W");
                sUserInfo.setPk_group("00011110000000000HT7");
                redisService.set(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + sUserInfo.getCode(), sUserInfo, 7 * 24 * 60 * 60);
                return new TResult(sUserInfo);
            } else if("f7fe8402-9e1b-4942-a5f2-16623f1608cb".equals(yhtUserId)){
                SUserInfo sUserInfo = new SUserInfo();

                sUserInfo.setCode("9999");
                sUserInfo.setSessionId(StringUtil.getUUID() + sUserInfo.getCode());
                sUserInfo.setName("谢惠英");
                sUserInfo.setDeptcode("01999");
                sUserInfo.setDeptname("无");
                sUserInfo.setOrgid("00011110000000000PHX");
                sUserInfo.setOrgname("广东水电二局股份有限公司");
                sUserInfo.setOrgcode("01002");
                sUserInfo.setUsercode("yy09");
                sUserInfo.setUserId("100111100000000131QG");
                sUserInfo.setPk_group("00011110000000000HT7");
                redisService.set(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + sUserInfo.getCode(), sUserInfo, 7 * 24 * 60 * 60);
                return new TResult(sUserInfo);
            }
            return new TResult(C.ERROR_CODE_COM, "用户不存在");
        } else {
            String sql = SqlProvider.getUserInfoByCode(code, "");
            SUserInfo sUserInfo = commonService.selectObjectBySql(sql, SUserInfo.class);

            if (sUserInfo == null) {
                return new TResult(C.ERROR_CODE_COM, "用户不存在");
            }

            sUserInfo.setSessionId(StringUtil.getUUID() + sUserInfo.getCode());

            //C.sessionInfo.put("userId_"+sUserInfo.getCode(),sUserInfo);
            redisService.set(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + sUserInfo.getCode(), sUserInfo, 7 * 24 * 60 * 60);
            return new TResult(0, sUserInfo);
        }
    }

    @GetMapping(value = "/jsTicket")
    @ApiOperation("获取jsTicket")
    @ValidateApiNotAuth
    public TResult<String> jsTicket() {
        return new TResult<>(youSpaceService.getJsTicket());
    }
}
