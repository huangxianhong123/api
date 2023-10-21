package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class BankBean {
    @ApiModelProperty(value = "主键")
    private String id;
    @ApiModelProperty(value = "账号名称")
    private String accname;
    @ApiModelProperty(value = "账号")
    private String accnum;
    @ApiModelProperty(value = "账户分类 0=个人，1=客户，2=公司，3=供应商")
    private String accclass;
    @ApiModelProperty(value = "银行类别主键")
    private String banktypeid;
    @ApiModelProperty(value = "银行类别编码")
    private String banktypecode;
    @ApiModelProperty(value = "银行类别名称")
    private String bankname;
    @ApiModelProperty(value = "开户行主键")
    private String bankdocid;
    @ApiModelProperty(value = "开户行编码")
    private String bankdoccode;
    @ApiModelProperty(value = "开户行名称")
    private String bankdocname;
    @ApiModelProperty(value = "所属组织主键")
    private String pk_org;
    @ApiModelProperty(value = "状态 1=未启用，2=已启用，3=已停用")
    private String enablestate;


}
