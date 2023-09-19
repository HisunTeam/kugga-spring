package com.hisun.kugga.duke.league.vo.task;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-07-22 15:37
 */
@Data
public class BaseTaskCreateVO {

    @ApiModelProperty(value = "内部订单号")
    private String appOrderNo;
    @ApiModelProperty(value = "支付密码")
    private String password;
    @ApiModelProperty(value = "公钥")
    private String publicKey;
}
