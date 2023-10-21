package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class BusitemsBean {
    @ApiModelProperty(value = "交通费用页签：arap_bxbusitem 住宿费用页签：other 餐费：zsitem 午餐补贴：bzitem")
    private String tableCode;

    @ApiModelProperty(value = "出发日期、入住日期、就餐日期  格式：2021-01-17 10:00:00")
    private String defitem1;
    @ApiModelProperty(value = "到达日期、离店日期 格式：2021-01-17 10:00:00")
    private String defitem2;
    @ApiModelProperty(value = "出发地点、餐饮公司")
    private String defitem3;
    @ApiModelProperty(value = "到达地点")
    private String defitem4;
    @ApiModelProperty(value = "交通工具")
    private String defitem5;
    @ApiModelProperty(value = "实际消费金额")
    private String defitem6;

    @ApiModelProperty(value = "票据张数")
    private String defitem50;
    @ApiModelProperty(value = "住宿天数、就餐人数")
    private String defitem9;
    @ApiModelProperty(value = "误餐补贴标准")
    private String defitem45;
    @ApiModelProperty(value = "人数")
    private String defitem46;
    @ApiModelProperty(value = "天数")
    private String defitem47;
    @ApiModelProperty(value = "备注/是否高标准地区")
    private String defitem49;
    @ApiModelProperty(value = "费用发生时间")
    private String defitem48;

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
