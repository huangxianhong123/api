package com.liuc.server.api.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SVerification implements Serializable {
    @ApiModelProperty(name = "token", value = "token")
    private String token;
    @ApiModelProperty(name = "pic", value = "64位编码")
    private String pic;//64位编码
}
