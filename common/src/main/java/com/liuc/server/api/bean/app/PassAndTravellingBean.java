package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class PassAndTravellingBean {


    @ApiModelProperty(value="通用借款单")
    private TravellingLoanBean er_jkzb;

    @ApiModelProperty(value="借款明细")
    private List<PassBean> er_busitem;


}
