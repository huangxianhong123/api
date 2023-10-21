package com.liuc.server.api.bean;

import com.github.pagehelper.PageInfo;
import com.liuc.server.api.common.C;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * Created by jclang on 2017/6/2.
 */
@ApiModel
@Data
public class TResult<T extends Object> implements Serializable {

    @ApiModelProperty(name = "code", value = "业务错误码 ", required = true)
    private int code;
    @ApiModelProperty(name = "msg", value = "业务提示信息", required = true)
    private String msg;
    @ApiModelProperty(name = "data", value = "业务返回", required = true)
    private T data;

    public TResult() {
        this.code = 0;
        this.msg = code == 0 ? "SUCCESS" : "ERROR";
    }

    public TResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = null;
    }

    public TResult(Object data) {
        this.code = 0;
        this.msg = code == 0 ? "SUCCESS" : "ERROR";
        if (data instanceof PageInfo) {
            PageInfo<T> page = (PageInfo<T>) data;
            this.data = (T) page;
        } else {
            this.data = (T) data;
        }
    }

    public TResult(int code, Object data) {
        this.code = code;
        this.msg = code == 0 ? "SUCCESS" : "ERROR";

        if (data instanceof PageInfo) {
            PageInfo<T> page = (PageInfo<T>) data;
            this.data = (T) page;
        } else {
            this.data = (T) data;
        }
    }

    public TResult(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;

        if (data instanceof PageInfo) {
            PageInfo<T> page = (PageInfo<T>) data;
            this.data = (T) page;
        } else {
            this.data = (T) data;
        }
    }

    public TResult error(String msg) {
        this.code = C.ERROR_CODE_COM;
        this.msg = msg;
        this.data = null;
        return this;
    }

}
