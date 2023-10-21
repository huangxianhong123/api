package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author S.Yang
 * @date 2021/2/24 17:34
 */
@Data
@ApiModel
public class BxErBusitemBean {
    //通用字段
    @ApiModelProperty(value="OA单据明细行主键")
    private String itemid;
    @ApiModelProperty(value="费用明细项目")
    private String pk_reimtype;
    @ApiModelProperty(value="收支项目")
    private String szxmid;
    @ApiModelProperty(value="不含税金额")
    private Double tni_amount;
    @ApiModelProperty(value="税金金额")
    private Double tax_amount;
    @ApiModelProperty(value="含税金额")
    private Double vat_amount;
    @ApiModelProperty(value="税率")
    private String tax_rate;
    @ApiModelProperty(value="是否超标")
    private String sfcb;

    @ApiModelProperty(value="出发日期")
    private String defitem1;
    @ApiModelProperty(value="到达日期")
    private String defitem2;
    @ApiModelProperty(value="出发地点")
    private String defitem3;
    @ApiModelProperty(value="到达地点")
    private String defitem4;
    @ApiModelProperty(value="交通工具")
    private String defitem5;
    @ApiModelProperty(value="住宿天数")
    private String defitem9;

    @ApiModelProperty(value="误餐补贴标准")
    private String defitem45;
    @ApiModelProperty(value="人数")
    private String defitem46;
    @ApiModelProperty(value="天数")
    private String defitem47;
    @ApiModelProperty(value="费用发生时间")
    private String defitem48;
    @ApiModelProperty(value="备注")
    private String defitem49;
    @ApiModelProperty(value="票据张数")
    private String defitem50;



    @ApiModelProperty(value="报销金额")
    private String bxje;
    @ApiModelProperty(value="冲借款金额")
    private String cjkje;
    @ApiModelProperty(value="摘要")
    private String zy;
}
