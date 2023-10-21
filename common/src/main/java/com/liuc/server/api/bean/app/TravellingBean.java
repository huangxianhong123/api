package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author huangxianhong
 * @date 2021/3/8 15:20
 */
@Data
@ApiModel
public class TravellingBean {

    private String pk_group;
    private String userid;
    private String sign;
    private long timestamp;
    @ApiModelProperty(value = "表头")
    private CostHeadBean head;
    @ApiModelProperty(value = "明细")
    private List<DetailsBean> details;
}
