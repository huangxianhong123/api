package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author S.Yang
 * @date 2021/2/24 16:31
 */
@Data
@ApiModel
public class ErBxcontrastBean {
    @ApiModelProperty(value="借款人")
    private String jkbxr;
    @ApiModelProperty(value="收支项目")
    private String szxmid;
    @ApiModelProperty(value="借款编号")
    private String jkdjbh;
    @ApiModelProperty(value="冲销原币金额")
    private String cjkybje;
    @ApiModelProperty(value="冲销日期")
    private String cxrq;
    @ApiModelProperty(value="借款单oa业务单据主键")
    private String pk_jkd;
    @ApiModelProperty(value="借款单oa单据明细行主键")
    private String pk_itemId;

}
