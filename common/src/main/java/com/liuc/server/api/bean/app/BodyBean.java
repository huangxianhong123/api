package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class BodyBean {
    @ApiModelProperty(value = "NC单据主键")
    private String ncitemid;
    @ApiModelProperty(value = "移动单据主键")
    private String oaitemid;



}
