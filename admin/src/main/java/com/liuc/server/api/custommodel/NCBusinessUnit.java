package com.liuc.server.api.custommodel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class NCBusinessUnit {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "name名称")
    private String name;

    @ApiModelProperty(value = "pid上级组织主键")
    private String pid;

    @ApiModelProperty(value = "code编码")
    private String code;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }
}