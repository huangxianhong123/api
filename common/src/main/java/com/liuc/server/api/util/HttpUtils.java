package com.liuc.server.api.util;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by chijr on 17/3/10.
 */
public class HttpUtils {

    private final static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build();

    static public String requestGet(String url, Map<String, String> params, Map<String, String> heads) throws CommonException {
        Response response = null;

        Request.Builder reqBuild = new Request.Builder();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();

        if (params != null) {
            for (String key : params.keySet()) {

                urlBuilder.addQueryParameter(key, params.get(key));
            }
        }


        reqBuild.url(urlBuilder.build());


        if (heads != null) {
            for (String key : heads.keySet()) {

                reqBuild.addHeader(key, heads.get(key));
            }
        }


        Request req = reqBuild.build();

        try {
            response = okHttpClient.newCall(req).execute();
            //判断请求是否成功
            if (response.isSuccessful()) {

                String content = response.body().string();

                logger.info("GET Response" + content);

                return content;
                //打印服务端返回结果
            } else {
                String content = response.body().string();
                logger.error("Response" + content);
                throw new CommonException(response.code(), response.message());
            }


        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CommonException(505, e.getMessage());
        } finally {
            if (response != null)
                response.close();
        }


    }

    static public String requestPostBody(String url, String json, Map<String, String> heads) throws CommonException {

        Response response = null;

        logger.info("requestPostBody url:" + url);
        logger.info("body:" + json);


        MediaType JSONType = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSONType, json);

        Request.Builder builder = new Request.Builder();

        builder.url(url);

        if (heads != null) {
            for (String key : heads.keySet()) {

                builder.addHeader(key, heads.get(key));
            }
        }

        builder.post(body);
        Request req = builder.build();

        try {

            response = okHttpClient.newCall(req).execute();
            logger.info(response.toString());
            if (response.isSuccessful()) {
                String returnString = response.body().string();
                return returnString;
                //打印服务端返回结果
            } else {
                logger.error("failure: " + response.body().string());
                throw new CommonException(response.code(), response.message());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CommonException(505, e.getMessage());
        } finally {
            if (response != null)
                response.close();
        }


    }

