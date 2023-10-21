package com.liuc.server.api.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author S.Yang
 * @date 2021/2/25 11:08
 */
@RestController
@RequestMapping("/api/v1/hkBillinfo")
@Api(value = "hkBillinfo", description = "还款单", tags = "还款单")
@Slf4j
public class HkBillInfoController {

    @Value("${spring.profiles.active}")
    private String string;
    @Autowired
    private RedisService redisService;
    @Autowired
    private CommonService commonService;

//    @PostMapping("/add")
//    @ApiOperation(value = "还款单")
//    public TResult<HxBillInfoAndErsBean> add(HttpServletRequest request, @RequestBody HxBillInfoAndErsBean hxBillInfoAndErsBean) {
//
//        log.info("还款单{}", new Gson().toJson(hxBillInfoAndErsBean));
//        Date date = new Date();
//
//        String userId = SpringUtil.getSessionUserId(request);
//
//        //SUserInfo sUserInfo =  C.sessionInfo.get("userId_"+userId);
//        SUserInfo sUserInfo = (SUserInfo) redisService.get(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + userId);
//
//        HxBillInfoBean hxBillInfoBean = hxBillInfoAndErsBean.getEr_bxzb();
//        //loanBean1.setOaid();//主键
//        hxBillInfoBean.setDjbh(commonService.getSn(3));//单据编号
//
//        hxBillInfoBean.setApprover(sUserInfo.getUsercode());
//        hxBillInfoBean.setCreationtime(DateUtils.formatDate(date));
//        hxBillInfoBean.setCreator(sUserInfo.getUsercode());
//        hxBillInfoBean.setApprovertime(DateUtils.formatDate(date));
//        hxBillInfoAndErsBean.setEr_bxzb(hxBillInfoBean);
//        try {
//            String s = HttpUtils.requestPostBody(C.HX_BILL_ADD_URL, JSONObject.toJSONString(hxBillInfoAndErsBean), null);
//            log.error("推送成功："+s);
//            org.json.JSONObject jsonObjectResult = new org.json.JSONObject(s);
//            String data = String.valueOf(jsonObjectResult.get("data"));
//            org.json.JSONObject dataResult = new org.json.JSONObject(data);
//
//            String head = String.valueOf(dataResult.get("head"));
//            String body = String.valueOf(dataResult.getJSONArray("body"));
//
//            org.json.JSONObject headResult = new org.json.JSONObject(head);
//            List<BodyBean> bodyBean = JSON.parseArray(body, BodyBean.class);
//
//            HeadBean headBean = new HeadBean();
//            headBean.setNcid(headResult.get("ncid").toString());
//            headBean.setOaid(headResult.get("oaid").toString());
//
//            BodyAndHeadBean bodyAndHeadBean = new BodyAndHeadBean();
//            bodyAndHeadBean.setBody(bodyBean);
//            bodyAndHeadBean.setHead(headBean);
//
//            return new TResult<>(bodyAndHeadBean);
//        }catch (Exception e){
//            e.printStackTrace();
//            log.error("推送失败" + e);
//            return new TResult<>(C.ERROR_CODE_COM,"推送失败");
//        }
//    }

