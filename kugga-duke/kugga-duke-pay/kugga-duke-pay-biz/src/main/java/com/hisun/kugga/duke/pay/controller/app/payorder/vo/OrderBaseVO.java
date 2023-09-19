package com.hisun.kugga.duke.pay.controller.app.payorder.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

import static com.hisun.kugga.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 订单 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class OrderBaseVO {

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "订单类型【1、创建公会；2、公会认证；3、写推荐报告；4、发起聊天】")
    private String orderType;

    @ApiModelProperty(value = "交易人(用户ID)", required = true)
    @NotNull(message = "交易人(用户ID)不能为空")
    private Long userId;

    @ApiModelProperty(value = "入账人（用户ID或者公会ID）", required = true)
    @NotNull(message = "入账人（用户ID或者公会ID）不能为空")
    private Long enterId;

    @ApiModelProperty(value = "入账类型【1、用户 2、公会】")
    private String enterType;

    @ApiModelProperty(value = "交易金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "完成时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date completeTime;

    @ApiModelProperty(value = "交易状态: U-初始,S-成功,F-失败,R-退款", required = true)
    @NotNull(message = "交易状态: U-初始,S-成功,F-失败,R-退款不能为空")
    private String status;

    @ApiModelProperty(value = "备注")
    private String remark;

}
