package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class ChargeBean {

    @ApiModelProperty(value="人员编码")
    private String cuserid;


    @ApiModelProperty(value="用户名称")
    private String user_name;

    @ApiModelProperty(value="单据日期")
    private String djrq;

    @ApiModelProperty(value="收支项目名称")
    private String szxm_code;

    @ApiModelProperty(value="收支项目名称")
    private String szxm_name;

    @ApiModelProperty(value="币种编码")
    private String currtype_code;

    @ApiModelProperty(value="币种编码")
    private String currtype_name;

    @ApiModelProperty(value="交易类型名称")
    private String jylx_name;

    @ApiModelProperty(value="本币金额")
    private String bbje;

    @ApiModelProperty(value="本币余额")
    private String bbye;

    @ApiModelProperty(value="还款本币金额")
    private String hkbbje;

    @ApiModelProperty(value="单据编号")
    private String djbh;

    @ApiModelProperty(value="金额（全部传个金额）")
    private String yjye;

    @ApiModelProperty(value="冲借款本币金额")
    private String cjkbbje;

    private String jkbxr_code;
    private String jkbxr_name;
}
