package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class DriverBean {

    @ApiModelProperty(value = "费用明细页签：arap_bxbusitem")
    private String tableCode;
    @ApiModelProperty(value = "司机名称")
    private String defitem46;
    @ApiModelProperty(value = "车牌号")
    private String defitem47;
    @ApiModelProperty(value = "票据张数")
    private String defitem48;
    @ApiModelProperty(value = "备注")
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
