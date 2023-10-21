package com.liuc.server.api.config.interceptor;

import com.liuc.server.api.annotation.ValidateApiNotAuth;
import com.liuc.server.api.common.C;
import com.liuc.server.api.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Slf4j
public class WebInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, @NotNull HttpServletResponse httpServletResponse, @NotNull Object handler) throws Exception {
        String uri = httpServletRequest.getRequestURI();
        String appkey = httpServletRequest.getHeader(C.API_HEADER_PARAM_APPKEY);
        String timestamp = httpServletRequest.getHeader(C.API_HEADER_PARAM_TIMESTAMP);
        String sign = httpServletRequest.getHeader(C.API_HEADER_PARAM_SIGN);
        if ((C.DEBUG && C.SUPER_KEY.equals(appkey))) {
            return true;
        }


        //标识 不需检查 的注解
        if (handler instanceof HandlerMethod) {
            HandlerMethod h = (HandlerMethod) handler;
            ValidateApiNotAuth methodAnnotation = h.getMethodAnnotation(ValidateApiNotAuth.class);
            if (methodAnnotation != null)
                return true;
        }

        if (StringUtils.isEmpty(appkey)
                || StringUtils.isEmpty(timestamp)
                || StringUtils.isEmpty(sign)) {
            httpServletResponse.sendError(403, "forbidden request");
            return false;
        }
        if (Math.abs(System.currentTimeMillis() / 1000 - Long.parseLong(timestamp)) > 1800) {
            httpServletResponse.sendError(402, "invalid timestamp");
            return false;
        } else {
            boolean isSignValid = false;
            switch (appkey) {
                case C.APP_APPKEY_WEB_BACK:
                    if (checkSign(sign, C.APP_APPKEY_WEB_BACK, C.APP_SECRET_WEB_BACK, timestamp)) {
                        isSignValid = true;
                    }
                    break;
            }
            if (!isSignValid) {
                httpServletResponse.sendError(403, "invalid appkey or sign");
                return false;
            } else {
                return true;
            }
        }
    }

    private boolean checkSign(String sign, String appkey, String secret, String timestamp) {
        String content = appkey + timestamp + secret;
        String mySign = Objects.requireNonNull(MD5Util.MD5(content)).toLowerCase();
        return StringUtils.equals(sign, mySign);
    }

}
