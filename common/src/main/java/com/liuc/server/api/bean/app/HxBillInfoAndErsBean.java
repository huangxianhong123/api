package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author S.Yang
 * @date 2021/2/25 14:32
 */
@Data
@ApiModel
public class HxBillInfoAndErsBean {
    @ApiModelProperty(value="还款单头")
    private HxBillInfoBean er_bxzb;

    @ApiModelProperty(value="冲销明细")
    private List<ErBxcontrastBean> er_bxcontrast;
}
