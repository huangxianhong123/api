package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class PostBean {

    @ApiModelProperty(name = "employeeStatus", value = "员工状态性质")
    private String employeeStatus;
    @ApiModelProperty(name = "jobGrade", value = "职级")
    private String jobGrade;
    @ApiModelProperty(name = "dataTime", value = "任现职级时间")
    private String dataTime;
    @ApiModelProperty(name = "positionName", value = "全部职务")
    private String positionName;
    @ApiModelProperty(name = "postName", value = "全部岗位")
    private String postName;
    @ApiModelProperty(name = "deptid", value = "部门id")
    private String deptid;
    @ApiModelProperty(name = "deptname", value = "部门名称")
    private String deptname;

}
