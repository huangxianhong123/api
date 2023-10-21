package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class BusitemsFileBean {

    @ApiModelProperty(value = "创建人(不用传)")
    private String creator;
    @ApiModelProperty(value = "集团(不用传)")
    private String pk_group;
    @ApiModelProperty(value = "报销、借款、还款主键(不用传)")
    private String parentpath;
    @ApiModelProperty(value = "文件下载地址（必传）")
    private String file;
    @ApiModelProperty(value = "文件名称（必传）")
    private String wjname;
    private String ipiname;
    @ApiModelProperty(value = "单据号（必传）")
    private String djbh;
}
