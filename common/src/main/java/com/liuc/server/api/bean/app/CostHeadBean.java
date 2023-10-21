package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class CostHeadBean {


    @ApiModelProperty(value = "创建人")
    private String creator;
    @ApiModelProperty(value = "报销单位")
    private String pk_org;
    @ApiModelProperty(value = "事由")
    private String incident;
    @ApiModelProperty(value = "支付单位")
    private String pk_payorg;
    @ApiModelProperty(value = "单位银行账户")
    private String fkyhzh;
    @ApiModelProperty(value = "结算方式")
    private String jsfs;
    @ApiModelProperty(value = "现金流量项目")
    private String cashitem;
    @ApiModelProperty(value = "资金计划项目")
    private String cashproj;
    @ApiModelProperty(value = "费用承担单位")
    private String fydwbm;
    @ApiModelProperty(value = "费用承担部门")
    private String fydeptid;
    @ApiModelProperty(value = "收支项目")
    private String szxmid;
    @ApiModelProperty(value = "供应商")
    private String hbbm;

    @ApiModelProperty(value = "客户")
    private String customer;
    @ApiModelProperty(value = "报销人")
    private String receiver;
    @ApiModelProperty(value = "币种")
    private String bzbm;
    @ApiModelProperty(value = "交易类型")
    private String djlxbm;
    @ApiModelProperty(value = "单据日期 格式：2021-01-17 10:00:00")
    private String djrq;
    @ApiModelProperty(value = "报销人单位")
    private String dwbm;
    @ApiModelProperty(value = "报销人部门")
    private String deptid;
    @ApiModelProperty(value = "个人银行账户")
    private String skyhzh;
    @ApiModelProperty(value = "出差人员")
    private String zyx27;
    @ApiModelProperty(value = "出差人数/票据张数")
    private String zyx29;
    @ApiModelProperty(value = "费用大类(费用大类主键)")
    private String zyx30;
    @ApiModelProperty(value = "报销单单据号")
    private String billcode;
    @ApiModelProperty(value = "单位银行类别")
    private String zyx1;
    @ApiModelProperty(value = "报销月份")
    private String zyx28;

    @ApiModelProperty(value = "本币金额")
    private String bbje;
    @ApiModelProperty(value = "附件张数")
    private String fjzs;

    @ApiModelProperty(value = "费用大类")
    private String zyx25;

    @ApiModelProperty(value = "是否分摊")
    private String iscostshare;


    private String work;
}
