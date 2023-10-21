package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class RepaymentDetailBean {

    private String userid;
    @ApiModelProperty(value = "bxdjbh：报销单据编号")
    private String bxdjbh;
    @ApiModelProperty(value = "借款单据编号")
    private String jkdjbh;
    @ApiModelProperty(value = "收支项目名称")
    private String szxm_name;
    @ApiModelProperty(value = "借款人")
    private String jkbxr;
    @ApiModelProperty(value = "币种名称")
    private String currtype_name;
    @ApiModelProperty(value = "冲销借款原币金额")
    private String cjkybje;
    @ApiModelProperty(value = "冲销日期")
    private String cxrq;

}
