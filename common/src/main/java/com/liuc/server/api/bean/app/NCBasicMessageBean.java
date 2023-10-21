package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class NCBasicMessageBean implements Comparable<NCBasicMessageBean> {

    @ApiModelProperty(name = "code", value = "人员编码")
    private String code;
    @ApiModelProperty(name = "name", value = "姓名")
    private String name;
    @ApiModelProperty(name = "photo", value = "照片")
    private String photo;
    @ApiModelProperty(name = "sex", value = "性别")
    private String sex;
    @ApiModelProperty(name = "idtype", value = "证件类型")
    private String idtype;
    @ApiModelProperty(name = "id", value = "证件号码")
    private String id;
    @ApiModelProperty(name = "polity", value = "政治面貌")
    private String polity;
    @ApiModelProperty(name = "joinpolitydate", value = "入党时间")
    private String joinpolitydate;
    @ApiModelProperty(name = "marital", value = "婚姻状况")
    private String marital;
    @ApiModelProperty(name = "glbdef1", value = "籍贯")
    private String glbdef1;
    @ApiModelProperty(name = "joinworkdate", value = "参加工作时间")
    private String joinworkdate;

    @ApiModelProperty(name = "glbdef41", value = "加入集团时间")
    private String glbdef41;
    @ApiModelProperty(name = "glbdef101", value = "入职机施时间")
    private String glbdef101;
    @ApiModelProperty(name = "glbdef82", value = "加入子公司时间")
    private String glbdef82;
    @ApiModelProperty(name = "mobile", value = "手机号码")
    private String mobile;
    @ApiModelProperty(name = "officephone", value = "手机短号")
    private String officephone;
    @ApiModelProperty(name = "glbdef57", value = "家庭地址（通讯地址）")
    private String glbdef57;
    @ApiModelProperty(name = "glbdef81", value = "户口所在地（户口地址）")
    private String glbdef81;

    private String begindate;
    private String titletechpost;
    private String pk_post;
    private String glbdef43;
    private String glbdef99;
    private String glbdef37;
    private String pk_job;

    private String edu;
    private String pk_degree;
    private String glbdef16;
    private String glbdef19;
    private String prof;
    private String glbdef24;

    private String glbdef49;
    private String glbdef50;
    private String glbdef51;

    private String deptid;
    private String deptname;
    private String salary;
    private String joinGroupTime;

    private int longTime;

    @Override
    public int compareTo(NCBasicMessageBean o) {
        return o.getLongTime() - this.longTime;
    }


}