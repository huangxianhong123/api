package com.liuc.server.api.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.gson.Gson;
import com.liuc.server.api.annotation.ValidateApiNotAuth;
import com.liuc.server.api.bean.TResult;
import com.liuc.server.api.bean.app.*;
import com.liuc.server.api.common.C;
import com.liuc.server.api.common.SqlProvider;
import com.liuc.server.api.service.CommonService;
import com.liuc.server.api.service.RedisService;
import com.liuc.server.api.util.DateUtils;
import com.liuc.server.api.util.HttpUtils;
import com.liuc.server.api.util.SpringUtil;
import com.liuc.server.api.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author huangxianhong
 * @date 2021/2/23 18:27
 */

@RestController
@RequestMapping("/api/v1/travelling")
@Api(value = "travelling", description = "借款", tags = "借款")
@Slf4j
public class TravellingController {
    @Value("${spring.profiles.active}")
    private String string;
    @Autowired
    private CommonService commonService;
    @Autowired
    private RedisService redisService;


//    @PostMapping("/add")
//    @ApiOperation(value = "差旅费借款单")
//    //@ValidateApiNotAuth
//    public TResult<ErBusitemAndTravellingBean> add(HttpServletRequest request, @RequestBody ErBusitemAndTravellingBean travellingLoanBean) {
//        log.info("差旅费借款:{}", new Gson().toJson(travellingLoanBean));
//        Date date = new Date();
//
//        String userId = SpringUtil.getSessionUserId(request);
//        SUserInfo sUserInfo = (SUserInfo) redisService.get(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + userId);
//        //SUserInfo sUserInfo =  C.sessionInfo.get("userId_"+userId);
//
//        TravellingLoanBean loanBean1 = new TravellingLoanBean();
//
//        //loanBean1.setOaid();//主键
//        loanBean1.setDjbh(commonService.getSn(1));//单据编号
//        loanBean1.setJylx("2631");//交易类型
//        loanBean1.setApprover(sUserInfo.getUsercode());
//        loanBean1.setHjje(travellingLoanBean.getEr_jkzb().getHjje());
//        loanBean1.setCreationtime(DateUtils.formatDate(date));
//        loanBean1.setCreator(sUserInfo.getUsercode());
//        loanBean1.setApprovertime(DateUtils.formatDate(date));
//        loanBean1.setFydw(sUserInfo.getOrgcode());//单据所属组织（未知）
//
//        loanBean1.setFybm(sUserInfo.getDeptcode());
//        loanBean1.setDjrq(travellingLoanBean.getEr_jkzb().getDjrq());
//        loanBean1.setBxdw(sUserInfo.getOrgcode());
//        loanBean1.setBxbm(sUserInfo.getDeptcode());
//        loanBean1.setBxr(sUserInfo.getCode());
//        loanBean1.setCurrtype(travellingLoanBean.getEr_jkzb().getCurrtype());
//        loanBean1.setGhrq(DateUtils.formatDate(date));//归还日期
//        loanBean1.setBz(travellingLoanBean.getEr_jkzb().getBz());
//        loanBean1.setCustaccount(travellingLoanBean.getEr_jkzb().getCustaccount());
//        loanBean1.setFydl(travellingLoanBean.getEr_jkzb().getFydl());
//        loanBean1.setGys("0100007");//供应商
//
//
//        ErBusitemAndTravellingBean erBusitemAndTravellingBean = new ErBusitemAndTravellingBean();
//
//        erBusitemAndTravellingBean.setEr_jkzb(loanBean1);
//        erBusitemAndTravellingBean.setEr_busitem(travellingLoanBean.getEr_busitem());
//        log.info("打印差旅费借款1:{}", new Gson().toJson(erBusitemAndTravellingBean));
//        try {
//            String s = HttpUtils.requestPostBody(C.CHAI_LV_FEI_JIE_KUAI_DAN_URL, JSONObject.toJSONString(erBusitemAndTravellingBean), null);
//            log.error("推送成功："+s);
//
//            org.json.JSONObject jsonObjectResult = new org.json.JSONObject(s);
//            if("0".equals(jsonObjectResult.get("code"))){
//                String data = String.valueOf(jsonObjectResult.get("data"));
//                org.json.JSONObject dataResult = new org.json.JSONObject(data);
//
//                String head = String.valueOf(dataResult.get("head"));
//                String body = String.valueOf(dataResult.getJSONArray("body"));
//
//                org.json.JSONObject headResult = new org.json.JSONObject(head);
//                List<BodyBean> bodyBean = JSON.parseArray(body, BodyBean.class);
//
//                HeadBean headBean = new HeadBean();
//                headBean.setNcid(headResult.get("ncid").toString());
//                headBean.setOaid(headResult.get("oaid").toString());
//
//                BodyAndHeadBean bodyAndHeadBean = new BodyAndHeadBean();
//                bodyAndHeadBean.setBody(bodyBean);
//                bodyAndHeadBean.setHead(headBean);
//
//                return new TResult<>(bodyAndHeadBean);
//            }else{
//                return new TResult<>(1,jsonObjectResult.get("msg"));
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//            log.error("推送失败" + e);
//            return new TResult<>(C.ERROR_CODE_COM,"推送失败");
//        }
//
//    }
//
//
//    @PostMapping("/addPass")
//    @ApiOperation(value = "通用借款单")
//    //@ValidateApiNotAuth
//    public TResult<PassAndTravellingBean> addPass(HttpServletRequest request, @RequestBody PassAndTravellingBean passAndTravellingBean) {
//        log.info("通用借款单:{}", new Gson().toJson(passAndTravellingBean));
//        Date date = new Date();
//
//        String userId = SpringUtil.getSessionUserId(request);
//
//        //SUserInfo sUserInfo =  C.sessionInfo.get("userId_"+userId);
//        SUserInfo sUserInfo = (SUserInfo) redisService.get(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + userId);
//        TravellingLoanBean loanBean1 = new TravellingLoanBean();
//
//        //loanBean1.setOaid();//主键
//        loanBean1.setDjbh(commonService.getSn(2));//单据编号
//        loanBean1.setJylx("263X-Cxx-TYJKD");//交易类型
//        loanBean1.setApprover(sUserInfo.getUsercode());
//        loanBean1.setHjje(passAndTravellingBean.getEr_jkzb().getHjje());
//        loanBean1.setCreationtime(DateUtils.formatDate(date));
//        loanBean1.setCreator(sUserInfo.getUsercode());
//        loanBean1.setApprovertime(DateUtils.formatDate(date));
//        loanBean1.setFydw(sUserInfo.getOrgcode());//单据所属组织（未知）
//
//        loanBean1.setFybm(sUserInfo.getDeptcode());
//        loanBean1.setDjrq(passAndTravellingBean.getEr_jkzb().getDjrq());
//        loanBean1.setBxdw(sUserInfo.getOrgcode());
//        loanBean1.setBxbm(sUserInfo.getDeptcode());
//        loanBean1.setBxr(sUserInfo.getCode());
//        loanBean1.setGhrq(DateUtils.formatDate(date));//归还日期
//        loanBean1.setCurrtype(passAndTravellingBean.getEr_jkzb().getCurrtype());
//        loanBean1.setBz(passAndTravellingBean.getEr_jkzb().getBz());
//        loanBean1.setCustaccount(passAndTravellingBean.getEr_jkzb().getCustaccount());
//        loanBean1.setFydl(passAndTravellingBean.getEr_jkzb().getFydl());
//        loanBean1.setGys("0100007");//供应商
//
//
//        PassAndTravellingBean passAndTravellingBean1 = new PassAndTravellingBean();
//
//        passAndTravellingBean1.setEr_jkzb(loanBean1);
//        passAndTravellingBean1.setEr_busitem(passAndTravellingBean.getEr_busitem());
//        log.info("打印通用借款单1:{}", new Gson().toJson(passAndTravellingBean1));
//        try {
//            String s = HttpUtils.requestPostBody(C.CHAI_LV_FEI_JIE_KUAI_DAN_URL, JSONObject.toJSONString(passAndTravellingBean1), null);
//            log.error("推送成功："+s);
//
//            org.json.JSONObject jsonObjectResult = new org.json.JSONObject(s);
//            if("0".equals(jsonObjectResult.get("code"))){
//
//                return new TResult<>(s);
//            }else{
//
//                return new TResult<>(1,jsonObjectResult.get("msg"));
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//            log.error("推送失败" + e);
//            return new TResult<>(C.ERROR_CODE_COM,"推送失败");
//        }
//
//    }
//


