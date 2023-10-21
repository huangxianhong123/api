package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class PermitBean {

    @ApiModelProperty(name = "education", value = "最高学历")
    private String education;
    @ApiModelProperty(name = "degree", value = "最高学位")
    private String degree;
    @ApiModelProperty(name = "engage", value = "聘用职称")
    private String engage;
    @ApiModelProperty(name = "engineeringType", value = "工程类职称")
    private String engineeringType;
    @ApiModelProperty(name = "positionName", value = "经济类职称")
    private String economyType;
    @ApiModelProperty(name = "politicalWork", value = "政工类职称")
    private String politicalWork;
    @ApiModelProperty(name = "occupation", value = "职业资格")
    private String occupation;
    @ApiModelProperty(name = "constructor", value = "建造师执业情况")
    private String constructor;
}
