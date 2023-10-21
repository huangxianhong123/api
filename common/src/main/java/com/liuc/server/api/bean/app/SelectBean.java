package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class SelectBean {

    @ApiModelProperty(value = "2631:差旅费借款单 263X-Cxx-TYJKD:通用借款单 2641 差旅费报销单 2645 招待费报销单 264X-Cxx-TYBXD 通用报销单 264X-Cxx-XCFBXD 行车费报销单 2647 还款单")
    private String djlxbm;
    @ApiModelProperty(value = "billcode")
    private String billcode;




}
