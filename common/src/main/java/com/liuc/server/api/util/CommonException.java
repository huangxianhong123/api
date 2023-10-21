package com.liuc.server.api.util;

import com.liuc.server.api.common.C;

/**
 * Created by chijr on 16/11/5.
 */
public class CommonException extends Exception {

    private int code;
    private String message;

    public CommonException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public CommonException(String msg){
        this.code = C.ERROR_CODE_COM;
        this.message = msg;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }
}
