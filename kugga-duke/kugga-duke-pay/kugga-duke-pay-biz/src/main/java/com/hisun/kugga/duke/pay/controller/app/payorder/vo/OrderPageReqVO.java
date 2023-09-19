package com.hisun.kugga.duke.pay.controller.app.payorder.vo;

import com.hisun.kugga.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

import static com.hisun.kugga.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@ApiModel("管理后台 - 订单分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OrderPageReqVO extends PageParam {

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "订单类型【1、创建公会；2、公会认证；3、写推荐报告；4、发起聊天】")
    private String orderType;

    @ApiModelProperty(value = "交易人(用户ID)")
    private Long userId;

    @ApiModelProperty(value = "入账人（用户ID或者公会ID）")
    private Long enterId;

    @ApiModelProperty(value = "入账类型【1、用户 2、公会】")
    private String enterType;

    @ApiModelProperty(value = "交易金额")
    private BigDecimal amount;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "开始完成时间")
    private Date beginCompleteTime;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "结束完成时间")
    private Date endCompleteTime;

    @ApiModelProperty(value = "交易状态: U-初始,S-成功,F-失败,R-退款")
    private String status;

    @ApiModelProperty(value = "备注")
    private String remark;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "开始创建时间")
    private Date beginCreateTime;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "结束创建时间")
    private Date endCreateTime;

}
