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
public class DepartmentBean {

    @ApiModelProperty(value="id")
    private String id;

    @ApiModelProperty(value="name")
    private String name;

    @ApiModelProperty(value="上级pid")
    private String code;

    @ApiModelProperty(value="上级编码")
    private String fathercode;

    @ApiModelProperty(value="上级名称")
    private String fathername;

    private String maindoccode;

    private String maindocname;

    @ApiModelProperty(value="下级数组")
    private List<DepartmentBean> ids = null;
}
