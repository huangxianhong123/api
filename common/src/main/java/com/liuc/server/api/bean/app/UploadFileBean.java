package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class UploadFileBean {

    @ApiModelProperty(value = "创建人(不用传)")
    private String creator;
    @ApiModelProperty(value = "集团(不用传)")
    private String pk_group;
    private String sign;
    private long timestamp;
    @ApiModelProperty(value = "单据号（必传）")
    private String djbh;
    private List<BusitemsFileBean> busitems;
}
