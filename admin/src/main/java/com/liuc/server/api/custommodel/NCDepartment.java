package com.liuc.server.api.custommodel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class NCDepartment {
    
    @ApiModelProperty(value="id")
    private String id;
    
    @ApiModelProperty(value="name名称")
    private String name;

    @ApiModelProperty(value="pkOrg所属组织id")
    private String pkOrg;
    
    @ApiModelProperty(value="pid上级部门主键")
    private String pid;
    
    @ApiModelProperty(value="code编码")
    private String code;
    
    @ApiModelProperty(value="enablestate状态 1:未启用;2:已启用;3:已停用")
    private String enablestate;

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

    public String getPkOrg() {
        return pkOrg;
    }

    public void setPkOrg(String pkOrg) {
        this.pkOrg = pkOrg == null ? null : pkOrg.trim();
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

    public String getEnablestate() {
        return enablestate;
    }

    public void setEnablestate(String enablestate) {
        this.enablestate = enablestate == null ? null : enablestate.trim();
    }
}