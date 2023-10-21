package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author S.Yang
 * @date 2021/2/25 13:49
 */
@Data
@ApiModel
public class HxBillInfoBean {
    @ApiModelProperty(value="审批人")
    private String approver;
    @ApiModelProperty(value="创建人")
    private String creator;
    @ApiModelProperty(value="审批时间")
    private String approvertime;
    @ApiModelProperty(value="单据编号")
    private String djbh;
    @ApiModelProperty(value="付款银行账号")
    private String yhzh;
    @ApiModelProperty(value="报销事由")
    private String sy;
    @ApiModelProperty(value="报销单位")
    private String bxdw;
    @ApiModelProperty(value="报销部门")
    private String bxbm;
    @ApiModelProperty(value="交易类型")
    private String jylx;
    @ApiModelProperty(value="单据日期")
    private String djrq;
    @ApiModelProperty(value="本次冲销金额")
    private Double bccxjehj;
    @ApiModelProperty(value="创建时间")
    private String creationtime;
    @ApiModelProperty(value="报销人")
    private String jkbxr;
    @ApiModelProperty(value="移动业务单据主键")
    private String oaid;
    @ApiModelProperty(value="币种")
    private String bzbm;
    @ApiModelProperty(value="费用大类")
    private String fydl;
    @ApiModelProperty(value="交易类型")
    private String pk_tradetypeid;
    @ApiModelProperty(value="还款金额")
    private Double hkybje;
    @ApiModelProperty(value="录入人")
    private String operator;
    @ApiModelProperty(value="费用承担单位")
    private String fydwbm_v;
    @ApiModelProperty(value="费用承担部门")
    private String fydeptid_v;
    @ApiModelProperty(value="报销人单位")
    private String dwbm_v;
    @ApiModelProperty(value="报销人部门")
    private String deptid_v;
    @ApiModelProperty(value="单位银行类别")
    private String zyx1;
    @ApiModelProperty(value="单位银行账号")
    private String fkyhzh;


}
