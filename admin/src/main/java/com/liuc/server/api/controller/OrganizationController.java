//package com.liuc.server.api.controller;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.liuc.server.api.bean.TResult;
//import com.liuc.server.api.bean.app.OrganizationBean;
//import com.liuc.server.api.bean.app.SUserInfo;
//import com.liuc.server.api.common.C;
//import com.liuc.server.api.custommodel.NCBusinessUnit;
//import com.liuc.server.api.custommodel.NCDepartment;
//import com.liuc.server.api.service.NCService;
//import com.liuc.server.api.service.OrganizationService;
//import com.liuc.server.api.util.SpringUtil;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.*;
//
///**
// * @author huangxianhong
// * @date 2021/01/28 14:30
// */
//@RestController
//@RequestMapping("/api/v1/organization")
//@Api(value = "organization", description = "组织架构", tags = "组织架构", produces = MediaType.APPLICATION_JSON_VALUE)
//@Slf4j
//public class OrganizationController {
//
//    @Autowired
//    private NCService ncService;
//    @Autowired
//    private OrganizationService organizationService;
//
//
//
//    @GetMapping("/listOrganization")
//    @ApiOperation(value = "组织架构列表", notes = "组织架构列表", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
//    public TResult<List<OrganizationBean>> listOrganization(HttpServletRequest request){
//
//        String userId = SpringUtil.getSessionUserId(request);
////        SUserInfo sUserInfo = (SUserInfo) redisService.get(C.RedisPrefix.SESSION_WEB_YONGYOU_JISHI + userId);
//       // String deptid = C.deptid.get("deptid_"+userId);
//        SUserInfo sUserInfo =  C.sessionInfo.get("userId_"+userId);
//
//        List<OrganizationBean> beans =  organizationService.list(sUserInfo.getDeptid());
//
//        return new TResult(beans);
//    }
//
//
//    @GetMapping("/test")
//    @ApiOperation(value = "test", notes = "test", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
//    public TResult<List<OrganizationBean>> test(HttpServletRequest request){
//
//        List<OrganizationBean> list = new ArrayList<>();
//        List<OrganizationBean> listBusinessUnits = new ArrayList<>();
//        List<OrganizationBean> listDepartment = new ArrayList<>();
//
//        List<NCBusinessUnit> ncBusinessUnits = ncService.getNCBusinessUnitList();
//        for(NCBusinessUnit unit : ncBusinessUnits){
//            OrganizationBean organizationBean = new OrganizationBean();
//            BeanUtils.copyProperties(unit, organizationBean);
//            listBusinessUnits.add(organizationBean);
//        }
//
//        List<NCDepartment> ncDepartmentList = ncService.getNCDepartmentList();
//        for(NCDepartment department : ncDepartmentList){
//            OrganizationBean departmentBean = new OrganizationBean();
//            BeanUtils.copyProperties(department, departmentBean);
//            departmentBean.setPid(department.getPkOrg());
//            listDepartment.add(departmentBean);
//        }
//
//        list.addAll(listBusinessUnits);
//        list.addAll(listDepartment);
//
//
//        JSONArray studentJsonArray  = JSON.parseArray(JSONObject.toJSONString(list));
//        //System.out.println("\n方式 1: " + studentJsonArray.toJSONString());
//
//        JSONArray listTree = listToTree(studentJsonArray,"id","pid","ids");
//        return new TResult(listTree);
//
//    }
//
//
//    /**
//
//     - listToTree
//     - <p>方法说明<p>
//     - 将JSONArray数组转为树状结构
//     - @param arr 需要转化的数据
//     - @param id 数据唯一的标识键值
//     - @param pid 父id唯一标识键值
//     - @param child 子节点键值
//     - @return JSONArray
//     */
//    public static com.alibaba.fastjson.JSONArray listToTree(com.alibaba.fastjson.JSONArray arr, String id, String pid, String child){
//        com.alibaba.fastjson.JSONArray r = new com.alibaba.fastjson.JSONArray();
//        com.alibaba.fastjson.JSONObject hash = new com.alibaba.fastjson.JSONObject();
//        //将数组转为Object的形式，key为数组中的id
//        for(int i=0;i<arr.size();i++){
//            com.alibaba.fastjson.JSONObject json = (com.alibaba.fastjson.JSONObject) arr.get(i);
//            hash.put(json.getString(id), json);
//        }
//        //遍历结果集
//        for(int j=0;j<arr.size();j++){
//            //单条记录
//            com.alibaba.fastjson.JSONObject aVal = (com.alibaba.fastjson.JSONObject) arr.get(j);
//            //在hash中取出key为单条记录中pid的值
//            com.alibaba.fastjson.JSONObject hashVP = (com.alibaba.fastjson.JSONObject) hash.get(aVal.get(pid).toString());
//            //如果记录的pid存在，则说明它有父节点，将她添加到孩子节点的集合中
//            if(hashVP!=null){
//                //检查是否有child属性
//                if(hashVP.get(child)!=null){
//                    com.alibaba.fastjson.JSONArray ch = (com.alibaba.fastjson.JSONArray) hashVP.get(child);
//                    ch.add(aVal);
//                    hashVP.put(child, ch);
//                }else{
//                    com.alibaba.fastjson.JSONArray ch = new com.alibaba.fastjson.JSONArray();
//                    ch.add(aVal);
//                    hashVP.put(child, ch);
//                }
//            }else{
//                r.add(aVal);
//            }
//        }
//        return r;
//    }
//
//
//
//}
