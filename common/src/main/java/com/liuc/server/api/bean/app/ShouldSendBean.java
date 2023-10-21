package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class ShouldSendBean {

    @ApiModelProperty(name = "postSalary", value = "岗位职级工资")
    private String postSalary;//f_79
    @ApiModelProperty(name = "sickLeaveSalary", value = "病假工资")
    private String sickLeaveSalary;//f_183
    @ApiModelProperty(name = "maternityLeaveSalary", value = "产事假工资")
    private String maternityLeaveSalary;//f_184
    @ApiModelProperty(name = "acationSalary", value = "假期工资")
    private String acationSalary;//f_182
    @ApiModelProperty(name = "dimissionSalary", value = "离岗退养工资")
    private String dimissionSalary;//f_96
    @ApiModelProperty(name = "overtimeSalary", value = "加班工资")
    private String overtimeSalary;//f_181
    @ApiModelProperty(name = "otherSalary", value = "其他工资")
    private String otherSalary;//f_190
    @ApiModelProperty(name = "senioritySalary", value = "工龄补贴")
    private String senioritySalary;//f_28
    @ApiModelProperty(name = "hyperthermiaSalary", value = "高温补贴")
    private String hyperthermiaSalary;//f_29
    @ApiModelProperty(name = "messageSalary", value = "通讯补贴")
    private String messageSalary;//f_126
    @ApiModelProperty(name = "trafficSalary", value = "交通补贴")
    private String trafficSalary;//f_31
    @ApiModelProperty(name = "positionSubsidy", value = "岗位职务补贴")
    private String positionSubsidy;//f_124
    @ApiModelProperty(name = "safetySubsidy", value = "安全补贴")
    private String safetySubsidy;//f_33
    @ApiModelProperty(name = "constructorSubsidy", value = "建造师补贴")
    private String constructorSubsidy;//f_128
    @ApiModelProperty(name = "stationedAbroadSubsidy", value = "驻外临时")
    private String stationedAbroadSubsidy;//f_36
    @ApiModelProperty(name = "temporarySubsidy", value = "临时补贴（个人）")
    private String temporarySubsidy;//f_272
    @ApiModelProperty(name = "otherSubsidy", value = "其他补贴（个人）")
    private String otherSubsidy;//f_38
    @ApiModelProperty(name = "overtimeSubsidy", value = "加班加点预效毅然益奖")
    private String overtimeSubsidy;//f_267
    @ApiModelProperty(name = "oneBonus", value = "其他奖金1（个人）")
    private String oneBonus;//f_286
    @ApiModelProperty(name = "twoBonus", value = "其他奖金2（个人）")
    private String twoBonus;//f_289
    @ApiModelProperty(name = "threeBonus", value = "其他奖金3(个人)")
    private String threeBonus;//f_291
    @ApiModelProperty(name = "fourBonus", value = "其他奖金4（个人）")
    private String fourBonus;//f_293
    @ApiModelProperty(name = "fiveBonus", value = "其他奖金5（个人）")
    private String fiveBonus;//没有
    @ApiModelProperty(name = "yingFaSalary", value = "员工月度应发工资")
    private String yingFaSalary;//f_76
    @ApiModelProperty(name = "providentFund", value = "企业公积金")
    private String providentFund;//f_198


}