    @PostMapping("/addRepayment")
    @ApiOperation(value = "新增还款单")
    public TResult<RepaymentBean> add(HttpServletRequest request, @RequestBody RepaymentBean RRepaymentBean) {

        log.info("还款单:{}", new Gson().toJson(RRepaymentBean));

        String userId = SpringUtil.getSessionUserId(request);

        //SUserInfo sUserInfo =  C.sessionInfo.get("userId_"+userId);
        SUserInfo sUserInfo = (SUserInfo) redisService.get(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + userId);

        CostHeadBean costHeadBean = new CostHeadBean();
        costHeadBean.setCreator(sUserInfo.getUsercode());
        costHeadBean.setPk_org(sUserInfo.getOrgcode());
        costHeadBean.setIncident(RRepaymentBean.getHead().getIncident());
        costHeadBean.setPk_payorg(sUserInfo.getOrgcode());//支付单位
        costHeadBean.setFkyhzh("");//单位银行账户
        costHeadBean.setJsfs("");//结算方式
        costHeadBean.setCashitem("");//现金流量项目
        costHeadBean.setCashproj("");//资金计划项目
        costHeadBean.setFydwbm(RRepaymentBean.getHead().getFydwbm());//sUserInfo.getOrgcode()
        costHeadBean.setFydeptid(RRepaymentBean.getHead().getFydeptid());//sUserInfo.getDeptcode()
        costHeadBean.setSzxmid(RRepaymentBean.getHead().getSzxmid());
        costHeadBean.setHbbm("");//供应商
        costHeadBean.setCustomer("");//客户
        costHeadBean.setReceiver(RRepaymentBean.getHead().getReceiver());//sUserInfo.getCode()
        costHeadBean.setBzbm(RRepaymentBean.getHead().getBzbm());
        costHeadBean.setDjlxbm("2647");//交易类型
        costHeadBean.setDjrq(RRepaymentBean.getHead().getDjrq());
        costHeadBean.setDwbm(RRepaymentBean.getHead().getDwbm());//sUserInfo.getOrgcode()
        costHeadBean.setDeptid(RRepaymentBean.getHead().getDeptid());//sUserInfo.getDeptcode()
        costHeadBean.setSkyhzh(RRepaymentBean.getHead().getSkyhzh());
        costHeadBean.setWork(RRepaymentBean.getHead().getWork());
        costHeadBean.setFjzs(RRepaymentBean.getHead().getFjzs());
        List<ContrastsBean> contrasts = RRepaymentBean.getContrasts();
        List<ContrastsBean> allList =  new ArrayList<>();

        if(contrasts.size() > 0){
            for(ContrastsBean contrastsBean :contrasts ){
                ContrastsBean bean = new ContrastsBean();

                BeanUtils.copyProperties(contrastsBean, bean);
                bean.setSxrq(DateUtils.formatDate(new Date()));
                allList.add(bean);
            }
        }

        RepaymentBean beanList = new RepaymentBean();
        beanList.setUserid(sUserInfo.getUserId());
        beanList.setPk_group(sUserInfo.getPk_group());
        beanList.setHead(costHeadBean);
        beanList.setContrasts(allList);
        log.info("打印还款单:{}", new Gson().toJson(beanList));


        try {
            String s = "";
            if ("loc".equals(string)) {
                s = HttpUtils.requestPostBody(C.INSERT_REPAYMENT_ADD_URL_DEV, JSONObject.toJSONString(beanList), null);
            } else if ("dev".equals(string)){
                s = HttpUtils.requestPostBody(C.INSERT_REPAYMENT_ADD_URL_DEV, JSONObject.toJSONString(beanList), null);
            }else if("gray".equals(string)){
                s = HttpUtils.requestPostBody(C.INSERT_REPAYMENT_ADD_URL_PRD, JSONObject.toJSONString(beanList), null);
            }else{
                s = HttpUtils.requestPostBody(C.INSERT_REPAYMENT_ADD_URL_PRD, JSONObject.toJSONString(beanList), null);
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

    @PostMapping("/submitRepayment")
    @ApiOperation(value = "提交还款单")
    public TResult<SubmitBean> submitRepayment(HttpServletRequest request, @RequestBody SubmitBean RRepaymentBean) {

        log.info("还款单:{}", new Gson().toJson(RRepaymentBean));

        String userId = SpringUtil.getSessionUserId(request);

        //SUserInfo sUserInfo =  C.sessionInfo.get("userId_"+userId);
        SUserInfo sUserInfo = (SUserInfo) redisService.get(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + userId);

        SubmitHeadBean submitHeadBean = new SubmitHeadBean();
        submitHeadBean.setBillcode(RRepaymentBean.getHead().getBillcode());
        submitHeadBean.setWork(RRepaymentBean.getHead().getWork());

        SubmitBean beanList = new SubmitBean();
        beanList.setUserid(sUserInfo.getUserId());
        beanList.setPk_group(sUserInfo.getPk_group());
        beanList.setHead(submitHeadBean);

        log.info("打印还款单:{}", new Gson().toJson(beanList));


        try {
            String s = "";
            if ("loc".equals(string)) {
                s = HttpUtils.requestPostBody(C.SUBMIT_REPAYMENT_ADD_URL_DEV, JSONObject.toJSONString(beanList), null);
            } else if ("dev".equals(string)){
                s = HttpUtils.requestPostBody(C.SUBMIT_REPAYMENT_ADD_URL_DEV, JSONObject.toJSONString(beanList), null);
            }else if("gray".equals(string)){
                s = HttpUtils.requestPostBody(C.SUBMIT_REPAYMENT_ADD_URL_PRD, JSONObject.toJSONString(beanList), null);
            }else{
                s = HttpUtils.requestPostBody(C.SUBMIT_REPAYMENT_ADD_URL_PRD, JSONObject.toJSONString(beanList), null);
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

    @PostMapping("/deleteRepayment")
    @ApiOperation(value = "删除还款单")
    public TResult<SubmitBean> deleteRepayment(HttpServletRequest request, @RequestBody SubmitBean RSubmitBean) {
        log.info("还款单:{}", new Gson().toJson(RSubmitBean));
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

        log.info("打印还款单:{}", new Gson().toJson(beanList));
        try {
            String s = "";
            if ("loc".equals(string)) {
                s = HttpUtils.requestPostBody(C.DELETE_REPAYMENT_ADD_URL_DEV, JSONObject.toJSONString(beanList), null);
            } else if ("dev".equals(string)){
                s = HttpUtils.requestPostBody(C.DELETE_REPAYMENT_ADD_URL_DEV, JSONObject.toJSONString(beanList), null);
            }else if("gray".equals(string)){
                s = HttpUtils.requestPostBody(C.DELETE_REPAYMENT_ADD_URL_PRD, JSONObject.toJSONString(beanList), null);
            }else{
                s = HttpUtils.requestPostBody(C.DELETE_REPAYMENT_ADD_URL_PRD, JSONObject.toJSONString(beanList), null);
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



//    @PostMapping("/updateRepayment")
//    @ApiOperation(value = "修改还款单")
//    public TResult<RepaymentBean> updateRepayment(HttpServletRequest request, @RequestBody RepaymentBean RRepaymentBean) {
//
//        log.info("修改还款单:{}", new Gson().toJson(RRepaymentBean));
//
//        String userId = SpringUtil.getSessionUserId(request);
//
//        SUserInfo sUserInfo = (SUserInfo) redisService.get(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + userId);
//
//        if(StringUtil.isEmpty(RRepaymentBean.getHead().getBillcode())){
//            return new TResult<>(C.ERROR_CODE_COM,"单据不能为空");
//        }
//
//
//        CostHeadBean costHeadBean = new CostHeadBean();
//        costHeadBean.setCreator(sUserInfo.getCode());
//        costHeadBean.setPk_org(sUserInfo.getOrgcode());
//        costHeadBean.setIncident(RRepaymentBean.getHead().getIncident());
//        costHeadBean.setPk_payorg(sUserInfo.getOrgcode());//支付单位
//        costHeadBean.setFkyhzh("");//单位银行账户
//        costHeadBean.setJsfs("");//结算方式
//        costHeadBean.setCashitem("");//现金流量项目
//        costHeadBean.setCashproj("");//资金计划项目
//        costHeadBean.setFydwbm(sUserInfo.getOrgcode());
//        costHeadBean.setFydeptid(sUserInfo.getDeptcode());
//        costHeadBean.setSzxmid(RRepaymentBean.getHead().getSzxmid());
//        costHeadBean.setHbbm("");//供应商
//        costHeadBean.setCustomer("");//客户
//        costHeadBean.setReceiver(sUserInfo.getCode());
//        costHeadBean.setBzbm(RRepaymentBean.getHead().getBzbm());
//        costHeadBean.setDjlxbm("2647");//交易类型
//        costHeadBean.setDjrq(RRepaymentBean.getHead().getDjrq());
//        costHeadBean.setDwbm(sUserInfo.getOrgcode());
//        costHeadBean.setDeptid(sUserInfo.getDeptcode());
//        costHeadBean.setSkyhzh(RRepaymentBean.getHead().getSkyhzh());
//        costHeadBean.setBillcode(RRepaymentBean.getHead().getBillcode());
//
//
//        List<ContrastsBean> contrasts = RRepaymentBean.getContrasts();
//        List<ContrastsBean> allList =  new ArrayList<>();
//
//        if(contrasts.size() > 0){
//            for(ContrastsBean contrastsBean :contrasts ){
//                ContrastsBean bean = new ContrastsBean();
//                BeanUtils.copyProperties(contrastsBean, bean);
//                bean.setSxrq(DateUtils.formatDate(new Date()));
//                allList.add(bean);
//            }
//        }
//
//
//        RepaymentBean beanList = new RepaymentBean();
//        beanList.setUserid(sUserInfo.getUserId());
//        beanList.setPk_group(sUserInfo.getPk_group());
//        beanList.setHead(costHeadBean);
//        beanList.setContrasts(allList);
//        log.info("打印还款单:{}", new Gson().toJson(beanList));
//
//
//        try {
//            String s = HttpUtils.requestPostBody(C.UPDATE_REPAYMENT_ADD_URL, JSONObject.toJSONString(beanList), null);
//            log.error("推送成功："+s);
//            String substring = commonService.stringJieQu(s);
//            JSONObject jsonObject = JSONObject.parseObject(substring);
//
//            log.info("jsonObjectResult" + jsonObject);
//            if("0".equals(String.valueOf(jsonObject.get("result")))){
//                return new TResult<>(0,null,String.valueOf(jsonObject.get("billcode")));
//            }else{
//                return new TResult<>(1,String.valueOf(jsonObject.get("message")));
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            log.error("推送失败" + e);
//            return new TResult<>(C.ERROR_CODE_COM,"推送失败");
//        }
//    }

    @PostMapping("/selectApprover")
    @ApiOperation(value = "查询还款审批人")
    //@ValidateApiNotAuth
    public TResult<UserBean> updatePass(HttpServletRequest request, @RequestBody SubmitBean RTravellingBean) {

        log.info("查询还款:{}", new Gson().toJson(RTravellingBean));
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

        log.info("打印查询还款审批人:{}", new Gson().toJson(beanList));


        try {
            String s = "";
            if ("loc".equals(string)) {
                s = HttpUtils.requestPostBody(C.WORK_REPAYMENT_URL_DEV, JSONObject.toJSONString(beanList), null);
            } else if ("dev".equals(string)){
                s = HttpUtils.requestPostBody(C.WORK_REPAYMENT_URL_DEV, JSONObject.toJSONString(beanList), null);
            }else if("gray".equals(string)){
                s = HttpUtils.requestPostBody(C.WORK_REPAYMENT_URL_PRD, JSONObject.toJSONString(beanList), null);
            }else{
                s = HttpUtils.requestPostBody(C.WORK_REPAYMENT_URL_PRD, JSONObject.toJSONString(beanList), null);
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


    @PostMapping("/approveRepayment")
    @ApiOperation(value = "还款单批准接口")
    //@ValidateApiNotAuth
    public TResult<SubmitBean> approveRepayment(HttpServletRequest request, @RequestBody SubmitBean RTravellingBean) {
        log.info("还款单批准接口:{}", new Gson().toJson(RTravellingBean));
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


        log.info("打印还款单批准接口:{}", new Gson().toJson(beanApprove));
        try {
            String s = "";
            if ("loc".equals(string)) {
                s = HttpUtils.requestPostBody(C.APPROVE_REPAYMENT_URL_DEV, JSONObject.toJSONString(beanApprove), null);
            } else if ("dev".equals(string)){
                s = HttpUtils.requestPostBody(C.APPROVE_REPAYMENT_URL_DEV, JSONObject.toJSONString(beanApprove), null);
            }else if("gray".equals(string)){
                s = HttpUtils.requestPostBody(C.APPROVE_REPAYMENT_URL_PRD, JSONObject.toJSONString(beanApprove), null);
            }else{
                s = HttpUtils.requestPostBody(C.APPROVE_REPAYMENT_URL_PRD, JSONObject.toJSONString(beanApprove), null);
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

    @PostMapping("/rejectRepayment")
    @ApiOperation(value = "还款单驳回接口")
    //@ValidateApiNotAuth
    public TResult<BeanApprove> rejectRepayment(HttpServletRequest request, @RequestBody BeanApprove RTravellingBean) {
        log.info("还款单驳回接口:{}", new Gson().toJson(RTravellingBean));
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


        log.info("打印还款单驳回接口:{}", new Gson().toJson(beanApprove));
        try {
            String s = "";
            if ("loc".equals(string)) {
                s = HttpUtils.requestPostBody(C.REJECT_REPAYMENT_URL_DEV, JSONObject.toJSONString(beanApprove), null);
            } else if ("dev".equals(string)){
                s = HttpUtils.requestPostBody(C.REJECT_REPAYMENT_URL_DEV, JSONObject.toJSONString(beanApprove), null);
            }else if("gray".equals(string)){
                s = HttpUtils.requestPostBody(C.REJECT_REPAYMENT_URL_PRD, JSONObject.toJSONString(beanApprove), null);
            }else{
                s = HttpUtils.requestPostBody(C.REJECT_REPAYMENT_URL_PRD, JSONObject.toJSONString(beanApprove), null);
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

    @PostMapping("/backRepayment")
    @ApiOperation(value = "回收还款单")
    public TResult<SubmitBean> backRepayment(HttpServletRequest request, @RequestBody SubmitBean RRepaymentBean) {

        log.info("回收还款单:{}", new Gson().toJson(RRepaymentBean));

        String userId = SpringUtil.getSessionUserId(request);

        //SUserInfo sUserInfo =  C.sessionInfo.get("userId_"+userId);
        SUserInfo sUserInfo = (SUserInfo) redisService.get(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + userId);

        SubmitHeadBean submitHeadBean = new SubmitHeadBean();
        submitHeadBean.setBillcode(RRepaymentBean.getHead().getBillcode());

        SubmitBean beanList = new SubmitBean();
        beanList.setUserid(sUserInfo.getUserId());
        beanList.setPk_group(sUserInfo.getPk_group());
        beanList.setHead(submitHeadBean);

        log.info("打印回收还款单:{}", new Gson().toJson(beanList));


        try {
            String s = "";
            if ("loc".equals(string)) {
                s = HttpUtils.requestPostBody(C.BACK_REPAYMENT_URL_DEV, JSONObject.toJSONString(beanList), null);
            } else if ("dev".equals(string)){
                s = HttpUtils.requestPostBody(C.BACK_REPAYMENT_URL_DEV, JSONObject.toJSONString(beanList), null);
            }else if("gray".equals(string)){
                s = HttpUtils.requestPostBody(C.BACK_REPAYMENT_URL_PRD, JSONObject.toJSONString(beanList), null);
            }else{
                s = HttpUtils.requestPostBody(C.BACK_REPAYMENT_URL_PRD, JSONObject.toJSONString(beanList), null);
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


    @PostMapping("/selectRepayment")
    @ApiOperation(value = "环节查询驳回还款单接口")
    //@ValidateApiNotAuth
    public TResult<SelectBean> selectRepayment(HttpServletRequest request, @RequestBody SelectListBean rSelectBean) {
        log.info("环节查询驳回还款单接口:{}", new Gson().toJson(rSelectBean));
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

        log.info("打印环节查询驳回还款单接口:{}", new Gson().toJson(beanList));
        try {
            String s = "";
            if ("loc".equals(string)) {
                s = HttpUtils.requestPostBody(C.XPDL_REPAYMENT_URL_DEV, JSONObject.toJSONString(beanList), null);
            } else if ("dev".equals(string)){
                s = HttpUtils.requestPostBody(C.XPDL_REPAYMENT_URL_DEV, JSONObject.toJSONString(beanList), null);
            }else if("gray".equals(string)){
                s = HttpUtils.requestPostBody(C.XPDL_REPAYMENT_URL_PRD, JSONObject.toJSONString(beanList), null);
            }else{
                s = HttpUtils.requestPostBody(C.XPDL_REPAYMENT_URL_PRD, JSONObject.toJSONString(beanList), null);
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

    @PostMapping("/assignRepayment")
    @ApiOperation(value = "查询分组员工列表")
    //@ValidateApiNotAuth
    public TResult<SubmitBean> assignRepayment(HttpServletRequest request, @RequestBody SubmitBean SubmitBean) {
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
                s = HttpUtils.requestPostBody(C.ASSIGN_REPAYMENT_URL_DEV, JSONObject.toJSONString(beanList), null);
            } else if ("dev".equals(string)){
                s = HttpUtils.requestPostBody(C.ASSIGN_REPAYMENT_URL_DEV, JSONObject.toJSONString(beanList), null);
            }else if("gray".equals(string)){
                s = HttpUtils.requestPostBody(C.ASSIGN_REPAYMENT_URL_PRD, JSONObject.toJSONString(beanList), null);
            }else{
                s = HttpUtils.requestPostBody(C.ASSIGN_REPAYMENT_URL_PRD, JSONObject.toJSONString(beanList), null);
            }

            log.error("推送成功："+s);
            String string = JSONObject.parseObject(s, String.class);
            JSONObject jsonObject = JSONObject.parseObject(string);

            log.info("jsonObjectResult" + jsonObject);

            if("0".equals(String.valueOf(jsonObject.get("result")))){

                String jsonArray = jsonObject.getString("billcode");
                if(StringUtil.notEmpty(jsonArray)){
                    List<BxSPResult> listArrayList = JSONObject.parseArray(jsonArray, BxSPResult.class);
                    if(listArrayList.size() > 0){
                        for(BxSPResult bean : listArrayList){

                            List<BxSPDetResult> results = bean.getBxdetret();
                            if(results.size() > 0){
                                for(BxSPDetResult list : results){
                                    list.setActid(bean.getActid());
                                }
                            }
                        }
                    }
                    return new TResult<>(0,null,listArrayList);
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
