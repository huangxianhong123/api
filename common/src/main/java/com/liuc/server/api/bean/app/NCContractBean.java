package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class NCContractBean {

    @ApiModelProperty(name = "psndoccode", value = "人员编码")
    private String psndoccode;
    @ApiModelProperty(name = "psndocame", value = "姓名")
    private String psndocame;
    @ApiModelProperty(name = "conttype", value = "合同类型")
    private String conttype;
    @ApiModelProperty(name = "glbdef2", value = "合同类别")
    private String glbdef2;
    @ApiModelProperty(name = "signdate", value = "业务发生日期")
    private String signdate;
    @ApiModelProperty(name = "termtype", value = "合同期限类型")
    private String termtype;
    @ApiModelProperty(name = "glbdef6", value = "合同期限（年）")
    private String glbdef6;
    @ApiModelProperty(name = "termmonth", value = "合同期限（月）")
    private String termmonth;
    @ApiModelProperty(name = "begindate", value = "合同开始日期")
    private String begindate;
    @ApiModelProperty(name = "enddate", value = "合同结束日期")
    private String enddate;
    @ApiModelProperty(name = "glbdef5", value = "个人身份")
    private String glbdef5;
    @ApiModelProperty(name = "pk_majorcorp", value = "合同主体单位")
    private String pk_majorcorp;

}
