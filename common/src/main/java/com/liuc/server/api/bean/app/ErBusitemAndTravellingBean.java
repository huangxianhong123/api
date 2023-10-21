package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class ErBusitemAndTravellingBean {

    @ApiModelProperty(value="差旅费借款单")
    private TravellingLoanBean er_jkzb;

    @ApiModelProperty(value="借款明细")
    private List<ErBusitemBean> er_busitem;


}
