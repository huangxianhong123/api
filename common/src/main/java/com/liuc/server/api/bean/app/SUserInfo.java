package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class SUserInfo {
    @ApiModelProperty(name = "id", value = "id")
    private String id;
    @ApiModelProperty(name = "code", value = "人员编码")
    private String code;
    @ApiModelProperty(name = "name", value = "用户名")
    private String name;
    @ApiModelProperty(name = "enablestate", value = "状态：1=未启用，2=已启用，3=已停用")
    private String enablestate;
    @ApiModelProperty(name = "enablestate", value = "部门编码")
    private String deptcode;
    @ApiModelProperty(name = "enablestate", value = "部门名称")
    private String deptname;
    @ApiModelProperty(name = "orgid", value = "所属公司主键")
    private String orgid;
    @ApiModelProperty(name = "orgname", value = "所属公司名称")
    private String orgname;
    @ApiModelProperty(name = "orgcode", value = "所属公司编码")
    private String orgcode;
    @ApiModelProperty(name = "usercode", value = "用户编码")
    private String usercode;
    private String sessionId;
    private String pk_group;
    private String userId;
}
