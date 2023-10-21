package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class ApplyListBean implements Comparable<ApplyListBean>{

    private String userid;
    @ApiModelProperty(value = "单据编号")
    private String djbh;
    @ApiModelProperty(value = "单据日期")
    private String djrq;
    @ApiModelProperty(value = "币种编码")
    private String currtype_code;
    @ApiModelProperty(value = "币种名称")
    private String currtype_name;
    @ApiModelProperty(value = "合计金额")
    private String total;
    @ApiModelProperty(value = "事由")
    private String zy;
    @ApiModelProperty(value = "借款、报销单位code")
    private String pk_prg_code;
    @ApiModelProperty(value = "借款、报销单位")
    private String pk_org;
    @ApiModelProperty(value = "费用承担单位code")
    private String fydwbm_code;
    @ApiModelProperty(value = "费用承担单位id")
    private String fydwbm_id;
    @ApiModelProperty(value = "费用承担单位")
    private String fydwbm;
    @ApiModelProperty(value = "借款人、报销人单位code")
    private String dwbm_code;
    @ApiModelProperty(value = "借款人、报销人单位")
    private String dwbm;
    @ApiModelProperty(value = "借款人、报销人部门")
    private String deptid;
    @ApiModelProperty(value = "借款人、报销人部门code")
    private String deptid_code;
    @ApiModelProperty(value = "费用承担部门")
    private String fydeptid;
    @ApiModelProperty(value = "费用承担部门code")
    private String fydeptid_code;


    @ApiModelProperty(value = "借款、报销人")
    private String jkbxr;
    private String jkbxr_code;
    @ApiModelProperty(value = "个人银行账户")
    private String skyhzh;
    @ApiModelProperty(value = "个人银行账户名称")
    private String skyhzh_name;
    @ApiModelProperty(value = "结算方式")
    private String jsfs;

    @ApiModelProperty(value = "结算方式名称")
    private String jsfs_name;

    @ApiModelProperty(value = "交易类型名称")
    private String jylx_name;
    private String jylx_code;
    @ApiModelProperty(value = "审批状态（0=审批未通过，1=审批通过，2=审批进行中，3=提交，-1=自由）")
    private String spzt;
    @ApiModelProperty(value = "支付组织")
    private String pk_payorg;
    @ApiModelProperty(value = "收支项目名称")
    private String szxm_name;
    private String szxmid;
    @ApiModelProperty(value = "还款本币金额")
    private String hkbbje;



    @ApiModelProperty(value = "创建人id")
    private String creator;

    @ApiModelProperty(value = "单位银行类别")
    private String zyx1;
    @ApiModelProperty(value = "出差人员")
    private String zyx27;
    @ApiModelProperty(value = "出差人数")
    private String zyx29;
    @ApiModelProperty(value = "报销单费用大类")
    private String zyx30;
    @ApiModelProperty(value = "报销单费用大名称")
    private String zyx30_name;
    @ApiModelProperty(value = "附件张数")
    private String fjzs;
    @ApiModelProperty(value = "借款单费用大类")
    private String zyx25;

    private String zyx28;

    private String creationtime;

    @ApiModelProperty(value = "是否分摊 是：Y  否：N")
    private String iscostshare;

    private int longTime;
    private String pk_jkbx;

    @ApiModelProperty(value = "url")
    private String fileUrl;
    @ApiModelProperty(value = "文件名称")
    private String fileName;
    private String ipiname;
    @ApiModelProperty(value = "附件文件")
    private List<FileUrlBean> fileUrlBeans;
    @ApiModelProperty(value = "审批状态（Y：同意   R:驳回 N:不同意  null:待审批）")
    private String APPROVERESULT;

    @Override
    public int compareTo(ApplyListBean o){
        return o.getLongTime() - this.longTime;
    }

}
