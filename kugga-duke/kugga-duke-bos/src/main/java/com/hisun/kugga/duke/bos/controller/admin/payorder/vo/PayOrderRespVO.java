package com.hisun.kugga.duke.bos.controller.admin.payorder.vo;

import com.hisun.kugga.duke.enums.OrderType;
import com.hisun.kugga.duke.enums.PayChannel;
import com.hisun.kugga.duke.enums.PayOrderStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@ApiModel("管理后台 - 支付订单 Response VO")
@Data
public class PayOrderRespVO {

    @ApiModelProperty(value = "内部订单号")
    private String appOrderNo;

    @ApiModelProperty(value = "订单类型")
    private OrderType orderType;

    @ApiModelProperty(value = "支付方式")
    private PayChannel payChannel;

    @ApiModelProperty(value = "交易金额")
    private BigDecimal payAmount;

    @ApiModelProperty(value = "交易状态")
    private PayOrderStatus status;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "子订单")
    private List<PaySubOrderVO> children;

}
