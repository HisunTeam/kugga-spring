package com.hisun.kugga.duke.league.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author zuocheng
 */
@ApiModel("创建公会 Request VO")
@Data
public class LeagueCreateReqVO {
    @ApiModelProperty(value = "内部订单号", required = true, example = "231212454365436")
    @NotBlank(message = "内部订单号不能为空")
    private String appOrderNo;

    @ApiModelProperty(value = "密码", required = true, example = "admin123")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "公钥", required = true, example = "FJADPQJ34JJDP23I4JF23FDJAKFA")
    @NotBlank(message = "公钥不能为空")
    private String publicKey;
}
