package com.liuc.server.api;

import com.liuc.server.api.bean.TResult;
import com.liuc.server.api.common.C;
import com.liuc.server.api.util.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jclang on 2017/5/20.
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalDefaultExceptionHandler {


    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> defaultErrorHandler(HttpServletRequest req, Exception ex) {

        // 全局异常处理
        if (!(ex instanceof LCAuthException)) {
            log.error(req.getRequestURI(), ex);
        }else{
            log.error("头部验证不通过");
        }

        if(ex instanceof CommonException){
            //自定义错误就不抛出栈异常
            //log.error("网络异常:",ex);
            log.error("网络异常:"+ex.getMessage()+req.getRequestURI()+req.getParameterMap());
            return new ResponseEntity(new TResult<>(((CommonException) ex).getCode(),ex.getMessage()), HttpStatus.OK);
        }


        if (ex instanceof HttpMessageNotReadableException || ex instanceof MethodArgumentNotValidException || ex instanceof NullPointerException || ex instanceof MethodArgumentTypeMismatchException) {
            return new ResponseEntity(new TResult(C.ERROR_CODE_INVALID_PARAM, C.ERROR_MSG_INVALID_PARAM), HttpStatus.OK);
        } else if (ex instanceof LCAuthException) {
            if (((LCAuthException) ex).getExce_type() == LCAuthException.EXCE_TYPE_PASS) {
                return new ResponseEntity(new TResult<>(C.ERROR_CODE_APPPASS, C.ERROR_MSG_APPPASS), HttpStatus.OK);
            } else if (((LCAuthException) ex).getExce_type() == LCAuthException.EXCE_TYPE_SESSION) {
                return new ResponseEntity(new TResult<>(C.ERROR_CODE_INVALID_USER, C.ERROR_MSG_INVALID_USER), HttpStatus.OK);
            }
        }
        return new ResponseEntity(new TResult(500, ex.getMessage()), HttpStatus.OK);
    }

    public static class LCAuthException extends RuntimeException {

        public static final int EXCE_TYPE_PASS = 1;
        public static final int EXCE_TYPE_SESSION = 2;

        private int exce_type;

        public LCAuthException(int exce_type) {
            this.exce_type = exce_type;
        }

        public int getExce_type() {
            return exce_type;
        }

    }

}
