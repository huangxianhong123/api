package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author S.Yang
 * @date 2021/2/24 16:15
 */
@Data
@ApiModel
public class BxBillInfoBean {
    @ApiModelProperty(value="oa业务单据主键")
    private String oaid;
    @ApiModelProperty(value="单据编号")
    private String djbh;
    @ApiModelProperty(value="交易类型")
    private String jylx;
    @ApiModelProperty(value="单据日期")
    private String djrq;
    @ApiModelProperty(value="币种")
    private String bzbm;
    @ApiModelProperty(value="含税金额")
    private String vat_amount;
    @ApiModelProperty(value="费用大类")
    private String zyx30;
    @ApiModelProperty(value="报销事由")
    private String zy;
    @ApiModelProperty(value="报销单位")
    private String dwbm_v;
    @ApiModelProperty(value="报销部门")
    private String deptid;
    @ApiModelProperty(value="报销人部门")
    private String deptid_v;
    @ApiModelProperty(value="报销人")
    private String jkbxr;
    @ApiModelProperty(value="个人银行账号")
    private String skyhzh;
    @ApiModelProperty(value="附件张数")
    private String fjzs;
    @ApiModelProperty(value="结算方式")
    private String jsfs;
    @ApiModelProperty(value="单位银行类别")
    private String zyx1;
    @ApiModelProperty(value="单位银行账号")
    private String fkyhzh;
    @ApiModelProperty(value="创建时间")
    private String creationtime;
    @ApiModelProperty(value="创建人")
    private String creator;
    @ApiModelProperty(value="分摊")
    private String iscostshare;
    @ApiModelProperty(value="合计金额")
    private Double total;
    @ApiModelProperty(value="出差人员")
    private String zyx27;
    @ApiModelProperty(value="票据张数")
    private String zyx29;
    @ApiModelProperty(value="收支项目")
    private String szxmid;
    @ApiModelProperty(value="报销单位")
    private String pk_org;
    @ApiModelProperty(value="原费用承担单位")
    private String fydwbm;
    @ApiModelProperty(value="费用承担单位")
    private String fydwbm_v;
    @ApiModelProperty(value="费用承担部门")
    private String fydeptid_v;
    @ApiModelProperty(value="原费用承担部门")
    private String fydeptid;



    @ApiModelProperty(value="本次报销金额")
    private Double bcbxjehj;
    @ApiModelProperty(value="本次冲销金额")
    private Double bccxjehj;
    @ApiModelProperty(value="本次实报金额")
    private Double bcsbjehj;
    @ApiModelProperty(value="不含税金额")
    private String tni_amount_total;

    @ApiModelProperty(value="审批时间")
    private String approvertime;
    @ApiModelProperty(value="审批人")
    private String approver;






}
