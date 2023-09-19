package com.hisun.kugga.duke.pay.controller.app.userbill.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel("用户账单 Response VO")
@Data
public class UserBillPageRspVO {

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "交易类型")
    private String remark;

    @ApiModelProperty(value = "交易金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "手续费")
    private BigDecimal fee;

    @ApiModelProperty(value = "账单状态: I-初始,W-在途,S-成功,F-失败")
    private String status;
}
