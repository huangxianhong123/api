package com.liuc.server.api.aop;

import com.github.pagehelper.util.StringUtil;
import com.liuc.server.api.GlobalDefaultExceptionHandler;
import com.liuc.server.api.annotation.ValidateApiNotAuth;
import com.liuc.server.api.bean.app.SUserInfo;
import com.liuc.server.api.common.C;
import com.liuc.server.api.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * created by lijh
 * 19-12-09
 */
@Aspect
@Component
@Slf4j
public class AuthAspect {

    @Autowired
    private RedisService redisService;

    @Pointcut("@annotation(io.swagger.annotations.ApiOperation)")
    public void auth() {
    }

    @Before("auth()")
    public void doBefore(JoinPoint joinPoint) throws GlobalDefaultExceptionHandler.LCAuthException {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();

        String appKey = request.getHeader(C.API_HEADER_PARAM_APPKEY);
        String session = request.getHeader(C.API_HEADER_PARAM_SESSION);
        Method sourceMethod = getSourceMethod(joinPoint);
        if (sourceMethod != null) {
            ValidateApiNotAuth validateApiNotAuth = sourceMethod.getAnnotation(ValidateApiNotAuth.class);

            //标识了该注解则不需要检查
            if (validateApiNotAuth != null) {
                return;
            }

            if (!C.SUPER_KEY.equals(appKey)) {
                if (StringUtil.isEmpty(session) || session.length() <= 32) {
                    throw new GlobalDefaultExceptionHandler.LCAuthException(GlobalDefaultExceptionHandler.LCAuthException.EXCE_TYPE_SESSION);
                }
                String userId = session.substring(32);
                if (C.APP_APPKEY_WEB_BACK.equals(appKey)) {
                    //SUserInfo adminUser =  C.sessionInfo.get("userId_"+userId);
                    SUserInfo adminUser = (SUserInfo) redisService.get(C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + userId);
                    if (adminUser == null) {
                        log.error("can not find session for sessionKey:" + C.RedisPrefix.SESSION_WEB_YONGYOU_SHUIDIAN + userId);
                        throw new GlobalDefaultExceptionHandler.LCAuthException(GlobalDefaultExceptionHandler.LCAuthException.EXCE_TYPE_SESSION);
                    }
                    String webSession = adminUser.getSessionId();
                    if (StringUtil.isEmpty(webSession) || !session.equals(webSession)) {
                        log.error("request's session not match db's session:" + webSession + ",request's session:" + session);
                        throw new GlobalDefaultExceptionHandler.LCAuthException(GlobalDefaultExceptionHandler.LCAuthException.EXCE_TYPE_SESSION);
                    }
                }
            }

        }
    }

    private Method getSourceMethod(JoinPoint jp) {
        Method proxyMethod = ((MethodSignature) jp.getSignature()).getMethod();
        try {
            return jp.getTarget().getClass().getMethod(proxyMethod.getName(), proxyMethod.getParameterTypes());
        } catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
        return null;
    }

}
