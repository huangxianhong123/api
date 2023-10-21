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
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
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
 * @date 2021/2/24 16:09
 */
@RestController
@RequestMapping("/api/v1/bxBillinfo")
@Api(value = "bxBillinfo", description = "费用报销单", tags = "费用报销单")
@Slf4j
public class BxBillInfoController {

    @Value("${spring.profiles.active}")
    private String string;

    @Autowired
    private CommonService commonService;
    @Autowired
    private RedisService redisService;

//    @PostMapping("/add")
//    @ApiOperation(value = "费用报销单")
//    public TResult<BxBillInfoAndErsBean> add(HttpServletRequest request, @RequestBody BxBillInfoAndErsBean bxBillInfoAndErsBean) {
//        log.info("费用报销单:{}", new Gson().toJson(bxBillInfoAndErsBean));
//
//        Date date = new Date();
//
//        String userId = SpringUtil.getSessionUserId(request);
//
//        //SUserInfo sUserInfo =  C.sessionInfo.get("userId_"+userId);
//        SUserInfo sUserInfo = (SUserInfo) redisService.get(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + userId);
//
//        BxBillInfoBean bxBillInfoBean = bxBillInfoAndErsBean.getEr_bxzb();
//        //loanBean1.setOaid();//主键
//        bxBillInfoBean.setDjbh(commonService.getSn(3));//单据编号
//        bxBillInfoBean.setApprover(sUserInfo.getUsercode());
//        bxBillInfoBean.setCreationtime(DateUtils.formatDate(date));
//        bxBillInfoBean.setCreator(sUserInfo.getUsercode());
//        bxBillInfoBean.setApprovertime(DateUtils.formatDate(date));
////        bxBillInfoBean.setBxdw(sUserInfo.getOrgcode());//单据所属组织（未知）
////        bxBillInfoBean.setBxbm(sUserInfo.getDeptcode());
////        bxBillInfoBean.setBxr(sUserInfo.getCode());
//        bxBillInfoAndErsBean.setEr_bxzb(bxBillInfoBean);
//
//        try {
//            String s = HttpUtils.requestPostBody(C.BX_BILL_ADD_URL, JSONObject.toJSONString(bxBillInfoAndErsBean), null);
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

