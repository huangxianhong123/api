package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author huangxianhong
 * @date 2020/12/30 14:34
 */
@Data
@ApiModel
public class OrganizationBean {

    @ApiModelProperty(value="id")
    private String id;

    @ApiModelProperty(value="name")
    private String name;

    @ApiModelProperty(value="上级pid")
    private String pid;

    private String code;

    private String pk_org;
    @ApiModelProperty(value="A 单元，B 部门")
    private String value;

    @ApiModelProperty(value="下级数组")
    private List<OrganizationBean> ids = null;
}
