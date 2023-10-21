package com.liuc.server.api.bean.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author S.Yang
 * @date 2021/2/24 16:40
 */
@Data
@ApiModel
public class BxBillInfoAndErsBean {
    @ApiModelProperty(value="报销单头")
    private BxBillInfoBean er_bxzb;

    @ApiModelProperty(value="交通费用子表")
    private List<BxErBusitemBean> er_busitem;

    @ApiModelProperty(value="冲销子表")
    private List<ErBxcontrastBean> er_bxcontrast;

    @ApiModelProperty(value="住宿子表")
    private List<ErBxcontrastBean> other;

    @ApiModelProperty(value="餐费子表")
    private List<ErBxcontrastBean> zsitem;

    @ApiModelProperty(value="误餐补贴子表")
    private List<ErBxcontrastBean> bzitem;

}
