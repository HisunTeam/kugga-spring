package com.hisun.kugga.duke.pay.controller.app.charge.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: zhou_xiong
 */
@Data
@ApiModel("充值状态 Response VO")
public class ChargeDetailRspVO {
    @ApiModelProperty(value = "钱包订单号")
    private String orderNo;
    @ApiModelProperty(value = "充值状态")
    private String status;
}
