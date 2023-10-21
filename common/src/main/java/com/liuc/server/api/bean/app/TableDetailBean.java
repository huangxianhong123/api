package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class TableDetailBean {

    private String userid;
    @ApiModelProperty(value = "单据编号")
    private String djbh;
    @ApiModelProperty(value = "页签编码（arap_bxbusitem：交通费用、jk_busitem：借款明细、other：住宿费用）")
    private String tableCode;
    @ApiModelProperty(value = "报销单（出发日期）、借款单（预计出差时间）")
    private String defitem1;
    @ApiModelProperty(value = "报销单（到达日期）、借款单（预计出差地点）")
    private String defitem2;
    @ApiModelProperty(value = "报销单（出发地点）、借款单（预计出差天数）")
    private String defitem3;
    @ApiModelProperty(value = "报销单（到达地点）、借款单（交通工具）")
    private String defitem4;
    @ApiModelProperty(value = "报销单（交通工具））")
    private String defitem5;
    @ApiModelProperty(value = "报销单（交通工具）名称）")
    private String defitem5_name;
    @ApiModelProperty(value = "报销单（实际消费金额）")
    private String defitem6;
    @ApiModelProperty(value = "含税金额")
    private String vat_amount;
    @ApiModelProperty(value = "报销类型")
    private String reimtype_name;
    private String reimtype_code;
    @ApiModelProperty(value = "不含税金额")
    private String tni_amount;
    @ApiModelProperty(value = "税金金额")
    private String tax_amount;
    @ApiModelProperty(value = "税率")
    private String tax_rate;
    @ApiModelProperty(value = "收支项目名称")
    private String szxm_name;
    @ApiModelProperty(value = "收支项目code")
    private String szxmid;
    @ApiModelProperty(value = "金额")
    private String amount;

    @ApiModelProperty(value = "住宿天数、就餐人数")
    private String defitem9;
    @ApiModelProperty(value = "误餐补贴标准")
    private String defitem45;
    @ApiModelProperty(value = "人数/司机名称")
    private String defitem46;
    @ApiModelProperty(value = "天数/车牌号/招待人数")
    private String defitem47;
    @ApiModelProperty(value = "费用发生时间/票据张数")
    private String defitem48;
    @ApiModelProperty(value = "备注")
    private String defitem49;
    @ApiModelProperty(value = "票据张数/招待类型/备注")
    private String defitem50;

    private String defitem50_name;



}
