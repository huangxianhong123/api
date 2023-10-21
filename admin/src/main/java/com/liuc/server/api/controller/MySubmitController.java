package com.liuc.server.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.liuc.server.api.bean.TResult;
import com.liuc.server.api.bean.app.*;
import com.liuc.server.api.common.C;
import com.liuc.server.api.common.SqlProvider;
import com.liuc.server.api.service.CommonService;
import com.liuc.server.api.service.RedisService;
import com.liuc.server.api.util.HttpUtils;
import com.liuc.server.api.util.ListPageUtil;
import com.liuc.server.api.util.SpringUtil;
import com.liuc.server.api.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author huangxianhong
 * @date 2021/3/8 17:13
 */
@RestController
@RequestMapping("/api/v1/mySubmit")
@Api(value = "mySubmit", description = "我提交", tags = "我提交")
@Slf4j
public class MySubmitController {
    @Value("${spring.profiles.active}")
    private String string;
    @Autowired
    private RedisService redisService;
    @Autowired
    private CommonService commonService;

    @GetMapping("/list")
    @ApiOperation(value = "列表", notes = "列表", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    public TResult<PageInfo<ApplyListBean>> list(
            @ApiParam(required = true, value = "状态（1=已完成，-1=未完成  0 审批中）") @RequestParam(required = true) String spzt,
            @ApiParam(required = false, value = "关键词") @RequestParam(required = false) String keyWord,
            @ApiParam(required = false, value = "交易类型名称（差旅费报销单、通用报销单、招待费报销单、行车费报销单、差旅费借款单、通用借款单、还款单）") @RequestParam(required = false) String jylx_name,
            @ApiParam(required = false, value = "借款、报销人") @RequestParam(required = false) String jkbxr,
//            @ApiParam(required = false, value = "开始提交时间") @RequestParam(required = false) String startTime,
//            @ApiParam(required = false, value = "结束提交时间") @RequestParam(required = false) String endTime,
            @ApiParam(required = false, value = "时间状态 0 降序 1 升序") @RequestParam(required = false) Integer time_type,
            @ApiParam(required = true, value = "当前页码") @RequestParam(required = true) Integer page,
            @ApiParam(required = true, value = "每页条数") @RequestParam(required = true) Integer size,
            HttpServletRequest request) throws ParseException {


        String userId = SpringUtil.getSessionUserId(request);
        SUserInfo sUserInfo = (SUserInfo) redisService.get(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + userId);
        String sql = SqlProvider.getMySubmitList(sUserInfo.getUserId(),keyWord,spzt,jkbxr,time_type,jylx_name);
        List<ApplyListBean> list = commonService.selectBySql(sql, ApplyListBean.class);
        if(list.size() > 0){
            for(ApplyListBean bean : list){
                String sqlCode = SqlProvider.getCurrtypeCode(bean.getCurrtype_code());
                CurrencyBean currencyBean =commonService.selectObjectBySql(sqlCode,CurrencyBean.class);
                if(currencyBean != null){
                    bean.setCurrtype_name(currencyBean.getName());
                }

            }
        }
        PageInfo<ApplyListBean> pageInfo = new PageInfo<>();
        pageInfo.setPageNum(page);
        pageInfo.setPageSize(size);
        pageInfo.setTotal(list.size());
        int pages = list.size() / size;
        if (list.size() % size != 0) {
            pages++;
        }
        pageInfo.setPages(pages);
        if (list.size() > 0) {
            List<ApplyListBean> listPage = ListPageUtil.getListPage(page, size, list);
            pageInfo.setList(listPage);
        } else {
            pageInfo.setList(new ArrayList<>());
        }
        return new TResult(pageInfo);
    }

    @GetMapping("/getSubmitDetail")
    @ApiOperation(value = "报销单详情", notes = "报销单详情", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    public TResult<ApplyListBean> getSubmitDetail(
            @ApiParam(required = true, value = "单据编号") @RequestParam(required = true) String djbh,
            HttpServletRequest request) throws ParseException {


        String userId = SpringUtil.getSessionUserId(request);
        SUserInfo sUser = (SUserInfo) redisService.get(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + userId);

        String sql = SqlProvider.getSubmitDetail(djbh);
        ApplyListBean detailApply = commonService.selectObjectBySql(sql, ApplyListBean.class);
        String sqlCode = SqlProvider.getCurrtypeCode(detailApply.getCurrtype_code());
        CurrencyBean currencyBean =commonService.selectObjectBySql(sqlCode,CurrencyBean.class);
        if(currencyBean != null){
            detailApply.setCurrtype_name(currencyBean.getName());
        }

        QueryFileBean beanList = new QueryFileBean();
        beanList.setUserid(sUser.getUserId());
        beanList.setPk_group(sUser.getPk_group());
        beanList.setParentpath(detailApply.getPk_jkbx());

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
            if("1".equals(String.valueOf(jsonObject.get("result")))){
                return new TResult<>(1,String.valueOf(jsonObject.get("message")));
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("推送失败" + e);
            return new TResult<>(C.ERROR_CODE_COM,"推送失败");
        }


        String sqlBank = SqlProvider.getPersonBankList("",detailApply.getSkyhzh());
        PersonBankBean listBank = commonService.selectObjectBySql(sqlBank, PersonBankBean.class);
        if(listBank != null){
            detailApply.setSkyhzh_name(listBank.getBankdocname());
        }

        String sqlSet = SqlProvider.getSettlementList(detailApply.getJsfs());
        SettlementBean listSet = commonService.selectObjectBySql(sqlSet, SettlementBean.class);
        if(listSet != null){
            detailApply.setJsfs(listSet.getCode());
            detailApply.setJsfs_name(listSet.getName());
        }

        if("通用借款单".equals(detailApply.getJylx_name()) || "差旅费借款单".equals(detailApply.getJylx_name())){
            String sqlAll = SqlProvider.getDefdocId("",detailApply.getZyx25());
            CostBean listBean = commonService.selectObjectBySql(sqlAll, CostBean.class);

            if(listBean != null){
                detailApply.setZyx30_name(listBean.getName());
            }
        }else{

            String sqlAll = SqlProvider.getDefdocId("",detailApply.getZyx30());
            CostBean listBean = commonService.selectObjectBySql(sqlAll, CostBean.class);

            if(listBean != null){
                detailApply.setZyx30_name(listBean.getName());
            }
         }



        String sqlUser = SqlProvider.getUserCode(detailApply.getJkbxr());
        SUserInfo sUserInfo =commonService.selectObjectBySql(sqlUser,SUserInfo.class);
        if(sUserInfo != null){
            detailApply.setJkbxr_code(sUserInfo.getCode());
        }

        String sqlOrg = SqlProvider.getOrgCode(detailApply.getPk_org());
        BussinessUnitBean orgCode =commonService.selectObjectBySql(sqlOrg,BussinessUnitBean.class);
        if(orgCode != null){
            detailApply.setPk_prg_code(orgCode.getCode());
        }

        String sqlFydwbm = SqlProvider.getOrgCode(detailApply.getFydwbm());
        BussinessUnitBean orgFydwbm =commonService.selectObjectBySql(sqlFydwbm,BussinessUnitBean.class);
        if(orgFydwbm != null){
            detailApply.setFydwbm_code(orgFydwbm.getCode());
        }

        String sqlDwbm = SqlProvider.getOrgCode(detailApply.getDwbm());
        BussinessUnitBean orgDwbm =commonService.selectObjectBySql(sqlDwbm,BussinessUnitBean.class);
        if(orgDwbm != null){
            detailApply.setDwbm_code(orgDwbm.getCode());
        }

        String sqlDeptid = SqlProvider.getDeptCode(detailApply.getDeptid(),orgDwbm.getId());
        DeptInfoBean orgDeptid =commonService.selectObjectBySql(sqlDeptid,DeptInfoBean.class);
        if(orgDeptid != null){
            detailApply.setDeptid_code(orgDeptid.getCode());
        }

        String sqlFydeptid = SqlProvider.getDeptCode(detailApply.getFydeptid(),orgFydwbm.getId());
        DeptInfoBean orgFydeptid =commonService.selectObjectBySql(sqlFydeptid,DeptInfoBean.class);
        if(orgFydeptid != null){
            detailApply.setFydeptid_code(orgFydeptid.getCode());
            detailApply.setFydwbm_id(orgFydeptid.getOrgid());
        }

        String sqlProject = SqlProvider.getPaymentName(detailApply.getSzxm_name());
        ProjectBean projectName = commonService.selectObjectBySql(sqlProject, ProjectBean.class);
        if(projectName !=null){
            detailApply.setSzxmid(projectName.getCode());
        }

        String sqlFileUrl = SqlProvider.getFileUrl(detailApply.getPk_jkbx());
        List<FileUrlBean> fileUrlBean = commonService.selectBySql(sqlFileUrl,FileUrlBean.class);
//        if(fileUrlBean != null){
//            detailApply.setFileUrl(fileUrlBean.getUrl());
//            detailApply.setFileName(fileUrlBean.getName());
//            detailApply.setIpiname(fileUrlBean.getIpiname());
//        }

        detailApply.setFileUrlBeans(fileUrlBean);

        return new TResult(detailApply);
    }

    @GetMapping("/getTableDetail")
    @ApiOperation(value = "报销单/借款单明细", notes = "报销单/借款单明细", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    public TResult<TableDetailBean> getTableDetail(
            @ApiParam(required = true, value = "单据编号") @RequestParam(required = true) String djbh,
            @ApiParam(required = true, value = "页签编码（jk_busitem: 借款单明细  arap_bxbusitem：交通费用、jk_busitem：借款明细、other：住宿费用 zsitem 餐费 bzitem 午餐补贴）") @RequestParam(required = true) String tableCode,
            HttpServletRequest request) throws ParseException {


        String sql = SqlProvider.getTableDetail(djbh,tableCode);
        List<TableDetailBean> tableDetailBean = commonService.selectBySql(sql, TableDetailBean.class);
        if(tableDetailBean.size() > 0){
            for(TableDetailBean bean : tableDetailBean){
                String sqlAll = SqlProvider.getZdlxList(bean.getDefitem50());
                ErBean list = commonService.selectObjectBySql(sqlAll, ErBean.class);
                if(list != null){
                    bean.setDefitem50_name(list.getName());
                }

                String sqlName = SqlProvider.getReimtypeList(bean.getReimtype_name());
                ErBean listName = commonService.selectObjectBySql(sqlName, ErBean.class);
                if(listName != null){
                    bean.setReimtype_code(listName.getCode());
                }

                if(StringUtil.notEmpty(bean.getDefitem5()) && ! "~".equals(bean.getDefitem5())){
                    String sqlFive = SqlProvider.getEnumvalueCode("",bean.getDefitem5());
                    CostBean costBean = commonService.selectObjectBySql(sqlFive, CostBean.class);
                    if(costBean !=null){
                        bean.setDefitem5_name(costBean.getCode());
                    }
                }else{
                    String sqlFour = SqlProvider.getEnumvalueCode("",bean.getDefitem4());
                    CostBean costBean = commonService.selectObjectBySql(sqlFour, CostBean.class);
                    if(costBean !=null){
                        bean.setDefitem5_name(costBean.getCode());
                    }
                }


                String sqlProject = SqlProvider.getPaymentName(bean.getSzxm_name());
                ProjectBean projectName = commonService.selectObjectBySql(sqlProject, ProjectBean.class);
                if(projectName !=null){
                    bean.setSzxmid(projectName.getCode());
                }
            }
        }
        return new TResult(tableDetailBean);
    }

    @GetMapping("/getRepaymentDetail")
    @ApiOperation(value = "还款单冲销明细", notes = "还款单冲销明细", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    public TResult<RepaymentDetailBean> getRepaymentDetail(
            @ApiParam(required = true, value = "单据编号") @RequestParam(required = true) String djbh,
            HttpServletRequest request) throws ParseException {


        String sql = SqlProvider.getRepaymentDetail(djbh);
        List<RepaymentDetailBean> repaymentList = commonService.selectBySql(sql, RepaymentDetailBean.class);

        return new TResult(repaymentList);
    }

    @GetMapping("/getApproveList")
    @ApiOperation(value = "审批进度", notes = "审批进度", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    public TResult<ApproveBean> getApproveList(
            @ApiParam(required = true, value = "单据编号") @RequestParam(required = true) String djbh,
            HttpServletRequest request) throws ParseException {


        String sql = SqlProvider.getApproveList(djbh);
        List<ApproveBean> approveList = commonService.selectBySql(sql, ApproveBean.class);
        List<ApproveBean> list = new ArrayList<>();



        if(approveList.size() > 0){

            ApproveBean approveBean = new ApproveBean();
            approveBean.setAPPROVERESULT("Y");
            approveBean.setDealdate(approveList.get(0).getSenddate());
            approveBean.setUser_name(approveList.get(0).getSenderman());
            approveBean.setBillno(approveList.get(0).getBillno());
            approveBean.setChecknote("");
            approveBean.setIscheck("N");
            list.add(approveBean);

            for(ApproveBean bean : approveList){
//                if(bean.getSenderman().equals(approveList.get(0).getSenderman())){
//                    ApproveBean approveBean3 = new ApproveBean();
//                    approveBean3.setAPPROVERESULT("Y");
//                    approveBean3.setDealdate(approveList.get(0).getSenddate());
//                    approveBean3.setUser_name(approveList.get(0).getSenderman());
//                    approveBean3.setBillno(approveList.get(0).getBillno());
//                    approveBean3.setChecknote("");
//                    approveBean3.setIscheckl(approveList.get(0).getIscheckl());
//                    list.add(approveBean3);
//                }


                ApproveBean approveBean1 = new ApproveBean();
                approveBean1.setAPPROVERESULT(bean.getAPPROVERESULT());
                approveBean1.setDealdate(bean.getDealdate());
                approveBean1.setUser_name(bean.getUser_name());
                approveBean1.setBillno(bean.getBillno());
                approveBean1.setChecknote(bean.getChecknote());
                approveBean1.setIscheck(bean.getIscheck());
                list.add(approveBean1);
            }
        }
        return new TResult(list);
    }

    @GetMapping("/getFeiYongFenTanMingXl")
    @ApiOperation(value = "费用分摊明细", notes = "费用分摊明细", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    public TResult<ShareBean> getFeiYongFenTanMingXl(
            @ApiParam(required = true, value = "单据编号") @RequestParam(required = true) String djbh,
            HttpServletRequest request) throws ParseException {


        String sql = SqlProvider.getFeiYongFenTanMingXl(djbh);
        List<ShareBean> shareBeans = commonService.selectBySql(sql, ShareBean.class);
        if(shareBeans.size() > 0){
            for(ShareBean bean : shareBeans){
                String sqlDwbm = SqlProvider.getOrgCode(bean.getOrg_name());
                BussinessUnitBean orgDwbm =commonService.selectObjectBySql(sqlDwbm,BussinessUnitBean.class);
                if(orgDwbm != null){
                    bean.setOrg_id(orgDwbm.getId());
                }
            }
        }
        return new TResult(shareBeans);
    }


}
