package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class ShareBean {

    @ApiModelProperty(value = "报销单单据编号")
    private String djbh;
    @ApiModelProperty(value = "分摊明细单据编号")
    private String billno;
    @ApiModelProperty(value = "分摊明细单据编号")
    private String org_id;
    @ApiModelProperty(value = "组织名称")
    private String org_name;
    @ApiModelProperty(value = "组织编码")
    private String org_code;
    @ApiModelProperty(value = "部门名称")
    private String dept_name;
    @ApiModelProperty(value = "部门编码")
    private String dept_code;
    @ApiModelProperty(value = "收支项目名称")
    private String szxm_name;
    @ApiModelProperty(value = "收支项目编码")
    private String szxm_code;
    @ApiModelProperty(value = "承担比例")
    private String share_ratio;
    @ApiModelProperty(value = "承担金额")
    private String assume_amount;


}
