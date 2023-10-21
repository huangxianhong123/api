package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class SalaryListBean implements Comparable<SalaryListBean>{

    @ApiModelProperty(name = "title", value = "标题")
    private String title;
    @ApiModelProperty(name = "salary", value = "发放时间")
    private String createTime;
    @ApiModelProperty(name = "year", value = "年")
    private String year;
    @ApiModelProperty(name = "month", value = "月")
    private String month;


    private int longTime;

    @Override
    public int compareTo(SalaryListBean o){
        return o.getLongTime() - this.longTime;
    }
}
