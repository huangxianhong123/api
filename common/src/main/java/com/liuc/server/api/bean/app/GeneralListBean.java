package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class GeneralListBean {

    private String pk_group;
    private String userid;
    private String sign;
    private long timestamp;
    @ApiModelProperty(value = "表头")
    private GeneralBean head;
    @ApiModelProperty(value = "明细")
    private List<GeneralAmountBean> details;
}
