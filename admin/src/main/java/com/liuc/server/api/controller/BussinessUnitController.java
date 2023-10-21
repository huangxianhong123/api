package com.liuc.server.api.controller;

import com.alibaba.fastjson.JSON;
import com.liuc.server.api.annotation.ValidateApiNotAuth;
import com.liuc.server.api.bean.TResult;
import com.liuc.server.api.bean.app.*;
import com.liuc.server.api.common.C;
import com.liuc.server.api.common.SqlProvider;
import com.liuc.server.api.service.CommonService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author S.Yang
 * @date 2021/2/23 16:19
 */
@RestController
@RequestMapping("/api/v1/bussinessUnit")
@Api(value = "bussinessUnit", description = "单位部门", tags = "单位部门")
@Slf4j
public class BussinessUnitController {
    @Autowired
    private CommonService commonService;

    @GetMapping(value = "/getBussinessUnit")
    @ApiOperation(value = "业务单元", notes = "业务单元", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @ValidateApiNotAuth
    public TResult<List<BussinessUnitBean>> getBussinessUnit(
            @ApiParam(required = false, value = "关键词") @RequestParam(required = false) String keyword
    )  {

        String sql = SqlProvider.getAllBussinessUnit(keyword);
        List<BussinessUnitBean> list = commonService.selectBySql(sql, BussinessUnitBean.class);
        List<BussinessUnitBean> level1list = list.stream().filter(BussinessUnitBean->("01".equals(BussinessUnitBean.getCode()))).collect(Collectors.toList());
        if(level1list.size() > 0){
            for(BussinessUnitBean level1:level1list){
                List<BussinessUnitBean> level2list = list.stream().filter(BussinessUnitBean->(level1.getCode().equals(BussinessUnitBean.getFatherCode()))).collect(Collectors.toList());
                for(BussinessUnitBean level2:level2list){
                    List<BussinessUnitBean> level3list = list.stream()
                            .filter(BussinessUnitBean->(level2.getCode().equals(BussinessUnitBean.getFatherCode())))
                            .collect(Collectors.toList());
                    for(BussinessUnitBean level3:level3list){
                        List<BussinessUnitBean> level4list = list.stream()
                                .filter(BussinessUnitBean->(level3.getCode().equals(BussinessUnitBean.getFatherCode())))
                                .collect(Collectors.toList());
                        level3.setChilds(level4list);
                    }
                    level2.setChilds(level3list);
                }
                level1.setChilds(level2list);
            }
            return new TResult(0, level1list);
        }else{
            for(BussinessUnitBean level1:list){
                List<BussinessUnitBean> level2list = list.stream().filter(BussinessUnitBean->(level1.getCode().equals(BussinessUnitBean.getFatherCode()))).collect(Collectors.toList());
                for(BussinessUnitBean level2:level2list){
                    List<BussinessUnitBean> level3list = list.stream()
                            .filter(BussinessUnitBean->(level2.getCode().equals(BussinessUnitBean.getFatherCode())))
                            .collect(Collectors.toList());
                    for(BussinessUnitBean level3:level3list){
                        List<BussinessUnitBean> level4list = list.stream()
                                .filter(BussinessUnitBean->(level3.getCode().equals(BussinessUnitBean.getFatherCode())))
                                .collect(Collectors.toList());
                        level3.setChilds(level4list);
                    }
                    level2.setChilds(level3list);
                }
                level1.setChilds(level2list);
            }
            return new TResult(0, list);
        }

    }
    @GetMapping(value = "/getDept")
    @ApiOperation(value = "部门列表", notes = "部门列表", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @ValidateApiNotAuth
    public TResult<List<DeptInfoBean>> getDept(String orgId,
    @ApiParam(required = false, value = "关键词") @RequestParam(required = false) String keyword)  {

        String sql = SqlProvider.getDistinctPid(orgId,keyword);
        List<DeptInfoBean> list = commonService.selectBySql(sql, DeptInfoBean.class);
        if(list.size() > 0){
            for(DeptInfoBean beanOne : list){
                //第二级
                String sqlTwo = SqlProvider.listDepartment(beanOne.getCode(),orgId);
                List<DeptInfoBean> listTwo = commonService.selectBySql(sqlTwo, DeptInfoBean.class);
                if(listTwo.size() > 0){
                    for(DeptInfoBean beanTwo : listTwo){
                        //第二级
                        String sqlThree = SqlProvider.listDepartment(beanTwo.getCode(),orgId);
                        List<DeptInfoBean> listThree = commonService.selectBySql(sqlThree, DeptInfoBean.class);
                        beanTwo.setChilds(listThree);
                    }
                }
                beanOne.setChilds(listTwo);
            }
        }

        return new TResult(0,list);
    }


    @GetMapping(value = "/getUserInfoList")
    @ApiOperation(value = "人员列表", notes = "人员列表", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @ValidateApiNotAuth
    public TResult<SUserInfo> getUserInfoByCode(@ApiParam(required = true, value = "部门code") @RequestParam(required = true) String code) {
        String sql = SqlProvider.getUserInfoByCode("",code);
        List<SUserInfo> sUserInfo =commonService.selectBySql(sql,SUserInfo.class);

        return new TResult(0, sUserInfo);
    }

}
