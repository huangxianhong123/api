package com.liuc.server.api.bean.app;


import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel
public class BxResult {
	String result ;
	String message;
	String billcode;

	
}
