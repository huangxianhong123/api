package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class ServeBean {
    @ApiModelProperty(value = "页签：arap_bxbusitem")
    private String tableCode;
    @ApiModelProperty(value = "陪同人数")
    private String defitem46;
    @ApiModelProperty(value = "招待人数")
    private String defitem47;
    @ApiModelProperty(value = "费用发生时间")
    private String defitem48;
    @ApiModelProperty(value = "备注")
    private String defitem49;
    @ApiModelProperty(value = "招待类型")
    private String defitem50;

    @ApiModelProperty(value = "报销金额")
    private String vat_amount;
    @ApiModelProperty(value = "费用明细项目")
    private String pk_reimtype;
    @ApiModelProperty(value = "不含税金额")
    private String tni_amount;
    @ApiModelProperty(value = "税金金额")
    private String tax_amount;
    @ApiModelProperty(value = "税率")
    private String tax_rate;
    @ApiModelProperty(value = "收支项目")
    private String szxm;

}
