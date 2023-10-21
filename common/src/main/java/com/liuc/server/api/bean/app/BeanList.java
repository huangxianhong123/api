package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class BeanList {

    private String billcode;
    private String checknote;
    private String activitydefid;
    private String activitydefname;
    private String px;
}
