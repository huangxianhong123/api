package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author S.Yang
 * @date 2021/3/1 14:38
 */
@Data
@ApiModel
public class DeptInfoBean {

    @ApiModelProperty(value="orgid")
    private String orgid;
    @ApiModelProperty(value="id")
    private String id;
    @ApiModelProperty(value="编码")
    private String code;
    @ApiModelProperty(value="名称")
    private String name;
    @ApiModelProperty(value="父级编码")
    private String fathercode;
    @ApiModelProperty(value="父级名称")
    private String fathername;
    @ApiModelProperty(value="子部门")
    private List<DeptInfoBean> childs;
    private String pk_fatherorg;
}
