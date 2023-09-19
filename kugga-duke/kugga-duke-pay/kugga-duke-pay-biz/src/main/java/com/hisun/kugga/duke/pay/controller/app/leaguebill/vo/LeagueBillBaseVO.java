package com.hisun.kugga.duke.pay.controller.app.leaguebill.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 公会账单 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class LeagueBillBaseVO {

    @ApiModelProperty(value = "账单号")
    private String billNo;

    @ApiModelProperty(value = "钱包订单号")
    private String walletOrderNo;

    @ApiModelProperty(value = "公会ID", required = true)
    @NotNull(message = "公会ID不能为空")
    private Long leagueId;

    @ApiModelProperty(value = "交易金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "账单状态: U-初始,W-在途,S-成功,F-失败", required = true)
    private String status;

    @ApiModelProperty(value = "备注")
    private String remark;

}
