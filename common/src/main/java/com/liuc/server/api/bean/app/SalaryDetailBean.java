package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class SalaryDetailBean {

    @ApiModelProperty(name = "deductions", value = "扣款项目")
    private DeductionsBean deductions;
    @ApiModelProperty(name = "shouldSend", value = "应发项目")
    private ShouldSendBean shouldSend;
    @ApiModelProperty(name = "summation", value = "表头统计")
    private SummationBean summation;


}
