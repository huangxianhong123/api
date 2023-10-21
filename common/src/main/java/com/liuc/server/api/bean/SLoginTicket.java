package com.liuc.server.api.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SLoginTicket implements Serializable {

    @ApiModelProperty(name = "ticket", value = "获取用户信息ticket", required = true)
    private String ticket;
}