    @PostMapping("/addTravelOnBusiness")
    @ApiOperation(value = "新增差旅费报销单")
    public TResult<ApplyBean> addTravelOnBusiness(HttpServletRequest request, @RequestBody ApplyBean RApplyBean) {
        log.info("差旅费报销单:{}", new Gson().toJson(RApplyBean));

        String userId = SpringUtil.getSessionUserId(request);

        //SUserInfo sUserInfo =  C.sessionInfo.get("userId_"+userId);
        SUserInfo sUserInfo = (SUserInfo) redisService.get(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + userId);

        CostHeadBean costHeadBean = new CostHeadBean();
        costHeadBean.setCreator(sUserInfo.getUsercode());
        costHeadBean.setPk_org(RApplyBean.getHead().getPk_org());//sUserInfo.getOrgcode()
        costHeadBean.setIncident(RApplyBean.getHead().getIncident());
        costHeadBean.setPk_payorg(sUserInfo.getOrgcode());//支付单位
        costHeadBean.setFkyhzh("");//单位银行账户
        costHeadBean.setJsfs("");//结算方式
        costHeadBean.setCashitem("");//现金流量项目
        costHeadBean.setCashproj("");//资金计划项目
        costHeadBean.setFydwbm(RApplyBean.getHead().getFydwbm());//sUserInfo.getOrgcode()
        costHeadBean.setFydeptid(RApplyBean.getHead().getFydeptid());//sUserInfo.getDeptcode()
        costHeadBean.setSzxmid(RApplyBean.getHead().getSzxmid());
        costHeadBean.setHbbm("");//供应商
        costHeadBean.setCustomer("");//客户
        costHeadBean.setReceiver(RApplyBean.getHead().getReceiver());//sUserInfo.getCode()
        costHeadBean.setBzbm(RApplyBean.getHead().getBzbm());
        costHeadBean.setDjlxbm("2641");//交易类型
        costHeadBean.setDjrq(RApplyBean.getHead().getDjrq());
        costHeadBean.setDwbm(RApplyBean.getHead().getDwbm());//sUserInfo.getOrgcode()
        costHeadBean.setDeptid(RApplyBean.getHead().getDeptid());//sUserInfo.getDeptcode()
        costHeadBean.setSkyhzh(RApplyBean.getHead().getSkyhzh());
        costHeadBean.setZyx27(RApplyBean.getHead().getZyx27());
        costHeadBean.setZyx29(RApplyBean.getHead().getZyx29());
        costHeadBean.setZyx30(RApplyBean.getHead().getZyx30());
        costHeadBean.setBillcode(RApplyBean.getHead().getBillcode());
        costHeadBean.setFjzs(RApplyBean.getHead().getFjzs());
        costHeadBean.setIscostshare(RApplyBean.getHead().getIscostshare());
        costHeadBean.setWork(RApplyBean.getHead().getWork());

        List<BusitemsBean> details = RApplyBean.getBusitems();
        List<BusitemsBean> allList =  new ArrayList<>();

        for(BusitemsBean list : details){

            if (StringUtils.isNotEmpty(list.getTableCode())) {
                BusitemsBean bean = new BusitemsBean();

                BeanUtils.copyProperties(list, bean);
                if(StringUtil.notEmpty(list.getDefitem5())){
                    String sql = SqlProvider.getEnumvalueCode(list.getDefitem5(),"");
                    CostBean costBean = commonService.selectObjectBySql(sql, CostBean.class);
                    if(costBean !=null){
                        bean.setDefitem5(costBean.getName());
                    }
                }else{
                    bean.setDefitem5("");
                }



                allList.add(bean);
            }

        }


        ApplyBean beanList = new ApplyBean();
        beanList.setUserid(sUserInfo.getUserId());
        beanList.setPk_group(sUserInfo.getPk_group());
        beanList.setHead(costHeadBean);
        beanList.setBusitems(allList);
        beanList.setContrasts(RApplyBean.getContrasts());
        beanList.setCshares(RApplyBean.getCshares());

        log.info("打印差旅费报销单:{}", new Gson().toJson(beanList));

        try {
            String s = "";
            if ("loc".equals(string)) {
                s = HttpUtils.requestPostBody(C.INSERT_APPLY_COST_ADD_URL_DEV, JSONObject.toJSONString(beanList), null);
            } else if ("dev".equals(string)){
                s = HttpUtils.requestPostBody(C.INSERT_APPLY_COST_ADD_URL_DEV, JSONObject.toJSONString(beanList), null);
            }else if("gray".equals(string)){
                s = HttpUtils.requestPostBody(C.INSERT_APPLY_COST_ADD_URL_PRD, JSONObject.toJSONString(beanList), null);
            }else{
                s = HttpUtils.requestPostBody(C.INSERT_APPLY_COST_ADD_URL_PRD, JSONObject.toJSONString(beanList), null);
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

    @PostMapping("/addGeneral")
    @ApiOperation(value = "新增通用报销单")
    public TResult<ApplyBean> addGeneral(HttpServletRequest request, @RequestBody ApplyBean RApplyBean) {
        log.info("通用报销单:{}", new Gson().toJson(RApplyBean));

        String userId = SpringUtil.getSessionUserId(request);

        //SUserInfo sUserInfo =  C.sessionInfo.get("userId_"+userId);
        SUserInfo sUserInfo = (SUserInfo) redisService.get(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + userId);

        CostHeadBean costHeadBean = new CostHeadBean();
        costHeadBean.setCreator(sUserInfo.getUsercode());
        costHeadBean.setPk_org(sUserInfo.getOrgcode());//sUserInfo.getOrgcode()
        costHeadBean.setIncident(RApplyBean.getHead().getIncident());
        costHeadBean.setPk_payorg(sUserInfo.getOrgcode());//支付单位
        costHeadBean.setFkyhzh("");//单位银行账户
        costHeadBean.setJsfs(RApplyBean.getHead().getJsfs());//结算方式
        costHeadBean.setCashitem("");//现金流量项目
        costHeadBean.setCashproj("");//资金计划项目
        costHeadBean.setFydwbm(RApplyBean.getHead().getFydwbm());//sUserInfo.getOrgcode()
        costHeadBean.setFydeptid(RApplyBean.getHead().getFydeptid());//sUserInfo.getDeptcode()
        costHeadBean.setSzxmid(RApplyBean.getHead().getSzxmid());
        costHeadBean.setHbbm("");//供应商
        costHeadBean.setCustomer("");//客户
        costHeadBean.setReceiver(RApplyBean.getHead().getReceiver());//sUserInfo.getCode()
        costHeadBean.setBzbm(RApplyBean.getHead().getBzbm());
        costHeadBean.setDjlxbm("264X-Cxx-TYBXD");//交易类型
        costHeadBean.setDjrq(RApplyBean.getHead().getDjrq());
        costHeadBean.setDwbm(RApplyBean.getHead().getDwbm());//sUserInfo.getOrgcode()
        costHeadBean.setDeptid(RApplyBean.getHead().getDeptid());//sUserInfo.getDeptcode()
        costHeadBean.setSkyhzh(RApplyBean.getHead().getSkyhzh());
        costHeadBean.setZyx30(RApplyBean.getHead().getZyx30());
        costHeadBean.setBillcode(RApplyBean.getHead().getBillcode());
        costHeadBean.setFjzs(RApplyBean.getHead().getFjzs());
        costHeadBean.setIscostshare(RApplyBean.getHead().getIscostshare());
        costHeadBean.setWork(RApplyBean.getHead().getWork());


        ApplyBean beanList = new ApplyBean();
        beanList.setUserid(sUserInfo.getUserId());
        beanList.setPk_group(sUserInfo.getPk_group());
        beanList.setHead(costHeadBean);
        beanList.setBusitems(RApplyBean.getBusitems());
        beanList.setContrasts(RApplyBean.getContrasts());
        beanList.setCshares(RApplyBean.getCshares());

        log.info("打印通用报销单:{}", new Gson().toJson(beanList));
        try {
            String s = "";
            if ("loc".equals(string)) {
                s = HttpUtils.requestPostBody(C.INSERT_APPLY_COST_ADD_URL_DEV, JSONObject.toJSONString(beanList), null);
            } else if ("dev".equals(string)){
                s = HttpUtils.requestPostBody(C.INSERT_APPLY_COST_ADD_URL_DEV, JSONObject.toJSONString(beanList), null);
            }else if("gray".equals(string)){
                s = HttpUtils.requestPostBody(C.INSERT_APPLY_COST_ADD_URL_PRD, JSONObject.toJSONString(beanList), null);
            }else{
                s = HttpUtils.requestPostBody(C.INSERT_APPLY_COST_ADD_URL_PRD, JSONObject.toJSONString(beanList), null);
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

    @PostMapping("/addDrive")
    @ApiOperation(value = "新增行车报销单")
    public TResult<DriverListBean> addDrive(HttpServletRequest request, @RequestBody DriverListBean RApplyBean) {
        log.info("行车报销单:{}", new Gson().toJson(RApplyBean));

        String userId = SpringUtil.getSessionUserId(request);

        //SUserInfo sUserInfo =  C.sessionInfo.get("userId_"+userId);
        SUserInfo sUserInfo = (SUserInfo) redisService.get(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + userId);


        CostHeadBean costHeadBean = new CostHeadBean();
        costHeadBean.setCreator(sUserInfo.getUsercode());
        costHeadBean.setPk_org(sUserInfo.getOrgcode());//sUserInfo.getOrgcode()
        costHeadBean.setIncident(RApplyBean.getHead().getIncident());
        costHeadBean.setPk_payorg(sUserInfo.getOrgcode());//支付单位
        costHeadBean.setFkyhzh("");//单位银行账户
        costHeadBean.setJsfs("");//结算方式
        costHeadBean.setCashitem("");//现金流量项目
        costHeadBean.setCashproj("");//资金计划项目
        costHeadBean.setFydwbm(RApplyBean.getHead().getFydwbm());//sUserInfo.getOrgcode()
        costHeadBean.setFydeptid(RApplyBean.getHead().getFydeptid());//sUserInfo.getDeptcode()
        costHeadBean.setSzxmid(RApplyBean.getHead().getSzxmid());
        costHeadBean.setHbbm("");//供应商
        costHeadBean.setCustomer("");//客户
        costHeadBean.setReceiver(RApplyBean.getHead().getReceiver());//sUserInfo.getCode()
        costHeadBean.setBzbm(RApplyBean.getHead().getBzbm());
        costHeadBean.setDjlxbm("264X-Cxx-XCFBXD");//交易类型
        costHeadBean.setDjrq(RApplyBean.getHead().getDjrq());
        costHeadBean.setDwbm(RApplyBean.getHead().getDwbm());//sUserInfo.getOrgcode()
        costHeadBean.setDeptid(RApplyBean.getHead().getDeptid());//sUserInfo.getDeptcode()
        costHeadBean.setSkyhzh(RApplyBean.getHead().getSkyhzh());
        costHeadBean.setZyx30(RApplyBean.getHead().getZyx30());
        costHeadBean.setZyx29(RApplyBean.getHead().getZyx29());
        costHeadBean.setZyx28(RApplyBean.getHead().getZyx28());
        costHeadBean.setZyx1(RApplyBean.getHead().getZyx1());
        costHeadBean.setIscostshare(RApplyBean.getHead().getIscostshare());
        costHeadBean.setWork(RApplyBean.getHead().getWork());


        DriverListBean beanList = new DriverListBean();
        beanList.setUserid(sUserInfo.getUserId());
        beanList.setPk_group(sUserInfo.getPk_group());
        beanList.setHead(costHeadBean);
        beanList.setBusitems(RApplyBean.getBusitems());
        beanList.setContrasts(RApplyBean.getContrasts());
        beanList.setCshares(RApplyBean.getCshares());



        log.info("打印行车报销单:{}", new Gson().toJson(beanList));
        try {
            String s = "";
            if ("loc".equals(string)) {
                s = HttpUtils.requestPostBody(C.INSERT_APPLY_COST_ADD_URL_DEV, JSONObject.toJSONString(beanList), null);
            } else if ("dev".equals(string)){
                s = HttpUtils.requestPostBody(C.INSERT_APPLY_COST_ADD_URL_DEV, JSONObject.toJSONString(beanList), null);
            }else if("gray".equals(string)){
                s = HttpUtils.requestPostBody(C.INSERT_APPLY_COST_ADD_URL_PRD, JSONObject.toJSONString(beanList), null);
            }else{
                s = HttpUtils.requestPostBody(C.INSERT_APPLY_COST_ADD_URL_PRD, JSONObject.toJSONString(beanList), null);
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


    @PostMapping("/addServe")
    @ApiOperation(value = "新增招待报销单")
    public TResult<ServeListBean> addServe(HttpServletRequest request, @RequestBody ServeListBean RServeListBean) {
        log.info("招待报销单:{}", new Gson().toJson(RServeListBean));

        String userId = SpringUtil.getSessionUserId(request);

        //SUserInfo sUserInfo =  C.sessionInfo.get("userId_"+userId);
        SUserInfo sUserInfo = (SUserInfo) redisService.get(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + userId);

        CostHeadBean costHeadBean = new CostHeadBean();
        costHeadBean.setCreator(sUserInfo.getUsercode());
        costHeadBean.setPk_org(sUserInfo.getOrgcode());//sUserInfo.getOrgcode()
        costHeadBean.setIncident(RServeListBean.getHead().getIncident());
        costHeadBean.setPk_payorg(sUserInfo.getOrgcode());//支付单位
        costHeadBean.setFkyhzh("");//单位银行账户
        costHeadBean.setJsfs("");//结算方式
        costHeadBean.setCashitem("");//现金流量项目
        costHeadBean.setCashproj("");//资金计划项目
        costHeadBean.setFydwbm(RServeListBean.getHead().getFydwbm());//费用承担单位
        costHeadBean.setFydeptid(RServeListBean.getHead().getFydeptid());//费用承担部门
        costHeadBean.setSzxmid(RServeListBean.getHead().getSzxmid());
        costHeadBean.setHbbm("");//供应商
        costHeadBean.setCustomer("");//客户
        costHeadBean.setReceiver(RServeListBean.getHead().getReceiver());//sUserInfo.getCode()
        costHeadBean.setBzbm(RServeListBean.getHead().getBzbm());
        costHeadBean.setDjlxbm("2645");//交易类型
        costHeadBean.setDjrq(RServeListBean.getHead().getDjrq());
        costHeadBean.setDwbm(RServeListBean.getHead().getDwbm());//sUserInfo.getOrgcode()
        costHeadBean.setDeptid(RServeListBean.getHead().getDeptid());//sUserInfo.getDeptcode()
        costHeadBean.setSkyhzh(RServeListBean.getHead().getSkyhzh());
        costHeadBean.setZyx30(RServeListBean.getHead().getZyx30());
        costHeadBean.setFjzs(RServeListBean.getHead().getFjzs());
        costHeadBean.setIscostshare(RServeListBean.getHead().getIscostshare());
        costHeadBean.setWork(RServeListBean.getHead().getWork());


        ServeListBean beanList = new ServeListBean();
        beanList.setUserid(sUserInfo.getUserId());
        beanList.setPk_group(sUserInfo.getPk_group());
        beanList.setHead(costHeadBean);
        beanList.setBusitems(RServeListBean.getBusitems());
        beanList.setContrasts(RServeListBean.getContrasts());
        beanList.setCshares(RServeListBean.getCshares());



        log.info("打印招待报销单:{}", new Gson().toJson(beanList));
        try {
            String s = "";
            if ("loc".equals(string)) {
                s = HttpUtils.requestPostBody(C.INSERT_APPLY_COST_ADD_URL_DEV, JSONObject.toJSONString(beanList), null);
            } else if ("dev".equals(string)){
                s = HttpUtils.requestPostBody(C.INSERT_APPLY_COST_ADD_URL_DEV, JSONObject.toJSONString(beanList), null);
            }else if("gray".equals(string)){
                s = HttpUtils.requestPostBody(C.INSERT_APPLY_COST_ADD_URL_PRD, JSONObject.toJSONString(beanList), null);
            }else{
                s = HttpUtils.requestPostBody(C.INSERT_APPLY_COST_ADD_URL_PRD, JSONObject.toJSONString(beanList), null);
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


    @PostMapping("/submitTravelOnBusiness")
    @ApiOperation(value = "提交差旅费报销单")
    public TResult<SubmitBean> submitTravelOnBusiness(HttpServletRequest request, @RequestBody SubmitBean RApplyBean) {
        log.info("差旅费报销单:{}", new Gson().toJson(RApplyBean));

        String userId = SpringUtil.getSessionUserId(request);

        //SUserInfo sUserInfo =  C.sessionInfo.get("userId_"+userId);
        SUserInfo sUserInfo = (SUserInfo) redisService.get(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + userId);



        SubmitHeadBean submitHeadBean = new SubmitHeadBean();
        submitHeadBean.setBillcode(RApplyBean.getHead().getBillcode());
        submitHeadBean.setWork(RApplyBean.getHead().getWork());

        SubmitBean beanList = new SubmitBean();
        beanList.setUserid(sUserInfo.getUserId());
        beanList.setPk_group(sUserInfo.getPk_group());
        beanList.setHead(submitHeadBean);


        log.info("打印差旅费报销单:{}", new Gson().toJson(beanList));

        try {
            String s = "";
            if ("loc".equals(string)) {
                s = HttpUtils.requestPostBody(C.SUBMIT_APPLY_COST_ADD_URL_DEV, JSONObject.toJSONString(beanList), null);
            } else if ("dev".equals(string)){
                s = HttpUtils.requestPostBody(C.SUBMIT_APPLY_COST_ADD_URL_DEV, JSONObject.toJSONString(beanList), null);
            }else if("gray".equals(string)){
                s = HttpUtils.requestPostBody(C.SUBMIT_APPLY_COST_ADD_URL_PRD, JSONObject.toJSONString(beanList), null);
            }else{
                s = HttpUtils.requestPostBody(C.SUBMIT_APPLY_COST_ADD_URL_PRD, JSONObject.toJSONString(beanList), null);
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

    @PostMapping("/submitGeneral")
    @ApiOperation(value = "提交通用报销单")
    public TResult<SubmitBean> submitGeneral(HttpServletRequest request, @RequestBody SubmitBean RApplyBean) {
        log.info("通用报销单:{}", new Gson().toJson(RApplyBean));

        String userId = SpringUtil.getSessionUserId(request);

        //SUserInfo sUserInfo =  C.sessionInfo.get("userId_"+userId);
        SUserInfo sUserInfo = (SUserInfo) redisService.get(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + userId);

        SubmitHeadBean submitHeadBean = new SubmitHeadBean();
        submitHeadBean.setBillcode(RApplyBean.getHead().getBillcode());
        submitHeadBean.setWork(RApplyBean.getHead().getWork());

        SubmitBean beanList = new SubmitBean();
        beanList.setUserid(sUserInfo.getUserId());
        beanList.setPk_group(sUserInfo.getPk_group());
        beanList.setHead(submitHeadBean);

        log.info("打印通用报销单:{}", new Gson().toJson(beanList));
        try {

            String s = "";
            if ("loc".equals(string)) {
                s = HttpUtils.requestPostBody(C.SUBMIT_APPLY_COST_ADD_URL_DEV, JSONObject.toJSONString(beanList), null);
            } else if ("dev".equals(string)){
                s = HttpUtils.requestPostBody(C.SUBMIT_APPLY_COST_ADD_URL_DEV, JSONObject.toJSONString(beanList), null);
            }else if("gray".equals(string)){
                s = HttpUtils.requestPostBody(C.SUBMIT_APPLY_COST_ADD_URL_PRD, JSONObject.toJSONString(beanList), null);
            }else{
                s = HttpUtils.requestPostBody(C.SUBMIT_APPLY_COST_ADD_URL_PRD, JSONObject.toJSONString(beanList), null);
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

    @PostMapping("/submitDrive")
    @ApiOperation(value = "提交行车报销单")
    public TResult<SubmitBean> submitDrive(HttpServletRequest request, @RequestBody SubmitBean RApplyBean) {
        log.info("行车报销单:{}", new Gson().toJson(RApplyBean));

        String userId = SpringUtil.getSessionUserId(request);

        //SUserInfo sUserInfo =  C.sessionInfo.get("userId_"+userId);
        SUserInfo sUserInfo = (SUserInfo) redisService.get(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + userId);


        SubmitHeadBean submitHeadBean = new SubmitHeadBean();
        submitHeadBean.setBillcode(RApplyBean.getHead().getBillcode());
        submitHeadBean.setWork(RApplyBean.getHead().getWork());

        SubmitBean beanList = new SubmitBean();
        beanList.setUserid(sUserInfo.getUserId());
        beanList.setPk_group(sUserInfo.getPk_group());
        beanList.setHead(submitHeadBean);


        log.info("打印行车报销单:{}", new Gson().toJson(beanList));
        try {
            String s = "";
            if ("loc".equals(string)) {
                s = HttpUtils.requestPostBody(C.SUBMIT_APPLY_COST_ADD_URL_DEV, JSONObject.toJSONString(beanList), null);
            } else if ("dev".equals(string)){
                s = HttpUtils.requestPostBody(C.SUBMIT_APPLY_COST_ADD_URL_DEV, JSONObject.toJSONString(beanList), null);
            }else if("gray".equals(string)){
                s = HttpUtils.requestPostBody(C.SUBMIT_APPLY_COST_ADD_URL_PRD, JSONObject.toJSONString(beanList), null);
            }else{
                s = HttpUtils.requestPostBody(C.SUBMIT_APPLY_COST_ADD_URL_PRD, JSONObject.toJSONString(beanList), null);
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


    @PostMapping("/submitServe")
    @ApiOperation(value = "提交招待报销单")
    public TResult<SubmitBean> submitServe(HttpServletRequest request, @RequestBody SubmitBean RServeListBean) {
        log.info("招待报销单:{}", new Gson().toJson(RServeListBean));

        String userId = SpringUtil.getSessionUserId(request);

        //SUserInfo sUserInfo =  C.sessionInfo.get("userId_"+userId);
        SUserInfo sUserInfo = (SUserInfo) redisService.get(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + userId);

        SubmitHeadBean submitHeadBean = new SubmitHeadBean();
        submitHeadBean.setBillcode(RServeListBean.getHead().getBillcode());
        submitHeadBean.setWork(RServeListBean.getHead().getWork());

        SubmitBean beanList = new SubmitBean();
        beanList.setUserid(sUserInfo.getUserId());
        beanList.setPk_group(sUserInfo.getPk_group());
        beanList.setHead(submitHeadBean);

        log.info("打印招待报销单:{}", new Gson().toJson(beanList));
        try {
            String s = "";
            if ("loc".equals(string)) {
                s = HttpUtils.requestPostBody(C.SUBMIT_APPLY_COST_ADD_URL_DEV, JSONObject.toJSONString(beanList), null);
            } else if ("dev".equals(string)){
                s = HttpUtils.requestPostBody(C.SUBMIT_APPLY_COST_ADD_URL_DEV, JSONObject.toJSONString(beanList), null);
            }else if("gray".equals(string)){
                s = HttpUtils.requestPostBody(C.SUBMIT_APPLY_COST_ADD_URL_PRD, JSONObject.toJSONString(beanList), null);
            }else{
                s = HttpUtils.requestPostBody(C.SUBMIT_APPLY_COST_ADD_URL_PRD, JSONObject.toJSONString(beanList), null);
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

    @PostMapping("/deleteApply")
    @ApiOperation(value = "删除报销单")
    public TResult<SubmitBean> deleteApply(HttpServletRequest request, @RequestBody SubmitBean RSubmitBean) {
        log.info("报销单:{}", new Gson().toJson(RSubmitBean));
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

        log.info("打印差报销单:{}", new Gson().toJson(beanList));
        try {
            String s = "";
            if ("loc".equals(string)) {
                s = HttpUtils.requestPostBody(C.DELETE_APPLY_COST_ADD_URL_DEV, JSONObject.toJSONString(beanList), null);
            } else if ("dev".equals(string)){
                s = HttpUtils.requestPostBody(C.DELETE_APPLY_COST_ADD_URL_DEV, JSONObject.toJSONString(beanList), null);
            }else if("gray".equals(string)){
                s = HttpUtils.requestPostBody(C.DELETE_APPLY_COST_ADD_URL_PRD, JSONObject.toJSONString(beanList), null);
            }else{
                s = HttpUtils.requestPostBody(C.DELETE_APPLY_COST_ADD_URL_PRD, JSONObject.toJSONString(beanList), null);
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

    @PostMapping("/updateTravelOnBusiness")
    @ApiOperation(value = "修改差旅费报销单")
    public TResult<ApplyBean> updateTravelOnBusiness(HttpServletRequest request, @RequestBody ApplyBean RApplyBean) {
        log.info("差旅费报销单:{}", new Gson().toJson(RApplyBean));

        String userId = SpringUtil.getSessionUserId(request);

        //SUserInfo sUserInfo =  C.sessionInfo.get("userId_"+userId);
        SUserInfo sUserInfo = (SUserInfo) redisService.get(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + userId);

        if(StringUtil.isEmpty(RApplyBean.getHead().getBillcode())){
            return new TResult<>(C.ERROR_CODE_COM,"单据不能为空");
        }

        CostHeadBean costHeadBean = new CostHeadBean();
        costHeadBean.setCreator(sUserInfo.getUsercode());
        costHeadBean.setPk_org(RApplyBean.getHead().getPk_org());//sUserInfo.getOrgcode()
        costHeadBean.setIncident(RApplyBean.getHead().getIncident());
        costHeadBean.setPk_payorg(sUserInfo.getOrgcode());//支付单位
        costHeadBean.setFkyhzh("");//单位银行账户
        costHeadBean.setJsfs("");//结算方式
        costHeadBean.setCashitem("");//现金流量项目
        costHeadBean.setCashproj("");//资金计划项目
        costHeadBean.setFydwbm(RApplyBean.getHead().getFydwbm());//费用承担单位
        costHeadBean.setFydeptid(RApplyBean.getHead().getFydeptid());//费用承担部门
        costHeadBean.setSzxmid(RApplyBean.getHead().getSzxmid());
        costHeadBean.setHbbm("");//供应商
        costHeadBean.setCustomer("");//客户
        costHeadBean.setReceiver(RApplyBean.getHead().getReceiver());//sUserInfo.getCode()
        costHeadBean.setBzbm(RApplyBean.getHead().getBzbm());
        costHeadBean.setDjlxbm("2641");//交易类型
        costHeadBean.setDjrq(RApplyBean.getHead().getDjrq());
        costHeadBean.setDwbm(RApplyBean.getHead().getDwbm());//sUserInfo.getOrgcode()
        costHeadBean.setDeptid(RApplyBean.getHead().getDeptid());//sUserInfo.getDeptcode()
        costHeadBean.setSkyhzh(RApplyBean.getHead().getSkyhzh());
        costHeadBean.setZyx27(RApplyBean.getHead().getZyx27());
        costHeadBean.setZyx29(RApplyBean.getHead().getZyx29());
        costHeadBean.setZyx30(RApplyBean.getHead().getZyx30());
        costHeadBean.setBillcode(RApplyBean.getHead().getBillcode());
        costHeadBean.setFjzs(RApplyBean.getHead().getFjzs());
        costHeadBean.setIscostshare(RApplyBean.getHead().getIscostshare());
        costHeadBean.setWork(RApplyBean.getHead().getWork());


        List<BusitemsBean> details = RApplyBean.getBusitems();
        List<BusitemsBean> allList =  new ArrayList<>();

        for(BusitemsBean list : details){

            if (StringUtils.isNotEmpty(list.getTableCode())) {
                BusitemsBean bean = new BusitemsBean();

                BeanUtils.copyProperties(list, bean);
                if(StringUtil.notEmpty(list.getDefitem5())){
                    String sql = SqlProvider.getEnumvalueCode(list.getDefitem5(),"");
                    CostBean costBean = commonService.selectObjectBySql(sql, CostBean.class);
                    if(costBean !=null){
                        bean.setDefitem5(costBean.getName());
                    }
                }else{
                    bean.setDefitem5("");
                }



                allList.add(bean);
            }
        }



        ApplyBean beanList = new ApplyBean();
        beanList.setUserid(sUserInfo.getUserId());
        beanList.setPk_group(sUserInfo.getPk_group());
        beanList.setHead(costHeadBean);
        beanList.setBusitems(allList);
        beanList.setContrasts(RApplyBean.getContrasts());
        beanList.setCshares(RApplyBean.getCshares());

        log.info("打印差旅费报销单:{}", new Gson().toJson(beanList));

        try {
            String s = "";
            if ("loc".equals(string)) {
                s = HttpUtils.requestPostBody(C.UPDATE_APPLY_COST_ADD_URL_DEV, JSONObject.toJSONString(beanList), null);
            } else if ("dev".equals(string)){
                s = HttpUtils.requestPostBody(C.UPDATE_APPLY_COST_ADD_URL_DEV, JSONObject.toJSONString(beanList), null);
            }else if("gray".equals(string)){
                s = HttpUtils.requestPostBody(C.UPDATE_APPLY_COST_ADD_URL_PRD, JSONObject.toJSONString(beanList), null);
            }else{
                s = HttpUtils.requestPostBody(C.UPDATE_APPLY_COST_ADD_URL_PRD, JSONObject.toJSONString(beanList), null);
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

    @PostMapping("/updateGeneral")
    @ApiOperation(value = "修改通用报销单")
    public TResult<ApplyBean> updateGeneral(HttpServletRequest request, @RequestBody ApplyBean RApplyBean) {
        log.info("通用报销单:{}", new Gson().toJson(RApplyBean));

        String userId = SpringUtil.getSessionUserId(request);

        //SUserInfo sUserInfo =  C.sessionInfo.get("userId_"+userId);
        SUserInfo sUserInfo = (SUserInfo) redisService.get(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + userId);

        if(StringUtil.isEmpty(RApplyBean.getHead().getBillcode())){
            return new TResult<>(C.ERROR_CODE_COM,"单据不能为空");
        }


        CostHeadBean costHeadBean = new CostHeadBean();
        costHeadBean.setCreator(sUserInfo.getUsercode());
        costHeadBean.setPk_org(sUserInfo.getOrgcode());//sUserInfo.getOrgcode()
        costHeadBean.setIncident(RApplyBean.getHead().getIncident());
        costHeadBean.setPk_payorg(sUserInfo.getOrgcode());//支付单位
        costHeadBean.setFkyhzh("");//单位银行账户
        costHeadBean.setJsfs(RApplyBean.getHead().getJsfs());//结算方式
        costHeadBean.setCashitem("");//现金流量项目
        costHeadBean.setCashproj("");//资金计划项目
        costHeadBean.setFydwbm(RApplyBean.getHead().getFydwbm());//费用承担单位
        costHeadBean.setFydeptid(RApplyBean.getHead().getFydeptid());//费用承担部门
        costHeadBean.setSzxmid(RApplyBean.getHead().getSzxmid());
        costHeadBean.setHbbm("");//供应商
        costHeadBean.setCustomer("");//客户
        costHeadBean.setReceiver(RApplyBean.getHead().getReceiver());//sUserInfo.getCode()
        costHeadBean.setBzbm(RApplyBean.getHead().getBzbm());
        costHeadBean.setDjlxbm("264X-Cxx-TYBXD");//交易类型
        costHeadBean.setDjrq(RApplyBean.getHead().getDjrq());
        costHeadBean.setDwbm(RApplyBean.getHead().getDwbm());//sUserInfo.getOrgcode()
        costHeadBean.setDeptid(RApplyBean.getHead().getDeptid());//sUserInfo.getDeptcode()
        costHeadBean.setSkyhzh(RApplyBean.getHead().getSkyhzh());
        costHeadBean.setZyx30(RApplyBean.getHead().getZyx30());
        costHeadBean.setBillcode(RApplyBean.getHead().getBillcode());
        costHeadBean.setFjzs(RApplyBean.getHead().getFjzs());
        costHeadBean.setIscostshare(RApplyBean.getHead().getIscostshare());
        costHeadBean.setWork(RApplyBean.getHead().getWork());



        ApplyBean beanList = new ApplyBean();
        beanList.setUserid(sUserInfo.getUserId());
        beanList.setPk_group(sUserInfo.getPk_group());
        beanList.setHead(costHeadBean);
        beanList.setBusitems(RApplyBean.getBusitems());
        beanList.setContrasts(RApplyBean.getContrasts());
        beanList.setCshares(RApplyBean.getCshares());


        log.info("打印通用报销单:{}", new Gson().toJson(beanList));
        try {
            String s = "";
            if ("loc".equals(string)) {
                s = HttpUtils.requestPostBody(C.UPDATE_APPLY_COST_ADD_URL_DEV, JSONObject.toJSONString(beanList), null);
            } else if ("dev".equals(string)){
                s = HttpUtils.requestPostBody(C.UPDATE_APPLY_COST_ADD_URL_DEV, JSONObject.toJSONString(beanList), null);
            }else if("gray".equals(string)){
                s = HttpUtils.requestPostBody(C.UPDATE_APPLY_COST_ADD_URL_PRD, JSONObject.toJSONString(beanList), null);
            }else{
                s = HttpUtils.requestPostBody(C.UPDATE_APPLY_COST_ADD_URL_PRD, JSONObject.toJSONString(beanList), null);
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

    @PostMapping("/updateDrive")
    @ApiOperation(value = "修改行车报销单")
    public TResult<DriverListBean> updateDrive(HttpServletRequest request, @RequestBody DriverListBean RApplyBean) {
        log.info("行车报销单:{}", new Gson().toJson(RApplyBean));

        String userId = SpringUtil.getSessionUserId(request);

        //SUserInfo sUserInfo =  C.sessionInfo.get("userId_"+userId);
        SUserInfo sUserInfo = (SUserInfo) redisService.get(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + userId);

        if(StringUtil.isEmpty(RApplyBean.getHead().getBillcode())){
            return new TResult<>(C.ERROR_CODE_COM,"单据不能为空");
        }


        CostHeadBean costHeadBean = new CostHeadBean();
        costHeadBean.setCreator(sUserInfo.getUsercode());
        costHeadBean.setPk_org(sUserInfo.getOrgcode());//sUserInfo.getOrgcode()
        costHeadBean.setIncident(RApplyBean.getHead().getIncident());
        costHeadBean.setPk_payorg(sUserInfo.getOrgcode());//支付单位
        costHeadBean.setFkyhzh("");//单位银行账户
        costHeadBean.setJsfs("");//结算方式
        costHeadBean.setCashitem("");//现金流量项目
        costHeadBean.setCashproj("");//资金计划项目
        costHeadBean.setFydwbm(RApplyBean.getHead().getFydwbm());//费用承担单位
        costHeadBean.setFydeptid(RApplyBean.getHead().getFydeptid());//费用承担部门
        costHeadBean.setSzxmid(RApplyBean.getHead().getSzxmid());
        costHeadBean.setHbbm("");//供应商
        costHeadBean.setCustomer("");//客户
        costHeadBean.setReceiver(RApplyBean.getHead().getReceiver());//sUserInfo.getCode()
        costHeadBean.setBzbm(RApplyBean.getHead().getBzbm());
        costHeadBean.setDjlxbm("264X-Cxx-XCFBXD");//交易类型
        costHeadBean.setDjrq(RApplyBean.getHead().getDjrq());
        costHeadBean.setDwbm(RApplyBean.getHead().getDwbm());//sUserInfo.getOrgcode()
        costHeadBean.setDeptid(RApplyBean.getHead().getDeptid());//sUserInfo.getDeptcode()
        costHeadBean.setSkyhzh(RApplyBean.getHead().getSkyhzh());
        costHeadBean.setZyx30(RApplyBean.getHead().getZyx30());
        costHeadBean.setZyx29(RApplyBean.getHead().getZyx29());
        costHeadBean.setZyx28(RApplyBean.getHead().getZyx28());
        costHeadBean.setZyx1(RApplyBean.getHead().getZyx1());
        costHeadBean.setBillcode(RApplyBean.getHead().getBillcode());
        costHeadBean.setIscostshare(RApplyBean.getHead().getIscostshare());
        costHeadBean.setWork(RApplyBean.getHead().getWork());



        DriverListBean beanList = new DriverListBean();
        beanList.setUserid(sUserInfo.getUserId());
        beanList.setPk_group(sUserInfo.getPk_group());
        beanList.setHead(costHeadBean);
        beanList.setBusitems(RApplyBean.getBusitems());
        beanList.setContrasts(RApplyBean.getContrasts());
        beanList.setCshares(RApplyBean.getCshares());


        log.info("打印行车报销单:{}", new Gson().toJson(beanList));
        try {
            String s = "";
            if ("loc".equals(string)) {
                s = HttpUtils.requestPostBody(C.UPDATE_APPLY_COST_ADD_URL_DEV, JSONObject.toJSONString(beanList), null);
            } else if ("dev".equals(string)){
                s = HttpUtils.requestPostBody(C.UPDATE_APPLY_COST_ADD_URL_DEV, JSONObject.toJSONString(beanList), null);
            }else if("gray".equals(string)){
                s = HttpUtils.requestPostBody(C.UPDATE_APPLY_COST_ADD_URL_PRD, JSONObject.toJSONString(beanList), null);
            }else{
                s = HttpUtils.requestPostBody(C.UPDATE_APPLY_COST_ADD_URL_PRD, JSONObject.toJSONString(beanList), null);
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


    @PostMapping("/updateServe")
    @ApiOperation(value = "修改招待报销单")
    public TResult<ServeListBean> updateServe(HttpServletRequest request, @RequestBody ServeListBean RServeListBean) {
        log.info("招待报销单:{}", new Gson().toJson(RServeListBean));

        String userId = SpringUtil.getSessionUserId(request);

        //SUserInfo sUserInfo =  C.sessionInfo.get("userId_"+userId);
        SUserInfo sUserInfo = (SUserInfo) redisService.get(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + userId);

        if(StringUtil.isEmpty(RServeListBean.getHead().getBillcode())){
            return new TResult<>(C.ERROR_CODE_COM,"单据不能为空");
        }

        CostHeadBean costHeadBean = new CostHeadBean();
        costHeadBean.setCreator(sUserInfo.getUsercode());
        costHeadBean.setPk_org(sUserInfo.getOrgcode());//sUserInfo.getOrgcode()
        costHeadBean.setIncident(RServeListBean.getHead().getIncident());
        costHeadBean.setPk_payorg(sUserInfo.getOrgcode());//支付单位
        costHeadBean.setFkyhzh("");//单位银行账户
        costHeadBean.setJsfs("");//结算方式
        costHeadBean.setCashitem("");//现金流量项目
        costHeadBean.setCashproj("");//资金计划项目
        costHeadBean.setFydwbm(RServeListBean.getHead().getFydwbm());//费用承担单位
        costHeadBean.setFydeptid(RServeListBean.getHead().getFydeptid());//费用承担部门
        costHeadBean.setSzxmid(RServeListBean.getHead().getSzxmid());
        costHeadBean.setHbbm("");//供应商
        costHeadBean.setCustomer("");//客户
        costHeadBean.setReceiver(RServeListBean.getHead().getReceiver());//sUserInfo.getCode()
        costHeadBean.setBzbm(RServeListBean.getHead().getBzbm());
        costHeadBean.setDjlxbm("2645");//交易类型
        costHeadBean.setDjrq(RServeListBean.getHead().getDjrq());
        costHeadBean.setDwbm(RServeListBean.getHead().getDwbm());//sUserInfo.getOrgcode()
        costHeadBean.setDeptid(RServeListBean.getHead().getDeptid());//sUserInfo.getDeptcode()
        costHeadBean.setSkyhzh(RServeListBean.getHead().getSkyhzh());
        costHeadBean.setZyx30(RServeListBean.getHead().getZyx30());
        costHeadBean.setBillcode(RServeListBean.getHead().getBillcode());
        costHeadBean.setIscostshare(RServeListBean.getHead().getIscostshare());
        costHeadBean.setWork(RServeListBean.getHead().getWork());
        costHeadBean.setFjzs(RServeListBean.getHead().getFjzs());


        ServeListBean beanList = new ServeListBean();
        beanList.setUserid(sUserInfo.getUserId());
        beanList.setPk_group(sUserInfo.getPk_group());
        beanList.setHead(costHeadBean);
        beanList.setBusitems(RServeListBean.getBusitems());
        beanList.setContrasts(RServeListBean.getContrasts());
        beanList.setCshares(RServeListBean.getCshares());

        log.info("打印招待报销单:{}", new Gson().toJson(beanList));
        try {
            String s = "";
            if ("loc".equals(string)) {
                s = HttpUtils.requestPostBody(C.UPDATE_APPLY_COST_ADD_URL_DEV, JSONObject.toJSONString(beanList), null);
            } else if ("dev".equals(string)){
                s = HttpUtils.requestPostBody(C.UPDATE_APPLY_COST_ADD_URL_DEV, JSONObject.toJSONString(beanList), null);
            }else if("gray".equals(string)){
                s = HttpUtils.requestPostBody(C.UPDATE_APPLY_COST_ADD_URL_PRD, JSONObject.toJSONString(beanList), null);
            }else{
                s = HttpUtils.requestPostBody(C.UPDATE_APPLY_COST_ADD_URL_PRD, JSONObject.toJSONString(beanList), null);
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
    @ApiOperation(value = "查询报销审批人")
    //@ValidateApiNotAuth
    public TResult<UserBean> updatePass(HttpServletRequest request, @RequestBody SubmitBean RTravellingBean) {

        log.info("查询报销:{}", new Gson().toJson(RTravellingBean));
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

        log.info("打印查询报销审批人:{}", new Gson().toJson(beanList));


        try {
            String s = "";
            if ("loc".equals(string)) {
                s = HttpUtils.requestPostBody(C.WORK_APPLY_URL_DEV, JSONObject.toJSONString(beanList), null);
            } else if ("dev".equals(string)){
                s = HttpUtils.requestPostBody(C.WORK_APPLY_URL_DEV, JSONObject.toJSONString(beanList), null);
            }else if("gray".equals(string)){
                s = HttpUtils.requestPostBody(C.WORK_APPLY_URL_PRD, JSONObject.toJSONString(beanList), null);
            }else{
                s = HttpUtils.requestPostBody(C.WORK_APPLY_URL_PRD, JSONObject.toJSONString(beanList), null);
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

    @PostMapping("/approveBaoXiaoDan")
    @ApiOperation(value = "报销单批准接口")
    //@ValidateApiNotAuth
    public TResult<SubmitBean> approveBaoXiaoDan(HttpServletRequest request, @RequestBody SubmitBean RTravellingBean) {
        log.info("报销单批准接口:{}", new Gson().toJson(RTravellingBean));
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


        log.info("打印报销单批准接口:{}", new Gson().toJson(beanApprove));
        try {
            String s = "";
            if ("loc".equals(string)) {
                s = HttpUtils.requestPostBody(C.APPROVE_APPLY_URL_DEV, JSONObject.toJSONString(beanApprove), null);
            } else if ("dev".equals(string)){
                s = HttpUtils.requestPostBody(C.APPROVE_APPLY_URL_DEV, JSONObject.toJSONString(beanApprove), null);
            }else if("gray".equals(string)){
                s = HttpUtils.requestPostBody(C.APPROVE_APPLY_URL_PRD, JSONObject.toJSONString(beanApprove), null);
            }else{
                s = HttpUtils.requestPostBody(C.APPROVE_APPLY_URL_PRD, JSONObject.toJSONString(beanApprove), null);
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

    @PostMapping("/rejectBaoXiaoDan")
    @ApiOperation(value = "报销单驳回接口")
    //@ValidateApiNotAuth
    public TResult<BeanApprove> rejectBaoXiaoDan(HttpServletRequest request, @RequestBody BeanApprove RTravellingBean) {
        log.info("报销单驳回接口:{}", new Gson().toJson(RTravellingBean));
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


        log.info("打印报销单驳回接口:{}", new Gson().toJson(beanApprove));
        try {
            String s = "";
            if ("loc".equals(string)) {
                s = HttpUtils.requestPostBody(C.REJECT_APPLY_URL_DEV, JSONObject.toJSONString(beanApprove), null);
            } else if ("dev".equals(string)){
                s = HttpUtils.requestPostBody(C.REJECT_APPLY_URL_DEV, JSONObject.toJSONString(beanApprove), null);
            }else if("gray".equals(string)){
                s = HttpUtils.requestPostBody(C.REJECT_APPLY_URL_PRD, JSONObject.toJSONString(beanApprove), null);
            }else{
                s = HttpUtils.requestPostBody(C.REJECT_APPLY_URL_PRD, JSONObject.toJSONString(beanApprove), null);
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

    @PostMapping("/backBaoXiaoDan")
    @ApiOperation(value = "回收报销单")
    public TResult<SubmitBean> backBaoXiaoDan(HttpServletRequest request, @RequestBody SubmitBean RApplyBean) {
        log.info("回收报销单:{}", new Gson().toJson(RApplyBean));

        String userId = SpringUtil.getSessionUserId(request);

        //SUserInfo sUserInfo =  C.sessionInfo.get("userId_"+userId);
        SUserInfo sUserInfo = (SUserInfo) redisService.get(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + userId);

        SubmitHeadBean submitHeadBean = new SubmitHeadBean();
        submitHeadBean.setBillcode(RApplyBean.getHead().getBillcode());

        SubmitBean beanList = new SubmitBean();
        beanList.setUserid(sUserInfo.getUserId());
        beanList.setPk_group(sUserInfo.getPk_group());
        beanList.setHead(submitHeadBean);


        log.info("打印回收报销单:{}", new Gson().toJson(beanList));

        try {
            String s = "";
            if ("loc".equals(string)) {
                s = HttpUtils.requestPostBody(C.BACK_APPLY_URL_DEV, JSONObject.toJSONString(beanList), null);
            } else if ("dev".equals(string)){
                s = HttpUtils.requestPostBody(C.BACK_APPLY_URL_DEV, JSONObject.toJSONString(beanList), null);
            }else if("gray".equals(string)){
                s = HttpUtils.requestPostBody(C.BACK_APPLY_URL_PRD, JSONObject.toJSONString(beanList), null);
            }else{
                s = HttpUtils.requestPostBody(C.BACK_APPLY_URL_PRD, JSONObject.toJSONString(beanList), null);
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


    @PostMapping("/checkTravelOnBusiness")
    @ApiOperation(value = "校验差旅费报销单")
    public TResult<ApplyBean> checkTravelOnBusiness(HttpServletRequest request, @RequestBody ApplyBean RApplyBean) {
        log.info("校验差旅费报销单:{}", new Gson().toJson(RApplyBean));

        String userId = SpringUtil.getSessionUserId(request);

        //SUserInfo sUserInfo =  C.sessionInfo.get("userId_"+userId);
        SUserInfo sUserInfo = (SUserInfo) redisService.get(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + userId);

        CostHeadBean costHeadBean = new CostHeadBean();
        costHeadBean.setCreator(sUserInfo.getUsercode());
        costHeadBean.setPk_org(RApplyBean.getHead().getPk_org());//sUserInfo.getOrgcode()
        costHeadBean.setIncident(RApplyBean.getHead().getIncident());
        costHeadBean.setPk_payorg(sUserInfo.getOrgcode());//支付单位
        costHeadBean.setFkyhzh("");//单位银行账户
        costHeadBean.setJsfs("");//结算方式
        costHeadBean.setCashitem("");//现金流量项目
        costHeadBean.setCashproj("");//资金计划项目
        costHeadBean.setFydwbm(RApplyBean.getHead().getFydwbm());//费用承担单位
        costHeadBean.setFydeptid(RApplyBean.getHead().getFydeptid());//费用承担部门
        costHeadBean.setSzxmid(RApplyBean.getHead().getSzxmid());
        costHeadBean.setHbbm("");//供应商
        costHeadBean.setCustomer("");//客户
        costHeadBean.setReceiver(sUserInfo.getCode());
        costHeadBean.setBzbm(RApplyBean.getHead().getBzbm());
        costHeadBean.setDjlxbm("2641");//交易类型
        costHeadBean.setDjrq(RApplyBean.getHead().getDjrq());
        costHeadBean.setDwbm(RApplyBean.getHead().getDwbm());//sUserInfo.getOrgcode()
        costHeadBean.setDeptid(RApplyBean.getHead().getDeptid());//sUserInfo.getDeptcode()
        costHeadBean.setSkyhzh(RApplyBean.getHead().getSkyhzh());
        costHeadBean.setZyx27(RApplyBean.getHead().getZyx27());
        costHeadBean.setZyx29(RApplyBean.getHead().getZyx29());
        costHeadBean.setZyx30(RApplyBean.getHead().getZyx30());
        costHeadBean.setBillcode(RApplyBean.getHead().getBillcode());
        costHeadBean.setFjzs(RApplyBean.getHead().getFjzs());
        costHeadBean.setIscostshare(RApplyBean.getHead().getIscostshare());
        costHeadBean.setWork(RApplyBean.getHead().getWork());

        List<BusitemsBean> details = RApplyBean.getBusitems();
        List<BusitemsBean> allList =  new ArrayList<>();

        for(BusitemsBean list : details){

            if (StringUtils.isNotEmpty(list.getTableCode())) {
                BusitemsBean bean = new BusitemsBean();

                BeanUtils.copyProperties(list, bean);
                if(StringUtil.notEmpty(list.getDefitem5())){
                    String sql = SqlProvider.getEnumvalueCode(list.getDefitem5(),"");
                    CostBean costBean = commonService.selectObjectBySql(sql, CostBean.class);
                    if(costBean !=null){
                        bean.setDefitem5(costBean.getName());
                    }
                }else{
                    bean.setDefitem5("");
                }



                allList.add(bean);
            }

        }


        ApplyBean beanList = new ApplyBean();
        beanList.setUserid(sUserInfo.getUserId());
        beanList.setPk_group(sUserInfo.getPk_group());
        beanList.setHead(costHeadBean);
        beanList.setBusitems(allList);
        beanList.setContrasts(RApplyBean.getContrasts());
        beanList.setCshares(RApplyBean.getCshares());

        log.info("打印校验差旅费报销单:{}", new Gson().toJson(beanList));

        try {
            String s = "";
            if ("loc".equals(string)) {
                s = HttpUtils.requestPostBody(C.CHECK_APPLY_URL_DEV, JSONObject.toJSONString(beanList), null);
            } else if ("dev".equals(string)){
                s = HttpUtils.requestPostBody(C.CHECK_APPLY_URL_DEV, JSONObject.toJSONString(beanList), null);
            }else if("gray".equals(string)){
                s = HttpUtils.requestPostBody(C.CHECK_APPLY_URL_PRD, JSONObject.toJSONString(beanList), null);
            }else{
                s = HttpUtils.requestPostBody(C.CHECK_APPLY_URL_PRD, JSONObject.toJSONString(beanList), null);
            }

            log.error("推送成功："+s);
//            String substring = commonService.stringJieQu(s);
//            JSONObject jsonObject = JSONObject.parseObject(substring);


            String string = JSONObject.parseObject(s, String.class);
            JSONObject jsonObject = JSONObject.parseObject(string);

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

    @PostMapping("/checkGeneral")
    @ApiOperation(value = "校验通用报销单")
    public TResult<ApplyBean> checkGeneral(HttpServletRequest request, @RequestBody ApplyBean RApplyBean) {
        log.info("校验通用报销单:{}", new Gson().toJson(RApplyBean));

        String userId = SpringUtil.getSessionUserId(request);

        //SUserInfo sUserInfo =  C.sessionInfo.get("userId_"+userId);
        SUserInfo sUserInfo = (SUserInfo) redisService.get(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + userId);

        CostHeadBean costHeadBean = new CostHeadBean();
        costHeadBean.setCreator(sUserInfo.getUsercode());
        costHeadBean.setPk_org(sUserInfo.getOrgcode());//sUserInfo.getOrgcode()
        costHeadBean.setIncident(RApplyBean.getHead().getIncident());
        costHeadBean.setPk_payorg(sUserInfo.getOrgcode());//支付单位
        costHeadBean.setFkyhzh("");//单位银行账户
        costHeadBean.setJsfs(RApplyBean.getHead().getJsfs());//结算方式
        costHeadBean.setCashitem("");//现金流量项目
        costHeadBean.setCashproj("");//资金计划项目
        costHeadBean.setFydwbm(RApplyBean.getHead().getFydwbm());//费用承担单位
        costHeadBean.setFydeptid(RApplyBean.getHead().getFydeptid());//费用承担部门
        costHeadBean.setSzxmid(RApplyBean.getHead().getSzxmid());
        costHeadBean.setHbbm("");//供应商
        costHeadBean.setCustomer("");//客户
        costHeadBean.setReceiver(RApplyBean.getHead().getReceiver());//sUserInfo.getCode()
        costHeadBean.setBzbm(RApplyBean.getHead().getBzbm());
        costHeadBean.setDjlxbm("264X-Cxx-TYBXD");//交易类型
        costHeadBean.setDjrq(RApplyBean.getHead().getDjrq());
        costHeadBean.setDwbm(RApplyBean.getHead().getDwbm());//sUserInfo.getOrgcode()
        costHeadBean.setDeptid(RApplyBean.getHead().getDeptid());//sUserInfo.getDeptcode()
        costHeadBean.setSkyhzh(RApplyBean.getHead().getSkyhzh());
        costHeadBean.setZyx30(RApplyBean.getHead().getZyx30());
        costHeadBean.setBillcode(RApplyBean.getHead().getBillcode());
        costHeadBean.setFjzs(RApplyBean.getHead().getFjzs());
        costHeadBean.setIscostshare(RApplyBean.getHead().getIscostshare());
        costHeadBean.setWork(RApplyBean.getHead().getWork());


        ApplyBean beanList = new ApplyBean();
        beanList.setUserid(sUserInfo.getUserId());
        beanList.setPk_group(sUserInfo.getPk_group());
        beanList.setHead(costHeadBean);
        beanList.setBusitems(RApplyBean.getBusitems());
        beanList.setContrasts(RApplyBean.getContrasts());
        beanList.setCshares(RApplyBean.getCshares());

        log.info("打印校验通用报销单:{}", new Gson().toJson(beanList));
        try {
            String s = "";
            if ("loc".equals(string)) {
                s = HttpUtils.requestPostBody(C.CHECK_APPLY_URL_DEV, JSONObject.toJSONString(beanList), null);
            } else if ("dev".equals(string)){
                s = HttpUtils.requestPostBody(C.CHECK_APPLY_URL_DEV, JSONObject.toJSONString(beanList), null);
            }else if("gray".equals(string)){
                s = HttpUtils.requestPostBody(C.CHECK_APPLY_URL_PRD, JSONObject.toJSONString(beanList), null);
            }else{
                s = HttpUtils.requestPostBody(C.CHECK_APPLY_URL_PRD, JSONObject.toJSONString(beanList), null);
            }
            log.error("推送成功："+s);
//            String substring = commonService.stringJieQu(s);
//            JSONObject jsonObject = JSONObject.parseObject(substring);
            String string = JSONObject.parseObject(s, String.class);
            JSONObject jsonObject = JSONObject.parseObject(string);

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

    @PostMapping("/selectBoHuiBaoXiaoDan")
    @ApiOperation(value = "环节查询驳回报销单接口")
    //@ValidateApiNotAuth
    public TResult<SelectBean> selectBoHuiBaoXiaoDan(HttpServletRequest request, @RequestBody SelectListBean rSelectBean) {
        log.info("环节查询驳回报销单接口:{}", new Gson().toJson(rSelectBean));
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

        log.info("打印环节查询驳回报销单接口:{}", new Gson().toJson(beanList));
        try {
            String s = "";
            if ("loc".equals(string)) {
                s = HttpUtils.requestPostBody(C.XPDL_APPLY_URL_DEV, JSONObject.toJSONString(beanList), null);
            } else if ("dev".equals(string)){
                s = HttpUtils.requestPostBody(C.XPDL_APPLY_URL_DEV, JSONObject.toJSONString(beanList), null);
            }else if("gray".equals(string)){
                s = HttpUtils.requestPostBody(C.XPDL_APPLY_URL_PRD, JSONObject.toJSONString(beanList), null);
            }else{
                s = HttpUtils.requestPostBody(C.XPDL_APPLY_URL_PRD, JSONObject.toJSONString(beanList), null);
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
    @PostMapping("/assignBaoXiaoDan")
    @ApiOperation(value = "查询分组员工列表")
    //@ValidateApiNotAuth
    public TResult<SubmitBean> rejectBaoXiaoDan(HttpServletRequest request, @RequestBody SubmitBean SubmitBean) {
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
                s = HttpUtils.requestPostBody(C.ASSIGN_APPLY_URL_DEV, JSONObject.toJSONString(beanList), null);
            } else if ("dev".equals(string)){
                s = HttpUtils.requestPostBody(C.ASSIGN_APPLY_URL_DEV, JSONObject.toJSONString(beanList), null);
            }else if("gray".equals(string)){
                s = HttpUtils.requestPostBody(C.ASSIGN_APPLY_URL_PRD, JSONObject.toJSONString(beanList), null);
            }else{
                s = HttpUtils.requestPostBody(C.ASSIGN_APPLY_URL_PRD, JSONObject.toJSONString(beanList), null);
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

    @PostMapping("/cancel")
    @ApiOperation(value = "取消")
    //@ValidateApiNotAuth
    public TResult<SubmitBean> cancel(HttpServletRequest request, @RequestBody SubmitBean RTravellingBean) {
        log.info("取消:{}", new Gson().toJson(RTravellingBean));
        Date date = new Date();

        String userId = SpringUtil.getSessionUserId(request);
        SUserInfo sUserInfo = (SUserInfo) redisService.get(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + userId);

        SubmitHeadBean submitHeadBean = new SubmitHeadBean();
        submitHeadBean.setBillcode(RTravellingBean.getHead().getBillcode());

        SubmitBean beanList = new SubmitBean();
        beanList.setUserid(sUserInfo.getUserId());
        beanList.setPk_group(sUserInfo.getPk_group());
        beanList.setHead(submitHeadBean);

        log.info("取消:{}", new Gson().toJson(beanList));
        try {
            String s = "";
            if ("loc".equals(string)) {
                s = HttpUtils.requestPostBody(C.UNAUDIT_URL_DEV, JSONObject.toJSONString(beanList), null);
            } else if ("dev".equals(string)){
                s = HttpUtils.requestPostBody(C.UNAUDIT_URL_DEV, JSONObject.toJSONString(beanList), null);
            }else if("gray".equals(string)){
                s = HttpUtils.requestPostBody(C.UNAUDIT_URL_PRD, JSONObject.toJSONString(beanList), null);
            }else{
                s = HttpUtils.requestPostBody(C.UNAUDIT_URL_PRD, JSONObject.toJSONString(beanList), null);
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


//    public static void main(String[] aa){
//        String  bb = "\"{\\\"result\\\":\\\"0\\\",\\\"billcode\\\":\\\"【差旅费费用报销单】系统设置住宿报销标准300.00元/天，实际报销住宿费为800.00元/天，请确认是否继续？\\\\r\\\\n\\\"}\"";
//
////        org.json.JSONObject jsonObject = new org.json.JSONObject(bb);
//
//        String string = JSONObject.parseObject(bb, String.class);
//        JSONObject jsonObject = JSONObject.parseObject(string);
//        System.out.println(jsonObject);
//    }

}
