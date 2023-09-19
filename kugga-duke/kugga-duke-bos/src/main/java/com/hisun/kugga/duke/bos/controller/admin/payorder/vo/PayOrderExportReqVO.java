package com.hisun.kugga.duke.bos.controller.admin.payorder.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static com.hisun.kugga.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@ApiModel(value = "管理后台 - 支付订单 Excel 导出 Request VO", description = "参数和 PayOrderPageReqVO 是一致的")
@Data
public class PayOrderExportReqVO {

    @ApiModelProperty(value = "内部订单号")
    private String appOrderNo;

    @ApiModelProperty(value = "订单类型")
    private String orderType;


    @ApiModelProperty(value = "交易状态")
    private String status;


    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "开始创建时间")
    @NotNull(message = "时间范围不能为空")
    private LocalDateTime beginCreateTime;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "结束创建时间")
    @NotNull(message = "时间范围不能为空")
    private LocalDateTime endCreateTime;

}