    static public String requestPostForm(String url, Map<String, String> params, Map<String, String> heads) throws CommonException {

//        okHttpClient = new OkHttpClient.Builder()
//                .connectTimeout(60,TimeUnit.SECONDS)
//                .readTimeout(60,TimeUnit.SECONDS)
//                .build();

        Response response = null;
        //OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        logger.info("form url:" + url);
        logger.info("params:" + params.toString());


        if (params != null) {
            for (String key : params.keySet()) {
                bodyBuilder.add(key, params.get(key));
            }
        }

        RequestBody body = bodyBuilder.build();
        Request.Builder builder = new Request.Builder();

        if (heads != null) {
            for (String key : heads.keySet()) {
                builder.addHeader(key, heads.get(key));
            }
        }
        builder.url(url);
        builder.post(body);
        Request req = builder.build();

        try {
            response = okHttpClient.newCall(req).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                logger.error("failure: " + response.body().string());
                throw new CommonException(response.code(), response.message());
            }
            //打印服务端返回结果
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new CommonException(505, e.getMessage());
        } finally {
            if (response != null)
                response.close();
        }


    }

    static public String requestPostFormObject(String url, Map<String, Object> params, Map<String, String> heads) throws CommonException {


        MultipartBody.Builder requestBuiler = new MultipartBody.Builder();
        requestBuiler.setType(MultipartBody.FORM);

        logger.info("form url:" + url);
        logger.info("params:" + params.toString());

        try {
            if (params != null) {
                for (String key : params.keySet()) {

                    if (params.get(key) instanceof File) {
                        File file = (File) params.get(key);
                        if (file.exists()) {
                            RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpeg"), (File) params.get(key));
                            requestBuiler.addFormDataPart("file", file.getName(), fileBody);
                        } else {
                            logger.info("requestPostFormObject方法，FILE不存在");
                        }
                    } else {
                        if (StringUtils.isNotEmpty((String) params.get(key))) {
                            requestBuiler.addFormDataPart(key, (String) params.get(key));
                        } else {
                            requestBuiler.addFormDataPart(key, "");
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }

        RequestBody body = requestBuiler.build();
        Request.Builder builder = new Request.Builder();

        if (heads != null) {
            for (String key : heads.keySet()) {

                builder.addHeader(key, heads.get(key));
            }
        }
        builder.url(url);
        builder.post(body);

        if (heads != null) {
            for (String key : heads.keySet()) {
                builder.addHeader(key, heads.get(key));
            }
        }
        builder.url(url);
        builder.post(body);

        Request req = builder.build();

        try {
            Response response = OkHttpUtil.execute(req);
            if (response.isSuccessful()) {
                return response.body().string();

            } else {

                throw new CommonException(response.code(), response.message());


            }

            //打印服务端返回结果
        } catch (IOException e) {
            e.printStackTrace();

            throw new CommonException(505, e.getMessage());


        }


    }


    static public String requestPutBody(String url, String json, Map<String, String> heads) throws CommonException {

//        okHttpClient = new OkHttpClient.Builder()
//                .connectTimeout(60,TimeUnit.SECONDS)
//                .readTimeout(60,TimeUnit.SECONDS)
//                .build();

        Response response = null;
        //OkHttpClient okHttpClient = new OkHttpClient();
        logger.info("requestPostBody url:" + url);
        logger.info("body:" + json);

        MediaType JSONType = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSONType, json);
        Request.Builder builder = new Request.Builder();
        builder.url(url);

        if (heads != null) {
            for (String key : heads.keySet()) {

                builder.addHeader(key, heads.get(key));
            }
        }
        builder.put(body);

        Request req = builder.build();

        try {

            response = OkHttpUtil.execute(req);
            //判断请求是否成功
            if (response.isSuccessful()) {

                String content = response.body().string();

                logger.info("PUT Response" + content);

                return content;
                //打印服务端返回结果
            } else {

                String content = response.body().string();

                logger.error("Response" + content);

                throw new CommonException(response.code(), response.message());

            }

        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            if (response != null)
                response.close();
        }

        return "";
    }


    static public String requestPostBodyYB(String url, String json, Map<String, String> heads, Callback cb) throws CommonException {

//        okHttpClient = new OkHttpClient.Builder()
//                .connectTimeout(60,TimeUnit.SECONDS)
//                .readTimeout(60,TimeUnit.SECONDS)
//                .build();

        logger.info("requestPostBody url:" + url);
        logger.info("body:" + json);


        MediaType JSONType = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSONType, json);
        Request.Builder builder = new Request.Builder();
        builder.url(url);

        if (heads != null) {
            for (String key : heads.keySet()) {
                builder.addHeader(key, heads.get(key));
            }
        }
        builder.post(body);
        Request req = builder.build();

        try {
            OkHttpUtil.enqueue(req, cb);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } finally {
        }
        return "";
    }


    static public String requestPostFormYB(String url, Map<String, String> params, Map<String, String> heads, Callback cb) {

//        okHttpClient = new OkHttpClient.Builder()
//                .connectTimeout(60,TimeUnit.SECONDS)
//                .readTimeout(60,TimeUnit.SECONDS)
//                .build();


        FormBody.Builder bodyBuilder = new FormBody.Builder();
        logger.info("form url:" + url);
        logger.info("params:" + params.toString());


        if (params != null) {
            for (String key : params.keySet()) {
                bodyBuilder.add(key, params.get(key));
            }
        }

        RequestBody body = bodyBuilder.build();

        Request.Builder builder = new Request.Builder();

        if (heads != null) {
            for (String key : heads.keySet()) {

                builder.addHeader(key, heads.get(key));
            }
        }
        builder.url(url);
        builder.post(body);

        Request req = builder.build();

        try {
            OkHttpUtil.enqueue(req, cb);
            //打印服务端返回结果
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {

        }

        return "";

    }

    /**
     * 微信公众号上传临时素材
     *
     * @param fileType
     * @param filePath
     * @param token
     * @return
     * @throws Exception
     */

    public static JSONObject UploadMeida(String fileType, String filePath, String token) throws Exception {

//        okHttpClient = new OkHttpClient.Builder()
//                .connectTimeout(60,TimeUnit.SECONDS)
//                .readTimeout(60,TimeUnit.SECONDS)
//                .build();
        //返回结果
        String result = null;
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new IOException("文件不存在");
        }
        String urlString = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=" + token + "&type=" + fileType;
        URL url = new URL(urlString);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("POST");//以POST方式提交表单
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);//POST方式不能使用缓存
        //设置请求头信息
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Charset", "UTF-8");
        //设置边界
        String BOUNDARY = "----------" + System.currentTimeMillis();
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
        //请求正文信息
        //第一部分
        StringBuilder sb = new StringBuilder();
        sb.append("--");//必须多两条道
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;name=\"media\"; filename=\"" + file.getName() + "\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");
        System.out.println("sb:" + sb);

        //获得输出流
        OutputStream out = new DataOutputStream(conn.getOutputStream());
        //输出表头
        out.write(sb.toString().getBytes("UTF-8"));
        //文件正文部分
        //把文件以流的方式 推送道URL中
        DataInputStream din = new DataInputStream(new FileInputStream(file));
        int bytes = 0;
        byte[] buffer = new byte[1024];
        while ((bytes = din.read(buffer)) != -1) {
            out.write(buffer, 0, bytes);
        }
        din.close();
        //结尾部分
        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("UTF-8");//定义数据最后分割线
        out.write(foot);
        out.flush();
        out.close();
        if (HttpsURLConnection.HTTP_OK == conn.getResponseCode()) {

            StringBuffer strbuffer = null;
            BufferedReader reader = null;
            try {
                strbuffer = new StringBuffer();
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String lineString = null;
                while ((lineString = reader.readLine()) != null) {
                    strbuffer.append(lineString);

                }
                if (result == null) {
                    result = strbuffer.toString();
                    System.out.println("result:" + result);
                }
            } catch (IOException e) {
                System.out.println("发送POST请求出现异常！" + e);
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    reader.close();
                }
            }
        }
        JSONObject jsonObject = JSONObject.parseObject(result);
        return jsonObject;

    }


}
