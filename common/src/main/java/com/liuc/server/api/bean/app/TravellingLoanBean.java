package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class TravellingLoanBean {

    @ApiModelProperty(value="移动业务单据主键")
    private String oaid;
    @ApiModelProperty(value="单据编号")
    private String djbh;
    @ApiModelProperty(value="交易类型")
    private String jylx;
    @ApiModelProperty(value="审批人")
    private String approver;
    @ApiModelProperty(value="合计金额")
    private Double hjje;
    @ApiModelProperty(value="创建时间")
    private String creationtime;
    @ApiModelProperty(value="创建人")
    private String creator;
    @ApiModelProperty(value="审批时间")
    private String approvertime;
    @ApiModelProperty(value="单据所属组织")
    private String fydw;
    @ApiModelProperty(value="单据所属部门")
    private String fybm;
    @ApiModelProperty(value="单据日期")
    private String djrq;
    @ApiModelProperty(value="借款单位")
    private String bxdw;
    @ApiModelProperty(value="借款部门")
    private String bxbm;
    @ApiModelProperty(value="借款人")
    private String bxr;
    @ApiModelProperty(value="归还日期")
    private String ghrq;
    @ApiModelProperty(value="备注")
    private String bz;
    @ApiModelProperty(value="客商银行账号")
    private String custaccount;
    @ApiModelProperty(value=" 供应商")
    private String gys;
    @ApiModelProperty(value=" 币种")
    private String currtype;
    @ApiModelProperty(value=" 费用大类")
    private String fydl;

}
