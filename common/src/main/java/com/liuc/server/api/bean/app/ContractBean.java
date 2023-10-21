package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class ContractBean implements Comparable<ContractBean>{
    @ApiModelProperty(name = "userId", value = "userId")
    private String userId;
    @ApiModelProperty(name = "psndoccode", value = "人员编码")
    private String psndoccode;
    @ApiModelProperty(name = "name", value = "姓名")
    private String name;
    @ApiModelProperty(name = "contractType", value = "合同类型")
    private String contractType;
    @ApiModelProperty(name = "contractCategory", value = "合同类别")
    private String contractCategory;
    @ApiModelProperty(name = "businessStartTime", value = "业务发生日期")
    private String businessStartTime;
    @ApiModelProperty(name = "contractTermType", value = "合同期限类型")
    private String contractTermType;
    @ApiModelProperty(name = "contractTermYear", value = "合同期限（年）")
    private String contractTermYear;
    @ApiModelProperty(name = "contractTermMonth", value = "合同期限（月）")
    private String contractTermMonth;
    @ApiModelProperty(name = "contractStartTime", value = "合同开始日期")
    private String contractStartTime;
    @ApiModelProperty(name = "contractEndTime", value = "合同结束日期")
    private String contractEndTime;
    @ApiModelProperty(name = "personalIdentity", value = "个人身份")
    private String personalIdentity;
    @ApiModelProperty(name = "ContractEntity", value = "合同主体单位")
    private String ContractEntity;

    private int longTime;

    @Override
    public int compareTo(ContractBean o){
        return o.getLongTime() - this.longTime;
    }

}
