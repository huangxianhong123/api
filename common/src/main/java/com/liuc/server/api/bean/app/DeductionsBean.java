package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class DeductionsBean {

    @ApiModelProperty(name = "buckleProvidentFund", value = "扣公积金单位及个人部分")
    private String buckleProvidentFund; //f_146
    @ApiModelProperty(name = "bucklePension", value = "扣养老个人部门")
    private String bucklePension; //f_62
    @ApiModelProperty(name = "buckleMedicalInsurance", value = "扣医保个人部分")
    private String buckleMedicalInsurance; //f_63
    @ApiModelProperty(name = "buckleUnemployment", value = "扣失业个人部门")
    private String buckleUnemployment; //f_64
    @ApiModelProperty(name = "annuity", value = "年金（扣个人部分）")
    private String annuity; //f_66
    @ApiModelProperty(name = "buckleRent", value = "扣房租")
    private String buckleRent;//f_67
    @ApiModelProperty(name = "buckleUnionFees", value = "扣工会费")
    private String buckleUnionFees;//f_69
    @ApiModelProperty(name = "buckleIndividualIncomeTax", value = "扣个人所得税")
    private String buckleIndividualIncomeTax;//f_232
    @ApiModelProperty(name = "buckleOther", value = "其他扣税")
    private String buckleOther; //f_284
    @ApiModelProperty(name = "actualSalary", value = "员工月度实发工资")
    private String actualSalary; //f_78

}
