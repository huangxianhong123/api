package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class UserBean {

    @ApiModelProperty(value = "id")
    private String cuserid;
    @ApiModelProperty(value = "name")
    private String name;



}
