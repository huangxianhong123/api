package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class PassBean {

    @ApiModelProperty(value="移动单据明细行主键")
    private String itemid;
    @ApiModelProperty(value="")
    private String bz;
    @ApiModelProperty(value="借款金额")
    private String jkje;
    @ApiModelProperty(value="摘要")
    private String zy;


}
