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
public class PayOrderExcelVO {

    @ExcelProperty(value = "订单号")
    @ColumnWidth(30)
    private String appOrderNo;

    @ExcelProperty(value = "子订单号")
    @ColumnWidth(30)
    private Long id;

    @ExcelProperty(value = "交易时间")
    @ColumnWidth(30)
    private Date createTime;

    @ExcelProperty(value = "支付金额")
    @ColumnWidth(30)
    private BigDecimal payAmount;

    @ExcelProperty(value = "支付方式")
    @ColumnWidth(30)
    private String payChannel;

    @ExcelProperty(value = "订单状态")
    @ColumnWidth(30)
    private String status;

    @ExcelProperty(value = "交易类型")
    @ColumnWidth(30)
    private String orderType;

}
