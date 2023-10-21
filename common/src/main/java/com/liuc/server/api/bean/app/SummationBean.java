package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class SummationBean {


    @ApiModelProperty(name = "shouldAmount", value = "员工月度应发合计")
    private String shouldAmount;//f_1
    @ApiModelProperty(name = "realityAmount", value = "员工月度实发合计")
    private String realityAmount;//f_3


}
