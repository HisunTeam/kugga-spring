package com.hisun.kugga.duke.pay.controller.app.userbill.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 用户账单 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class UserBillBaseVO {

    @ApiModelProperty(value = "账单号")
    private String billNo;

    @ApiModelProperty(value = "钱包订单号")
    private String walletOrderNo;

    @ApiModelProperty(value = "用户ID", required = true)
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @ApiModelProperty(value = "账单金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "账单状态: I-初始,W-在途,S-成功,F-失败", required = true)
    @NotNull(message = "账单状态: I-初始,W-在途,S-成功,F-失败不能为空")
    private String status;

    @ApiModelProperty(value = "备注")
    private String remark;

}
