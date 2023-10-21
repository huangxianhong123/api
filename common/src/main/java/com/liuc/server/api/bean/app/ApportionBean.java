package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class ApportionBean {

    @ApiModelProperty(value = "承担单位")
    private String assume_org;
    @ApiModelProperty(value = "收支项目")
    private String szxmcode;
    @ApiModelProperty(value = "承担部门")
    private String assume_dept;
    @ApiModelProperty(value = "承担金额")
    private String assume_amount;
    @ApiModelProperty(value = "分摊比例")
    private String share_ratio;
}
