package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class BasicMessageBean implements Comparable<BasicMessageBean> {

    @ApiModelProperty(name = "staffCode", value = "人员编码")
    private String staffCode;
    @ApiModelProperty(name = "name", value = "姓名")
    private String name;
    @ApiModelProperty(name = "photo", value = "照片")
    private String photo;
    @ApiModelProperty(name = "gender", value = "性别")
    private String gender;
    @ApiModelProperty(name = "identityType", value = "证件类型")
    private String identityType;
    @ApiModelProperty(name = "identityNo", value = "证件号码")
    private String identityNo;
    @ApiModelProperty(name = "politicsStatus", value = "政治面貌")
    private String politicsStatus;
    @ApiModelProperty(name = "joinTime", value = "入党时间")
    private String joinTime;
    @ApiModelProperty(name = "marriage", value = "婚姻状况")
    private String marriage;
    @ApiModelProperty(name = "nativePlace", value = "籍贯")
    private String nativePlace;
    @ApiModelProperty(name = "enterTime", value = "参加工作时间")
    private String enterTime;

    @ApiModelProperty(name = "joinGroupTime", value = "加入集团时间")
    private String joinGroupTime;
    @ApiModelProperty(name = "entryTime", value = "入职机施时间")
    private String entryTime;
    @ApiModelProperty(name = "joinSubsidiaryTime", value = "加入子公司时间")
    private String joinSubsidiaryTime;
    @ApiModelProperty(name = "phone", value = "手机号码")
    private String phone;
    @ApiModelProperty(name = "phoneCornet", value = "手机短号")
    private String phoneCornet;
    @ApiModelProperty(name = "homeAddress", value = "家庭地址（通讯地址）")
    private String homeAddress;
    @ApiModelProperty(name = "domicilePlace", value = "户口所在地（户口地址）")
    private String domicilePlace;

    private String titletechpost;
    private String pk_post;
    private String begindate;
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


    private int longTime;

    @Override
    public int compareTo(BasicMessageBean o){
        return o.getLongTime() - this.longTime;
    }
}
