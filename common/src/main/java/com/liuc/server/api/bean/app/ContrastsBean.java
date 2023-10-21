package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class ContrastsBean {
    @ApiModelProperty(value = "借款单号")
    private String jkdjbh;
    @ApiModelProperty(value = "收支项目")
    private String szxmid;
    @ApiModelProperty(value = "借款人")
    private String jkbxr;
    @ApiModelProperty(value = "冲销本币金额")
    private String cjkbbje;
    @ApiModelProperty(value = "冲销原币金额")
    private String cjkybje;
    @ApiModelProperty(value = "冲销日期")
    private String cxrq;
    @ApiModelProperty(value = "生效日期")
    private String sxrq;

    private String yjye;

}
