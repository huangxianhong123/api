package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author S.Yang
 * @date 2021/2/23 16:34
 */
@Data
@ApiModel
public class BussinessUnitBean {

    @ApiModelProperty(value="id")
    private String id;


    @ApiModelProperty(value="code")
    private String code;

    @ApiModelProperty(value="name")
    private String name;

    @ApiModelProperty(value="fatherCode")
    private String fatherCode;

    @ApiModelProperty(value="fatherName")
    private String fatherName;

    @ApiModelProperty(value="下级单元")
    private List<BussinessUnitBean> childs = null;
}
