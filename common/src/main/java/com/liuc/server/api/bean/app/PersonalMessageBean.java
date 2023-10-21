package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class PersonalMessageBean {

    @ApiModelProperty(name = "picture", value = "形象照")
    private String picture;
    @ApiModelProperty(name = "name", value = "姓名")
    private String name;
    @ApiModelProperty(name = "gender", value = "性别")
    private String gender;
    @ApiModelProperty(name = "postName", value = "岗位")
    private String postName;
    @ApiModelProperty(name = "organizationName", value = "所属组织")
    private String organizationName;

    @ApiModelProperty(name = "staffCode", value = "人员编码")
    private String staffCode;

    private String joinGroupTime;

}
