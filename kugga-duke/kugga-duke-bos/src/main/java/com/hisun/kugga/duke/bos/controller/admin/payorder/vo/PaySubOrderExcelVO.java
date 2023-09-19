package com.hisun.kugga.duke.bos.controller.admin.payorder.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel("管理后台 - 支付订单 Response VO")
@Data
public class PaySubOrderExcelVO {

    @ExcelProperty(value = "子订单号")
    private Long id;

    @ExcelProperty(value = "内部订单号")
    private String appOrderNo;

    @ExcelProperty(value = "订单类型")
    private String orderType;

    @ExcelProperty(value = "支付方式【balance：余额支付，paypal：paypal支付】")
    private String payChannel;

    @ExcelProperty(value = "交易金额")
    private BigDecimal payAmount;

    @ExcelProperty(value = "交易状态")
    private String status;

    @ExcelProperty(value = "创建时间")
    private Date createTime;


}
