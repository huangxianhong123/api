package com.liuc.server.api.bean.app;


import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class BxSPResult {

	private String actid;

	private String actname;
	private List<BxSPDetResult> bxdetret;
	

}
