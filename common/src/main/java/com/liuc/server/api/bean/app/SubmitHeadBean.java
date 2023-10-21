package com.liuc.server.api.bean.app;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class SubmitHeadBean {

	private String billcode;
	private String work;
	private String checknote;
	
}
