package com.liuc.server.api.service;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;
import com.liuc.server.api.common.CommonMapper;
import com.liuc.server.api.util.JSONUtil;
import com.liuc.server.api.util.MD5Util;
import io.jsonwebtoken.lang.Collections;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;


@Component
@Slf4j
public class CommonService {

    @Autowired
    private PKIDService pkidService;
    @Autowired
    private CommonMapper commonMapper;

    public String getPKID() {
        return "LC" + pkidService.getInstance().nextId();
    }

    public String getID() {
        return "ID" + pkidService.getInstance().nextId();
    }

    public String getCODE() {
        return pkidService.getInstance().nextId() + "";
    }

    public <T> List<T> selectBySql(String sql, Class<T> cls) {
        Map<String, String> map = new HashMap<>();
        map.put("sql", sql);
        List<Map<String, Object>> maps = commonMapper.commonSelect(map);
        return JSONUtil.jsonToList(JSONArray.toJSONString(maps), cls);
    }

    public <T> PageInfo<T> selectBySql(String sql, int page, int size, Class<T> cls) {

        PageInfo<T> pageInfo = new PageInfo<>();
        Map<String, String> map = new HashMap<>();

        //分页
        if (page > 0) {
            page = (page - 1) * size;
        }
        String sb = "select t.* from ( " +
                sql +
                " ) t limit " + page + "," + size;
        map.put("sql", sb);
        List<Map<String, Object>> dataList = commonMapper.commonSelect(map);

        //总页数
        String countSql = "select count(1) total from ( " +
                sql +
                " ) t";
        map.put("sql", countSql);
        List<Map<String, Object>> countList = commonMapper.commonSelect(map);

        if (!Collections.isEmpty(dataList)) {

            Long total = (Long) countList.get(0).get("total");
            List<T> list = JSONUtil.jsonToList(JSONArray.toJSONString(dataList), cls);

            long pages = total % size > 0 ? total / size + 1 : total / size;

            pageInfo.setTotal(total);
            pageInfo.setPageSize(size);
            pageInfo.setPageNum(page + 1);
            pageInfo.setPages(Integer.parseInt(Long.toString(pages)));
            pageInfo.setList(list);

        }

        return pageInfo;
    }

    public <T> T selectObjectBySql(String sql, Class<T> cls) {
        Map<String, String> map = new HashMap<>();
        map.put("sql", sql);
        List<Map<String, Object>> maps = commonMapper.commonSelect(map);
        List<T> list = JSONUtil.jsonToList(JSONArray.toJSONString(maps), cls);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public String getSn(Integer type) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        if(type ==1){
            System.out.println("2631" + "-" + sdf.format(new Date()) + "-" + (int) ((Math.random() * 9 + 1) * 10000));
            return "2631" + "-" + sdf.format(new Date()) + "-" + (int) ((Math.random() * 9 + 1) * 10000);
        }else if(type == 3){
            return "djlx" + "-" + sdf.format(new Date()) + "-" + (int) ((Math.random() * 9 + 1) * 10000);
        }else{
            System.out.println("263X-Cxx-TYJKD" + "-" + sdf.format(new Date()) + "-" + (int) ((Math.random() * 9 + 1) * 10000));
            return "263X-Cxx-TYJKD" + "-" + sdf.format(new Date()) + "-" + (int) ((Math.random() * 9 + 1) * 10000);
        }

    }

    public String stringJieQu(String string){
        String replaceAll = string.replaceAll("\\\\", "");
        System.out.println("replaceAll:"+replaceAll);

        String substring = replaceAll.substring(1, replaceAll.length() - 1);
        System.out.println("substring:"+substring);
        return substring;
    }


    public String checkSign() {

        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String content = "2af89e0dc4e1acb5c52b3155f85a1307" + timestamp;
        String mySign = Objects.requireNonNull(MD5Util.MD5(content)).toLowerCase();
        return mySign;
    }

}
