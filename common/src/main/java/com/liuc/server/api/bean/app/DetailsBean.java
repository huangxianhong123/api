package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class DetailsBean {
    @ApiModelProperty(value = "arap_bxbusitem")
    private String tableCode;
    @ApiModelProperty(value = "收支项目")
    private String szxmcode;
    @ApiModelProperty(value = "预计出差时间")
    private String defitem1;
    @ApiModelProperty(value = "预计出差地点")
    private String defitem2;
    @ApiModelProperty(value = "预计出差天数")
    private String defitem3;
    @ApiModelProperty(value = "交通工具")
    private String defitem4;
    @ApiModelProperty(value = "金额")
    private String amount;


}
