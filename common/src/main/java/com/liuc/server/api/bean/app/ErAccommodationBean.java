package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author S.Yang
 * @date 2021/2/25 9:56
 */
@Data
@ApiModel
public class ErAccommodationBean {
    @ApiModelProperty(value="移动单据明细行主键")
    private String itemId;
    @ApiModelProperty(value="报销类型")
    private String pk_reimtype;
    @ApiModelProperty(value="报销金额")
    private String bxje;
    @ApiModelProperty(value="冲借款金额")
    private String cjkje;
    @ApiModelProperty(value="不含税金额")
    private String tni_amount;
    @ApiModelProperty(value="含税金额")
    private String tax_amount;
    @ApiModelProperty(value="摘要")
    private String zy;
}
