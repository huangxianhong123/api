package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class CheckBean {

    @ApiModelProperty(name = "oneYear", value = "近一年考核结果等级")
    private String oneYear;
    @ApiModelProperty(name = "twoYear", value = "近两年考核结果等级")
    private String twoYear;
    @ApiModelProperty(name = "threeYear", value = "近三年考核结果等级")
    private String threeYear;


}