    @PostMapping("/add")
    @ApiOperation(value = "新增差旅费借款单")
    //@ValidateApiNotAuth
    public TResult<TravellingBean> add(HttpServletRequest request, @RequestBody TravellingBean RTravellingBean) {
        log.info("差旅费借款:{}", new Gson().toJson(RTravellingBean));
        Date date = new Date();

        String userId = SpringUtil.getSessionUserId(request);
        SUserInfo sUserInfo = (SUserInfo) redisService.get(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + userId);
        //SUserInfo sUserInfo =  C.sessionInfo.get("userId_"+userId);

        CostHeadBean costHeadBean = new CostHeadBean();
        costHeadBean.setCreator(sUserInfo.getUsercode());
        costHeadBean.setPk_org(sUserInfo.getOrgcode());//sUserInfo.getOrgcode()
        costHeadBean.setIncident(RTravellingBean.getHead().getIncident());
        costHeadBean.setZyx25(RTravellingBean.getHead().getZyx25());
        costHeadBean.setPk_payorg(sUserInfo.getOrgcode());//支付单位
        costHeadBean.setFkyhzh("");//单位银行账户
        costHeadBean.setJsfs("");//结算方式
        costHeadBean.setCashitem("");//现金流量项目
        costHeadBean.setCashproj("");//资金计划项目
        costHeadBean.setFydwbm(sUserInfo.getOrgcode());//sUserInfo.getOrgcode()
        costHeadBean.setFydeptid(sUserInfo.getDeptcode());//sUserInfo.getDeptcode()
        costHeadBean.setSzxmid(RTravellingBean.getHead().getSzxmid());
        costHeadBean.setHbbm("");//供应商
        costHeadBean.setCustomer("");//客户
        costHeadBean.setReceiver(RTravellingBean.getHead().getReceiver());//sUserInfo.getCode()
        costHeadBean.setBzbm(RTravellingBean.getHead().getBzbm());
        costHeadBean.setDjlxbm("2631");//交易类型
        costHeadBean.setDjrq(RTravellingBean.getHead().getDjrq());
        costHeadBean.setDwbm(RTravellingBean.getHead().getDwbm());//sUserInfo.getOrgcode()
        costHeadBean.setDeptid(RTravellingBean.getHead().getDeptid());//sUserInfo.getDeptcode()
        costHeadBean.setSkyhzh(RTravellingBean.getHead().getSkyhzh());
        costHeadBean.setBbje(RTravellingBean.getHead().getBbje());
        costHeadBean.setZyx25(RTravellingBean.getHead().getZyx25());
        costHeadBean.setFjzs(RTravellingBean.getHead().getFjzs());
        costHeadBean.setWork(RTravellingBean.getHead().getWork());

        List<DetailsBean> details = RTravellingBean.getDetails();
        List<DetailsBean> allList =  new ArrayList<>();

        for(DetailsBean list : details){

            DetailsBean bean = new DetailsBean();
            list.setSzxmcode(RTravellingBean.getHead().getSzxmid());
            BeanUtils.copyProperties(list, bean);
            if(StringUtil.notEmpty(list.getDefitem4())){
                String sql = SqlProvider.getEnumvalueCode(list.getDefitem4(),"");
                CostBean costBean = commonService.selectObjectBySql(sql, CostBean.class);
                if(costBean !=null){
                    bean.setDefitem4(costBean.getName());
                }
            }else{
                bean.setDefitem4("");
            }


            allList.add(bean);
        }


        TravellingBean beanList = new TravellingBean();
        beanList.setUserid(sUserInfo.getUserId());
        beanList.setPk_group(sUserInfo.getPk_group());
        beanList.setHead(costHeadBean);
        beanList.setDetails(allList);

        log.info("打印差旅费借款:{}", new Gson().toJson(beanList));
        try {
            String s = "";
            if ("loc".equals(string)) {
                s = HttpUtils.requestPostBody(C.INSERT_TRAVLLING_ADD_URL_DEV, JSONObject.toJSONString(beanList), null);
            } else if ("dev".equals(string)){
                s = HttpUtils.requestPostBody(C.INSERT_TRAVLLING_ADD_URL_DEV, JSONObject.toJSONString(beanList), null);
            }else if("gray".equals(string)){
                s = HttpUtils.requestPostBody(C.INSERT_TRAVLLING_ADD_URL_PRD, JSONObject.toJSONString(beanList), null);
            }else{
                s = HttpUtils.requestPostBody(C.INSERT_TRAVLLING_ADD_URL_PRD, JSONObject.toJSONString(beanList), null);
            }

            log.error("推送成功："+s);
            String substring = commonService.stringJieQu(s);
            JSONObject jsonObject = JSONObject.parseObject(substring);

            log.info("jsonObjectResult" + jsonObject);
            if("0".equals(String.valueOf(jsonObject.get("result")))){
                return new TResult<>(0,null,String.valueOf(jsonObject.get("billcode")));
            }else{
                return new TResult<>(1,String.valueOf(jsonObject.get("message")));
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("推送失败" + e);
            return new TResult<>(C.ERROR_CODE_COM,"推送失败");
        }

    }


    @PostMapping("/addPass")
    @ApiOperation(value = "新增通用借款单")
    //@ValidateApiNotAuth
    public TResult<GeneralListBean> addPass(HttpServletRequest request, @RequestBody GeneralListBean RTravellingBean) {
        log.info("通用借款单:{}", new Gson().toJson(RTravellingBean));
        Date date = new Date();

        String userId = SpringUtil.getSessionUserId(request);

        //SUserInfo sUserInfo =  C.sessionInfo.get("userId_"+userId);
        SUserInfo sUserInfo = (SUserInfo) redisService.get(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + userId);

        GeneralBean costHeadBean = new GeneralBean();
        costHeadBean.setCreator(sUserInfo.getUsercode());
        costHeadBean.setPk_org(sUserInfo.getOrgcode());//sUserInfo.getOrgcode()
        costHeadBean.setIncident(RTravellingBean.getHead().getIncident());
        costHeadBean.setPk_payorg(sUserInfo.getOrgcode());//支付单位
        costHeadBean.setFkyhzh("");//单位银行账户
        costHeadBean.setJsfs("");//结算方式
        costHeadBean.setCashitem("");//现金流量项目
        costHeadBean.setCashproj("");//资金计划项目
        costHeadBean.setFydwbm(sUserInfo.getOrgcode());//sUserInfo.getOrgcode()
        costHeadBean.setFydeptid(sUserInfo.getDeptcode());//sUserInfo.getDeptcode()
        costHeadBean.setSzxmid(RTravellingBean.getHead().getSzxmid());
        costHeadBean.setHbbm("");//供应商
        costHeadBean.setCustomer("");//客户
        costHeadBean.setReceiver(RTravellingBean.getHead().getReceiver());//sUserInfo.getCode()
        costHeadBean.setBzbm(RTravellingBean.getHead().getBzbm());
        costHeadBean.setDjlxbm("263X-Cxx-TYJKD");//交易类型
        costHeadBean.setDjrq(RTravellingBean.getHead().getDjrq());
        costHeadBean.setDwbm(RTravellingBean.getHead().getDwbm());//sUserInfo.getOrgcode()
        costHeadBean.setDeptid(RTravellingBean.getHead().getDeptid());//sUserInfo.getDeptcode()
        costHeadBean.setSkyhzh(RTravellingBean.getHead().getSkyhzh());
        costHeadBean.setZyx25(RTravellingBean.getHead().getZyx25());
        costHeadBean.setZyx1(RTravellingBean.getHead().getZyx1());
        costHeadBean.setBbje(RTravellingBean.getHead().getBbje());
        costHeadBean.setWork(RTravellingBean.getHead().getWork());
        costHeadBean.setFjzs(RTravellingBean.getHead().getFjzs());


        GeneralListBean beanList = new GeneralListBean();
        beanList.setUserid(sUserInfo.getUserId());
        beanList.setPk_group(sUserInfo.getPk_group());
        beanList.setHead(costHeadBean);
        beanList.setDetails(RTravellingBean.getDetails());

        log.info("打印通用借款单:{}", new Gson().toJson(beanList));
        try {
            String s = "";
            if ("loc".equals(string)) {
                s = HttpUtils.requestPostBody(C.INSERT_TRAVLLING_ADD_URL_DEV, JSONObject.toJSONString(beanList), null);
            } else if ("dev".equals(string)){
                s = HttpUtils.requestPostBody(C.INSERT_TRAVLLING_ADD_URL_DEV, JSONObject.toJSONString(beanList), null);
            }else if("gray".equals(string)){
                s = HttpUtils.requestPostBody(C.INSERT_TRAVLLING_ADD_URL_PRD, JSONObject.toJSONString(beanList), null);
            }else{
                s = HttpUtils.requestPostBody(C.INSERT_TRAVLLING_ADD_URL_PRD, JSONObject.toJSONString(beanList), null);
            }
            log.error("推送成功："+s);

            String substring = commonService.stringJieQu(s);
            JSONObject jsonObject = JSONObject.parseObject(substring);

            log.info("jsonObjectResult" + jsonObject);
            if("0".equals(String.valueOf(jsonObject.get("result")))){
                return new TResult<>(0,null,String.valueOf(jsonObject.get("billcode")));
            }else{
                return new TResult<>(1,String.valueOf(jsonObject.get("message")));
            }

        }catch (Exception e){
            e.printStackTrace();
            log.error("推送失败" + e);
            return new TResult<>(C.ERROR_CODE_COM,"推送失败");
        }

    }



//    @GetMapping(value = "/test"
//    @ApiOperation(value = "test", notes = "test", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
//    @ValidateApiNotAuth
//    public static void main(String aa[])  {
//
//        String bb = "{\"result\":\"0\",\"billcode\":\"[{\"actid\":\"a6e89226-a98b-11eb-be3d-bfb1886b4ec9\",\"actname\":\"分子公司分管领导流程用户组u003c审核u003e\",\"bxdetret\":[{\"userid\":\"1001A2100000000A32GT\",\"usercode\":\"008587\",\"username\":\"罗海坤\"}]}]\"}";
//        String replaceAll = bb.replaceAll("\\\\", "");
//        System.out.println("replaceAll:"+replaceAll);
//
//        String substring = replaceAll.substring(1, replaceAll.length() - 1);
//        System.out.println("substring:"+substring);
//        org.json.JSONObject jsonObjectResult = new org.json.JSONObject(substring);
//        System.out.println(jsonObjectResult.toString());
//
//    }


    @PostMapping("/submitTravelling")
    @ApiOperation(value = "提交差旅费借款单")
    //@ValidateApiNotAuth
    public TResult<SubmitBean> submitTravelling(HttpServletRequest request, @RequestBody SubmitBean RTravellingBean) {
        log.info("差旅费借款:{}", new Gson().toJson(RTravellingBean));
        Date date = new Date();

        String userId = SpringUtil.getSessionUserId(request);
        SUserInfo sUserInfo = (SUserInfo) redisService.get(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + userId);
        //SUserInfo sUserInfo =  C.sessionInfo.get("userId_"+userId);


        SubmitHeadBean submitHeadBean = new SubmitHeadBean();
        submitHeadBean.setBillcode(RTravellingBean.getHead().getBillcode());
        submitHeadBean.setWork(RTravellingBean.getHead().getWork());


        SubmitBean beanList = new SubmitBean();
        beanList.setUserid(sUserInfo.getUserId());
        beanList.setPk_group(sUserInfo.getPk_group());
        beanList.setHead(submitHeadBean);

        log.info("打印差旅费借款:{}", new Gson().toJson(beanList));
        try {
            String s = "";
            if ("loc".equals(string)) {
                s = HttpUtils.requestPostBody(C.SUBMIT_TRAVLLING_ADD_URL_DEV, JSONObject.toJSONString(beanList), null);
            } else if ("dev".equals(string)){
                s = HttpUtils.requestPostBody(C.SUBMIT_TRAVLLING_ADD_URL_DEV, JSONObject.toJSONString(beanList), null);
            }else if("gray".equals(string)){
                s = HttpUtils.requestPostBody(C.SUBMIT_TRAVLLING_ADD_URL_PRD, JSONObject.toJSONString(beanList), null);
            }else{
                s = HttpUtils.requestPostBody(C.SUBMIT_TRAVLLING_ADD_URL_PRD, JSONObject.toJSONString(beanList), null);
            }

            log.error("推送成功："+s);
            String substring = commonService.stringJieQu(s);
            JSONObject jsonObject = JSONObject.parseObject(substring);

            log.info("jsonObjectResult" + jsonObject);
            if("0".equals(String.valueOf(jsonObject.get("result")))){
                return new TResult<>(0,null,String.valueOf(jsonObject.get("billcode")));
            }else{
                return new TResult<>(1,String.valueOf(jsonObject.get("message")));
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("推送失败" + e);
            return new TResult<>(C.ERROR_CODE_COM,"推送失败");
        }

    }

    @PostMapping("/submitPass")
    @ApiOperation(value = "提交通用借款单")
    //@ValidateApiNotAuth
    public TResult<SubmitBean> submitPass(HttpServletRequest request, @RequestBody SubmitBean RTravellingBean) {
        log.info("通用借款单:{}", new Gson().toJson(RTravellingBean));
        Date date = new Date();

        String userId = SpringUtil.getSessionUserId(request);

        //SUserInfo sUserInfo =  C.sessionInfo.get("userId_"+userId);
        SUserInfo sUserInfo = (SUserInfo) redisService.get(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + userId);

        SubmitHeadBean submitHeadBean = new SubmitHeadBean();
        submitHeadBean.setBillcode(RTravellingBean.getHead().getBillcode());
        submitHeadBean.setWork(RTravellingBean.getHead().getWork());

        SubmitBean beanList = new SubmitBean();
        beanList.setUserid(sUserInfo.getUserId());
        beanList.setPk_group(sUserInfo.getPk_group());
        beanList.setHead(submitHeadBean);


        log.info("打印通用借款单:{}", new Gson().toJson(beanList));
        try {
            String s = "";
            if ("loc".equals(string)) {
                s = HttpUtils.requestPostBody(C.SUBMIT_TRAVLLING_ADD_URL_DEV, JSONObject.toJSONString(beanList), null);
            } else if ("dev".equals(string)){
                s = HttpUtils.requestPostBody(C.SUBMIT_TRAVLLING_ADD_URL_DEV, JSONObject.toJSONString(beanList), null);
            }else if("gray".equals(string)){
                s = HttpUtils.requestPostBody(C.SUBMIT_TRAVLLING_ADD_URL_PRD, JSONObject.toJSONString(beanList), null);
            }else{
                s = HttpUtils.requestPostBody(C.SUBMIT_TRAVLLING_ADD_URL_PRD, JSONObject.toJSONString(beanList), null);
            }

            log.error("推送成功："+s);
            String substring = commonService.stringJieQu(s);
            JSONObject jsonObject = JSONObject.parseObject(substring);

            log.info("jsonObjectResult" + jsonObject);
            if("0".equals(String.valueOf(jsonObject.get("result")))){
                return new TResult<>(0,null,String.valueOf(jsonObject.get("billcode")));
            }else{
                return new TResult<>(1,String.valueOf(jsonObject.get("message")));
            }

        }catch (Exception e){
            e.printStackTrace();
            log.error("推送失败" + e);
            return new TResult<>(C.ERROR_CODE_COM,"推送失败");
        }

    }


    @PostMapping("/deleteTravelling")
    @ApiOperation(value = "删除借款单")
    public TResult<SubmitBean> deleteTravelling(HttpServletRequest request, @RequestBody SubmitBean RSubmitBean) {
        log.info("借款:{}", new Gson().toJson(RSubmitBean));
        Date date = new Date();

        String userId = SpringUtil.getSessionUserId(request);
        SUserInfo sUserInfo = (SUserInfo) redisService.get(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + userId);
        //SUserInfo sUserInfo =  C.sessionInfo.get("userId_"+userId);


        SubmitHeadBean submitHeadBean = new SubmitHeadBean();
        submitHeadBean.setBillcode(RSubmitBean.getHead().getBillcode());


        SubmitBean beanList = new SubmitBean();
        beanList.setUserid(sUserInfo.getUserId());
        beanList.setPk_group(sUserInfo.getPk_group());
        beanList.setHead(submitHeadBean);

        log.info("打印借款:{}", new Gson().toJson(beanList));
        try {
            String s = "";
            if ("loc".equals(string)) {
                s = HttpUtils.requestPostBody(C.DELETE_TRAVLLING_ADD_URL_DEV, JSONObject.toJSONString(beanList), null);
            } else if ("dev".equals(string)){
                s = HttpUtils.requestPostBody(C.DELETE_TRAVLLING_ADD_URL_DEV, JSONObject.toJSONString(beanList), null);
            }else if("gray".equals(string)){
                s = HttpUtils.requestPostBody(C.DELETE_TRAVLLING_ADD_URL_PRD, JSONObject.toJSONString(beanList), null);
            }else{
                s = HttpUtils.requestPostBody(C.DELETE_TRAVLLING_ADD_URL_PRD, JSONObject.toJSONString(beanList), null);
            }
            log.error("推送成功："+s);
            String substring = commonService.stringJieQu(s);
            JSONObject jsonObject = JSONObject.parseObject(substring);

            log.info("jsonObjectResult" + jsonObject);
            if("0".equals(String.valueOf(jsonObject.get("result")))){
                return new TResult<>(0,null,String.valueOf(jsonObject.get("billcode")));
            }else{
                return new TResult<>(1,String.valueOf(jsonObject.get("message")));
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("推送失败" + e);
            return new TResult<>(C.ERROR_CODE_COM,"推送失败");
        }

    }


    @PostMapping("/updateTravellingBean")
    @ApiOperation(value = "修改差旅费借款单")
    //@ValidateApiNotAuth
    public TResult<TravellingBean> updateTravellingBean(HttpServletRequest request, @RequestBody TravellingBean RTravellingBean) {
        log.info("修改差旅费借款单:{}", new Gson().toJson(RTravellingBean));
        Date date = new Date();

        String userId = SpringUtil.getSessionUserId(request);
        SUserInfo sUserInfo = (SUserInfo) redisService.get(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + userId);
        //SUserInfo sUserInfo =  C.sessionInfo.get("userId_"+userId);

        if(StringUtil.isEmpty(RTravellingBean.getHead().getBillcode())){
            return new TResult<>(C.ERROR_CODE_COM,"单据不能为空");
        }

        CostHeadBean costHeadBean = new CostHeadBean();
        costHeadBean.setCreator(sUserInfo.getUsercode());
        costHeadBean.setPk_org(sUserInfo.getOrgcode());//sUserInfo.getOrgcode()
        costHeadBean.setIncident(RTravellingBean.getHead().getIncident());
        costHeadBean.setPk_payorg(sUserInfo.getOrgcode());//支付单位
        costHeadBean.setFkyhzh("");//单位银行账户
        costHeadBean.setJsfs("");//结算方式
        costHeadBean.setCashitem("");//现金流量项目
        costHeadBean.setCashproj("");//资金计划项目
        costHeadBean.setFydwbm(sUserInfo.getOrgcode());//sUserInfo.getOrgcode()
        costHeadBean.setFydeptid(sUserInfo.getDeptcode());//sUserInfo.getDeptcode()
        costHeadBean.setSzxmid(RTravellingBean.getHead().getSzxmid());
        costHeadBean.setHbbm("");//供应商
        costHeadBean.setCustomer("");//客户
        costHeadBean.setReceiver(RTravellingBean.getHead().getReceiver());//sUserInfo.getCode()
        costHeadBean.setBzbm(RTravellingBean.getHead().getBzbm());
        costHeadBean.setDjlxbm("2631");//交易类型
        costHeadBean.setDjrq(RTravellingBean.getHead().getDjrq());
        costHeadBean.setDwbm(RTravellingBean.getHead().getDwbm());//sUserInfo.getOrgcode()
        costHeadBean.setDeptid(RTravellingBean.getHead().getDeptid());//sUserInfo.getDeptcode()
        costHeadBean.setSkyhzh(RTravellingBean.getHead().getSkyhzh());
        costHeadBean.setBbje(RTravellingBean.getHead().getBbje());
        costHeadBean.setBillcode(RTravellingBean.getHead().getBillcode());
        costHeadBean.setZyx25(RTravellingBean.getHead().getZyx25());
        costHeadBean.setWork(RTravellingBean.getHead().getWork());
        costHeadBean.setFjzs(RTravellingBean.getHead().getFjzs());


        List<DetailsBean> details = RTravellingBean.getDetails();
        List<DetailsBean> allList =  new ArrayList<>();

        for(DetailsBean list : details){
            DetailsBean bean = new DetailsBean();
            list.setSzxmcode(RTravellingBean.getHead().getSzxmid());
            BeanUtils.copyProperties(list, bean);

            if(StringUtil.notEmpty(list.getDefitem4())){
                String sql = SqlProvider.getEnumvalueCode(list.getDefitem4(),"");
                CostBean costBean = commonService.selectObjectBySql(sql, CostBean.class);
                if(costBean !=null){
                    bean.setDefitem4(costBean.getName());
                }
            }else{
                bean.setDefitem4("");
            }

            allList.add(bean);
        }

        TravellingBean beanList = new TravellingBean();
        beanList.setUserid(sUserInfo.getUserId());
        beanList.setPk_group(sUserInfo.getPk_group());
        beanList.setHead(costHeadBean);
        beanList.setDetails(allList);

        log.info("打印借款:{}", new Gson().toJson(beanList));
        try {
            String s = "";
            if ("loc".equals(string)) {
                s = HttpUtils.requestPostBody(C.UPDATE_TRAVLLING_ADD_URL_DEV, JSONObject.toJSONString(beanList), null);
            } else if ("dev".equals(string)){
                s = HttpUtils.requestPostBody(C.UPDATE_TRAVLLING_ADD_URL_DEV, JSONObject.toJSONString(beanList), null);
            }else if("gray".equals(string)){
                s = HttpUtils.requestPostBody(C.UPDATE_TRAVLLING_ADD_URL_PRD, JSONObject.toJSONString(beanList), null);
            }else{
                s = HttpUtils.requestPostBody(C.UPDATE_TRAVLLING_ADD_URL_PRD, JSONObject.toJSONString(beanList), null);
            }
            log.error("推送成功："+s);
            String substring = commonService.stringJieQu(s);
            JSONObject jsonObject = JSONObject.parseObject(substring);

            log.info("jsonObjectResult" + jsonObject);
            if("0".equals(String.valueOf(jsonObject.get("result")))){
                return new TResult<>(0,null,String.valueOf(jsonObject.get("billcode")));
            }else{
                return new TResult<>(1,String.valueOf(jsonObject.get("message")));
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("推送失败" + e);
            return new TResult<>(C.ERROR_CODE_COM,"推送失败");
        }

    }

    @PostMapping("/updatePass")
    @ApiOperation(value = "修改通用借款单")
    //@ValidateApiNotAuth
    public TResult<GeneralListBean> updatePass(HttpServletRequest request, @RequestBody GeneralListBean RTravellingBean) {
        log.info("修改通用借款单:{}", new Gson().toJson(RTravellingBean));
        Date date = new Date();

        String userId = SpringUtil.getSessionUserId(request);

        //SUserInfo sUserInfo =  C.sessionInfo.get("userId_"+userId);
        SUserInfo sUserInfo = (SUserInfo) redisService.get(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + userId);

        GeneralBean costHeadBean = new GeneralBean();
        costHeadBean.setCreator(sUserInfo.getUsercode());
        costHeadBean.setPk_org(sUserInfo.getOrgcode());//sUserInfo.getOrgcode()
        costHeadBean.setIncident(RTravellingBean.getHead().getIncident());
        costHeadBean.setPk_payorg(sUserInfo.getOrgcode());//支付单位
        costHeadBean.setFkyhzh("");//单位银行账户
        costHeadBean.setJsfs("");//结算方式
        costHeadBean.setCashitem("");//现金流量项目
        costHeadBean.setCashproj("");//资金计划项目
        costHeadBean.setFydwbm(sUserInfo.getOrgcode());//sUserInfo.getOrgcode()
        costHeadBean.setFydeptid(sUserInfo.getDeptcode());//sUserInfo.getDeptcode()
        costHeadBean.setSzxmid(RTravellingBean.getHead().getSzxmid());
        costHeadBean.setHbbm("");//供应商
        costHeadBean.setCustomer("");//客户
        costHeadBean.setReceiver(RTravellingBean.getHead().getReceiver());//sUserInfo.getCode()
        costHeadBean.setBzbm(RTravellingBean.getHead().getBzbm());
        costHeadBean.setDjlxbm("263X-Cxx-TYJKD");//交易类型
        costHeadBean.setDjrq(RTravellingBean.getHead().getDjrq());
        costHeadBean.setDwbm(RTravellingBean.getHead().getDwbm());//sUserInfo.getOrgcode()
        costHeadBean.setDeptid(RTravellingBean.getHead().getDeptid());//sUserInfo.getDeptcode()
        costHeadBean.setSkyhzh(RTravellingBean.getHead().getSkyhzh());
        costHeadBean.setZyx25(RTravellingBean.getHead().getZyx25());
        costHeadBean.setZyx1(RTravellingBean.getHead().getZyx1());
        costHeadBean.setBbje(RTravellingBean.getHead().getBbje());
        costHeadBean.setBillcode(RTravellingBean.getHead().getBillcode());
        costHeadBean.setWork(RTravellingBean.getHead().getWork());
        costHeadBean.setFjzs(RTravellingBean.getHead().getFjzs());

        GeneralListBean beanList = new GeneralListBean();
        beanList.setUserid(sUserInfo.getUserId());
        beanList.setPk_group(sUserInfo.getPk_group());
        beanList.setHead(costHeadBean);
        beanList.setDetails(RTravellingBean.getDetails());

        log.info("打印通用借款单:{}", new Gson().toJson(beanList));
        try {
            String s = "";
            if ("loc".equals(string)) {
                s = HttpUtils.requestPostBody(C.UPDATE_TRAVLLING_ADD_URL_DEV, JSONObject.toJSONString(beanList), null);
            } else if ("dev".equals(string)){
                s = HttpUtils.requestPostBody(C.UPDATE_TRAVLLING_ADD_URL_DEV, JSONObject.toJSONString(beanList), null);
            }else if("gray".equals(string)){
                s = HttpUtils.requestPostBody(C.UPDATE_TRAVLLING_ADD_URL_PRD, JSONObject.toJSONString(beanList), null);
            }else{
                s = HttpUtils.requestPostBody(C.UPDATE_TRAVLLING_ADD_URL_PRD, JSONObject.toJSONString(beanList), null);
            }
            log.error("推送成功："+s);

            String substring = commonService.stringJieQu(s);
            JSONObject jsonObject = JSONObject.parseObject(substring);

            log.info("jsonObjectResult" + jsonObject);
            if("0".equals(String.valueOf(jsonObject.get("result")))){
                return new TResult<>(0,null,String.valueOf(jsonObject.get("billcode")));
            }else{
                return new TResult<>(1,String.valueOf(jsonObject.get("message")));
            }

        }catch (Exception e){
            e.printStackTrace();
            log.error("推送失败" + e);
            return new TResult<>(C.ERROR_CODE_COM,"推送失败");
        }

    }


    @PostMapping("/selectApprover")
    @ApiOperation(value = "查询借款审批人")
    //@ValidateApiNotAuth
    public TResult<UserBean> updatePass(HttpServletRequest request, @RequestBody SubmitBean RTravellingBean) {

        log.info("查询借款:{}", new Gson().toJson(RTravellingBean));
        Date date = new Date();

        String userId = SpringUtil.getSessionUserId(request);
        SUserInfo sUserInfo = (SUserInfo) redisService.get(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + userId);
        //SUserInfo sUserInfo =  C.sessionInfo.get("userId_"+userId);


        SubmitHeadBean submitHeadBean = new SubmitHeadBean();
        submitHeadBean.setBillcode(RTravellingBean.getHead().getBillcode());


        SubmitBean beanList = new SubmitBean();
        beanList.setUserid(sUserInfo.getUserId());
        beanList.setPk_group(sUserInfo.getPk_group());
        beanList.setHead(submitHeadBean);

        log.info("打印查询借款审批人:{}", new Gson().toJson(beanList));


        try {
            String s = "";
            if ("loc".equals(string)) {
                s = HttpUtils.requestPostBody(C.WORK_TRAVLLING_URL_DEV, JSONObject.toJSONString(beanList), null);
            } else if ("dev".equals(string)){
                s = HttpUtils.requestPostBody(C.WORK_TRAVLLING_URL_DEV, JSONObject.toJSONString(beanList), null);
            }else if("gray".equals(string)){
                s = HttpUtils.requestPostBody(C.WORK_TRAVLLING_URL_PRD, JSONObject.toJSONString(beanList), null);
            }else{
                s = HttpUtils.requestPostBody(C.WORK_TRAVLLING_URL_PRD, JSONObject.toJSONString(beanList), null);
            }
            log.error("推送成功："+s);

            String substring = commonService.stringJieQu(s);
            JSONObject jsonObject = JSONObject.parseObject(substring);

            log.info("jsonObjectResult" + jsonObject);
            if("0".equals(String.valueOf(jsonObject.get("result")))){

                if(StringUtil.isEmpty(String.valueOf(jsonObject.get("billcode")))){
                    return new TResult<>(0,null,String.valueOf(jsonObject.get("billcode")));
                }else{
                    List<UserBean> list = new ArrayList<>();
                    String string = String.valueOf(jsonObject.get("billcode"));
                    String[] str1 = string.split(",");
                    for(String s1 : str1){
                        System.out.println("replaceAll:"+s1);
                        UserBean userBean = new UserBean();
                        userBean.setName(s1);

                        String sql = SqlProvider.getUserId(s1);
                        UserBean username = commonService.selectObjectBySql(sql,UserBean.class);
                        if(username != null){
                            userBean.setCuserid(username.getCuserid());
                        }
                        list.add(userBean);

                    }
                    return new TResult<>(0,null,list);
                }

            }else{
                return new TResult<>(1,String.valueOf(jsonObject.get("message")));
            }

        }catch (Exception e){
            e.printStackTrace();
            log.error("推送失败" + e);
            return new TResult<>(C.ERROR_CODE_COM,"推送失败");
        }

    }


    @PostMapping("/approveTravelling")
    @ApiOperation(value = "借款单批准接口")
    //@ValidateApiNotAuth
    public TResult<SubmitBean> approveTravelling(HttpServletRequest request, @RequestBody SubmitBean RTravellingBean) {
        log.info("借款单批准接口:{}", new Gson().toJson(RTravellingBean));
        Date date = new Date();

        String userId = SpringUtil.getSessionUserId(request);

        //SUserInfo sUserInfo =  C.sessionInfo.get("userId_"+userId);
        SUserInfo sUserInfo = (SUserInfo) redisService.get(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + userId);

        SubmitHeadBean beanList = new SubmitHeadBean();
        beanList.setBillcode(RTravellingBean.getHead().getBillcode());
        beanList.setChecknote(RTravellingBean.getHead().getChecknote());
        beanList.setWork(RTravellingBean.getHead().getWork());

        SubmitBean beanApprove = new SubmitBean();
        beanApprove.setUserid(sUserInfo.getUserId());
        beanApprove.setPk_group(sUserInfo.getPk_group());
        beanApprove.setHead(beanList);


        log.info("打印借款单批准接口:{}", new Gson().toJson(beanApprove));
        try {
            String s = "";
            if ("loc".equals(string)) {
                s = HttpUtils.requestPostBody(C.APPROVE_TRAVLLING_URL_DEV, JSONObject.toJSONString(beanApprove), null);
            } else if ("dev".equals(string)){
                s = HttpUtils.requestPostBody(C.APPROVE_TRAVLLING_URL_DEV, JSONObject.toJSONString(beanApprove), null);
            }else if("gray".equals(string)){
                s = HttpUtils.requestPostBody(C.APPROVE_TRAVLLING_URL_PRD, JSONObject.toJSONString(beanApprove), null);
            }else{
                s = HttpUtils.requestPostBody(C.APPROVE_TRAVLLING_URL_PRD, JSONObject.toJSONString(beanApprove), null);
            }

            log.error("推送成功："+s);
            String substring = commonService.stringJieQu(s);
            JSONObject jsonObject = JSONObject.parseObject(substring);

            log.info("jsonObjectResult" + jsonObject);
            if("0".equals(String.valueOf(jsonObject.get("result")))){
                return new TResult<>(0,null,String.valueOf(jsonObject.get("billcode")));
            }else{
                return new TResult<>(1,String.valueOf(jsonObject.get("message")));
            }

        }catch (Exception e){
            e.printStackTrace();
            log.error("推送失败" + e);
            return new TResult<>(C.ERROR_CODE_COM,"推送失败");
        }

    }

    @PostMapping("/rejectTravelling")
    @ApiOperation(value = "借款单驳回接口")
    //@ValidateApiNotAuth
    public TResult<BeanApprove> rejectTravelling(HttpServletRequest request, @RequestBody BeanApprove RTravellingBean) {
        log.info("借款单驳回接口:{}", new Gson().toJson(RTravellingBean));
        Date date = new Date();

        String userId = SpringUtil.getSessionUserId(request);

        //SUserInfo sUserInfo =  C.sessionInfo.get("userId_"+userId);
        SUserInfo sUserInfo = (SUserInfo) redisService.get(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + userId);

        BeanList beanList = new BeanList();
        beanList.setBillcode(RTravellingBean.getHead().getBillcode());
        beanList.setChecknote(RTravellingBean.getHead().getChecknote());
        beanList.setActivitydefid(RTravellingBean.getHead().getActivitydefid());
        beanList.setActivitydefname(RTravellingBean.getHead().getActivitydefname());
        beanList.setPx(RTravellingBean.getHead().getPx());

        BeanApprove beanApprove = new BeanApprove();
        beanApprove.setUserid(sUserInfo.getUserId());
        beanApprove.setPk_group(sUserInfo.getPk_group());
        beanApprove.setHead(beanList);


        log.info("打印借款单驳回接口:{}", new Gson().toJson(beanApprove));
        try {
            String s = "";
            if ("loc".equals(string)) {
                s = HttpUtils.requestPostBody(C.REJECT_TRAVLLING_URL_DEV, JSONObject.toJSONString(beanApprove), null);
            } else if ("dev".equals(string)){
                s = HttpUtils.requestPostBody(C.REJECT_TRAVLLING_URL_DEV, JSONObject.toJSONString(beanApprove), null);
            }else if("gray".equals(string)){
                s = HttpUtils.requestPostBody(C.REJECT_TRAVLLING_URL_PRD, JSONObject.toJSONString(beanApprove), null);
            }else{
                s = HttpUtils.requestPostBody(C.REJECT_TRAVLLING_URL_PRD, JSONObject.toJSONString(beanApprove), null);
            }

            log.error("推送成功："+s);
            String substring = commonService.stringJieQu(s);
            JSONObject jsonObject = JSONObject.parseObject(substring);

            log.info("jsonObjectResult" + jsonObject);
            if("0".equals(String.valueOf(jsonObject.get("result")))){
                return new TResult<>(0,null,String.valueOf(jsonObject.get("billcode")));
            }else{
                return new TResult<>(1,String.valueOf(jsonObject.get("message")));
            }

        }catch (Exception e){
            e.printStackTrace();
            log.error("推送失败" + e);
            return new TResult<>(C.ERROR_CODE_COM,"推送失败");
        }

    }

    @PostMapping("/backTravelling")
    @ApiOperation(value = "收回借款单")
    //@ValidateApiNotAuth
    public TResult<SubmitBean> backTravelling(HttpServletRequest request, @RequestBody SubmitBean RTravellingBean) {
        log.info("收回借款单:{}", new Gson().toJson(RTravellingBean));
        Date date = new Date();

        String userId = SpringUtil.getSessionUserId(request);
        SUserInfo sUserInfo = (SUserInfo) redisService.get(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + userId);
        //SUserInfo sUserInfo =  C.sessionInfo.get("userId_"+userId);


        SubmitHeadBean submitHeadBean = new SubmitHeadBean();
        submitHeadBean.setBillcode(RTravellingBean.getHead().getBillcode());


        SubmitBean beanList = new SubmitBean();
        beanList.setUserid(sUserInfo.getUserId());
        beanList.setPk_group(sUserInfo.getPk_group());
        beanList.setHead(submitHeadBean);

        log.info("打印收回借款单:{}", new Gson().toJson(beanList));
        try {
            String s = "";
            if ("loc".equals(string)) {
                s = HttpUtils.requestPostBody(C.BACK_TRAVLLING_URL_DEV, JSONObject.toJSONString(beanList), null);
            } else if ("dev".equals(string)){
                s = HttpUtils.requestPostBody(C.BACK_TRAVLLING_URL_DEV, JSONObject.toJSONString(beanList), null);
            }else if("gray".equals(string)){
                s = HttpUtils.requestPostBody(C.BACK_TRAVLLING_URL_PRD, JSONObject.toJSONString(beanList), null);
            }else{
                s = HttpUtils.requestPostBody(C.BACK_TRAVLLING_URL_PRD, JSONObject.toJSONString(beanList), null);
            }

            log.error("推送成功："+s);
            String substring = commonService.stringJieQu(s);
            JSONObject jsonObject = JSONObject.parseObject(substring);

            log.info("jsonObjectResult" + jsonObject);
            if("0".equals(String.valueOf(jsonObject.get("result")))){
                return new TResult<>(0,null,String.valueOf(jsonObject.get("billcode")));
            }else{
                return new TResult<>(1,String.valueOf(jsonObject.get("message")));
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("推送失败" + e);
            return new TResult<>(C.ERROR_CODE_COM,"推送失败");
        }

    }

    @PostMapping("/selectBoHuiTravelling")
    @ApiOperation(value = "环节查询驳回借款单接口")
    //@ValidateApiNotAuth
    public TResult<SelectBean> selectBoHuiTravelling(HttpServletRequest request, @RequestBody SelectListBean rSelectBean) {
        log.info("查询环节查询驳回借款单接口:{}", new Gson().toJson(rSelectBean));
        Date date = new Date();

        String userId = SpringUtil.getSessionUserId(request);
        SUserInfo sUserInfo = (SUserInfo) redisService.get(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + userId);
        //SUserInfo sUserInfo =  C.sessionInfo.get("userId_"+userId);


        SelectBean selectBean = new SelectBean();
        selectBean.setBillcode(rSelectBean.getHead().getBillcode());
        selectBean.setDjlxbm(rSelectBean.getHead().getDjlxbm());


        SelectListBean beanList = new SelectListBean();
        beanList.setUserid(sUserInfo.getUserId());
        beanList.setPk_group(sUserInfo.getPk_group());
        beanList.setHead(selectBean);

        log.info("打印环节查询驳回借款单接口:{}", new Gson().toJson(beanList));
        try {
            String s = "";
            if ("loc".equals(string)) {
                s = HttpUtils.requestPostBody(C.XPDL_TRAVLLING_URL_DEV, JSONObject.toJSONString(beanList), null);
            } else if ("dev".equals(string)){
                s = HttpUtils.requestPostBody(C.XPDL_TRAVLLING_URL_DEV, JSONObject.toJSONString(beanList), null);
            }else if("gray".equals(string)){
                s = HttpUtils.requestPostBody(C.XPDL_TRAVLLING_URL_PRD, JSONObject.toJSONString(beanList), null);
            }else{
                s = HttpUtils.requestPostBody(C.XPDL_TRAVLLING_URL_PRD, JSONObject.toJSONString(beanList), null);
            }

            log.error("推送成功："+s);
            String substring = commonService.stringJieQu(s);
            JSONObject jsonObject = JSONObject.parseObject(substring);

            log.info("jsonObjectResult" + jsonObject);

            if("0".equals(String.valueOf(jsonObject.get("result")))){

                String string = jsonObject.get("bxbresult").toString();

                List<ActivityBean> listArrayList = JSON.parseArray(string, ActivityBean.class);
                if(listArrayList.size() > 0){
                    for(ActivityBean bean : listArrayList){

                        String name = bean.getName().replace("u003c","<");
                        String beanName = name.replace("u003e",">");
                        bean.setName(beanName);
                    }
                }

                return new TResult<>(0,null,listArrayList);
            }else{
                return new TResult<>(1,String.valueOf(jsonObject.get("message")));
            }


        }catch (Exception e){
            e.printStackTrace();
            log.error("推送失败" + e);
            return new TResult<>(C.ERROR_CODE_COM,"推送失败");
        }

    }


    @PostMapping("/assignTravelling")
    @ApiOperation(value = "查询分组员工列表")
    //@ValidateApiNotAuth
    public TResult<SubmitBean> assignTravelling(HttpServletRequest request, @RequestBody SubmitBean SubmitBean) {
        log.info("查询分组员工列表:{}", new Gson().toJson(SubmitBean));
        Date date = new Date();

        String userId = SpringUtil.getSessionUserId(request);

        SUserInfo sUserInfo = (SUserInfo) redisService.get(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + userId);

        SubmitHeadBean submitHeadBean = new SubmitHeadBean();
        submitHeadBean.setBillcode(SubmitBean.getHead().getBillcode());

        SubmitBean beanList = new SubmitBean();
        beanList.setUserid(sUserInfo.getUserId());
        beanList.setPk_group(sUserInfo.getPk_group());
        beanList.setHead(submitHeadBean);


        log.info("打印查询分组员工列表:{}", new Gson().toJson(beanList));
        try {
            String s = "";
            if ("loc".equals(string)) {
                s = HttpUtils.requestPostBody(C.ASSIGN_TRAVLLING_URL_DEV, JSONObject.toJSONString(beanList), null);
            } else if ("dev".equals(string)){
                s = HttpUtils.requestPostBody(C.ASSIGN_TRAVLLING_URL_DEV, JSONObject.toJSONString(beanList), null);
            }else if("gray".equals(string)){
                s = HttpUtils.requestPostBody(C.ASSIGN_TRAVLLING_URL_PRD, JSONObject.toJSONString(beanList), null);
            }else{
                s = HttpUtils.requestPostBody(C.ASSIGN_TRAVLLING_URL_PRD, JSONObject.toJSONString(beanList), null);
            }

            log.error("推送成功："+s);
            String string = JSONObject.parseObject(s, String.class);
            JSONObject jsonObject = JSONObject.parseObject(string);

            log.info("jsonObjectResult" + jsonObject);

            if("0".equals(String.valueOf(jsonObject.get("result")))){

                String jsonArray = jsonObject.getString("billcode");

                if(StringUtil.notEmpty(jsonArray)) {
                    List<BxSPResult> listArrayList = JSONObject.parseArray(jsonArray, BxSPResult.class);
                    if (listArrayList.size() > 0) {
                        for (BxSPResult bean : listArrayList) {

                            List<BxSPDetResult> results = bean.getBxdetret();
                            if (results.size() > 0) {
                                for (BxSPDetResult list : results) {
                                    list.setActid(bean.getActid());
                                }
                            }
                        }
                    }
                    return new TResult<>(0, null, listArrayList);
                }else{
                    return new TResult<>(0,null,null);
                }
            }else{
                return new TResult<>(0,String.valueOf(jsonObject.get("message")));
            }

        }catch (Exception e){
            e.printStackTrace();
            log.error("推送失败" + e);
            return new TResult<>(C.ERROR_CODE_COM,"推送失败");
        }

    }


}
