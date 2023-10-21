package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class FileUrlBean {

    private String idi;
    private String url;
    private String name;
    private String ipiname;

}
