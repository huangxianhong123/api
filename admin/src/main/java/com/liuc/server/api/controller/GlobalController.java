package com.liuc.server.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.liuc.server.api.bean.TResult;
import com.liuc.server.api.bean.app.*;
import com.liuc.server.api.common.C;
import com.liuc.server.api.common.SqlProvider;
import com.liuc.server.api.service.CommonService;
import com.liuc.server.api.service.RedisService;
import com.liuc.server.api.service.YouSpaceService;
import com.liuc.server.api.util.ListPageUtil;
import com.liuc.server.api.util.SpringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

;

/**
 * @author huangxianhong
 * @date 2021/1/27 15:28
 */
@RestController
@RequestMapping("/api/v1/globalDetail")
@Api(value = "globalDetail", description = "全局说明", tags = "全局说明")
@Slf4j
public class GlobalController {

    @Autowired
    private YouSpaceService youSpaceService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CommonService commonService;
    @Autowired
    private RedisService redisService;


    @GetMapping(value = "/getBankList")
    @ApiOperation(value = "银行账户", notes = "银行账户", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    public TResult<List<BankBean>> getBankList(@ApiParam(required = false, value = "accclass 账户分类 0=个人，1=客户，2=公司，3=供应商") @RequestParam(required = false) Integer accclass)  {

        String sql = SqlProvider.getBankList(accclass);
        List<BankBean> list = commonService.selectBySql(sql, BankBean.class);

        return new TResult(0, list);
    }

    @GetMapping(value = "/getPersonBankList")
    @ApiOperation(value = "个人银行", notes = "个人银行", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    public TResult<List<PersonBankBean>> getPersonBankList(HttpServletRequest request)  {

        String userId = SpringUtil.getSessionUserId(request);

        SUserInfo sUserInfo = (SUserInfo) redisService.get(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + userId);

        String sql = SqlProvider.getPersonBankList(sUserInfo.getCode(),"");
        List<PersonBankBean> list = commonService.selectBySql(sql, PersonBankBean.class);

        return new TResult(0, list);
    }


    @GetMapping(value = "/getSettlementList")
    @ApiOperation(value = "结算方式", notes = "结算方式", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    public TResult<List<SettlementBean>> getSettlementList()  {

        String sql = SqlProvider.getSettlementList("");
        List<SettlementBean> list = commonService.selectBySql(sql, SettlementBean.class);
        return new TResult(0, list);
    }


    @GetMapping(value = "/getTrafficList")
    @ApiOperation(value = "交通工具", notes = "交通工具", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    public TResult<List<TrafficBean>> getTrafficList()  {

        String sql = SqlProvider.getTrafficList();
        List<TrafficBean> list = commonService.selectBySql(sql, TrafficBean.class);
        return new TResult(0, list);
    }


    @GetMapping(value = "/getCurrency")
    @ApiOperation(value = "币种", notes = "币种", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    public TResult<List<CurrencyBean>> getCurrency()  {

        String sql = SqlProvider.getCurrency();
        List<CurrencyBean> list = commonService.selectBySql(sql, CurrencyBean.class);
        return new TResult(0, list);
    }

    @GetMapping(value = "/getCostList")
    @ApiOperation(value = "费用大类", notes = "费用大类", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    public TResult<List<CostBean>> getCostList(@ApiParam(required = true, value = "code查询（0007：差旅费报销单  0001：通用报销单）") @RequestParam(required = true) String code)  {

        String sql = SqlProvider.getReimtypeCode(code);
        DjkzgzBean djkzgzBean = commonService.selectObjectBySql(sql, DjkzgzBean.class);
        if(djkzgzBean == null) {
            String sqlAll = SqlProvider.getCostList();
            List<CostBean> list = commonService.selectBySql(sqlAll, CostBean.class);
            return new TResult(0, list);
        }else{
            String sqlOne = SqlProvider.getDefdocCode(djkzgzBean.getPk_restraint());
            List<CostBean> list = commonService.selectBySql(sqlOne, CostBean.class);
            return new TResult(0, list);

        }

    }

    @GetMapping(value = "/getChargeList")
    @ApiOperation(value = "选择冲销款", notes = "选择冲销款", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    public TResult<PageInfo<ChargeBean>> getChargeList(@ApiParam(required = false, value = "单据日期") @RequestParam(required = false) String djrq,
                                                       @ApiParam(required = true, value = "当前页码") @RequestParam(required = true) Integer page,
                                                       @ApiParam(required = true, value = "每页条数") @RequestParam(required = true) Integer size,
                                                       @ApiParam(required = false, value = "借款人") @RequestParam(required = false) String name,
                                                       @ApiParam(required = false, value = "开始时间") @RequestParam(required = false) String startTime,
                                                       @ApiParam(required = false, value = "结束时间") @RequestParam(required = false) String endTime,
                                                       HttpServletRequest request)  {
        String userId = SpringUtil.getSessionUserId(request);
        SUserInfo sUserInfo = (SUserInfo) redisService.get(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + userId);
        String sql = SqlProvider.getChargeList(sUserInfo.getId(),sUserInfo.getOrgid(),djrq,name,startTime,endTime);
        List<ChargeBean> list = commonService.selectBySql(sql, ChargeBean.class);
        if(list.size() > 0){
            for(ChargeBean bean : list){
                String sqlCode = SqlProvider.getCurrtypeCode(bean.getCurrtype_code());
                CurrencyBean currencyBean =commonService.selectObjectBySql(sqlCode,CurrencyBean.class);
                if(currencyBean != null){
                    bean.setCurrtype_name(currencyBean.getName());
                }

            }
        }
        PageInfo<ChargeBean> pageInfo = new PageInfo<>();
        pageInfo.setPageNum(page);
        pageInfo.setPageSize(size);
        pageInfo.setTotal(list.size());
        int pages = list.size() / size;
        if (list.size() % size != 0) {
            pages++;
        }
        pageInfo.setPages(pages);
        if (list.size() > 0) {
            List<ChargeBean> listPage = ListPageUtil.getListPage(page, size, list);
            pageInfo.setList(listPage);
        } else {
            pageInfo.setList(new ArrayList<>());
        }

        return new TResult(pageInfo);

    }

    @GetMapping(value = "/getPaymentProjectList")
    @ApiOperation(value = "收支项目", notes = "收支项目", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    public TResult<List<ProjectBean>> getPaymentProjectList()  {

        String sql = SqlProvider.getPaymentProjectList("");
        List<ProjectBean> list = commonService.selectBySql(sql, ProjectBean.class);
        return new TResult(0, list);
    }


    @GetMapping(value = "/getReimtypeList")
    @ApiOperation(value = "报销类型", notes = "报销类型", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    public TResult<List<ErBean>> getReimtypeList(@ApiParam(required = true, value = "code查询（0002：差旅费报销单  0003：招待费报销单 0004：行车费报销单 0006：通用报销单）") @RequestParam(required = true) String code)  {
        String sql = SqlProvider.getReimtypeCode(code);
        DjkzgzBean djkzgzBean = commonService.selectObjectBySql(sql, DjkzgzBean.class);
        if(djkzgzBean == null){
            String sqlAll = SqlProvider.getReimtypeList("");
            List<ErBean> list = commonService.selectBySql(sqlAll, ErBean.class);
            return new TResult(0, list);
        }else{
            String sqlOne = SqlProvider.getReimtypeListCode(djkzgzBean.getPk_restraint());
            List<ErBean> list = commonService.selectBySql(sqlOne, ErBean.class);
            return new TResult(0, list);
        }

    }

    @GetMapping(value = "/getZdlxList")
    @ApiOperation(value = "招待类型", notes = "招待类型", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    public TResult<List<ErBean>> getZdlxList()  {


        String sqlAll = SqlProvider.getZdlxList("");
        List<ErBean> list = commonService.selectBySql(sqlAll, ErBean.class);
        return new TResult(0, list);

    }
}
