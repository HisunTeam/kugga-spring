package com.hisun.kugga.duke.bos.controller.admin.payorder.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付订单 Excel VO
 *
 * @author 芋道源码
 */
@Data
public class ChargeWithdrawOrderExcelVO {

    @ExcelProperty(value = "订单号")
    @ColumnWidth(30)
    private String appOrderNo;

    @ExcelProperty(value = "交易时间")
    @ColumnWidth(30)
    private Date createTime;

    @ExcelProperty(value = "交易金额")
    @ColumnWidth(30)
    private BigDecimal amount;

    @ExcelProperty(value = "交易方式")
    @ColumnWidth(30)
    private String channel;

    @ExcelProperty(value = "交易状态")
    @ColumnWidth(30)
    private String status;

    @ExcelProperty(value = "交易类型")
    @ColumnWidth(30)
    private String orderType;

}
