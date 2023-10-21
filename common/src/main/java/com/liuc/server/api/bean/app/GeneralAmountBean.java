package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class GeneralAmountBean {

    @ApiModelProperty(value = "arap_bxbusitem")
    private String tableCode;
    @ApiModelProperty(value = "金额")
    private String amount;
}
