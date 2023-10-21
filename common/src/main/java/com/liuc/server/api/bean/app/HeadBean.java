package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class HeadBean {
    @ApiModelProperty(value = "NC单据主键")
    private String ncid;
    @ApiModelProperty(value = "移动单据主键")
    private String oaid;



}
