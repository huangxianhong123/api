package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class SalaryBean {

    @ApiModelProperty(name = "salary", value = "薪资档次")
    private String salary;



}
