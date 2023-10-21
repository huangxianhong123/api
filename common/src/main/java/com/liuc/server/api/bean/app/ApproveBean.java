package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class ApproveBean {

    @ApiModelProperty(value = "审批状态（Y：同意   R:驳回 N:不同意  null:待审批）")
    private String APPROVERESULT;
    @ApiModelProperty(value = "处理时间")
    private String dealdate;
    @ApiModelProperty(value = "审批人")
    private String user_name;
    @ApiModelProperty(value = "单据号")
    private String billno;
    @ApiModelProperty(value = "发送人")
    private String senderman;
    @ApiModelProperty(value = "批语")
    private String checknote;
    @ApiModelProperty(value = "是否作废（X：作废，N：标准）")
    private String ischeck;
    private String senddate;


}
