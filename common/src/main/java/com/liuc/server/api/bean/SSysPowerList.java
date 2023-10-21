package com.liuc.server.api.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel
public class SSysPowerList {

    @ApiModelProperty(name = "powerId", value = "权限id")
    private String powerId;
    @ApiModelProperty(name = "powerName", value = "权限名字")
    private String powerName;
    @ApiModelProperty(name = "parentId", value = "父id")
    private String parentId;
    @ApiModelProperty(name = "sub_power", value = "")
    private List<SSysPowerList> sub_power = new ArrayList<>();
    @ApiModelProperty(name = "show_menu", value = "是否有权限 false:否 true：是")
    private boolean showMenu;

}
