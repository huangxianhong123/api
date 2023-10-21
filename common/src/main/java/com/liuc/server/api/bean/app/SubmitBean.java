package com.liuc.server.api.bean.app;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class SubmitBean {


	private String pk_group;
	private String userid;
	private String sign;
	private long timestamp;
	@ApiModelProperty(value = "head")
	private SubmitHeadBean head;
	
}
