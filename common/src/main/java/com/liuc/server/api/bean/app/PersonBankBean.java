package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class PersonBankBean {
    @ApiModelProperty(value = "对应 人员 视图id 字段")
    private String pk_psndoc;
    @ApiModelProperty(value = "人员编码")
    private String code;
    @ApiModelProperty(value = "人员名称 ")
    private String name;
    @ApiModelProperty(value = "对应 银行账户 视图id字段")
    private String pk_bankaccsub;
    @ApiModelProperty(value = "银行账号")
    private String accnum;
    @ApiModelProperty(value = "银行账号名称")
    private String accname;
    private String bankdocname;



}
