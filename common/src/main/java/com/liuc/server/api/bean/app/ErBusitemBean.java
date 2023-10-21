package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class ErBusitemBean {

    @ApiModelProperty(value="移动单据明细行主键")
    private String itemid;
    @ApiModelProperty(value="借款金额")
    private String jkje;
    @ApiModelProperty(value="预计出差时间")
    private String defitem1;
    @ApiModelProperty(value="预计出差地点")
    private String defitem2;
    @ApiModelProperty(value="预计出差天数")
    private String defitem3;
    @ApiModelProperty(value="交通工具")
    private String defitem4;
    @ApiModelProperty(value="摘要")
    private String zy;


}
