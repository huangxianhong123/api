package com.liuc.server.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.liuc.server.api.annotation.ValidateApiNotAuth;
import com.liuc.server.api.bean.SLoginTicket;
import com.liuc.server.api.bean.TResult;
import com.liuc.server.api.bean.app.*;
import com.liuc.server.api.common.C;
import com.liuc.server.api.common.SqlProvider;
import com.liuc.server.api.service.CommonService;
import com.liuc.server.api.service.RedisService;
import com.liuc.server.api.util.*;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by huangxianhong on 2021/03/17.
 */
@RestController
@RequestMapping("/api/v1/common")
@Api(value = "common", description = "共用接口(附件物流)", tags = "共用接口(附件物流)")
@Slf4j
public class CommonController {

    @Value("${spring.profiles.active}")
    private String string;


    @Autowired
    CommonService commonService;

    @Value("${server.addr}")
    private String serverAddr;
    @Autowired
    private RedisService redisService;

    protected final static Logger logger = LoggerFactory.getLogger(CommonController.class);
    @Autowired
    private org.springframework.core.env.Environment env;

    @PostMapping("/upload")
    @ApiOperation(value = "文件上传", notes = "上传文件至服务器", httpMethod = "POST")
    @ValidateApiNotAuth
    public TResult<CommonFile> list(
            @ApiParam(required = true, value = "file") @RequestParam(required = true) MultipartFile file,
            String session,
            HttpServletRequest request
    ) throws IOException {
        SUserInfo sUserInfo = new SUserInfo();
        if(StringUtil.isEmpty(session)){
            String userId = SpringUtil.getSessionUserId(request);
            sUserInfo = (SUserInfo) redisService.get(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + userId);
        }else{
            sUserInfo.setUserId(commonService.getPKID());
        }

        log.info("文件上传 file --->" + file);
        BufferedOutputStream bufferedOutputStream = null;
        FileOutputStream fileOutputStream = null;
        java.io.File savePath = null;

        //类型默认为图片
        String uploadFilePath = file.getOriginalFilename().replaceAll("\\\\", "/");
        String uploadFileName = uploadFilePath.substring(
                uploadFilePath.lastIndexOf('/') + 1, uploadFilePath.lastIndexOf('.'));
        String uploadFileSuffix = uploadFilePath.substring(uploadFilePath.lastIndexOf('.') + 1, uploadFilePath.length());
        long currentTime = System.currentTimeMillis();
        String path = "";
        try {
            path = StringUtil.getRandomString(10) + "_" + currentTime + "." + uploadFileSuffix;
            savePath = new java.io.File(C.MEDIA_UPLOAD_LOCATION_ROOT + C.MEDIA_UPLOAD_LOCATION_PIC + path);

            fileOutputStream = new FileOutputStream(savePath);
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            bufferedOutputStream.write(file.getBytes());
            bufferedOutputStream.flush();
            bufferedOutputStream.close();


            CommonFile commonFile = new CommonFile();

            SLoginTicket loginTicket = new SLoginTicket();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("fileJson", path);
            if(sUserInfo !=null){
                loginTicket.setTicket(JWTUtil.createJWT(System.currentTimeMillis() + "", sUserInfo.getUserId() + "", jsonObject.toString(), 9999999999L));
            }else{
                loginTicket.setTicket(JWTUtil.createJWT(System.currentTimeMillis() + "", "123456" + "", jsonObject.toString(), 9999999999L));
            }



            commonFile.setFid(path);
            commonFile.setUploadType(1);
            commonFile.setImgUrl(serverAddr +"api/v1/common/downFile?ticket=" + loginTicket.getTicket());
            commonFile.setFileName(uploadFileName + "." + uploadFileSuffix);
            commonFile.setFileType(uploadFileSuffix);
            commonFile.setFileSize(Double.valueOf(savePath.length()));
            commonFile.setFileMd5(FileUtil.getMD5(savePath));
            commonFile.setCreateTime(new Date());

            return new TResult<>(commonFile);
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
            if (bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

    @PostMapping("/addUploadFile")
    @ApiOperation(value = "保存")
    public TResult<UploadFileBean> addUploadFile(HttpServletRequest request, @RequestBody UploadFileBean RApplyBean) {
        log.info("保存图片:{}", new Gson().toJson(RApplyBean));

        String userId = SpringUtil.getSessionUserId(request);
        SUserInfo sUserInfo = (SUserInfo) redisService.get(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + userId);


        String sql = SqlProvider.getSubmitDetail(RApplyBean.getDjbh());
        ApplyListBean detailApply = commonService.selectObjectBySql(sql, ApplyListBean.class);
        if(detailApply == null){
            return new TResult<>(C.ERROR_CODE_COM,"单据号为空");
        }

        List<BusitemsFileBean> busitems = RApplyBean.getBusitems();
        if(busitems.size() > 0){
            for(BusitemsFileBean list : busitems){
                list.setCreator(sUserInfo.getUsercode());
                list.setPk_group(sUserInfo.getPk_group());
                list.setWjname(commonService.getPKID() + list.getWjname());
                list.setFile(list.getFile());
                list.setIpiname(list.getIpiname());
                list.setDjbh(RApplyBean.getDjbh());
                list.setParentpath(detailApply.getPk_jkbx());
            }
        }
        UploadFileBean beanList = new UploadFileBean();
        beanList.setCreator(sUserInfo.getUsercode());
        beanList.setPk_group(sUserInfo.getPk_group());
        beanList.setBusitems(busitems);

        log.info("打印图片地址:{}", new Gson().toJson(beanList));
        try {
            String s = "";
            if ("loc".equals(string)) {
                s = HttpUtils.requestPostBody(C.INSERT_UPLOAD_FILE_URL_DEV, JSONObject.toJSONString(beanList), null);
            } else if ("dev".equals(string)){
                s = HttpUtils.requestPostBody(C.INSERT_UPLOAD_FILE_URL_DEV, JSONObject.toJSONString(beanList), null);
            }else if("gray".equals(string)){
                s = HttpUtils.requestPostBody(C.INSERT_UPLOAD_FILE_URL_PRD, JSONObject.toJSONString(beanList), null);
            }else{
                s = HttpUtils.requestPostBody(C.INSERT_UPLOAD_FILE_URL_PRD, JSONObject.toJSONString(beanList), null);
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



    @GetMapping(value = "/downFile")
    @ValidateApiNotAuth
    @ApiOperation(value = "下载文件", notes = "下载文件", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    public void downLoad(
            @ApiParam(required = true, value = "ticket") @RequestParam(required = true) String ticket,
            HttpServletResponse response) throws UnsupportedEncodingException {
        String filePath = "";
        if ("loc".equals(string)) {
            filePath = C.DOWNLOAD_FILE_PATH_LOC;
        } else if ("dev".equals(string)){
            filePath = C.DOWNLOAD_FILE_PATH_DEV ; //线上路径
        }else if("gray".equals(string)){
            filePath = C.DOWNLOAD_FILE_PATH_GRAY;
        }else{
            filePath = C.DOWNLOAD_FILE_PATH_PRD;
        }


        if (StringUtil.isEmpty(ticket)) {
            return;
        }

        Claims claims = null;
        try {
            claims = JWTUtil.parseJWT(ticket);
        } catch (Exception e) {
            e.printStackTrace();
        }
       // || !claims.getExpiration().after(new Date())
        if (claims == null) {
            return;
        }
        //解析出账号
        String subject = claims.getSubject();
        JSONObject jsonObject = JSONObject.parseObject(subject);
        String fileJson = (String) jsonObject.get("fileJson");


        File file = new File(filePath + "/" + fileJson);
        if(file.exists()){ //判断文件父目录是否存在
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            // response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;fileName=" +   java.net.URLEncoder.encode(fileJson,"UTF-8"));
            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;

            OutputStream os = null; //输出流
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while(i != -1){
                    os.write(buffer);
                    i = bis.read(buffer);
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("----------file download---" + fileJson);
            try {
                bis.close();
                fis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return;
    }


    @PostMapping("/deleteUploadFile")
    @ApiOperation(value = "删除")
    public TResult<UploadFileBean> deleteUploadFile(HttpServletRequest request, @RequestBody UploadFileBean RApplyBean) {
        log.info("保存图片:{}", new Gson().toJson(RApplyBean));

        String userId = SpringUtil.getSessionUserId(request);
        SUserInfo sUserInfo = (SUserInfo) redisService.get(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + userId);


        List<BusitemsFileBean> busitems = RApplyBean.getBusitems();
        if(busitems.size() > 0){
            for(BusitemsFileBean list : busitems){
                list.setCreator(sUserInfo.getUsercode());
                list.setPk_group(sUserInfo.getPk_group());
                list.setWjname(System.currentTimeMillis() + list.getWjname());
                list.setFile(list.getFile());
                list.setIpiname(list.getIpiname());
                list.setDjbh(RApplyBean.getDjbh());
                list.setParentpath(list.getParentpath());
            }
        }
        UploadFileBean beanList = new UploadFileBean();
        beanList.setCreator(sUserInfo.getUsercode());
        beanList.setPk_group(sUserInfo.getPk_group());
        beanList.setBusitems(busitems);


        log.info("打印图片地址:{}", new Gson().toJson(beanList));
        try {
            String s = "";
            if ("loc".equals(string)) {
                s = HttpUtils.requestPostBody(C.DELETE_UPLOAD_FILE_URL_DEV, JSONObject.toJSONString(beanList), null);
            } else if ("dev".equals(string)){
                s = HttpUtils.requestPostBody(C.DELETE_UPLOAD_FILE_URL_DEV, JSONObject.toJSONString(beanList), null);
            }else if("gray".equals(string)){
                s = HttpUtils.requestPostBody(C.DELETE_UPLOAD_FILE_URL_PRD, JSONObject.toJSONString(beanList), null);
            }else{
                s = HttpUtils.requestPostBody(C.DELETE_UPLOAD_FILE_URL_PRD, JSONObject.toJSONString(beanList), null);
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
//        String str = "http://192.168.50.194:8902/api/v1/common/downFile?ticket\\u003deyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJmaWxlSnNvblwiOlwicjV4ajJvNGF6d18xNjE2NTgxNDk5NTIyLmpwZ1wifSIsInVzZXJfaWQiOiIxMDAxQTIxMDAwMDAwMDBBMzFJSSIsImV4cCI6MTYyNjU4MTQ5OSwiaWF0IjoxNjE2NTgxNDk5LCJqdGkiOiIxNjE2NTgxNDk5NTIzIn0.WmqSCyioTRXy_N203afxMCLqdah2xoNB2f0AwR8TYus";
//        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
//        Matcher matcher = pattern.matcher(str);
//        char ch;
//        while (matcher.find()) {
//            ch = (char) Integer.parseInt(matcher.group(2), 16);
//            str = str.replace(matcher.group(1), ch + "");
//        }
//        System.out.println(str);
//    }


    @PostMapping("/uploadList")
    @ApiOperation(value = "文件上传多张", notes = "文件上传多张", httpMethod = "POST")
    @ValidateApiNotAuth
    public TResult<CommonFile> uploadList(
            @ApiParam(required = true, value = "file") @RequestParam(required = true) List<MultipartFile> fileList,
            String session,
            HttpServletRequest request
    ) throws IOException {
        SUserInfo sUserInfo = new SUserInfo();
        if(StringUtil.isEmpty(session)){
            String userId = SpringUtil.getSessionUserId(request);
            sUserInfo = (SUserInfo) redisService.get(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + userId);
        }else{
            sUserInfo.setUserId(commonService.getPKID());
        }
        List<CommonFile> list = new ArrayList<>();
        for(MultipartFile file : fileList){
            log.info("文件上传 file --->" + file);
            BufferedOutputStream bufferedOutputStream = null;
            FileOutputStream fileOutputStream = null;
            java.io.File savePath = null;

            //类型默认为图片
            String uploadFilePath = file.getOriginalFilename().replaceAll("\\\\", "/");
            String uploadFileName = uploadFilePath.substring(
                    uploadFilePath.lastIndexOf('/') + 1, uploadFilePath.lastIndexOf('.'));
            String uploadFileSuffix = uploadFilePath.substring(uploadFilePath.lastIndexOf('.') + 1, uploadFilePath.length());
            long currentTime = System.currentTimeMillis();
            String path = "";
            try {
                path = StringUtil.getRandomString(10) + "_" + currentTime + "." + uploadFileSuffix;
                savePath = new java.io.File(C.MEDIA_UPLOAD_LOCATION_ROOT + C.MEDIA_UPLOAD_LOCATION_PIC + path);

                fileOutputStream = new FileOutputStream(savePath);
                bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                bufferedOutputStream.write(file.getBytes());
                bufferedOutputStream.flush();
                bufferedOutputStream.close();


                CommonFile commonFile = new CommonFile();

                SLoginTicket loginTicket = new SLoginTicket();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("fileJson", path);
                loginTicket.setTicket(JWTUtil.createJWT(System.currentTimeMillis() + "", sUserInfo.getUserId() + "", jsonObject.toString(), 9999999999L));


                commonFile.setFid(path);
                commonFile.setUploadType(1);
                commonFile.setImgUrl(serverAddr +"api/v1/common/downFile?ticket=" + loginTicket.getTicket());
                commonFile.setFileName(uploadFileName + "." + uploadFileSuffix);
                commonFile.setFileType(uploadFileSuffix);
                commonFile.setFileSize(Double.valueOf(savePath.length()));
                commonFile.setFileMd5(FileUtil.getMD5(savePath));
                commonFile.setCreateTime(new Date());

                list.add(commonFile);
            } finally {
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e) {
                        log.error(e.getMessage(), e);
                    }
                }
                if (bufferedOutputStream != null) {
                    try {
                        bufferedOutputStream.close();
                    } catch (IOException e) {
                        log.error(e.getMessage(), e);
                    }
                }
            }
        }
        return new TResult<>(list);
    }


}
