package com.hisun.kugga.duke.pay.controller.app.leaguebill.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@ApiModel("管理后台 - 公会账单更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LeagueBillUpdateReqVO extends LeagueBillBaseVO {

    @ApiModelProperty(value = "交易id", required = true)
    @NotNull(message = "交易id不能为空")
    private Long id;

}
